/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月21日
 * <修改描述:>
 */
package com.tx.component.test.service;

/**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TestBeanService {
    
    private String test;
    
    /** <默认构造函数> */
    public TestBeanService(String test) {
        super();
        this.test = test;
        
        System.out.println("test service cons." + this.test);
    }
    
    public void insertTest() {
        
    }
}
