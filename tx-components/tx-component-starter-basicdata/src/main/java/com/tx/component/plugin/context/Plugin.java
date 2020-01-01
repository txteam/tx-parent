/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.plugin.context;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.configuration.context.ConfigContext;
import com.tx.component.plugin.model.PluginInstance;
import com.tx.component.plugin.service.PluginInstanceService;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 插件接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class Plugin<CONFIG extends PluginConfig>
        implements InitializingBean, ApplicationContextAware {
    
    /** spring容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** 插件实例业务层 */
    protected PluginInstanceService pluginInstanceService;
    
    /** 配置类型 */
    protected Class<CONFIG> configEntityType;
    
    /** 插件实例业务层 */
    protected PluginInstance pluginInstance;
    
    /** 获取插件配置 */
    protected CONFIG config;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /** <默认构造函数> */
    @SuppressWarnings("unchecked")
    public Plugin() {
        super();
        
        ResolvableType rt = ResolvableType.forClass(this.getClass());
        ResolvableType superType = rt;
        while (!Plugin.class.equals(superType.resolve())
                && !Object.class.equals(superType.resolve())) {
            superType = superType.getSuperType();
            AssertUtils.notTrue(Object.class.equals(superType.resolve()),
                    "superType is Object.");
        }
        this.configEntityType = (Class<CONFIG>) superType.getGeneric(0)
                .resolve();
    }
    
    /**
     * 获取配置实例类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<CONFIG> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<CONFIG> getConfigEntityType() {
        return configEntityType;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.pluginInstanceService = this.applicationContext
                .getBean(PluginInstanceService.class);
        
    }
    
    /**
     * 插件ID<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getId() {
        return getClass().getAnnotation(Component.class).value();
    }
    
    /**
     * 获取插件配置对应配置容器中的前缀<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getPrefix() {
        return "plugin." + getId();
    }
    
    /**
     * 获取插件分类<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract String getCatalog();
    
    /**
     * 插件版本<br/>
     *    当配置参数产生变化时，应该修改该值，保证插件启动期间自动更新为待安装
     *    需要重新安装，配置，启用
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract String getVersion();
    
    /**
     * 插件名称<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract String getName();
    
    /**
     * 获取插件描述<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getRemark() {
        return "";
    }
    
    /**
     * 获取插件优先级<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int getPriority() {
        return 0;
    }
    
    /**
     * 获取插件实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PluginInstance [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PluginInstance getPluginInstance() {
        if (this.pluginInstance == null) {
            this.pluginInstance = this.pluginInstanceService.findById(getId());
        }
        return this.pluginInstance;
    }
    
    /**
     * 插件是否安装<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isInstalled() {
        boolean installed = this.getPluginInstance().isInstalled();
        return installed;
    }
    
    /**
     * 获取插件配置<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PROS [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public CONFIG getConfig() {
        if (this.isInstalled() && this.config == null) {
            this.config = ConfigContext.getContext()
                    .setupConfigEntity(getPrefix(), this.configEntityType);
            return this.config;
        }else if(this.config != null){
            return this.config;
        }else{
            try {
                CONFIG config = this.configEntityType.newInstance();
                return config;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new SILException("实例化配置实体类型失败.可能为不存在可访问的无参构造函数。");
            }
        }
    }
    
    /**
     * 验证配置<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean validate() {
        CONFIG config = getConfig();
        if (config == null) {
            return false;
        }
        return true;
    }
    
    /**
     * 获取插件作者<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getAuthor() {
        return "txteam";
    }
    
    /**
     * 将插件进行安装<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean install() {
        boolean flag = this.pluginInstanceService.install(getId());
        
        //装载初始化配置
        PluginConfig config = ConfigContext.getContext()
                .setupConfigEntity(getPrefix(), this.configEntityType);
        config.setEnable(true);
        
        this.pluginInstance = null;
        return flag;
    }
    
    /**
     * 卸载插件，并设置启动为false<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean uninstall() {
        PluginConfig config = getConfig();
        config.setEnable(false);
        ConfigContext.getContext().uninstall(getPrefix(), configEntityType);
        
        boolean flag = this.pluginInstanceService.uninstall(getId());
        this.pluginInstance = null;
        return flag;
    }
    
    /**
     * 启用插件<br/>
     * <功能详细描述>
     * @param requestMap
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enable() {
        if (!validate()) {
            //插件配置验证不通过，不能被启用
            return false;
        }
        
        //启用插件
        boolean flag = this.pluginInstanceService.enableById(getId());
        //装载初始化配置
        PluginConfig config = getConfig();
        config.setEnable(true);
        
        this.pluginInstance = null;
        return flag;
    }
    
    /**
     * 禁用插件<br/>
     * <功能详细描述>
     * @param requestMap
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean disable() {
        //启用插件
        boolean flag = this.pluginInstanceService.disableById(getId());
        //装载初始化配置
        PluginConfig config = getConfig();
        config.setEnable(false);
        
        this.pluginInstance = null;
        return flag;
    }
    
    /**
     * 修改配置值<br/>
     * <功能详细描述>
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean setting(Map<String, String> params) {
        PluginConfig config = getConfig();
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(config);
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                continue;
            }
            
            String property = pd.getName();
            if (!params.containsKey(property)) {
                continue;
            }
            bw.setPropertyValue(property, params.get(property));
        }
        
        this.pluginInstance = null;
        return true;
    }
    
}
