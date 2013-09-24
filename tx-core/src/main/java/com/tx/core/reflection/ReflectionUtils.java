/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.core.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.reflection.MetaClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 *  反射工具类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ReflectionUtils {
    
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    
    /**
      * 获取含有指定注解的getter集合<br/>
      *<功能详细描述>
      * @param type
      * @param annotationType
      * @return [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <A extends Annotation> List<String> getGetterNamesByAnnotationType(
            Class<?> type, Class<A> annotationType) {
        AssertUtils.notNull(annotationType, "annotationType is null");
        
        MetaClass metaClass = MetaClass.forClass(type);
        String[] getterNames = metaClass.getGetterNames();
        
        List<String> resList = new ArrayList<String>();
        for (String getterNameTemp : getterNames) {
            if (!isHasAnnotationForGetter(type, getterNameTemp, annotationType)) {
                continue;
            }
            resList.add(getterNameTemp);
        }
        return resList;
    }
    
    /**
      * 获取指定类的属性（以及对应的getter方法上）上是否含有指定的注解
      *<功能详细描述>
      * @param type
      * @param getterName
      * @param annotationType
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <A extends Annotation> boolean isHasAnnotationForGetter(
            Class<?> type, String getterName, Class<A> annotationType) {
        A anno = getGetterAnnotation(type, getterName, annotationType);
        
        if (anno == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
      * 获取当前类具有set方法的属性名集合<br/>
      *<功能详细描述>
      * @param type
      * @param isIncludeHasNotSetMethod
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String[] getSetterNames(Class<?> type,
            boolean isIncludeHasNotSetMethod) {
        MetaClass metaClass = MetaClass.forClass(type);
        ClassReflector reflector = ClassReflector.forClass(type);
        
        List<String> resList = new ArrayList<String>(
                TxConstants.INITIAL_CONLLECTION_SIZE);
        for (String setterName : metaClass.getSetterNames()) {
            if (!isIncludeHasNotSetMethod) {
                Method setMethod = reflector.getSetterMethod(setterName);
                if (setMethod == null) {
                    continue;
                }
            }
            resList.add(setterName);
        }
        
        String[] resArr = resList.toArray(new String[resList.size()]);
        return resArr;
    }
    
    /**
      * 获取当前类getterNames
      *<功能详细描述>
      * @param type
      * @param isIncludeHasNotGetterMethod
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String[] getGetterNames(Class<?> type,
            boolean isIncludeHasNotGetMethod) {
        MetaClass metaClass = MetaClass.forClass(type);
        ClassReflector reflector = ClassReflector.forClass(type);
        
        List<String> resList = new ArrayList<String>(
                TxConstants.INITIAL_CONLLECTION_SIZE);
        for (String getterName : metaClass.getGetterNames()) {
            if (!isIncludeHasNotGetMethod) {
                Method getMethod = reflector.getGetterMethod(getterName);
                if (getMethod == null) {
                    continue;
                }
            }
            resList.add(getterName);
        }
        
        String[] resArr = resList.toArray(new String[resList.size()]);
        return resArr;
    }
    
    //    /**
    //      * 在对应类中是否存在对应属性的get方法
    //      *<功能详细描述>
    //      * @param type
    //      * @param getterName
    //      * @return [参数说明]
    //      * 
    //      * @return boolean [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public static boolean isHasGetMethod(Class<?> type, String getterName) {
    //        ClassReflector reflector = ClassReflector.forClass(type);
    //        
    //        Method getterMethod = reflector.getGetterMethod(getterName);
    //        if (getterMethod != null) {
    //            return true;
    //        } else {
    //            return false;
    //        }
    //    }
    //    
    //    /**
    //      * 在指定类中是否存在对应属性的set方法
    //      *<功能详细描述>
    //      * @param type
    //      * @param setterName
    //      * @return [参数说明]
    //      * 
    //      * @return boolean [返回类型说明]
    //      * @exception throws [异常类型] [异常说明]
    //      * @see [类、类#方法、类#成员]
    //     */
    //    public static boolean isHasSetMethod(Class<?> type, String setterName) {
    //        ClassReflector reflector = ClassReflector.forClass(type);
    //        
    //        Method setterMethod = reflector.getSetterMethod(setterName);
    //        if (setterMethod != null) {
    //            return true;
    //        } else {
    //            return false;
    //        }
    //    }
    
    /**
      * 获取某属性上的注解<br/>
      *<功能详细描述>
      * @param type
      * @param getterName
      * @param annotationType
      * @return [参数说明]
      * 
      * @return A [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <A extends Annotation> A getGetterAnnotation(Class<?> type,
            String getterName, Class<A> annotationType) {
        ClassReflector reflector = ClassReflector.forClass(type);
        
        A res = null;
        Method getterMethod = reflector.getGetterMethod(getterName);
        if (getterMethod != null) {
            res = getterMethod.getAnnotation(annotationType);
        }
        if (res != null) {
            return res;
        }
        
        Field field = reflector.getFiled(getterName);
        if (getterMethod != null) {
            res = field.getAnnotation(annotationType);
        }
        return res;
    }
}
