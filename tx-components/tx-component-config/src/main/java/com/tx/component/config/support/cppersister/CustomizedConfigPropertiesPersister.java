package com.tx.component.config.support.cppersister;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.thoughtworks.xstream.XStream;
import com.tx.component.config.exception.ConfigContextInitException;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.setting.ConfigPropertiesSetting;
import com.tx.component.config.setting.ConfigPropertiesSettings;
import com.tx.component.config.setting.ConfigResource;
import com.tx.component.config.setting.ConfigResourceTypeEnum;
import com.tx.component.config.support.ConfigResourcePropertiesPersister;
import com.tx.core.util.XstreamUtils;

/**
 * 
  * 自定义资源类型的持久化类<br/>
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class CustomizedConfigPropertiesPersister implements
        ConfigResourcePropertiesPersister {
    
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(CustomizedConfigPropertiesPersister.class);
    
    /** 资源路径解析器 */
    private ResourcePatternResolver resourcesLoader = new PathMatchingResourcePatternResolver();
    
    /** XML文件解析器 */
    private static XStream configPropertiesParse = XstreamUtils.getXstream(ConfigPropertiesSettings.class);
    
    /** 源id到资源文件的关系Map,读取时该对象赋值 */
    private static Map<String, List<Resource>> sourceId2ResourceMap = new HashMap<String, List<Resource>>();
    
    public String configPropertyTypeName() {
        return ConfigResourceTypeEnum.CUSTOMIZED.getName();
    }
    
    public static ConfigResourcePropertiesPersister newInstance() {
        return new CustomizedConfigPropertiesPersister();
    }
    
    public List<ConfigPropertyItem> load() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void update(ConfigPropertyItem configPropertyItem) {
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
        
    }
    
    /**
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
            //configPropertiesParse
            //保存,替换原文件
            pro.store(out, null);
        } catch (FileNotFoundException e) {
            throw new ConfigContextInitException("资源文件路径有误");
        } catch (IOException e) {
            throw new ConfigContextInitException("资源文件保存时出错");
        }
        
    }
    
    /**
     * 根据配置资源设置来读取自定义文件<br/>
     * 包括XML文件
     * @param props
     * @param configResource
     */
    public void loadFromConfigResource(
            List<ConfigPropertyItem> configPropertyItems,
            ConfigResource configResource) {
        
        List<String> locations = configResource.getConfigLocations();
        List<Resource> resourceList = new ArrayList<Resource>();
        //获取资源id
        String sourceId = configResource.getId();
        
        for (String location : locations) {
            Resource[] resources;
            try {
                resources = resourcesLoader.getResources(location);
            } catch (IOException e) {
                throw new ConfigContextInitException("资源文件路径有误");
            }
            for (Resource resource : resources) {
                if (resource.getFilename().toLowerCase().endsWith("xml")) {
                    resourceList.add(resource);
                } else {
                    logger.error("资源属性配置有误,资源名为 " + resource.getFilename()
                            + "的文件对应的类型不应是customized");
                }
            }
        }
        
        //缓存源id-->资源文件列表的Map,以便更新属性时使用
        sourceId2ResourceMap.put(sourceId, resourceList);
        
        if (resourceList != null && resourceList.size() != 0) {
            for (Resource resource : resourceList) {
                //读取xml中设置项列表
                List<ConfigPropertiesSetting> result = null;
                try {
                    result = getConfigPropertiesSettingList(resource.getInputStream());
                } catch (IOException e) {
                    throw new ConfigContextInitException("xml资源文件解析错误");
                }
                
                // 创建一个Properties对象,把xml文件中读取到的属性对放入其中
                //读取到的XML文件设置项,转换为数据库设置项
                for (ConfigPropertiesSetting c : result) {
                    ConfigPropertyItem[] items = this.convertFromCps2Cpi(c);
                    for (ConfigPropertyItem item : items) {
                        configPropertyItems.add(item);
                    }
                }
            }
        }
    }
    
    /**
     * 读取xml中设置项列表<br/>
     * @param resource
     * @return
     */
    private List<ConfigPropertiesSetting> getConfigPropertiesSettingList(
            InputStream is) {
        ConfigPropertiesSettings configPropertiesSettings = null;
        logger.info("PropertiesPersister is loading properties from xml files.............");
        
        // 解析xml文件,得到配置文件集合对象
        configPropertiesSettings = (ConfigPropertiesSettings) configPropertiesParse.fromXML(is);
        // 遍历这个集合对象及其子节点,得到其中所包含的所有子节点
        List<ConfigPropertiesSetting> list = configPropertiesSettings.getConfigPropertyList();
        List<ConfigPropertiesSetting> result = new ArrayList<ConfigPropertiesSetting>();
        for (ConfigPropertiesSetting c : list) {
            getConfigPropertiesSetting(c, result);
        }
        return result;
    }
    
    /**
     * 通过迭代方式得到一个设置属性及其子元素的属性<br/>
     * 结果放入参数result中
     * 
     * @param cps
     * @param result
     */
    private void getConfigPropertiesSetting(ConfigPropertiesSetting cps,
            List<ConfigPropertiesSetting> result) {
        //将属性元素放入结果集中
        result.add(cps);
        //在遍历当前元素子节点之前先得到其key
        String parentKey = cps.getKey();
        List<ConfigPropertiesSetting> list = cps.getChilds();
        if (list != null && list.size() != 0) {
            for (ConfigPropertiesSetting cpss : list) {
                //如果key不为空则设置到子节点的parentKey属性中
                if (StringUtils.isNotEmpty(parentKey)) {
                    cpss.setParentKey(parentKey);
                }
                getConfigPropertiesSetting(cpss, result);
            }
        }
    }
    
    /**
     * 保存属性到自定义资源文件
     * @param configPropertyItem
     */
    public void saveItem(ConfigPropertyItem configPropertyItem) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 清空属性
     */
    public void cleanItems() {
        throw new ConfigContextInitException("unreachable method");
    }
    
    /**
     * 
      *将XML文件对应的实体类转换为数据库对应的实体类
      *由于一条ConfigPropertiesSetting可能包含1-2条ConfigPropertyItem
      *所以返回一个数组
      * @param cps
      * @return [参数说明]
      * 
      * @return ConfigPropertyItem[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ConfigPropertyItem[] convertFromCps2Cpi(ConfigPropertiesSetting cps) {
        List<ConfigPropertyItem> cpiList = new ArrayList<ConfigPropertyItem>();
        if (StringUtils.isNotEmpty(cps.getDefaultValue())) {
            ConfigPropertyItem cpi = new ConfigPropertyItem();
            cpi.setValue(cps.getDefaultValue());
            convertFromCpi2CpsHandler(cps, cpi);
            cpiList.add(cpi);
        }
        if (StringUtils.isNotEmpty(cps.getDevelopValue())) {
            ConfigPropertyItem cpi = new ConfigPropertyItem();
            cpi.setValue(cps.getDevelopValue());
            convertFromCpi2CpsHandler(cps, cpi);
            cpiList.add(cpi);
        }
        
        return (ConfigPropertyItem[]) cpiList.toArray();
    }
    
    /** 
     * 转换时设置出value以外的其它属性
     * @param cps
     * @param cpi [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void convertFromCpi2CpsHandler(ConfigPropertiesSetting cps,
            ConfigPropertyItem cpi) {
        cpi.setId(cps.getId());
        cpi.setConfigResourceId(cps.getResource());
        cpi.setName(cps.getName());
        cpi.setKey(cps.getKey());
        cpi.setDescription(cps.getDescription());
        cpi.setEditAble(cps.isEditAble());
        cpi.setParentKey(cps.getParentKey());
        cpi.setViewAble(cps.isViewAble());
        //cpi.setCreateDate(createDate);
        //cpi.setViewExpression(viewExpression);
        //cpi.setValidateExpression(validateExpression);
        //cpi.setSystemSourceId(systemSourceId);
        //cpi.setLastUpdateDate(lastUpdateDate);
    }
    
    /**
     * 将数据库对应的实体类转换为XML文件对应的实体类
      *可能会是1-2个数据库对应实体类转换为一个XML文件对应实体类的情况
      * @param configPropertyItems
      * @return [参数说明]
      * 
      * @return ConfigPropertiesSetting [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private ConfigPropertiesSetting convertFromCpi2Cps(
            ConfigPropertyItem configPropertyItems) {
        ConfigPropertiesSetting cps = new ConfigPropertiesSetting();
        //TODO 这里本来应该根据 configPropertyItem的DevlopValue的属性来判断其中的值是开发值还是默认值
        //但是现在没有这个属性,就直接设置
        //configPropertyItem.
        cps.setName(configPropertyItems.getName());
        cps.setKey(configPropertyItems.getKey());
        cps.setDefaultValue(configPropertyItems.getValue());
        cps.setDescription(configPropertyItems.getDescription());
        cps.setEditAble(configPropertyItems.isEditAble());
        cps.setResource(configPropertyItems.getConfigResourceId());
        cps.setViewAble(configPropertyItems.isViewAble());
        cps.setParentKey(configPropertyItems.getParentKey());
        return cps;
    }
    
    /**
     * 
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ConfigPropertyItem) {
            //TODO 当  ConfigPropertyItem中的value更新后会调用两次此方法
            //第一次传入更改前的值
            //第二次传入更改后的值
        }
    }
    
    @Override
    public ConfigResourcePropertiesPersister newInstance(
            ConfigResource configResource) {
        // TODO Auto-generated method stub
        return null;
    }
}
