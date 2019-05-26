/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.core.generator.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.tx.core.exceptions.reflection.InvalidGetterMethodException;
import com.tx.core.exceptions.reflection.InvalidSetterMethodException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 *  反射工具类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class ReflectionUtils {
    
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    
    /**
     * 需要跳过的getter方法
     *     getClass需要跳过
     */
    private static Map<String, Class<?>> needSkipGetterMethod = new HashMap<String, Class<?>>();
    
    static {
        needSkipGetterMethod.put("getClass", Class.class);
    }
    
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
                && !Void.TYPE.equals(returnType)) {
            return false;
        }
        
        //如果为getClass则认为该方法非get方法
        if (needSkipGetterMethod.containsKey(methodName)
                && needSkipGetterMethod.get(methodName)
                        .isAssignableFrom(returnType)) {
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
                && !Void.TYPE.equals(returnType)) {
            throw new InvalidGetterMethodException(
                    MessageUtils.format("方法入参不为空，或返回类型为空.paramterTypes:{};returnType:{}",
                            new Object[] { method.getParameterTypes(),
                                    returnType }));
        }
        
        //如果为getClass则认为该方法非get方法
        if (needSkipGetterMethod.containsKey(methodName)
                && needSkipGetterMethod.get(methodName)
                        .isAssignableFrom(returnType)) {
            throw new InvalidGetterMethodException(
                    MessageUtils.format("getClass非get方法，it include needSkipGetterMethod.",
                            new Object[] { method.getParameterTypes(),
                                    returnType }));
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
            throw new InvalidGetterMethodException(
                    MessageUtils.format("方法名应该以is/get+首写字母为大写字母的字符串组成.methodName:{}",
                            new Object[] { methodName }));
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
        if (!Void.TYPE.equals(returnType)
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
        if (!Void.TYPE.equals(returnType)
                || method.getParameterTypes().length != 1) {
            throw new InvalidSetterMethodException(
                    MessageUtils.format("方法入参为空，或返回不为空.paramterTypes:{};returnType:{}",
                            new Object[] { method.getParameterTypes(),
                                    returnType }));
        }
        
        String setterName = null;
        if (methodName.length() > 3 && methodName.startsWith("set")
                && Character.isUpperCase(methodName.charAt(3))) {
            setterName = StringUtils.uncapitalize(methodName.substring(3));
        }
        
        if (!StringUtils.isEmpty(setterName)) {
            return setterName;
        } else {
            throw new InvalidSetterMethodException(
                    MessageUtils.format("方法名应该以set+首写字母为大写字母的字符串组成.methodName:{}",
                            new Object[] { methodName }));
        }
    }
    
    /**
      * 根据字段名推论得出字段对应的get方法<br/>
      *<功能详细描述>
      * @param fieldName
      * @param beanType [参数说明]
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
     * 根据字段名推论得出字段对应的get方法<br/>
     *<功能详细描述>
     * @param fieldName
     * @param beanType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String getGetMethodNameByGetterNameAndType(String getterName,
            Class<?> getterType) {
        String methodName = null;
        if (boolean.class.isAssignableFrom(getterType)) {
            //利用length>2排除掉fieldName = is的情况，如果为is对应方法isIs setIs
            if (getterName.length() > 2 && getterName.startsWith("is")
                    && Character.isUpperCase(getterName.charAt(3))) {
                methodName = getterName;
            } else {
                methodName = "is" + StringUtils.capitalize(getterName);
            }
        } else {
            methodName = "get" + StringUtils.capitalize(getterName);
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
        
        @SuppressWarnings("rawtypes")
        ClassReflector classReflector = ClassReflector.forClass(type);
        @SuppressWarnings("unchecked")
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
        @SuppressWarnings("rawtypes")
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
        @SuppressWarnings("rawtypes")
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
        @SuppressWarnings("rawtypes")
        ClassReflector reflector = ClassReflector.forClass(type);
        
        A res = null;
        //如果注解是可被继承的则查找其父类及接口的方法
        if (annotationType.isAnnotationPresent(Inherited.class)) {
            @SuppressWarnings("unchecked")
            List<Method> getterMethodList = reflector.getGetterMethodList(getterName);
            if (getterMethodList != null) {
                for (Method getterMethodTemp : getterMethodList) {
                    res = getterMethodTemp.getAnnotation(annotationType);
                    
                    if (res != null) {
                        return res;
                    }
                }
            }
        } else {
            Method getterMethod = reflector.getGetterMethod(getterName);
            if (getterMethod != null) {
                res = getterMethod.getAnnotation(annotationType);
            }
            if (res != null) {
                return res;
            }
        }
        
        Field field = reflector.getField(getterName);
        if (field != null) {
            res = field.getAnnotation(annotationType);
        }
        return res;
    }
}
