/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.basicdata.model.TestBasicData;
import com.tx.component.basicdata.testmodel.BaseTest2222;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.UUIDUtils;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-cache.xml", 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans-basicdata.xml" })
public class BaseTestBasicModelExecutorTest {
    
    private BasicDataExecutor<TestBasicData> executor1;
    
    private BasicDataExecutor<BaseTest2222> executor2;
    
    @Before
    public void before() {
        executor1 = BasicDataContext.getExecutor(TestBasicData.class);
        
        executor2 = BasicDataContext.getExecutor(BaseTest2222.class);
    }
    
    @Test
    public void testExecutor1() {

        TestBasicData newObj = new TestBasicData();
        newObj.setId(UUIDUtils.generateUUID());
        newObj.setCode("001");
        newObj.setName("1-1");
        newObj.setRemark("001");
        newObj.setCreateDate(new Date());
        newObj.setLastUpdateDate(new Date());
        
        executor1.insert(newObj);
        
        TestBasicData newObj2 = new TestBasicData();
        newObj2.setId(UUIDUtils.generateUUID());
        newObj2.setCode("002");
        newObj2.setName("1-1");
        newObj2.setRemark("001");
        newObj2.setCreateDate(new Date());
        newObj2.setLastUpdateDate(new Date());
        
        executor1.insert(newObj2);
        
        BaseTest2222 newObj3 = new BaseTest2222();
        newObj3.setId(UUIDUtils.generateUUID());
        newObj3.setCode("003");
        newObj3.setName("2-1");
        newObj3.setRemark("002");
        newObj3.setCreateDate(new Date());
        newObj3.setLastUpdateDate(new Date());
        
        executor2.insert(newObj3);
        
        executor1.list();
        executor1.list();
        executor1.getMultiValueMap("code").containsKey("001");
        executor1.getMultiValueMap("code").containsKey("001");
        executor1.getMultiValueMap("code").containsKey("002");
        
        executor1.execute("disable",newObj.getId());
        
        executor1.delete( executor1.getMultiValueMap("code").getFirst("001").getId());
        
        executor1.getMultiValueMap("code").containsKey("002");
        
        AssertUtils.isTrue(executor1.getMultiValueMap("code").containsKey("002"));
        
    }
}
