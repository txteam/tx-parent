/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-25
 * <修改描述:>
 */
package com.tx.core.support.method;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tx.core.exceptions.parameter.ParameterIsEmptyException;

/**
 * 参数解析器
 * 
 * @author  brady
 * @version  [版本号, 2013-1-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ParameterResolver {
    
    /** 参数类型 */
    private Class<?> paramterType;
    
    /** 参数注解 */
    private Map<Class<? extends Annotation>, Annotation> paramterAnnotationMap = new LinkedHashMap<Class<? extends Annotation>, Annotation>();
    
    public ParameterResolver(Class<?> paramterType, Annotation[] annotations) {
        if (paramterType == null) {
            throw new ParameterIsEmptyException("生成参数解析器发生异常，参数类型不能为空");
        }
        this.paramterType = paramterType;
        if (annotations != null) {
            for (Annotation anno : annotations) {
                this.paramterAnnotationMap.put(anno.annotationType(), anno);
            }
        }
    }
    
    /** 判断参数上是否具有某注解 */
    public <T extends Annotation> boolean isHasAnnotation(Class<T> annoType) {
        return paramterAnnotationMap.keySet().contains(annoType);
    }
    
    /**
      * 获取参数上的某注解实例
      * <功能详细描述>
      * @param annoType
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annoType) {
        if (!isHasAnnotation(annoType)) {
            return null;
        }
        if (annoType.isInstance(paramterAnnotationMap.get(annoType))) {
            return (T) paramterAnnotationMap.get(annoType);
        }
        return null;
    }
    
    /**
     * @return 返回 paramterType
     */
    public Class<?> getParamterType() {
        return paramterType;
    }
    
    /**
     * @param 对paramterType进行赋值
     */
    public void setParamterType(Class<?> paramterType) {
        this.paramterType = paramterType;
    }
}
