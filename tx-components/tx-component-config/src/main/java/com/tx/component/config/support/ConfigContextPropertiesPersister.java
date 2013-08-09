/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-10-16
 * <修改描述:>
 */
package com.tx.component.config.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PropertiesPersister;

import com.thoughtworks.xstream.XStream;
import com.tx.component.config.context.ConfigContextConfigurer;
import com.tx.component.config.exception.ConfigContextInitException;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.service.ConfigPropertyItemService;
import com.tx.component.config.setting.ConfigContextCfg;
import com.tx.component.config.setting.ConfigResource;
import com.tx.component.config.setting.ConfigResourceTypeEnum;
import com.tx.core.util.ClassScanUtils;
import com.tx.core.util.XstreamUtils;

/**
 * 配置容器配置属性持久器 <br/>
 * <功能详细描述>
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConfigContextPropertiesPersister implements PropertiesPersister {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ConfigContextConfigurer.class);
    
    /** 配置容器基础配置文件解析器 */
    private static XStream configContextParse = XstreamUtils.getXstream(ConfigContextCfg.class);
    
    /**
     * 属性解析器与配置资源间的映射
     */
    private Map<String, ConfigResourcePropertiesPersister> configResource2PersisterMapping = new HashMap<String, ConfigResourcePropertiesPersister>();
    
    /**配置属性项处理业务层*/
    private ConfigPropertyItemService configPropertyItemService;
    
    /**
     * <默认构造函数>
     */
    public ConfigContextPropertiesPersister(
            ConfigPropertyItemService configPropertyItemService) {
        logger.info("   配置容器初始化:  初始化配置容器配置文件解析器.");
        
        this.configPropertyItemService = configPropertyItemService;
        logger.info("   配置文件初始化:  注册系统默认的配置资源解析器.");
        //根据枚举型注册配置属性类型对应的解析器
        ConfigResourceTypeEnum[] crts = ConfigResourceTypeEnum.values();
        registeConfigResourceTypePersister(crts);
    }
    
    /**
      * 根据配置资源类型注册系统默认的配置资源解析器<br/>
      *<功能详细描述>
      * @param crts [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void registeConfigResourceTypePersister(
            ConfigResourceTypeEnum[] crts) {
        if (ArrayUtils.isEmpty(crts)) {
            return;
        }
        
        for (ConfigResourceTypeEnum configResourceType : crts) {
            logger.info("   >>> 注册默认配置资源解析器:  解析器类型名{},解析器类{}",
                    new Object[] { configResourceType.getName(),
                            configResourceType.getPersister() });
            configResource2PersisterMapping.put(configResourceType.getName(),
                    configResourceType.getPersister());
        }
    }
    
    /**
     * 读取总配置文件获取所有属性
     * @param props
     * @param is
     * @throws IOException
     */
    public void loadFromXml(Properties prop, InputStream is) throws IOException {
        //读取配置容器配置文件
        logger.info("   >>>配置容器加载:加载配置容器配置.");
        ConfigContextCfg configContextCfg = (ConfigContextCfg) configContextParse.fromXML(is);
        
        //读取配置容器配置属性
        logger.info("   >>>配置容器加载:  根据配置加载客户自定义的配置资源持久器.");
        String configResourcePersisterPackage = configContextCfg.getConfigResourcePersisterPackage();
        //加载自定义配置资源解析器
        registeConfigResourceTypePersister(configResourcePersisterPackage);
        
        logger.info("   >>>配置容器加载:  根据配置的配置资源,加载具体的配置项.");
        //配置资源集合
        Set<ConfigResource> configResourceSet = configContextCfg.getConfigResources();
        //如果没有配置配置资源集合,则完成加载
        if (CollectionUtils.isEmpty(configResourceSet)) {
            return;
        }
        
        //根据配置资源加载对应配置属性
        logger.info("   >>>配置容器加载: 开始根据配置资源类型逐一加载配置属性.");
        for (ConfigResource configResource : configResourceSet) {
            loadFromConfigResource(configResource);
        }
        
        ////将属性列表中的属性放入到Properties中
        //this.putConfigPropertyItem2Prop(configPropertyItems, prop);
        //将属性列表中的属性存储到DB中
        //this.putConfigPropertyItem2DB(configPropertyItems);
        
    }
    
    /**
     * 根据配置注册自定义的配置属性解析器<br/>
     *<功能详细描述>
     * @param configResourcePersisterPackage
     * @throws InstantiationException
     * @throws IllegalAccessException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void registeConfigResourceTypePersister(
            String configResourcePersisterPackage) {
        //如果没有配置,或配置非法
        if (StringUtils.isEmpty(configResourcePersisterPackage)) {
            return;
        }
        
        //获取到配置基础包,加载对应的解析器
        Set<Class<? extends ConfigResourcePropertiesPersister>> cppClassSet = ClassScanUtils.scanByParentClass(ConfigResourcePropertiesPersister.class,
                configResourcePersisterPackage);
        
        //如果芥子气不存在
        if (CollectionUtils.isEmpty(cppClassSet)) {
            return;
        }
        
        for (Class<? extends ConfigResourcePropertiesPersister> cppClassTemp : cppClassSet) {
            //加入判断,如果是接口的话就移除(因为上边的方法默认会把接口也扫描进来,所以需要此判断)
            if (!cppClassTemp.isInterface()) {
                ConfigResourcePropertiesPersister cppTemp;
                try {
                    cppTemp = cppClassTemp.newInstance();
                    
                    //TODO:根据设计进行调整
                    if (cppTemp.configPropertyTypeName() == null) {
                        continue;
                    }
                    logger.info("   >>>加客户自定义的配置资源持久器成功.解析器类型名{},解析器类{}",
                            new Object[] { cppTemp.configPropertyTypeName(),
                                    cppTemp });
                    configResource2PersisterMapping.put(cppTemp.configPropertyTypeName(),
                            cppTemp);
                } catch (InstantiationException e) {
                    throw new ConfigContextInitException(
                            "   >>>   加客户自定义的配置资源持久器异常:{},自定义解析器类为:{}",
                            new Object[] { e.toString(), cppClassTemp.getName() });
                } catch (IllegalAccessException e) {
                    throw new ConfigContextInitException(
                            "   >>>   加客户自定义的配置资源持久器异常:{},自定义解析器类为:{}",
                            new Object[] { e.toString(), cppClassTemp.getName() });
                }
            }
        }
    }
    
    /**
      *读取单个配置资源设置<br/>
      *<功能详细描述>
      * @param props
      * @param is [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private List<ConfigPropertyItem> loadFromConfigResource(
            ConfigResource configResource) {
        //解析配置资源设置文件为对象
        
        List<ConfigPropertyItem> resList = null;
        //遍历资源类型枚举判断是那种资源类型,并找出对应的持久化类
        if (configResource2PersisterMapping.containsKey(configResource.getType())) {
            //得到当前持久对象
            ConfigResourcePropertiesPersister curConfigPropertiesPersister = configResource2PersisterMapping.get(configResource.getType());
            resList = curConfigPropertiesPersister.load();
            logger.info("   >>>配置容器加载: 加载资源类型为:{}的配置资源,并为配置项添加观察者成功",
                    configResource.getType());
            for(ConfigPropertyItem itemTemp : resList){
                itemTemp.addObserver(curConfigPropertiesPersister);
            }
        } else {
            throw new ConfigContextInitException(
                    "   >>>配置容器加载: 加载资源类型为:{}的配置资源失败.找不到对应配置资源类型加载器",
                    configResource.getType());
        }
        return resList;
        
    }
    
    @Deprecated
    public void load(Properties props, InputStream is) throws IOException {
        throw new ConfigContextInitException("不应到达的方法");
    }
    
    @Deprecated
    public void load(Properties props, Reader reader) throws IOException {
        throw new ConfigContextInitException("不应到达的方法");
    }
    
    @Deprecated
    public void store(Properties props, OutputStream os, String header)
            throws IOException {
        throw new ConfigContextInitException("不应到达的方法");
    }
    
    @Deprecated
    public void store(Properties props, Writer writer, String header)
            throws IOException {
        throw new ConfigContextInitException("不应到达的方法");
    }
    
    @Deprecated
    public void storeToXml(Properties props, OutputStream os, String header)
            throws IOException {
        throw new ConfigContextInitException("不应到达的方法");
    }
    
    @Deprecated
    public void storeToXml(Properties props, OutputStream os, String header,
            String encoding) throws IOException {
        throw new ConfigContextInitException("不应到达的方法");
    }
    
}
