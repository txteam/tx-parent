/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.component.rule;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.rule.model.SimpleRulePropertyByte;
import com.tx.component.rule.service.SimpleRulePropertyByteService;
import com.tx.core.paged.model.PagedList;


 /**
  * SimpleRulePropertyByte业务层单元测试类
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-11]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-tx.xml", 
        "classpath:spring/beans-mybatis.xml",
        "classpath:spring/beans.xml" })
public class SimpleRulePropertyByteServiceTest {
    
    /** 设置jndi */
    @BeforeClass
    public static void setUp() {
    }
    
    @Resource(name="simpleRulePropertyByteService")
    private SimpleRulePropertyByteService simpleRulePropertyByteService;
    
    /**
      * 生成simpleRulePropertyByte实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return SimpleRulePropertyByte [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected SimpleRulePropertyByte getSimpleRulePropertyByte(){
        SimpleRulePropertyByte res = new SimpleRulePropertyByte();
        
        
        
        return res;
    }
    
    /**
      * 贯通测试，增加，查询，删除
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Test
    public void testInsertAndFindAndDelete(){
        //
        try {
            SimpleRulePropertyByte simpleRulePropertyByte = getSimpleRulePropertyByte();
            
            this.simpleRulePropertyByteService.insertSimpleRulePropertyByte(simpleRulePropertyByte);
            
            Assert.assertNotNull(simpleRulePropertyByte.getRuleId());
            
            String pk = simpleRulePropertyByte.getRuleId();
            
            SimpleRulePropertyByte res = this.simpleRulePropertyByteService.findSimpleRulePropertyByteByRuleId(pk);
            
            Assert.assertNotNull(res);
            
            int count = this.simpleRulePropertyByteService.deleteByRuleId(res.getRuleId());
            
            Assert.assertTrue(count > 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    
    /**
      * 测试查询列表
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Test
    public void testQueryList(){
        try {
            int count = this.simpleRulePropertyByteService.countSimpleRulePropertyByte();
            
            Assert.assertTrue(count >= 0);
            
            List<SimpleRulePropertyByte> simpleRulePropertyByteList = this.simpleRulePropertyByteService.querySimpleRulePropertyByteList();
            
            Assert.assertNotNull(simpleRulePropertyByteList);
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    
    /**
      * 测试分页查询功能
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Test
    public void testQueryPageList(){
        try {
            PagedList<SimpleRulePropertyByte> simpleRulePropertyBytePageList = this.simpleRulePropertyByteService.querySimpleRulePropertyBytePagedList(1, 10);
            
            Assert.assertNotNull(simpleRulePropertyBytePageList);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    
}
