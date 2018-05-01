/*
s * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-9
 * <修改描述:>
 */
package com.tx.test.reflection;

import org.junit.Assert;
import org.junit.Test;

import com.tx.core.reflection.JpaMetaClass;
import com.tx.test.reflection.model.TestGetSet;


 /**
  * 测试
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class JpaMetaClassTest {
    
    @Test
    public void testModulePackageName(){
        JpaMetaClass<TestGetSet> jpa = JpaMetaClass.forClass(TestGetSet.class);
        
        System.out.println(jpa.getModulePackageName());
        
        Assert.assertEquals("com.tx.core.reflection", jpa.getModulePackageName());
    }
    
    @Test
    public void testModulePackageSimpleName(){
        JpaMetaClass<TestGetSet> jpa = JpaMetaClass.forClass(TestGetSet.class);
        
        System.out.println(jpa.getModulePackageSimpleName());
        
        Assert.assertEquals("reflection", jpa.getModulePackageSimpleName());
    }
}
