/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.plugin.context;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanNameAware;

import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.configuration.model.ConfigProperty;
import com.tx.component.plugin.model.PluginInstance;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 插件容器构建器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class PluginContextBuilder extends PluginContextConfigurator
        implements BeanNameAware {
    
    /** 插件映射关联 */
    @SuppressWarnings("rawtypes")
    protected Map<String, Plugin> pluginMap = new HashMap<>();
    
    @SuppressWarnings("rawtypes")
    protected Map<Class<? extends Plugin>, Plugin> pluginTypeMap = new HashMap<>();
    
    /**
     * @throws Exception
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void doBuild() throws Exception {
        //加载基础数据类<br/>
        Collection<Plugin> plugins = applicationContext
                .getBeansOfType(Plugin.class).values();
        
        //查询数据库中已经存在的插件实例
        List<PluginInstance> dbList = this.pluginInstanceService.queryList(null,
                (Map<String, Object>) null);
        Map<String, PluginInstance> insMap = new HashMap<>();
        dbList.stream().forEach(ins -> insMap.put(ins.getId(), ins));
        
        //通过插件实例，存储插件实例信息
        for (Plugin pluginTemp : plugins) {
            AssertUtils.isTrue(!pluginMap.containsKey(pluginTemp.getId()),
                    "plugin is duplicate.pluginId:{}",
                    pluginTemp.getId());
            Class<? extends Plugin> type = (Class<? extends Plugin>) AopUtils
                    .getTargetClass(pluginTemp);
            AssertUtils.isTrue(!pluginTypeMap.containsKey(type),
                    "plugin is duplicate.pluginId:{}",
                    pluginTemp.getId());
            
            PluginInstance ins = init(pluginTemp);
            logger.info(
                    "............插件容器装载插件: id:{}, catalog:{}, name:{}, version:{}, prefix:{}, installed:{}, valid:{}",
                    ins.getId(),
                    ins.getCatalog(),
                    ins.getName(),
                    ins.getVersion(),
                    ins.getPrefix(),
                    ins.isInstalled(),
                    ins.isValid());
            
            //插件装载茹映射中
            pluginMap.put(pluginTemp.getId(), pluginTemp);
            pluginTypeMap.put(type, pluginTemp);
            
            insMap.remove(ins.getId());
        }
        
        //删除已经不存在的插件
        insMap.forEach((idTemp, ins) -> {
            if (ins.isInstalled()) {
                //根据前缀卸载原插件中的所有配置
                ConfigContext.getContext().uninstall(ins.getPrefix(), null);
            }
            this.pluginInstanceService.deleteById(idTemp);
        });
    }
    
    /**
     * 初始化插件功能
     * <功能详细描述>
     * @param plugin [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private PluginInstance init(Plugin<? extends PluginConfig> plugin) {
        String pluginId = plugin.getId();
        PluginInstance ins = this.pluginInstanceService.findById(pluginId);
        PluginInstance buildIns = doBuild(plugin);
        if (ins == null) {
            //doAdd
            this.pluginInstanceService.insert(buildIns);
            ins = buildIns;
        } else {
            doUpdate(ins, buildIns);
        }
        
        //根据是否安装检查配置容器中值
        if (!ins.isInstalled()) {
            //如果未安装则无需执行后续逻辑
            //注释掉的原因为：如果未安装，在插件中根本不会装载配置容器中的值
            String enableCode = ConfigContext.getContext().code(
                    plugin.getPrefix(), plugin.getConfigEntityType(), "enable");
            ConfigProperty cp = ConfigContext.getContext().find(enableCode);
            if (cp != null
                    && StringUtils.equalsAnyIgnoreCase("true", cp.getValue())) {
                //如果配置容器中的值修改为true
                ConfigContext.getContext().patch(enableCode, "false");
            }
            return ins;
        }
        
        //如果安装了
        PluginConfig config = ConfigContext.getContext().setupConfigEntity(
                plugin.getPrefix(), plugin.getConfigEntityType());
        //但是未生效
        if (!ins.isValid()) {
            //如果未安装则无需执行后续逻辑
            //注释掉的原因为：如果未安装，在插件中根本不会装载配置容器中的值
            AssertUtils.notNull(config, "config is null;");
            if (config.isEnable() == true) {
                //如果配置容器中的值修改为true
                config.setEnable(false);
            }
            return ins;
        }
        
        //装载配置实例，根据插件获取配置类型
        //PluginConfig config = plugin.getConfig();
        //如果配置值为未启用
        if (!plugin.validate()) {
            //如果验证不通过，则马上标定为无效
            ins.setValid(false);
            this.pluginInstanceService.updateById(ins);
            if (config.isEnable() == true) {
                //如果配置容器中的值修改为true
                config.setEnable(false);
            }
        }
        return ins;
    }
    
    /** 
     * 如果对象需要被更新，则进行更新操作<br/>
     * <功能详细描述>
     * @param ins
     * @param buildIns [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void doUpdate(PluginInstance ins, PluginInstance buildIns) {
        if (!StringUtils.equals(ins.getVersion(), buildIns.getVersion())
                || !StringUtils.equals(ins.getPrefix(), buildIns.getPrefix())) {
            ins.setAuthor(buildIns.getAuthor());
            ins.setName(buildIns.getName());
            ins.setCatalog(buildIns.getCatalog());
            ins.setRemark(buildIns.getRemark());
            ins.setPriority(buildIns.getPriority());
            
            //版本号变化、前缀变化、归属模块变化  需要特殊处理，需要标定插件为未安装，需要重新安装
            ins.setVersion(buildIns.getVersion());
            ins.setPrefix(buildIns.getPrefix());
            ins.setValid(false);
            ins.setInstalled(false);
            
            //更新插件
            this.pluginInstanceService.updateById(ins);
        } else if (!StringUtils.equals(ins.getAuthor(), buildIns.getAuthor())
                || !StringUtils.equals(ins.getName(), buildIns.getName())
                || !StringUtils.equals(ins.getRemark(), buildIns.getRemark())
                || !StringUtils.equals(ins.getCatalog(), buildIns.getCatalog())
                || ins.getPriority() != buildIns.getPriority()) {
            ins.setAuthor(buildIns.getAuthor());
            ins.setName(buildIns.getName());
            ins.setCatalog(buildIns.getCatalog());
            ins.setRemark(buildIns.getRemark());
            ins.setPriority(buildIns.getPriority());
            
            //更新插件
            this.pluginInstanceService.updateById(ins);
        }
    }
    
    /**
     * 构建插件实例对象<br/>
     * <功能详细描述>
     * @param plugin
     * @return [参数说明]
     * 
     * @return PluginInstance [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private PluginInstance doBuild(Plugin<?> plugin) {
        PluginInstance pi = new PluginInstance();
        pi.setId(plugin.getId());
        pi.setAuthor(plugin.getAuthor());
        
        pi.setCatalog(plugin.getCatalog());
        pi.setName(plugin.getName());
        pi.setVersion(plugin.getVersion());
        pi.setPrefix(plugin.getPrefix());
        pi.setPriority(plugin.getPriority());
        pi.setRemark(plugin.getRemark());
        
        pi.setValid(false);
        pi.setInstalled(false);
        
        Date now = new Date();
        pi.setCreateDate(now);
        pi.setLastUpdateDate(now);
        return pi;
    }
}
