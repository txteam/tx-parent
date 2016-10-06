/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.core.support.entrysupport.helper;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.entrysupport.model.EntityEntry;
import com.tx.core.support.entrysupport.model.EntryAble;
import com.tx.core.util.ObjectUtils;

/**
 * EntryAble工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class EntryAbleUtils {
    
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
    public static <EA extends EntryAble<EntityEntry>> EA toEntryAble(
            Class<EA> type, Object object) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notNull(object, "object is null.");
        
        EA entryAbleEntity = ObjectUtils.newInstance(type);
        BeanWrapper eaeBW = PropertyAccessorFactory.forBeanPropertyAccess(entryAbleEntity);
        
        BeanWrapper objBW = PropertyAccessorFactory.forBeanPropertyAccess(object);
        for (PropertyDescriptor pd : objBW.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            if ("class".equals(pd.getName())
                    || !objBW.isReadableProperty(propertyName)) {
                continue;
            }
            Object value = objBW.getPropertyValue(propertyName);
            if (eaeBW.isWritableProperty(propertyName)) {
                eaeBW.setPropertyValue(propertyName, value);
            } else {
                if (entryAbleEntity.getEntryList() == null) {
                    entryAbleEntity.setEntryList(new ArrayList<EntityEntry>());
                }
                entryAbleEntity.getEntryList().add(new EntityEntry(propertyName,
                        value == null ? null : value.toString()));
            }
        }
        return entryAbleEntity;
    }
    
    /**
      * 由EntryAble对象转换指定对象<br/>
      * <功能详细描述>
      * @param type
      * @param entryAbleEntity
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <EA extends EntryAble<EntityEntry>, T> T fromEntryAble(
            Class<T> type, EA entryAbleEntity) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notNull(entryAbleEntity, "dataDict is null.");
        
        BeanWrapper eaeBW = PropertyAccessorFactory.forBeanPropertyAccess(entryAbleEntity);
        
        T object = ObjectUtils.newInstance(type);
        BeanWrapper objBW = PropertyAccessorFactory.forBeanPropertyAccess(object);
        
        Set<String> writedFieldNameSet = new HashSet<>();
        for (PropertyDescriptor pd : eaeBW.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            if ("class".equals(pd.getName())
                    || !eaeBW.isReadableProperty(propertyName)) {
                continue;
            }
            Object value = eaeBW.getPropertyValue(propertyName);
            
            if (objBW.isWritableProperty(propertyName)) {
                writedFieldNameSet.add(propertyName);
                objBW.setPropertyValue(propertyName, value);
            }
        }
        
        if(!CollectionUtils.isEmpty(entryAbleEntity.getEntryList())){
            for(EntityEntry entityEntry : entryAbleEntity.getEntryList()){
                String entryKey = entityEntry.getEntryKey();
                String entryValue = entityEntry.getEntryValue();
                if(writedFieldNameSet.contains(entryKey)){
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
