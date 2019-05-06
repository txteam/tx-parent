/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.tx.component.basicdata.annotation.BasicDataEntity;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataEntityInfo;
import com.tx.component.basicdata.model.BasicDataViewTypeEnum;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * BasicDataType的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataEntityRegistry {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(BasicDataEntityRegistry.class);
    
    /** 编码到类型的映射 */
    private final Map<String, Class<? extends BasicData>> entityClassMap = new HashMap<>();
    
    /** 编码到类型的映射 */
    private final Map<String, String> moduleMap = new HashMap<>();
    
    /** 类型到类型的映射 */
    private final Map<String, BasicDataEntityInfo> entityInfoMap = new HashMap<>();
    
    /** 类型到类型的映射 */
    private final Map<String, BasicDataService<? extends BasicData>> serviceMap = new HashMap<>();
    
    /** 编码到类型的映射 */
    private final Map<Class<? extends BasicData>, String> class2typeMap = new HashMap<>();
    
    /** 编码到类型的映射 */
    private final Map<Class<? extends BasicData>, String> class2moduleMap = new HashMap<>();
    
    /** 类型到类型的映射 */
    private final Map<Class<? extends BasicData>, BasicDataEntityInfo> class2entityInfoMap = new HashMap<>();
    
    /** 类型到类型的映射 */
    private final Map<Class<? extends BasicData>, BasicDataService<? extends BasicData>> class2serviceMap = new HashMap<>();
    
    /** <默认构造函数> */
    public BasicDataEntityRegistry() {
        super();
    }
    
    /**
     * 注册基础数据<br/>
     * <功能详细描述>
     * @param type [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void register(String module,
            BasicDataService<? extends BasicData> service) {
        AssertUtils.notNull(service, "service is null.");
        
        /** 获取RawType(泛型类型) */
        Class<? extends BasicData> rawType = service.getRawType();
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(rawType),
                "rawType:{} is not assignableFrom BasicData.",
                new Object[] { rawType });
        
        //尝试实例化，确认rawType能够被实例化
        BeanUtils.instantiateClass(rawType, BasicData.class);
        
        //如果是注入的本地业务层;则直接
        String type = service.type();
        String tableName = service.tableName();
        
        BasicDataEntityInfo info = new BasicDataEntityInfo();
        info.setModule(module);
        info.setEntityClass(rawType);
        info.setTableName(tableName);
        info.setType(type);
        if (rawType.isAnnotationPresent(BasicDataEntity.class)) {
            BasicDataEntity anno = rawType.getAnnotation(BasicDataEntity.class);
            info.setName(StringUtils.isEmpty(anno.name())
                    ? rawType.getSimpleName() : anno.name());
            info.setRemark(anno.remark());
            info.setViewType(anno.viewType() == null
                    ? BasicDataViewTypeEnum.COMMON_PAGEDLIST : anno.viewType());
        } else {
            info.setName(rawType.getSimpleName());
            info.setRemark("");
            info.setViewType(BasicDataViewTypeEnum.COMMON_PAGEDLIST);
        }
        
        AssertUtils.isTrue(!entityClassMap.containsKey(rawType),
                "rawType:{} : service :{} is exist.",
                new Object[] { rawType, service });
        
        //注册写入<br/>
        this.entityClassMap.put(type, rawType);
        this.moduleMap.put(type, info.getModule());
        this.entityInfoMap.put(type, info);
        this.serviceMap.put(type, service);
        this.class2typeMap.put(rawType, type);
        this.class2moduleMap.put(rawType, info.getModule());
        this.class2entityInfoMap.put(rawType, info);
        this.class2serviceMap.put(rawType, service);
    }
    
    /**
     * 获取实例信息列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<BasicDataEntityInfo> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BasicDataEntityInfo> getEntityInfoList() {
        List<BasicDataEntityInfo> infoList = new ArrayList<>();
        
        for (BasicDataEntityInfo info : this.entityInfoMap.values()) {
            infoList.add(info);
        }
        return infoList;
    }
    
    /**
     * 获取实例信息列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<BasicDataEntityInfo> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<BasicDataEntityInfo> getEntityInfoList(String module,
            List<BasicDataViewTypeEnum> viewTypes) {
        List<BasicDataEntityInfo> infoList = new ArrayList<>(
                this.entityInfoMap.values());
        
        if (!StringUtils.isEmpty(module)) {
            CollectionUtils.filter(infoList,
                    (BasicDataEntityInfo object) -> !module
                            .equals(object.getModule()));
        }
        if (!CollectionUtils.isEmpty(viewTypes)) {
            CollectionUtils.filter(infoList,
                    (BasicDataEntityInfo object) -> !viewTypes
                            .contains(object.getViewType()));
        }
        
        return infoList;
    }
    
    /**
     * 根据类型名称获取对应的类实例<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return Class<? extends BasicData> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<? extends BasicData> getEntityClass(String type) {
        AssertUtils.notEmpty(type, "type is empty.");
        
        Class<? extends BasicData> entityClass = this.entityClassMap.get(type);
        return entityClass;
    }
    
    /**
     * 获取类对应的类型名<br/>
     * <功能详细描述>
     * @param entityClass
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getType(Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        String type = this.class2typeMap.get(entityClass);
        return type;
    }
    
    /**
     * 获取实例信息<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return BasicDataEntityInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BasicDataEntityInfo getEntityInfo(String type) {
        AssertUtils.notEmpty(type, "type is empty.");
        
        BasicDataEntityInfo info = this.entityInfoMap.get(type);
        return info;
    }
    
    /**
     * 获取实例信息<br/>
     * <功能详细描述>
     * @param entityClass
     * @return [参数说明]
     * 
     * @return BasicDataEntityInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BasicDataEntityInfo getEntityInfo(
            Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        BasicDataEntityInfo info = this.class2entityInfoMap.get(entityClass);
        return info;
    }
    
    /**
     * 获取实例信息<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return BasicDataEntityInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getModule(String type) {
        AssertUtils.notEmpty(type, "type is empty.");
        
        String module = this.moduleMap.get(type);
        return module;
    }
    
    /**
     * 获取实例信息<br/>
     * <功能详细描述>
     * @param entityClass
     * @return [参数说明]
     * 
     * @return BasicDataEntityInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getModule(Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        String module = this.class2moduleMap.get(entityClass);
        return module;
    }
    
    /**
     * 获取实例信息<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return BasicDataEntityInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BasicDataService<? extends BasicData> getService(String type) {
        AssertUtils.notEmpty(type, "type is empty.");
        
        BasicDataService<? extends BasicData> service = this.serviceMap
                .get(type);
        return service;
    }
    
    /**
     * 获取实例信息<br/>
     * <功能详细描述>
     * @param entityClass
     * @return [参数说明]
     * 
     * @return BasicDataEntityInfo [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BasicDataService<? extends BasicData> getService(
            Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        BasicDataService<? extends BasicData> service = this.class2serviceMap
                .get(entityClass);
        return service;
    }
    
}