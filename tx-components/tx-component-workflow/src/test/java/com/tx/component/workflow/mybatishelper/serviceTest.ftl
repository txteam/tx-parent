/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package ${service.basePackage};

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.TestBase;
import ${service.basePackage}.model.${service.entitySimpleName};
import ${service.basePackage}.service.${service.entitySimpleName}Service;
import com.tx.core.paged.model.PagedList;


 /**
  * ${service.entitySimpleName}业务层单元测试类
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
public class ${service.entitySimpleName}ServiceTest {
    
    /** 设置jndi */
    @BeforeClass
    public static void setUp() {
        TestBase.bindDsToJNDI();
    }
    
    @Resource(name="${service.lowerCaseEntitySimpleName}Service")
    private ${service.entitySimpleName}Service ${service.lowerCaseEntitySimpleName}Service;
    
    /**
      * 生成${service.lowerCaseEntitySimpleName}实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return ${service.entitySimpleName} [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected ${service.entitySimpleName} get${service.entitySimpleName}(){
        ${service.entitySimpleName} res = new ${service.entitySimpleName}();
        
        
        
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
            ${service.entitySimpleName} ${service.lowerCaseEntitySimpleName} = get${service.entitySimpleName}();
            
            this.${service.lowerCaseEntitySimpleName}Service.insert${service.entitySimpleName}(${service.lowerCaseEntitySimpleName});
            
            Assert.assertNotNull(${service.lowerCaseEntitySimpleName}.get${service.upCaseIdPropertyName}());
            
            String pk = ${service.lowerCaseEntitySimpleName}.get${service.upCaseIdPropertyName}();
            
            ${service.entitySimpleName} res = this.${service.lowerCaseEntitySimpleName}Service.find${service.entitySimpleName}By${service.upCaseIdPropertyName}(pk);
            
            Assert.assertNotNull(res);
            
            int count = this.${service.lowerCaseEntitySimpleName}Service.deleteBy${service.upCaseIdPropertyName}(res.get${service.upCaseIdPropertyName}());
            
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
            int count = this.${service.lowerCaseEntitySimpleName}Service.count${service.entitySimpleName}();
            
            Assert.assertTrue(count >= 0);
            
            List<${service.entitySimpleName}> ${service.lowerCaseEntitySimpleName}List = this.${service.lowerCaseEntitySimpleName}Service.query${service.entitySimpleName}List();
            
            Assert.assertNotNull(${service.lowerCaseEntitySimpleName}List);
            
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
            PagedList<${service.entitySimpleName}> ${service.lowerCaseEntitySimpleName}PageList = this.${service.lowerCaseEntitySimpleName}Service.query${service.entitySimpleName}PagedList(1, 10);
            
            Assert.assertNotNull(${service.lowerCaseEntitySimpleName}PageList);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    
}
