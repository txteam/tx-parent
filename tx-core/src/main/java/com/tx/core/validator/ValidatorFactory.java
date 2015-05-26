/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.core.validator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * 验证器工厂<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidatorFactory implements FactoryBean<Validator> {
    
    @SuppressWarnings("unused")
	private Validator validatorProxy;
    
    public void xxx() {
        validatorProxy = (Validator) Proxy.newProxyInstance(ValidatorFactory.class.getClassLoader(),
                new Class<?>[] { Validator.class },
                new ValidatorInvocationHandler(null));
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public Validator getObject() throws Exception {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Validator.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    private class ValidatorInvocationHandler implements InvocationHandler {
        
        private Validator validator;
        
        /** <默认构造函数> */
        public ValidatorInvocationHandler(Validator validator) {
            super();
            this.validator = validator;
        }
        
        /**
         * @param arg0
         * @param arg1
         * @param arg2
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object arg0, Method method, Object[] orgs)
                throws Throwable {
            Object resObject = null;
            resObject = method.invoke(validator, orgs);
            return resObject;
        }
        
    }
}
