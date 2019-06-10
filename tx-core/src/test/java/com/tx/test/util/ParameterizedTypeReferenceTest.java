/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月8日
 * <修改描述:>
 */
package com.tx.test.util;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ParameterizedTypeReferenceTest {
    
    public static class Test1 extends ParameterizedTypeReference<String> {
        
    }
    
    public static class TestCglibProxy implements MethodInterceptor {
        
        private Object target;
        
        /** 
         * 绑定委托对象并返回一个代理类 
         * @param target 
         * @return 
         */
        public static Object bind(Object target) {
            TestCglibProxy proxy = new TestCglibProxy();
            proxy.setTarget(target);
            //增强器，动态代码生成器
            Enhancer enhancer=new Enhancer();
            //回调方法
            enhancer.setCallback(proxy);
            enhancer.setSuperclass(target.getClass());
            //取得代理对象  
            return enhancer.create(); //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)  
        }
        
        /**
         * @param arg0
         * @param arg1
         * @param arg2
         * @param arg3
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object object, Method method, Object[] args,
                MethodProxy methodProxy) throws Throwable {
            // 被织入的横切内容，开始时间 before
            long start = System.currentTimeMillis();
            // 调用方法
            Object result = methodProxy.invoke(target, args);
            // 被织入的横切内容，结束时间
            Long span = System.currentTimeMillis() - start;
            System.out.println("共用时：" + span);
            return result;
        }
        
        /**
         * @param 对target进行赋值
         */
        public void setTarget(Object target) {
            this.target = target;
        }
    }
    
    public static void main(String[] args) {
        Test1 t = new Test1();
        System.out.println(t.getRawType().getName());
        
        Test1 tproxy = (Test1)TestCglibProxy.bind(t);
        System.out.println(t.getRawType().getName());
    }
}
