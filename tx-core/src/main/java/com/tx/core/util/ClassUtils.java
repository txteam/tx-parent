/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月3日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Class相关工具类
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class ClassUtils {
    
    private static final Set<Class<?>> commonSimpleClassSet = new HashSet<Class<?>>();
    
    static {
        commonSimpleClassSet.add(char.class);
        commonSimpleClassSet.add(char[].class);
        commonSimpleClassSet.add(Character.class);
        commonSimpleClassSet.add(Character[].class);
        commonSimpleClassSet.add(byte.class);
        commonSimpleClassSet.add(byte[].class);
        commonSimpleClassSet.add(Byte.class);
        commonSimpleClassSet.add(Byte[].class);
        commonSimpleClassSet.add(short.class);
        commonSimpleClassSet.add(short[].class);
        commonSimpleClassSet.add(Short.class);
        commonSimpleClassSet.add(Short[].class);
        commonSimpleClassSet.add(int.class);
        commonSimpleClassSet.add(int[].class);
        commonSimpleClassSet.add(long.class);
        commonSimpleClassSet.add(long[].class);
        commonSimpleClassSet.add(Long.class);
        commonSimpleClassSet.add(Long[].class);
        commonSimpleClassSet.add(boolean.class);
        commonSimpleClassSet.add(boolean[].class);
        commonSimpleClassSet.add(Boolean.class);
        commonSimpleClassSet.add(Boolean[].class);
        commonSimpleClassSet.add(double.class);
        commonSimpleClassSet.add(double[].class);
        commonSimpleClassSet.add(Double.class);
        commonSimpleClassSet.add(Double[].class);
        commonSimpleClassSet.add(float.class);
        commonSimpleClassSet.add(float[].class);
        commonSimpleClassSet.add(Float.class);
        commonSimpleClassSet.add(Float[].class);
        commonSimpleClassSet.add(String.class);
        commonSimpleClassSet.add(String[].class);
    }
    
    private static final Set<String> commonSimpleClassNameSet = new HashSet<String>();
    
    static {
        for (Class<?> classTemp : commonSimpleClassSet) {
            commonSimpleClassNameSet.add(classTemp.getName());
        }
    }
    
    /**
      * 判断是否是常用的基础类型<br/>
      *      用以支撑在代码生成时判断是否需要import对应的类
      *<功能详细描述>
      * @param className
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Deprecated
    public static boolean isCommonSimpleClass(String className) {
        boolean flag = commonSimpleClassNameSet.contains(className);
        return flag;
    }
    
}
