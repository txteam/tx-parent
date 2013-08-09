/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.config.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tx.component.config.TestBase;
import com.tx.component.config.dao.impl.ConfigPropertyItemDaoImpl;
import com.tx.component.config.model.ConfigPropertyItem;
import com.tx.component.config.util.UUIDUtils;


 /**
  * 配置属性项持久功能单元测试
  * <功能详细描述>
  * 
  * @author  wanxin
  * @version  [版本号, 2013-8-8]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ConfigPropertyItemDaoTest {
    
    @BeforeClass
    public static void setUp() {
        TestBase.bindDsToJNDI();
    }
    
    //dao类
    private ConfigPropertyItemDao configPropertyItemDao;
    
    //创建实体类用于测试
    ConfigPropertyItem c1 = new ConfigPropertyItem();
    ConfigPropertyItem c2 = new ConfigPropertyItem();
    ConfigPropertyItem c3 = new ConfigPropertyItem();
    ConfigPropertyItem c4 = new ConfigPropertyItem();
    ConfigPropertyItem c5 = new ConfigPropertyItem();
    
    
    @Before
    public void init(){
        //实例化Dao类
        this.configPropertyItemDao = new ConfigPropertyItemDaoImpl(TestBase.getLosDataSource(), "oracle", "test");
        
        //实例化测试数据
        c1.setId(UUIDUtils.createUUID());
        c1.setName("test-name1");
        c1.setKey("test-key1");
        c1.setValue("test-value1");
        c1.setDescription("test-description1");
        c1.setEditAble(true);
        c1.setConfigResourceId("test-configResourceId");
        c1.setLastUpdateDate(new Date(new java.util.Date().getTime()));
        
        
        //实例化测试数据
        c2.setId(UUIDUtils.createUUID());
        c2.setName("test-name1");
        c2.setKey("test-key1");
        c2.setValue("test-value1");
        c2.setDescription("test-description1");
        c2.setEditAble(true);
        c2.setConfigResourceId("test-configResourceId");
        c2.setLastUpdateDate(new Date(new java.util.Date().getTime()));

    }
    
    @Test
    public void testInsert(){
        try {
            this.configPropertyItemDao.insertConfigPropertyItem(c1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testBatchInsert(){
        c1.setId(UUIDUtils.createUUID());
        c2.setId(UUIDUtils.createUUID());
        List<ConfigPropertyItem> list = new ArrayList<ConfigPropertyItem>();
        list.add(c1);
        list.add(c2);
        try {
            this.configPropertyItemDao.batchInsertConfigPropertyItem(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //@Test
    public void testQuery(){
        
        List<ConfigPropertyItem> cpithis = configPropertyItemDao.queryConfigPropertyItemList(null);
        
        ConfigPropertyItem configPropertyItem = cpithis.get(0);
        assert(cpithis.size()==1);
        assert("test-name1".equals(configPropertyItem.getName()));
        assert("test-key1".equals(configPropertyItem.getKey()));
        assert("test-value1".equals(configPropertyItem.getValue()));
        assert("test-description1".equals(configPropertyItem.getDescription()));
        assert("test-configResourceId".equals(configPropertyItem.getConfigResourceId()));
        assert(configPropertyItem.isEditAble());
    }
}
