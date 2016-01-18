/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月11日
 * <修改描述:>
 */
package com.tx.core.util;

/**
 * 字符串工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年12月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringUtils {
    
    /**
      * 是否为空<br/>
      * <功能详细描述>
      * @param str
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(CharSequence str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }
    
    /**
     * 检查字符串是否非空<br/>
     * <功能详细描述>
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean isNotEmpty(CharSequence str) {
        return org.apache.commons.lang3.StringUtils.isNotEmpty(str);
    }
    
    /**
      * 截取字符串
      *    根据字符的长度进行处理<BR/>
      * <功能详细描述>
      * @param sourceString
      * @param start
      * @param end 可以为负值
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String substring(String str, int start, int end) {
        String resString = org.apache.commons.lang3.StringUtils.substring(str,
                start,
                end);
        return resString;
    }
    
    /**
      * 截取字符串
      *    根据字符的长度进行处理<BR/>
      * @param str
      * @param start
      * @param end
      * @param ending
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String substring(String str, int start, int end, String ending) {
        if (str == null) {
            return null;
        }
        String resString = null;
        if (str.length() <= end) {
            resString = org.apache.commons.lang3.StringUtils.substring(str,
                    start,
                    end);
        } else {
            resString = org.apache.commons.lang3.StringUtils.substring(str,
                    start,
                    end) + ending;
        }
        return resString;
    }
    
    /**
      * 根据指定长度截取字符串长度<br/>
      * <功能详细描述>
      * @param str
      * @param start
      * @param length
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String substr(String str, int start, int length) {
        if (str == null) {
            return null;
        }
        String resString = org.apache.commons.lang3.StringUtils.substring(str,
                start,
                start + length);
        return resString;
    }
    
    /**
      * 根据指定长度截取字符串<br/>
      * <功能详细描述>
      * @param str
      * @param start
      * @param length
      * @param ending
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String substr(String str, int start, int length, String ending) {
        if (str == null) {
            return null;
        }
        String resString = null;
        if (str.length() <= (start + length)) {
            resString = org.apache.commons.lang3.StringUtils.substring(str,
                    start,
                    start + length);
        } else {
            resString = org.apache.commons.lang3.StringUtils.substring(str,
                    start,
                    (start + length)) + ending;
        }
        return resString;
    }
}
