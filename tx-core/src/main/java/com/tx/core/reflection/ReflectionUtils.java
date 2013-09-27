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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.exception.InvalidGetterMethod;
import com.tx.core.reflection.exception.InvalidSetterMethod;

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
      * 是否为getter方法
      *<功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isGetterMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        
        //如果对应方法有入参则该方法不为getter对应方法
        if (method.getParameterTypes().length > 0
                && !Void.class.isAssignableFrom(returnType)) {
            return false;
        }
        
        if (boolean.class.isAssignableFrom(returnType)) {
            if (methodName.length() > 2 && methodName.startsWith("is")
                    && Character.isUpperCase(methodName.charAt(2))) {
                return true;
            }
        } else {
            if (methodName.length() > 3 && methodName.startsWith("get")
                    && Character.isUpperCase(methodName.charAt(3))) {
                return true;
            }
        }
        return false;
    }
    
    /**
      * 根据方法名和返回类型获得getterName
      *<功能详细描述>
      * @param methodName
      * @param returnType
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getGetterNameByMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        
        //如果对应方法有入参则该方法不为getter对应方法
        if (method.getParameterTypes().length > 0
                && !Void.class.isAssignableFrom(returnType)) {
            throw new InvalidGetterMethod(
                    "方法入参不为空，或返回类型为空.paramterTypes:{};returnType:{}",
                    new Object[] { method.getParameterTypes(), returnType });
        }
        
        String getterName = null;
        if (boolean.class.isAssignableFrom(returnType)) {
            if (methodName.length() > 2 && methodName.startsWith("is")
                    && Character.isUpperCase(methodName.charAt(2))) {
                getterName = StringUtils.uncapitalize(methodName.substring(2));
            }
        } else {
            if (methodName.length() > 3 && methodName.startsWith("get")
                    && Character.isUpperCase(methodName.charAt(3))) {
                getterName = StringUtils.uncapitalize(methodName.substring(3));
            }
        }
        
        if (!StringUtils.isEmpty(getterName)) {
            return getterName;
        } else {
            throw new InvalidGetterMethod(
                    "方法名应该以is/get+首写字母为大写字母的字符串组成.methodName:{}",
                    new Object[] { methodName });
        }
    }
    
    /**
     * 获取方法对应的setter
     *<功能详细描述>
     * @param method
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean isSetterMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        //Class<?> firstParameterType = null;
        
        //如果对应方法返回类型为空，且入参为一个
        if (!Void.class.isAssignableFrom(returnType)
                || method.getParameterTypes().length != 1) {
            return false;
        }
        
        if (methodName.length() > 3 && methodName.startsWith("set")
                && Character.isUpperCase(methodName.charAt(3))) {
            return true;
        }
        return false;
    }
    
    /**
      * 获取方法对应的setter
      *<功能详细描述>
      * @param method
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getSetterNameByMethod(Method method) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        //Class<?> firstParameterType = null;
        
        //如果对应方法返回类型为空，且入参为一个
        if (!Void.class.isAssignableFrom(returnType)
                || method.getParameterTypes().length != 1) {
            throw new InvalidSetterMethod(
                    "方法入参为空，或返回不为空.paramterTypes:{};returnType:{}",
                    new Object[] { method.getParameterTypes(), returnType });
        }
        
        String setterName = null;
        if (methodName.length() > 3 && methodName.startsWith("set")
                && Character.isUpperCase(methodName.charAt(3))) {
            setterName = StringUtils.uncapitalize(methodName.substring(3));
        }
        
        if (!StringUtils.isEmpty(setterName)) {
            return setterName;
        } else {
            throw new InvalidSetterMethod(
                    "方法名应该以set+首写字母为大写字母的字符串组成.methodName:{}",
                    new Object[] { methodName });
        }
    }
    
    /**
      * 根据字段名推论得出字段对应的get方法<br/>
      *<功能详细描述>
      * @param fieldName
      * @param type [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getGetMethodNameByField(Field field) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        String methodName = null;
        if (boolean.class.isAssignableFrom(fieldType)) {
            //利用length>2排除掉fieldName = is的情况，如果为is对应方法isIs setIs
            if (fieldName.length() > 2 && fieldName.startsWith("is")
                    && Character.isUpperCase(fieldName.charAt(3))) {
                methodName = fieldName;
            } else {
                methodName = "is" + StringUtils.capitalize(fieldName);
            }
        } else {
            methodName = "get" + StringUtils.capitalize(fieldName);
        }
        return methodName;
    }
    
    /**
      * 根据字段名获得对应的set方法<br/>
      *<功能详细描述>
      * @param fieldName
      * @param fieldType
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getSetMethodNameByField(Field field) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        String methodName = null;
        if (boolean.class.isAssignableFrom(fieldType)) {
            if (fieldName.length() > 2 && fieldName.startsWith("is")
                    && Character.isUpperCase(fieldName.charAt(3))) {
                methodName = "set"
                        + StringUtils.capitalize(fieldName.substring(2));
            } else {
                methodName = "set" + StringUtils.capitalize(fieldName);
            }
        } else {
            methodName = "set" + StringUtils.capitalize(fieldName);
        }
        return methodName;
    }
    
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
        
        ClassReflector classReflector = ClassReflector.forClass(type);
        Set<String> getterNames = classReflector.getGetterNames();
        
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
      * 在对应类中是否存在对应属性的get方法
      *<功能详细描述>
      * @param type
      * @param getterName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isHasGetMethod(Class<?> type, String getterName) {
        ClassReflector reflector = ClassReflector.forClass(type);
        
        Method getterMethod = reflector.getGetterMethod(getterName);
        if (getterMethod != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
      * 在指定类中是否存在对应属性的set方法
      *<功能详细描述>
      * @param type
      * @param setterName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isHasSetMethod(Class<?> type, String setterName) {
        ClassReflector reflector = ClassReflector.forClass(type);
        
        Method setterMethod = reflector.getSetterMethod(setterName);
        if (setterMethod != null) {
            return true;
        } else {
            return false;
        }
        
    }
    
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
        if (field != null) {
            res = field.getAnnotation(annotationType);
        }
        return res;
    }
}
