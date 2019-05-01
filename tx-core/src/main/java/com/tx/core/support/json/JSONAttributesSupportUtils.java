/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.core.support.json;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ClassUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ObjectUtils;

/**
 * JSONAttributesSupportUtils<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JSONAttributesSupportUtils {
    
    /**
     * 将对象转换为指定的EntryAble对象实例<br/>
     * <功能详细描述>
     * @param object
     * @return [参数说明]
     * 
     * @return DataDict [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <EA extends JSONAttributesSupport> EA toJSONAttributesSupport(
            Class<EA> type, Object object) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notNull(object, "object is null.");
        
        EA attrEntity = ObjectUtils.newInstance(type);
        BeanWrapper attrBW = PropertyAccessorFactory
                .forBeanPropertyAccess(attrEntity);
        
        BeanWrapper objBW = PropertyAccessorFactory
                .forBeanPropertyAccess(object);
        for (PropertyDescriptor pd : objBW.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            if ("class".equals(pd.getName())
                    || !objBW.isReadableProperty(propertyName)
                    || !objBW.isWritableProperty(propertyName)) {
                continue;
            }
            
            Object value = objBW.getPropertyValue(propertyName);
            if (attrBW.isWritableProperty(propertyName)) {
                attrBW.setPropertyValue(propertyName, value);
            } else {
                if (ClassUtils.isPrimitiveOrWrapper(value.getClass())
                        || value.getClass().isEnum() || ClassUtils.isAssignable(
                                CharSequence.class, value.getClass())) {
                    //如果不为枚举或基础类型或可以toString的字段均跳过
                    attrEntity.getAttributeJSONObject().put(propertyName,
                            String.valueOf(value));
                }
            }
        }
        return attrEntity;
    }
    
    /**
     * 由EntryAble对象转换指定对象<br/>
     * <功能详细描述>
     * @param type
     * @param attrEntity
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static <EA extends JSONAttributesSupport, T> T fromJSONAttributesSupport(
            Class<T> type, EA attrEntity) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notNull(attrEntity, "attrEntity is null.");
        
        BeanWrapper attrBW = PropertyAccessorFactory
                .forBeanPropertyAccess(attrEntity);
        
        T object = ObjectUtils.newInstance(type);
        BeanWrapper objBW = PropertyAccessorFactory
                .forBeanPropertyAccess(object);
        
        Set<String> writedFieldNameSet = new HashSet<>();
        for (PropertyDescriptor pd : attrBW.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            if ("class".equals(pd.getName())
                    || "attributes".equals(pd.getName())
                    || !attrBW.isReadableProperty(propertyName)
                    || !attrBW.isWritableProperty(propertyName)) {
                continue;
            }
            
            Object value = attrBW.getPropertyValue(propertyName);
            if (objBW.isWritableProperty(propertyName)) {
                writedFieldNameSet.add(propertyName);
                objBW.setPropertyValue(propertyName, value);
            }
        }
        
        if (!ObjectUtils.isEmpty(attrEntity.getAttributeJSONObject())
                && !MapUtils.isEmpty(attrEntity.getAttributeJSONObject())) {
            for (Entry<String, Object> entryTemp : attrEntity
                    .getAttributeJSONObject().entrySet()) {
                String entryKey = entryTemp.getKey();
                Object entryValue = entryTemp.getValue();
                if (writedFieldNameSet.contains(entryKey)) {
                    continue;
                }
                
                if (objBW.isWritableProperty(entryKey)) {
                    objBW.setPropertyValue(entryKey, entryValue);
                }
            }
        }
        return object;
    }
}
