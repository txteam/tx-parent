/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-16
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.ibatis.io.ResolverUtil;

/**
 * 类扫描工具
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ClassScanUtils {
    
    /**
      * 根据注解以及根包，查找类集合
      * <功能详细描述>
      * @param annotation
      * @param packageNames
      * @return [参数说明]
      * 
      * @return Set<Class<? extends Class<?>>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> Set<Class<? extends T>> scanByAnnotation(
            Class<? extends Annotation> annotation, String... packageNames) {
        ResolverUtil<T> resolverUtil = new ResolverUtil<T>();
        resolverUtil.findAnnotated(annotation, packageNames);
        
        return resolverUtil.getClasses();
    }
    
    /**
      * 根据父类以及根包，查找类集合
      * <功能详细描述>
      * @param parent
      * @param packageNames
      * @return [参数说明]
      * 
      * @return Set<Class<? extends Class<?>>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> Set<Class<? extends T>> scanByParentClass(
            Class<T> parent, String... packageNames) {
        ResolverUtil<T> resolverUtil = new ResolverUtil<T>();
        resolverUtil.findImplementations(parent, packageNames);
        
        return resolverUtil.getClasses();
    }
    
}
