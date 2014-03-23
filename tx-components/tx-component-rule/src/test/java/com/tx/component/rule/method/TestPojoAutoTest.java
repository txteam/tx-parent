/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月24日
 * <修改描述:>
 */
package com.tx.component.rule.method;

import com.tx.component.rule.method.model.TestPojo;
import com.tx.component.rule.method.model.TestPojoDao;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestPojoAutoTest {
    
    private TestPojo testPojo = null;
    
    private TestPojoDao testPojoDao = null;

    /**
     * @return 返回 testPojo
     */
    public TestPojo getTestPojo() {
        return testPojo;
    }

    /**
     * @param 对testPojo进行赋值
     */
    public void setTestPojo(TestPojo testPojo) {
        this.testPojo = testPojo;
    }

    /**
     * @return 返回 testPojoDao
     */
    public TestPojoDao getTestPojoDao() {
        return testPojoDao;
    }

    /**
     * @param 对testPojoDao进行赋值
     */
    public void setTestPojoDao(TestPojoDao testPojoDao) {
        this.testPojoDao = testPojoDao;
    }
}
