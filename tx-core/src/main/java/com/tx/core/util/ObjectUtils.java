/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-22
 * <修改描述:>
 */
package com.tx.core.util;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import com.thoughtworks.xstream.mapper.Mapper.Null;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 对象工具类<br/>
 *     主要补充commonslang包中方法不足
 *     利用该类补足对应方法<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-8-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ObjectUtils {
    
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
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return StringUtils.isEmpty((String) obj);
        } else if (obj instanceof Collection<?>) {
            return CollectionUtils.isEmpty((Collection<?>) obj);
        } else if (obj instanceof Map<?, ?>) {
            return MapUtils.isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof Object[]) {
            return ArrayUtils.isEmpty((Object[]) obj);
        } else {
            return false;
        }
    }
    
    /**
      * 根据依赖的属性名生成对象的HashCode
      *<功能详细描述>
      * @param thisObj
      * @param dependPropertyName
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int generateHashCode(Object thisObj,
            String... dependPropertyName) {
        if(thisObj == null){
            return Null.class.hashCode();
        }
        MetaObject metaObject = MetaObject.forObject(thisObj);
        
        int resHashCode = thisObj.getClass().hashCode();
        for(String propertyNameTemp : dependPropertyName){
            //String getterNameTemp = metaObject.getGetterNames();
        }
        return  resHashCode;
    }
    
    /**
      * 根据依赖的属性名，判断对象是否相等
      *<功能详细描述>
      * @param thisObj
      * @param otherObj
      * @param dependPropertyName
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean equals(Object thisObj, Object otherObj,
            String... dependPropertyName) {
        
        return false;
    }
}
