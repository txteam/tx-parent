/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.basicdata.executor.BasicDataExecutor;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.ReflectionUtils;

/**
 * 基础数据容器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataContext<T> {
    
    /** 基础数据容器单例 */
    private static Map<Class<?>, BasicDataContext<?>> context = new HashMap<Class<?>, BasicDataContext<?>>();
    
    private static BasicDataContextConfigurator configurator;
    
    private Class<T> type;
    
    private BasicData<T> basicData;
    
    private BasicDataExecutor<T> baiscDataExecutor;
    
    /** <默认构造函数> */
    protected BasicDataContext() {
        super();
    }
    
    private BasicDataContext(Class<T> type) {
        this.type = type;
    }
    
    private void parseBasicDataType(Class<T> type) {
        AssertUtils.isTrue(type.isAnnotationPresent(com.tx.component.basicdata.annotation.BasicData.class));
        com.tx.component.basicdata.annotation.BasicData basicDataAnno = type.getAnnotation(com.tx.component.basicdata.annotation.BasicData.class);
        
        basicDataAnno.name();
        basicDataAnno.tableName();
        basicDataAnno.annotationType();
        basicDataAnno.executor();
        basicDataAnno.conditions();
        
        ReflectionUtils.getGetterNamesByAnnotationType(type, annotationType)
    }
    
    /**
      * 包内可见的实例化方法<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <TYPE> BasicDataContext<TYPE> getContext(Class<TYPE> type) {
        @SuppressWarnings("rawtypes")
        BasicDataContext res = null;
        synchronized (type) {
            if (context.containsKey(type)) {
                res = context.get(type);
            } else {
                res = new BasicDataContext<TYPE>(type);
                context.put(type, res);
            }
        }
        return res;
    }
    
}
