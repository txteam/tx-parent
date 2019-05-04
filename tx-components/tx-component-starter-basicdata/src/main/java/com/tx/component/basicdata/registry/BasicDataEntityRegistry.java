/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.registry;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.basicdata.annotation.BasicDataEntity;
import com.tx.component.basicdata.context.BasicDataService;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataEntityInfo;
import com.tx.component.basicdata.model.BasicDataViewTypeEnum;
import com.tx.component.basicdata.util.BasicDataUtils;
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
public class BasicDataEntityRegistry implements InitializingBean {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(BasicDataEntityRegistry.class);
    
    /** 所属模块 */
    private String module;
    
    /** 编码到类型的映射 */
    private final Map<String, Class<? extends BasicData>> entityClassMap = new HashMap<>();
    
    /** 编码到类型的映射 */
    private final Map<String, String> moduleMap = new HashMap<>();
    
    /** 类型到类型的映射 */
    private final Map<String, BasicDataEntityInfo> entityInfoMap = new HashMap<>();
    
    /** 编码到类型的映射 */
    private final Map<Class<? extends BasicData>, String> class2moduleMap = new HashMap<>();
    
    /** 类型到类型的映射 */
    private final Map<Class<? extends BasicData>, BasicDataEntityInfo> class2entityInfoMap = new HashMap<>();
    
    /** <默认构造函数> */
    public BasicDataEntityRegistry(String module) {
        super();
        this.module = module;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(module, "module is empty.");
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
    public void register(BasicDataService<? extends BasicData> service) {
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
        info.setModule(this.module);
        info.setEntityClass(rawType);
        info.setTableName(tableName);
        info.setType(type);
        if (rawType.isAnnotationPresent(BasicDataEntity.class)) {
            BasicDataEntity anno = rawType.getAnnotation(BasicDataEntity.class);
            info.setName(anno.name());
            info.setRemark(anno.remark());
            info.setViewType(anno.viewType());
        } else {
            info.setName(rawType.getSimpleName());
            info.setRemark("");
            info.setViewType(BasicDataViewTypeEnum.COMMON_PAGEDLIST);
        }
        
        AssertUtils.isTrue(!entityClassMap.containsKey(rawType),
                "rawType:{} : service :{} is exist.",
                new Object[] { rawType, service });
        
        //注册写入<br/>
        this.entityClassMap.put(type, info.getEntityClass());
        this.moduleMap.put(type, info.getModule());
        this.entityInfoMap.put(type, info);
        this.class2moduleMap.put(info.getEntityClass(), info.getModule());
        this.class2entityInfoMap.put(info.getEntityClass(), info);
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
    public void register(Class<? extends BasicData> entityClass) {
        AssertUtils.notNull(entityClass, "entityClass is null.");
        
        /** 获取RawType(泛型类型) */
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(entityClass),
                "entityClass:{} is not assignableFrom BasicData.",
                new Object[] { entityClass });
        //尝试实例化，确认rawType能够被实例化
        BeanUtils.instantiateClass(entityClass, BasicData.class);
        
        //如果是注入的本地业务层;则直接
        String type = BasicDataUtils.getType(entityClass);
        String tableName = BasicDataUtils.getTableName(entityClass);
        BasicDataEntityInfo info = new BasicDataEntityInfo();
        info.setEntityClass(entityClass);
        info.setTableName(tableName);
        info.setType(type);
        if (entityClass.isAnnotationPresent(BasicDataEntity.class)) {
            BasicDataEntity anno = entityClass
                    .getAnnotation(BasicDataEntity.class);
            
            info.setName(anno.name());
            info.setRemark(anno.remark());
            info.setViewType(anno.viewType() == null
                    ? BasicDataViewTypeEnum.COMMON_PAGEDLIST : anno.viewType());
            info.setModule(StringUtils.isEmpty(anno.module()) ? this.module
                    : anno.module());
        } else {
            info.setName(entityClass.getSimpleName());
            info.setRemark("");
            info.setViewType(BasicDataViewTypeEnum.COMMON_PAGEDLIST);
            info.setModule(this.module);
        }
        
        AssertUtils.isTrue(!entityClassMap.containsKey(entityClass),
                "rawType:{} is exist.",
                new Object[] { entityClass });
        
        //注册写入<br/>
        this.entityClassMap.put(type, info.getEntityClass());
        this.moduleMap.put(type, info.getModule());
        this.entityInfoMap.put(type, info);
        this.class2moduleMap.put(info.getEntityClass(), info.getModule());
        this.class2entityInfoMap.put(info.getEntityClass(), info);
    }
    
}