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
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.TypeDescriptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tx.component.basicdata.annotation.BasicDataEntity;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
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
     * 将对象转换为Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> PagedList<BD> fromDataDictPagedList(
            PagedList<DataDict> dataPagedList, Class<BD> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assignableFrom BasicData.",
                new Object[] { type });
        if (dataPagedList == null) {
            return null;
        }
        
        PagedList<BD> pagedList = new PagedList<>();
        pagedList.setCount(dataPagedList.getCount());
        pagedList.setPageIndex(dataPagedList.getPageIndex());
        pagedList.setPageSize(dataPagedList.getPageSize());
        pagedList.setQueryPageSize(dataPagedList.getQueryPageSize());
        List<BD> objList = new ArrayList<>();
        pagedList.setList(objList);
        for (DataDict dataTemp : dataPagedList.getList()) {
            objList.add(fromDataDict(dataTemp, type));
        }
        return pagedList;
    }
    
    /**
     * 转换对象为DataDict列表<br/>
     * <功能详细描述>
     * @param dataList
     * @param type
     * @return [参数说明]
     * 
     * @return List<BD> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static <BD extends BasicData> List<BD> fromDataDictList(
            List<DataDict> dataList, Class<BD> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assignableFrom BasicData.",
                new Object[] { type });
        if (dataList == null) {
            return null;
        }
        
        List<BD> objList = new ArrayList<>();
        for (DataDict dataTemp : dataList) {
            objList.add(fromDataDict(dataTemp, type));
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
    public static <BD extends BasicData> BD fromDataDict(DataDict data,
            Class<BD> type) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
                "type:{} is not assignableFrom BasicData.",
                new Object[] { type });
        if (data == null) {
            return null;
        }
        
        BD bd = ObjectUtils.newInstance(type);
        BeanWrapper bdBW = PropertyAccessorFactory.forBeanPropertyAccess(bd);
        
        BeanWrapper dataBW = PropertyAccessorFactory
                .forBeanPropertyAccess(data);
        for (PropertyDescriptor pd : dataBW.getPropertyDescriptors()) {
            String property = pd.getName();
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null
                    || "attributes".equals(property)
                    || "attributeJSONObject".equals(property)) {
                continue;
            }
            Object value = dataBW.getPropertyValue(property);
            
            PropertyDescriptor bdBWPDTemp = bdBW
                    .getPropertyDescriptor(property);
            if (bdBWPDTemp == null || bdBWPDTemp.getWriteMethod() == null) {
                continue;
            }
            bdBW.setPropertyValue(property, value);
        }
        if (!MapUtils.isEmpty(data.getAttributeJSONObject())) {
            for (Entry<String, Object> entryTemp : data.getAttributeJSONObject()
                    .entrySet()) {
                String key = entryTemp.getKey();
                Object value = entryTemp.getValue();
                
                PropertyDescriptor bdBWPDTemp = bdBW.getPropertyDescriptor(key);
                if (bdBWPDTemp == null || bdBWPDTemp.getWriteMethod() == null) {
                    continue;
                }
                bdBW.setPropertyValue(key, value);
            }
        }
        return bd;
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
    public static <BD extends BasicData> DataDict toDataDict(BD object) {
        if (object == null) {
            return null;
        }
        
        //将对象转换为Map映射<br/>
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
            if (tdTemp.hasAnnotation(Transient.class)) {
                continue;
            }
            
            Object value = pdTemp.getValue(property);
            if (value == null) {
                continue;
            }
            resMap.put(property, value);
        }
        
        DataDict data = new DataDict();
        BeanWrapper ddBW = PropertyAccessorFactory.forBeanPropertyAccess(data);
        for (Entry<String, Object> entryTemp : resMap.entrySet()) {
            String key = entryTemp.getKey();
            if (ddBW.isWritableProperty(key)) {
                ddBW.setPropertyValue(key, entryTemp.getValue());
            } else {
                data.getAttributeJSONObject().put(key,
                        Objects.toString(entryTemp.getValue()));
            }
        }
        
        return data;
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
    public static <BD extends BasicData> List<DataDict> toDataDictList(
            List<BD> objectList) {
        if (objectList == null) {
            return null;
        }
        
        List<DataDict> ddList = new ArrayList<>();
        for (BD object : objectList) {
            ddList.add(toDataDict(object));
        }
        return ddList;
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
    public static <BD extends BasicData> PagedList<DataDict> toDataDictPagedList(
            PagedList<BD> objectPagedList) {
        if (objectPagedList == null) {
            return null;
        }
        
        PagedList<DataDict> pagedList = new PagedList<>();
        pagedList.setCount(objectPagedList.getCount());
        pagedList.setPageIndex(objectPagedList.getPageIndex());
        pagedList.setPageSize(objectPagedList.getPageSize());
        pagedList.setQueryPageSize(objectPagedList.getQueryPageSize());
        List<DataDict> ddList = new ArrayList<>();
        pagedList.setList(ddList);
        
        for (BD object : objectPagedList.getList()) {
            ddList.add(toDataDict(object));
        }
        return pagedList;
    }
    
    //    /**
    //     * 将对象转换为Map<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return Map<String,Object> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static <BD extends BasicData> PagedList<List<Map<String, Object>>> toMapPagedList(
    //            PagedList<BD> objectPagedList) {
    //        if (objectPagedList == null) {
    //            return null;
    //        }
    //        
    //        PagedList<List<Map<String, Object>>> pagedList = new PagedList<>();
    //        pagedList.setCount(objectPagedList.getCount());
    //        pagedList.setPageIndex(objectPagedList.getPageIndex());
    //        pagedList.setPageSize(objectPagedList.getPageSize());
    //        pagedList.setQueryPageSize(objectPagedList.getQueryPageSize());
    //        List<Map<String, Object>> mapList = new ArrayList<>();
    //        pagedList.setList(mapList);
    //        
    //        for (BD object : objectPagedList.getList()) {
    //            mapList.add(toMap(object));
    //        }
    //        return pagedList;
    //    }
    //    
    //    /**
    //     * 将对象转换为Map<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return Map<String,Object> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static <BD extends BasicData> List<Map<String, Object>> toMapList(
    //            List<BD> objectList) {
    //        if (objectList == null) {
    //            return null;
    //        }
    //        
    //        List<Map<String, Object>> mapList = new ArrayList<>();
    //        for (BD object : objectList) {
    //            mapList.add(toMap(object));
    //        }
    //        return mapList;
    //    }
    //    
    //    /**
    //     * 将对象转换为Map<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return Map<String,Object> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static <BD extends BasicData> Map<String, Object> toMap(BD object) {
    //        if (object == null) {
    //            return null;
    //        }
    //        
    //        Map<String, Object> resMap = new HashMap<>();
    //        BeanWrapper objBW = PropertyAccessorFactory
    //                .forBeanPropertyAccess(object);
    //        for (PropertyDescriptor pdTemp : objBW.getPropertyDescriptors()) {
    //            if (pdTemp.getReadMethod() == null
    //                    || pdTemp.getWriteMethod() == null) {
    //                continue;
    //            }
    //            String property = pdTemp.getName();
    //            TypeDescriptor tdTemp = objBW.getPropertyTypeDescriptor(property);
    //            if (tdTemp.hasAnnotation(JsonIgnore.class)) {
    //                continue;
    //            }
    //            if (tdTemp.hasAnnotation(Transient.class)) {
    //                continue;
    //            }
    //            
    //            Object value = pdTemp.getValue(property);
    //            if (value == null) {
    //                continue;
    //            }
    //            resMap.put(property, value);
    //        }
    //        return resMap;
    //    }
    //    
    //    /**
    //     * 将对象转换为Map<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return Map<String,Object> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static <BD extends BasicData> PagedList<BD> fromMapPagedList(
    //            PagedList<Map<String, Object>> mapPagedList, Class<BD> type) {
    //        AssertUtils.notNull(type, "type is null.");
    //        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
    //                "type:{} is not assignableFrom BasicData.",
    //                new Object[] { type });
    //        if (mapPagedList == null) {
    //            return null;
    //        }
    //        
    //        PagedList<BD> pagedList = new PagedList<>();
    //        pagedList.setCount(mapPagedList.getCount());
    //        pagedList.setPageIndex(mapPagedList.getPageIndex());
    //        pagedList.setPageSize(mapPagedList.getPageSize());
    //        pagedList.setQueryPageSize(mapPagedList.getQueryPageSize());
    //        List<BD> objList = new ArrayList<>();
    //        pagedList.setList(objList);
    //        for (Map<String, Object> mapTemp : mapPagedList.getList()) {
    //            objList.add(fromMap(mapTemp, type));
    //        }
    //        return pagedList;
    //    }
    //    
    //    /**
    //     * 从MapList转换为实例List
    //     * <功能详细描述>
    //     * @param mapList
    //     * @param type
    //     * @return [参数说明]
    //     * 
    //     * @return List<BD> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static <BD extends BasicData> List<BD> fromMapList(
    //            List<Map<String, Object>> mapList, Class<BD> type) {
    //        AssertUtils.notNull(type, "type is null.");
    //        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
    //                "type:{} is not assignableFrom BasicData.",
    //                new Object[] { type });
    //        if (mapList == null) {
    //            return null;
    //        }
    //        
    //        List<BD> objList = new ArrayList<>();
    //        for (Map<String, Object> mapTemp : mapList) {
    //            objList.add(fromMap(mapTemp, type));
    //        }
    //        return objList;
    //    }
    //    
    //    /**
    //     * 生成基础数据对象<br/>
    //     * <功能详细描述>
    //     * @param map
    //     * @param type
    //     * @return [参数说明]
    //     * 
    //     * @return BD [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static <BD extends BasicData> BD fromMap(Map<String, Object> map,
    //            Class<BD> type) {
    //        AssertUtils.notNull(type, "type is null.");
    //        AssertUtils.isTrue(BasicData.class.isAssignableFrom(type),
    //                "type:{} is not assignableFrom BasicData.",
    //                new Object[] { type });
    //        if (map == null) {
    //            return null;
    //        }
    //        
    //        BD bd = ObjectUtils.newInstance(type);
    //        BeanWrapper objBW = PropertyAccessorFactory.forBeanPropertyAccess(bd);
    //        
    //        for (Entry<String, Object> entryTemp : map.entrySet()) {
    //            String key = entryTemp.getKey();
    //            PropertyDescriptor pdTemp = objBW.getPropertyDescriptor(key);
    //            //            TypeDescriptor tdTemp = objBW.getPropertyTypeDescriptor(key);
    //            if (pdTemp == null || pdTemp.getReadMethod() == null
    //                    || pdTemp.getWriteMethod() == null) {
    //                continue;
    //            }
    //            //            if (tdTemp.hasAnnotation(JsonIgnore.class)) {
    //            //                continue;
    //            //            }
    //            
    //            Object value = entryTemp.getValue();
    //            objBW.setPropertyValue(key, value);
    //        }
    //        return bd;
    //    }
    
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
    
    //    public static void main(String[] args) {
    //        DataDict dd = new DataDict();
    //        BeanWrapper objBW = PropertyAccessorFactory.forBeanPropertyAccess(dd);
    //        
    //        System.out.println(objBW.getPropertyTypeDescriptor("parent")
    //                .hasAnnotation(JsonIgnore.class));
    //        
    //        BeanWrapper testBW = new BeanWrapperImpl(DataDict.class);
    //        System.out.println(testBW.getPropertyTypeDescriptor("parent")
    //                .hasAnnotation(JsonIgnore.class));
    //    }
}
