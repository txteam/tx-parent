/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-11
 * <修改描述:>
 */
package com.tx.core.proxy.handler;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;


 /**
  * 抑制Set方法执行代理handler
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-11]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SetSuppressingInvocationHandler implements InvocationHandler{

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object arg0, Method arg1, Object[] arg2)
            throws Throwable {
        
        return null;
    }
    
    
}
