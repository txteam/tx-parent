/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月9日
 * <修改描述:>
 */
package com.tx.component.configuration.context;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.core.convert.support.ConfigurableConversionService;

import com.tx.component.configuration.model.ConfigProperty;
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
public class ConfigEntityInvocationHandler implements InvocationHandler {
    
    /** 转换业务层 */
    private ConfigurableConversionService conversionService;
    
    /**  */
    private String prefix;
    
    /** 目标对象 */
    private Object target;
    
    /** target对应的BeanWrapper对象 */
    private BeanWrapper targetBW;
    
    /** 方法到编码的映射 */
    private Map<Method, String> method2codeMap = new HashMap<Method, String>();
    
    /** set方法映射，映射到属性名 */
    private Map<Method, String> setMethodMap = new HashMap<>();
    
    /** get方法映射，映射到属性名 */
    private Map<Method, String> getMethodMap = new HashMap<>();
    
    /** <默认构造函数> */
    public ConfigEntityInvocationHandler(String prefix, Object target) {
        super();
        this.prefix = prefix;
        this.target = target;
    }
    
    /**
     * 初始化<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void init() {
        this.targetBW = PropertyAccessorFactory
                .forBeanPropertyAccess(this.target);
        
        for (PropertyDescriptor pd : this.targetBW.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                continue;
            }
            AssertUtils.isTrue(
                    this.conversionService.canConvert(pd.getPropertyType(),
                            String.class),
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
        
        //取值
        String code = method2codeMap.get(method);
        if (getMethodMap.containsKey(method)) {
            AssertUtils.isTrue(ArrayUtils.isEmpty(args),
                    "getMethod args.length should == 0.method:{}",
                    new Object[] { method });
            
            //在get方法调用以前，将配置容器中的值读取写入到当前的bean中
            String property = getMethodMap.get(method);
            ConfigProperty cp = ConfigContext.getContext().find(code);
            AssertUtils.notNull(cp,
                    "ConfigProperty(code={}) is not exists.",
                    code);
            String value = ConfigContext.getContext().find(code).getValue();
            this.targetBW.setPropertyValue(property, value);
        } else if (setMethodMap.containsKey(method)) {
            AssertUtils.isTrue(args.length == 1,
                    "setMethod args.length should == 1.method:{}",
                    new Object[] { method });
            
            //调用set方法以前先修改配置值
            String newValue = this.conversionService.convert(args[0],
                    String.class);
            ConfigContext.getContext().patch(code, newValue);
        }
        //执行方法  
        result = method.invoke(target, args);
        return result;
    }
    
    /**
     * @param 对conversionService进行赋值
     */
    public void setConversionService(
            ConfigurableConversionService conversionService) {
        this.conversionService = conversionService;
    }
}