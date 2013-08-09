package com.tx.component.config.support.cppersister;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.tx.component.config.context.ConfigContext;
import com.tx.component.config.exception.ConfigContextInitException;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.setting.ConfigResource;
import com.tx.component.config.setting.ConfigResourceTypeEnum;
import com.tx.component.config.support.ConfigResourcePropertiesPersister;

/**
 * 文件类型属性文件持久化类<br/>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class PropertyConfigPropertiesPersister implements
        ConfigResourcePropertiesPersister {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(ConfigContext.class);
    
    /** 属性持久器的实例映射 */
    private static Map<ConfigResource, PropertyConfigPropertiesPersister> pcpPersisterMapping = new HashMap<ConfigResource, PropertyConfigPropertiesPersister>();
    
    /** 资源路径解析器 */
    private static ResourcePatternResolver resourcesLoader = new PathMatchingResourcePatternResolver();
    
    private ConfigResource configResource = new ConfigResource();
    
    /**
     * <默认构造函数>
     */
    private PropertyConfigPropertiesPersister(ConfigResource configResource) {
        super();
        this.configResource = configResource;
    }
    
    public String configPropertyTypeName() {
        return ConfigResourceTypeEnum.PROPERTY_FILE.getName();
    }
    
    /**
     * @param configResource
     * @return
     */
    public ConfigResourcePropertiesPersister newInstance(
            ConfigResource configResource) {
        PropertyConfigPropertiesPersister res = null;
        synchronized (PropertyConfigPropertiesPersister.class) {
            if (!pcpPersisterMapping.containsKey(configResource)) {
                res = new PropertyConfigPropertiesPersister(configResource);
                pcpPersisterMapping.put(configResource, res);
            } else {
                res = pcpPersisterMapping.get(configResource);
            }
        }
        return res;
    }
    
    public List<ConfigPropertyItem> load() {
        List<String> locations = this.configResource.getConfigLocations();
        List<Resource> resourceList = new ArrayList<Resource>();
        
        for (String location : locations) {
            Resource[] resources;
            try {
                resources = resourcesLoader.getResources(location);
            } catch (IOException e) {
                throw new ConfigContextInitException("资源文件路径有误");
            }
            for (Resource resource : resources) {
                if (resource.getFilename().toLowerCase().endsWith("properties")) {
                    
                    resourceList.add(resource);
                } else {
                    logger.error("资源属性配置有误,资源名为 " + resource.getFilename()
                            + "的文件对应的类型不应是property");
                }
            }
        }
        
        Properties prop = new Properties();
        
        if (resourceList != null && resourceList.size() != 0) {
            for (Resource resource : resourceList) {
                try {
                    prop.load(resource.getInputStream());
                } catch (IOException e) {
                    throw new ConfigContextInitException("Properties文件读取出现异常");
                }
            }
        }
        
        List<ConfigPropertyItem> resList = new ArrayList<ConfigPropertyItem>();
        //将读取结果放入到集合中
        for (Object key : prop.keySet()) {
            ConfigPropertyItem configPropertyItem = new ConfigPropertyItem();
            configPropertyItem.setConfigResourceId(this.configResource.getId());
            configPropertyItem.setKey(key.toString());
            configPropertyItem.setValue(prop.getProperty(key.toString()));
            configPropertyItem.setEditAble(false);
            configPropertyItem.setViewAble(true);
            configPropertyItem.setValid(true);
            
            resList.add(configPropertyItem);
        }
        return resList;
    }
    
    /**
     * 更新Properties文件中的属性
     * 如果传入属性项的源id不为空,则根据源id进行查找(查找范围较小)
     * 如果传入属性项不包含源id,则遍历所有文件查找(查找范围大)
     * @param configPropertyItem
     */
    public void update(ConfigPropertyItem configPropertyItem) {
        
        /*
        //定义一个变量来记录更改的行数
        int affectRowCount = 0;
        //判断传入属性项的源id是否为空
        if (StringUtils.isNotEmpty(configPropertyItem.getConfigResourceId())) {
            //如果不为空则通过sourceId来查找要更新的属性
            List<Resource> resourceList = sourceId2ResourceMap.get(configPropertyItem.getConfigResourceId());
            //如果找出的列表不为空
            if (CollectionUtils.isNotEmpty(resourceList)) {
                for (Resource resource : resourceList) {
                    this.updateHandler(resource, configPropertyItem);
                    affectRowCount++;
                }
            }
        }
        //如果一行记录都没有更改,说明没有找到对应需要修改的属性,则遍历全部资源文件去找
        if (affectRowCount == 0) {
            for (List<Resource> resourceList : sourceId2ResourceMap.values()) {
                if (CollectionUtils.isNotEmpty(resourceList)) {
                    for (Resource resource : resourceList) {
                        this.updateHandler(resource, configPropertyItem);
                        affectRowCount++;
                    }
                }
            }
        }
        
        //如果仍然未更新一行记录,则抛出异常
        if (affectRowCount == 0) {
            throw new ConfigContextInitException("更新操作时未找到对应key值得属性");
        }
        */
    }
    
    /**
     * 
      *根据Resource来更新Properties的属性<br/>
      *保存到原路径(替换原文件)
      *<功能详细描述>
      * @param resource
      * @param configPropertyItem [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private void updateHandler(Resource resource,
            ConfigPropertyItem configPropertyItem) {
        //创建临时属性文件
        Properties pro = new Properties();
        try {
            //从Resource中读取内容
            pro.load(resource.getInputStream());
        } catch (IOException e) {
            throw new ConfigContextInitException("Properties文件读取出现异常");
        }
        
        String key = configPropertyItem.getKey();
        String value = configPropertyItem.getValue();
        
        //将需要变更的属性设置到临时文件中
        pro.setProperty(key, value);
        
        try {
            //根据Resource得到原文件的输出流
            OutputStream out = new FileOutputStream(resource.getFile());
            
            //保存,替换原文件
            pro.store(out, null);
        } catch (FileNotFoundException e) {
            throw new ConfigContextInitException("资源文件路径有误");
        } catch (IOException e) {
            throw new ConfigContextInitException("资源文件保存时出错");
        }
    }
    
    /**
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        ConfigPropertyItem item = (ConfigPropertyItem) arg;
        if (!item.hasChanged()) {
            //TODO:另行定义异常
            //throw 
        }
    }
    
}
