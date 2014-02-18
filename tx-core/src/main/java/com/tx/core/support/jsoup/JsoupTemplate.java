/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-11-27
 * <修改描述:>
 */
package com.tx.core.support.jsoup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.jsoup.nodes.Element;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.jsoup.exception.JsoupParseException;

/**
 * Jsoup工具类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-11-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsoupTemplate implements JsoupSupport {
    
    private JsoupSupport jsoupSupport;
    
    private JsoupSupport jsoupSupportProxy;
    
    /** <默认构造函数> */
    public JsoupTemplate() {
        afterPropertiesSet();
    }
    
    /** <默认构造函数> */
    public JsoupTemplate(JsoupSupport jsoupSupport) {
        super();
        this.jsoupSupport = jsoupSupport;
        afterPropertiesSet();
    }
    
    /**
     * @throws Exception
     */
    public void afterPropertiesSet() {
        if (jsoupSupport == null) {
            this.jsoupSupport = new DefaultJsoupSupport();
        }
        this.jsoupSupportProxy = (JsoupSupport) Proxy.newProxyInstance(JsoupTemplate.class.getClassLoader(),
                new Class<?>[] { JsoupSupport.class },
                new JsoupSupportInvocationHandler(this.jsoupSupport));
    }
    
    /**
     * @param element
     * @param rowMapper
     * @return
     */
    @Override
    public <T> T evaluateForObject(Element element, RowMapper<T> rowMapper) {
        return this.jsoupSupportProxy.evaluateForObject(element, rowMapper);
    }
    
    /**
     * @param element
     * @param rowMapper
     * @return
     */
    @Override
    public <T> List<T> evaluateForList(List<Element> elements,
            RowMapper<T> rowMapper) {
        return this.jsoupSupportProxy.evaluateForList(elements, rowMapper);
    }
    
    /**
      * 利用代理DefaultSoupTemplate提供jsoupTemplate实现的扩展
      *     以及统一的异常处理机制<br/>
      * <功能详细描述>
      * 
      * @author  brady
      * @version  [版本号, 2013-11-27]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    private class JsoupSupportInvocationHandler implements InvocationHandler {
        
        /** jsoupSupport实例 */
        private JsoupSupport jsoupSupport;
        
        /** <默认构造函数> */
        private JsoupSupportInvocationHandler(JsoupSupport jsoupSupport) {
            super();
            AssertUtils.notNull(jsoupSupport, "jsoupSupport is null");
            this.jsoupSupport = jsoupSupport;
        }
        
        /**
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            Object resObj;
            try {
                resObj = method.invoke(jsoupSupport, args);
            } catch (Exception e) {
                throw new JsoupParseException(e.toString(), e);
            }
            return resObj;
        }
    }
    
    /**
     * @param 对jsoupSupport进行赋值
     */
    public void setJsoupSupport(JsoupSupport jsoupSupport) {
        this.jsoupSupport = jsoupSupport;
    }
}
