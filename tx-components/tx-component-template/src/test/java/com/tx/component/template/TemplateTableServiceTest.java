/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.component.template;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.component.template.model.TemplateTable;
import com.tx.component.template.service.TemplateTableService;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.UUIDUtils;


 /**
  * TemplateTable业务层单元测试类
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-11]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
        "classpath:spring/beans-aop.xml",
        "classpath:spring/beans-auth.xml",
        "classpath:spring/beans-cache.xml",
        "classpath:spring/beans-ds.xml",
        "classpath:spring/beans-i18n.xml",
        "classpath:spring/beans-tx.xml",
        "classpath:spring/beans.xml" })
@ActiveProfiles("dev")
public class TemplateTableServiceTest {
    
    /** 设置jndi */
    @BeforeClass
    public static void setUp() {
        //bindJNDI
    }
    
    @Resource(name="templateTableService")
    private TemplateTableService templateTableService;
    
    /**
      * 生成templateTable实例
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return TemplateTable [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected TemplateTable getTemplateTable(){
        TemplateTable res = new TemplateTable();
        
        return res;
    }
    
    @Test
    public void testInsertNewTemplateTable(){
        TemplateTable newTable = getTemplateTable();
        
        //this.templateTableService.insertTemplateTable(templateTable);
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
            TemplateTable templateTable = getTemplateTable();
            
            this.templateTableService.insertTemplateTable(templateTable);
            
            Assert.assertNotNull(templateTable.getId());
            
            String pk = templateTable.getId();
            
            TemplateTable res = this.templateTableService.findTemplateTableById(pk);
            
            Assert.assertNotNull(res);
            
            int count = this.templateTableService.deleteById(res.getId());
            
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
            int count = this.templateTableService.countTemplateTable();
            
            Assert.assertTrue(count >= 0);
            
            List<TemplateTable> templateTableList = this.templateTableService.queryTemplateTableList();
            
            Assert.assertNotNull(templateTableList);
            
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
            PagedList<TemplateTable> templateTablePageList = this.templateTableService.queryTemplateTablePagedList(1, 10);
            
            Assert.assertNotNull(templateTablePageList);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
    
}
