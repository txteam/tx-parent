/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tx.component.basicdata.annotation.BasicDataEntity;
import com.tx.component.basicdata.model.BasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.ObjectUtils;

/**
 * 基础数据工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataUtils {
    
    /**
     * 对应基础数据编码<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getType(Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        String code = entityClass.getSimpleName();
        if (entityClass.isAnnotationPresent(BasicDataEntity.class)
                && StringUtils.isNotEmpty(entityClass
                        .getAnnotation(BasicDataEntity.class).type())) {
            code = entityClass.getAnnotation(BasicDataEntity.class).type();
        }
        return code;
    }
    
    /**
     * 获取对应的表名<br/>
     *     用户写入基础数据类型中
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getTableName(Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        String tableName = entityClass.getSimpleName();
        if (entityClass.isAnnotationPresent(Table.class)) {
            tableName = entityClass.getAnnotation(Table.class).name();
        }
        if (entityClass.isAnnotationPresent(Entity.class)) {
            tableName = entityClass.getAnnotation(Entity.class).name();
        }
        if (StringUtils.isEmpty(tableName)) {
            tableName = "bd_data_dict";
        }
        return tableName;
    }
    
    /**
     * 将对象转换为Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> PagedList<List<Map<String, Object>>> toMapPagedList(
            PagedList<BD> objectPagedList) {
        if (objectPagedList == null) {
            return null;
        }
        
        PagedList<List<Map<String, Object>>> pagedList = new PagedList<>();
        pagedList.setCount(objectPagedList.getCount());
        pagedList.setPageIndex(objectPagedList.getPageIndex());
        pagedList.setPageSize(objectPagedList.getPageSize());
        pagedList.setQueryPageSize(objectPagedList.getQueryPageSize());
        List<Map<String, Object>> mapList = new ArrayList<>();
        pagedList.setList(mapList);
        
        for (BD object : objectPagedList.getList()) {
            mapList.add(toMap(object));
        }
        return pagedList;
    }
    
    /**
     * 将对象转换为Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> List<Map<String, Object>> toMapList(
            List<BD> objectList) {
        if (objectList == null) {
            return null;
        }
        
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BD object : objectList) {
            mapList.add(toMap(object));
        }
        return mapList;
    }
    
    /**
     * 将对象转换为Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> Map<String, Object> toMap(BD object) {
        if (object == null) {
            return null;
        }
        
        Map<String, Object> resMap = new HashMap<>();
        BeanWrapper objBW = PropertyAccessorFactory
                .forBeanPropertyAccess(object);
        for (PropertyDescriptor pdTemp : objBW.getPropertyDescriptors()) {
            if (pdTemp.getReadMethod() == null
                    || pdTemp.getWriteMethod() == null) {
                continue;
            }
            String property = pdTemp.getName();
            TypeDescriptor tdTemp = objBW.getPropertyTypeDescriptor(property);
            if (tdTemp.hasAnnotation(JsonIgnore.class)) {
                continue;
            }
            
            Object value = pdTemp.getValue(property);
            if (value == null) {
                continue;
            }
            resMap.put(property, value);
        }
        return resMap;
    }
    
    /**
     * 将对象转换为Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> PagedList<BD> fromMapPagedList(
            PagedList<Map<String, Object>> mapPagedList, Class<BD> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assignableFrom BasicData.",
                new Object[] { type });
        if (mapPagedList == null) {
            return null;
        }
        
        PagedList<BD> pagedList = new PagedList<>();
        pagedList.setCount(mapPagedList.getCount());
        pagedList.setPageIndex(mapPagedList.getPageIndex());
        pagedList.setPageSize(mapPagedList.getPageSize());
        pagedList.setQueryPageSize(mapPagedList.getQueryPageSize());
        List<BD> objList = new ArrayList<>();
        pagedList.setList(objList);
        for (Map<String, Object> mapTemp : mapPagedList.getList()) {
            objList.add(fromMap(mapTemp, type));
        }
        return pagedList;
    }
    
    /**
     * 从MapList转换为实例List
     * <功能详细描述>
     * @param mapList
     * @param type
     * @return [参数说明]
     * 
     * @return List<BD> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> List<BD> fromMapList(
            List<Map<String, Object>> mapList, Class<BD> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assignableFrom BasicData.",
                new Object[] { type });
        if (mapList == null) {
            return null;
        }
        
        List<BD> objList = new ArrayList<>();
        for (Map<String, Object> mapTemp : mapList) {
            objList.add(fromMap(mapTemp, type));
        }
        return objList;
    }
    
    /**
     * 生成基础数据对象<br/>
     * <功能详细描述>
     * @param map
     * @param type
     * @return [参数说明]
     * 
     * @return BD [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> BD fromMap(Map<String, Object> map,
            Class<BD> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assignableFrom BasicData.",
                new Object[] { type });
        if (map == null) {
            return null;
        }
        
        BD bd = ObjectUtils.newInstance(type);
        BeanWrapper objBW = PropertyAccessorFactory.forBeanPropertyAccess(bd);
        
        for (Entry<String, Object> entryTemp : map.entrySet()) {
            String key = entryTemp.getKey();
            PropertyDescriptor pdTemp = objBW.getPropertyDescriptor(key);
            //            TypeDescriptor tdTemp = objBW.getPropertyTypeDescriptor(key);
            if (pdTemp == null || pdTemp.getReadMethod() == null
                    || pdTemp.getWriteMethod() == null) {
                continue;
            }
            //            if (tdTemp.hasAnnotation(JsonIgnore.class)) {
            //                continue;
            //            }
            
            Object value = entryTemp.getValue();
            objBW.setPropertyValue(key, value);
        }
        return bd;
    }
    
    //    public static void main(String[] args) {
    //        DataDict dd = new DataDict();
    //        BeanWrapper objBW = PropertyAccessorFactory.forBeanPropertyAccess(dd);
    //        
    //        System.out.println(objBW.getPropertyTypeDescriptor("parent").hasAnnotation(JsonIgnore.class));
    //    }
}
