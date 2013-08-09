package com.tx.component.config.support.cppersister;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.service.ConfigPropertyItemService;
import com.tx.component.config.setting.ConfigResource;
import com.tx.component.config.support.ConfigResourcePropertiesPersister;

/**
 * 
  * 数据库资源属性文件持久化类<br/>
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class DatabaseConfigPropertiesPersister implements
        ConfigResourcePropertiesPersister {
    /** 日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(DatabaseConfigPropertiesPersister.class);
    
    /** dao接口类  */
    private ConfigPropertyItemService configPropertyItemService = new ConfigPropertyItemService();
    
    /**
     * 返回当前持久化类名称
     * @return
     */
    public String configPropertyTypeName() {
        return "database";
    }
    
    public ConfigResourcePropertiesPersister newInstance() {
        return new DatabaseConfigPropertiesPersister();
    }
    
    public List<ConfigPropertyItem> load() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void update(ConfigPropertyItem configPropertyItem) {

        //如果存在key重复的情况下,这里的更新操作将会把key相同的所有属性都更新
        
        //先根据key查询出需要更新的项
        List<ConfigPropertyItem> configPropertyItems = configPropertyItemService.queryConfigPropertyItemList(configPropertyItem);
        //遍历查询出来的项,对其value值重新设定后保存到数据库中
        for(ConfigPropertyItem c : configPropertyItems){
            c.setValue(configPropertyItem.getValue());
            configPropertyItemService.updateById(c);
            logger.info("one property has been updated ...");
        }  
    }
    
    /**
     * 根据配置资源设置来读取database
     * @param props
     * @param configResource
     */
    public void loadFromConfigResource(List<ConfigPropertyItem> configPropertyItems,
            ConfigResource configResource) {
        
        //得到是否开发模式和是否重复模式信息
        //ConfigContextCfg configContextCfg = ConfigContext.getPropertiesPersister().getConfigContextCfg();
        //boolean isDevelop = configContextCfg.isDevelop();
        //boolean isRepeat = configContextCfg.isRepeatAble();
        
        //从数据库中查询出配置属性项
        //传入null表示不设条件查询所有
        configPropertyItems = configPropertyItemService.queryConfigPropertyItemList(null); 
    }

    /**
     * 保存属性到数据库中
     * @param configPropertyItem
     */
    public void saveItem(ConfigPropertyItem configPropertyItem) {
        configPropertyItemService.insertConfigPropertyItem(configPropertyItem);
    }

    /**
     * 清空数据库表中数据
     */
    public void cleanItems() {
        configPropertyItemService.deleteAll();
    }
}
