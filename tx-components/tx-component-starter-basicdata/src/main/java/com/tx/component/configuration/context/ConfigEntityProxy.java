/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月9日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.core.convert.support.DefaultConversionService;

import com.tx.component.configuration.model.ConfigProperty;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 配置实体代理<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigEntityProxy implements InvocationHandler {
    
    /** 转换业务层 */
    private static DefaultConversionService conversionService = new DefaultConversionService();
    
    /**  */
    private String prefix;
    
    /** 目标对象 */
    private Object target;
    
    /** 是否可编辑 */
    private boolean modifyAble;
    
    /** 模块 */
    private String module;
    
    /** target对应的BeanWrapper对象 */
    private BeanWrapper targetBW;
    
    /** 方法到编码的映射 */
    private Map<Method, String> method2codeMap = null;
    
    /** set方法映射，映射到属性名 */
    private Map<Method, String> setMethodMap = null;
    
    /** get方法映射，映射到属性名 */
    private Map<Method, String> getMethodMap = null;
    
    /** <默认构造函数> */
    private ConfigEntityProxy(Object target) {
        super();
        this.target = target;
        this.modifyAble = true;
        init();
    }
    
    /** <默认构造函数> */
    private ConfigEntityProxy(String module, Object target) {
        super();
        this.module = module;
        this.target = target;
        this.modifyAble = false;
        init();
    }
    
    private void init() {
        this.targetBW = PropertyAccessorFactory
                .forBeanPropertyAccess(this.target);
        for (PropertyDescriptor pd : this.targetBW.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                continue;
            }
            AssertUtils.isTrue(
                    ConfigEntityProxy.conversionService
                            .canConvert(pd.getPropertyType(), String.class),
                    "property:{} can not be convert to String.",
                    pd.getName());
            
            String property = pd.getName();
            String code = this.prefix + property;
            Method getMethod = pd.getReadMethod();
            Method setMethod = pd.getWriteMethod();
            
            this.method2codeMap.put(getMethod, code);
            this.method2codeMap.put(setMethod, code);
            
            this.setMethodMap.put(setMethod, property);
            this.getMethodMap.put(getMethod, property);
        }
    }
    
    /** 
     * 绑定委托对象并返回一个代理类 
     * @param target 
     * @return 
     */
    @SuppressWarnings("unchecked")
    public static <T> T proxy(T target, boolean modifyAble) {
        AssertUtils.notNull(target, "target is null.");
        
        ConfigEntityProxy handler = new ConfigEntityProxy(target);
        T proxy = (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);
        
        return proxy;
    }
    
    @Override
    /** 
     * 调用方法 
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result = null;
        if (!method2codeMap.containsKey(method)) {
            //执行方法  
            return method.invoke(target, args);
        }
        //如果不能修改，则直接抛出异常
        if (modifyAble == false && setMethodMap.containsKey(method)) {
            throw new SILException("属性不能进行修改，出现不期望的调用.");
        }
        
        //取值
        String code = method2codeMap.get(method);
        
        if (getMethodMap.containsKey(method)) {
            AssertUtils.isTrue(ArrayUtils.isEmpty(args),
                    "getMethod args.length should == 0.method:{}",
                    new Object[] { method });
            
            ConfigProperty cp = ConfigContext.getContext().find(this.module,
                    code);
            AssertUtils.notNull(cp,
                    "ConfigProperty(module={},code={}) is not exists.",
                    this.module,
                    code);
            
            String value = ConfigContext.getContext()
                    .find(this.module, code)
                    .getValue();
            String property = getMethodMap.get(method);
            this.targetBW.setPropertyValue(property, value);
            
        } else if (setMethodMap.containsKey(method)) {
            AssertUtils.isTrue(args.length == 1,
                    "setMethod args.length should == 1.method:{}",
                    new Object[] { method });
            
            this.targetBW.setPropertyValue(setMethodMap.get(method), args[0]);
        } else {
            //执行方法  
            result = method.invoke(target, args);
        }
        //执行方法  
        result = method.invoke(target, args);
        return result;
    }
    
}