/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-24
 * <修改描述:>
 */
package com.tx.core.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 用以支持ognl判断的表达式工具类<br/>
 * 主要是根据对象的实际类型，使用合适的方法进行判定
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OgnlUtils {
    
    /**
      * 是否相等<br/>
      *    判断两个非控值是否相等
      *    如果obj为null会返回false
      *    obj 允许类型为 Boolean,String,Date
      *    OgnlUtils.isEquals(Boolean.TRUE,"true")  第二个字符串的转译依赖于BooleanUtils.toBoolean(other)
      *    OgnlUtils.isEquals("xxx","other")
      *    OgnlUtils.isEquals(Boolean.TRUE,"2013-01-02")
      * @param obj
      * @param other
      * @param args
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isEquals(Object obj, String other, String... args) {
        AssertUtils.notEmpty(other, "other:{} is empty.", other);
        if (isEmpty(obj)) {
            return false;
        }
        if (obj instanceof Boolean) {
            Boolean otherBoolean = BooleanUtils.toBoolean(other);
            return obj.equals(otherBoolean);
        } else if (obj instanceof String) {
            return other.equals(obj);
        } else if (obj instanceof BigDecimal) {
            return new BigDecimal(other).compareTo((BigDecimal) obj) == 0;
        } else if (obj instanceof Date && args.length > 1) {
            return other.equals(DateFormatUtils.format((Date) obj, args[0]));
        } else{
            return other.equals(obj.toString());
        }
    }
    
    /**
      * 判断入参数是否为空<br/>
      * " "不会被判定为false<br/>
      * <功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Object obj) {
        //为空时认为是empty的
        return ObjectUtils.isEmpty(obj);
    }
    
    /**
      * 判断对象是否不为空
      * <功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isNotEmpty(Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }
    
}
