/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年3月20日
 * <修改描述:>
 */
package com.tx.component.configuration.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.tx.component.configuration.model.ConfigProperty;

/**
 * 配置容器工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年3月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContextUtils {
    
    /**
     * 获取配置属性的实际值<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getValue(ConfigProperty configProperty) {
        String value = configProperty == null ? null
                : configProperty.getValue();
        return value;
    }
    
    /**
     * 获取Boolean类型的值<br/>
     * <pre>
     *   getValueBoolean(null)    = null
     *   getValueBoolean("true")  = Boolean.TRUE
     *   getValueBoolean("false") = Boolean.FALSE
     *   getValueBoolean("on")    = Boolean.TRUE
     *   getValueBoolean("ON")    = Boolean.TRUE
     *   getValueBoolean("yEs")   = Boolean.TRUE
     *   getValueBoolean("off")   = Boolean.FALSE
     *   getValueBoolean("oFf")   = Boolean.FALSE
     *   getValueBoolean("1")     = Boolean.TRUE
     *   getValueBoolean("0")     = Boolean.FALSE
     *   getValueBoolean("blue")  = null
     * </pre>
     * 
     * @param key 键
     * 
     * @return Boolean 值,可能为null
     * @exception [异常类型] [异常说明]
     * @see org.apache.commons.lang3.BooleanUtils#toBooleanObject(String)
     */
    public static Boolean getBooleanValue(ConfigProperty configProperty) {
        String value = getValue(configProperty);
        if (value == null) {
            return null;
        }
        
        // 优先考虑value最可能出现的情况:字符串"true"和"false"
        if (StringUtils.equalsAnyIgnoreCase("true", value)) {
            return Boolean.TRUE;
        } else if (StringUtils.equalsAnyIgnoreCase("false", value)) {
            return Boolean.FALSE;
        } else if (NumberUtils.isDigits(value)) {
            if ("0".equals(value)) {
                return false;
            } else {
                return true;
            }
        } else {
            //BooleanUtils.toBooleanObject(String) 方法没有考虑到 "0" 和 "1" 的问题.
            return BooleanUtils.toBooleanObject(value);
        }
    }
    
    /**
     * 获取boolean类型的值
     * 
     * <pre>
     *   getValueBoolean(null)    = defaultBoolean
     *   getValueBoolean("true")  = Boolean.TRUE
     *   getValueBoolean("false") = Boolean.FALSE
     *   getValueBoolean("on")    = Boolean.TRUE
     *   getValueBoolean("ON")    = Boolean.TRUE
     *   getValueBoolean("yEs")   = Boolean.TRUE
     *   getValueBoolean("off")   = Boolean.FALSE
     *   getValueBoolean("oFf")   = Boolean.FALSE
     *   getValueBoolean("1")     = Boolean.TRUE
     *   getValueBoolean("0")     = Boolean.FALSE
     *   getValueBoolean("blue")  = defaultBoolean
     * </pre>
     * 
     * @param key 键
     * @param defaultBoolean 默认值
     * 
     * @return boolean 值
     * @exception [异常类型] [异常说明]
     * @see org.apache.commons.lang3.BooleanUtils#toBooleanObject(String)
     */
    public static Boolean getBooleanValue(ConfigProperty configProperty,
            boolean defaultBoolean) {
        try {
            Boolean b = getBooleanValue(configProperty);
            return b == null ? defaultBoolean : b;
        } catch (Exception e) {
            return defaultBoolean;
        }
    }
    
    /**
     * 获取Integer类型的值
     * 
     * @param key 键
     * 
     * @return Integer 值,可能为null
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Integer getIntegerValue(ConfigProperty configProperty) {
        String value = getValue(configProperty);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }
    
    /**
     * 获取int类型的值
     * 
     * @param key 键
     * @param defaultLong 默认值
     * 
     * @return int 值
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Integer getIntegerValue(ConfigProperty configProperty,
            int defaultInt) {
        try {
            Integer i = getIntegerValue(configProperty);
            return i == null ? defaultInt : i;
        } catch (Exception e) {
            return defaultInt;
        }
    }
    
    /**
     * 获取Long类型的值
     * 
     * @param key 键
     * 
     * @return Long 值,可能为null
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Long getLongValue(ConfigProperty configProperty) {
        String value = getValue(configProperty);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Long.valueOf(value);
    }
    
    /**
     * 获取long类型的值
     * 
     * @param key 键
     * @param defaultLong 默认值
     * 
     * @return long 值
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static Long getLongValue(ConfigProperty configProperty,
            long defaultLong) {
        try {
            Long l = getLongValue(configProperty);
            return l == null ? defaultLong : l;
        } catch (Exception e) {
            return defaultLong;
        }
    }
}
