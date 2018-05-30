/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月31日
 * <修改描述:>
 */
package com.tx.test.method.service;


/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MethodTestService {
    
    public void test1(){
        System.out.println("test1 called.");
    }
    
    public String test2(String test1,String test2){
        System.out.println("test2 called.");
        return test2;
    }
}
