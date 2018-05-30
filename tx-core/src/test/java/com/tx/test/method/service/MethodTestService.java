/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月31日
 * <修改描述:>
 */
package com.tx.test.method.service;

import com.tx.core.method.annotation.MethodParam;
import com.tx.core.util.MessageUtils;

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
    
    public void test1() {
        System.out.println("test1 called.");
    }
    
    public String test2(String test1, String test2) {
        System.out.println(MessageUtils.format("test2 called. test1:'{}' test2:'{}'",test1,test2));
        return test2;
    }
    
    public String test3(String test1, @MethodParam("test3") String test2) {
        System.out.println(MessageUtils.format("test3 called. test1:'{}' test2:'{}'",test1,test2));
        return test2;
    }
}
