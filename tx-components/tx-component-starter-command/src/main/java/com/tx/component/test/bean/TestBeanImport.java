/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月4日
 * <修改描述:>
 */
package com.tx.component.test.bean;

import org.springframework.beans.factory.InitializingBean;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月4日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestBeanImport implements InitializingBean {
    
    /** <默认构造函数> */
    public TestBeanImport() {
        super();
        
        System.out.println("test bean import cons.");
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("test bean import afterPropertiesSet.");
    }
    
}
