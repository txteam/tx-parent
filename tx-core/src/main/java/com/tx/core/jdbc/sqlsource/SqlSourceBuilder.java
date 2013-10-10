/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

import javax.persistence.OrderBy;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.dialect.Dialect;

import com.tx.core.jdbc.sqlsource.annotation.QueryCondition;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreater;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLessOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLike;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLikeAfter;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLikeBefore;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;
import com.tx.core.reflection.ClassReflector;
import com.tx.core.reflection.JpaColumnInfo;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.reflection.ReflectionUtils;
import com.tx.core.util.JdbcUtils;
import com.tx.core.util.MessageUtils;

/**
 * SimpleSqlSource构建器<br/>
 *     基于JPA注解去构建<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SqlSourceBuilder {
    
    /**
     * 本地资源缓存映射,采用弱引用的形式，以便及时回收一些使用不高的sqlSource
     */
    @SuppressWarnings("rawtypes")
    private WeakHashMap<Class<?>, SqlSource> mapping = new WeakHashMap<Class<?>, SqlSource>();
    
    /**
      * 根据传入类型构建SimpleSqlSource
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return SimpleSqlSource [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public <T> SqlSource<T> build(Class<T> type, Dialect dialect) {
        synchronized (type) {
            SqlSource<T> simpleSqlSource = null;
            if (mapping.containsKey(type)) {
                return mapping.get(type);
            }
            
            JpaMetaClass<T> jpaMetaClass = JpaMetaClass.forClass(type);
            
            //简答的sqlSource源
            simpleSqlSource = new SqlSource<T>(type, dialect);
            simpleSqlSource.setPkName(jpaMetaClass.getPkGetterName());
            simpleSqlSource.setTableName(jpaMetaClass.getTableName());
            
            //添加getter与字段的映射关系<br/>
            addGetter2ColumnMapping(jpaMetaClass, simpleSqlSource);
            
            //添加可更新字段
            addUpdateAblePropertys(type, jpaMetaClass, simpleSqlSource);
            //添加查询条件
            addQueryCondition(type, simpleSqlSource);
            
            //添加排序条件
            addOrderBy(type, jpaMetaClass, simpleSqlSource);
            
            //缓存起来
            mapping.put(type, simpleSqlSource);
            return simpleSqlSource;
        }
    }
    
    /**
      * 添加字段排序
      *<功能详细描述>
      * @param type
      * @param simpleSqlSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private <T> void addOrderBy(Class<T> type, JpaMetaClass<T> jpaMetaClass,
            SqlSource<T> simpleSqlSource) {
        boolean isHasOrderColumn = false;
        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .entrySet()) {
            String getterNameTemp = entryTemp.getKey();
            
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    OrderBy.class)) {
                //社会之已经具有了排序字段
                isHasOrderColumn = true;
                
                OrderBy anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        OrderBy.class);
                String orderByStr = StringUtils.isBlank(anno.value()) ? entryTemp.getValue()
                        .getColumnName()
                        : anno.value();
                
                simpleSqlSource.addOrder(orderByStr);
            }
        }
        
        if (!isHasOrderColumn) {
            simpleSqlSource.addOrder(jpaMetaClass.getGetter2columnInfoMapping()
                    .get(jpaMetaClass.getPkGetterName())
                    .getColumnName());
        }
    }
    
    /**
      * 根据注解添加查询条件<br/>
      *<功能详细描述>
      * @param type
      * @param simpleSqlSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private <T> void addQueryCondition(Class<T> type,
            SqlSource<T> simpleSqlSource) {
        if (type.isAnnotationPresent(QueryCondition.class)) {
            QueryCondition qcAnnoTemp = type.getAnnotation(QueryCondition.class);
            simpleSqlSource.addOtherCondition(qcAnnoTemp.condition());
        }
        
        ClassReflector<T> classReflector = ClassReflector.forClass(type);
        
        Set<String> getterNames = classReflector.getGetterNames();
        for (String getterNameTemp : getterNames) {
            Class<?> getterType = classReflector.getGetterType(getterNameTemp);
            JdbcType getterJdbcType = null;
            try {
                getterJdbcType = JdbcUtils.getJdbcTypeByJavaType(getterType);
            } catch (Exception e) {
                //如果为不支持的类型则跳过
                continue;
            }
            String columnName = simpleSqlSource.getColumnNameByGetterName(getterNameTemp);
            
            //如果存在queryCondition条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryCondition.class)) {
                QueryCondition qcAnnoTemp = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryCondition.class);
                if (StringUtils.isBlank(qcAnnoTemp.key())) {
                    simpleSqlSource.addOtherCondition(qcAnnoTemp.condition());
                } else {
                    simpleSqlSource.addQueryConditionProperty2SqlMapping(qcAnnoTemp.key(),
                            qcAnnoTemp.condition(),
                            getterJdbcType);
                }
            }
            
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionEqual.class)) {
                QueryConditionEqual anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionEqual.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} = ?", columnName),
                        getterJdbcType);
            }
            
            //如果存在like条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionLike.class)) {
                QueryConditionLike anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionLike.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                List<String> concatArgs = new ArrayList<String>();
                concatArgs.add("'%'");
                concatArgs.add("?");
                concatArgs.add("'%'");
                String valueStr = simpleSqlSource.getDialect()
                        .getFunctions()
                        .get("concat")
                        .render(null, concatArgs, null);
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} LIKE {}",
                                columnName,
                                valueStr),
                        getterJdbcType);
            }
            
            //如果存在like后条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionLikeAfter.class)) {
                QueryConditionLikeAfter anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionLikeAfter.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                
                List<String> concatArgs = new ArrayList<String>();
                concatArgs.add("?");
                concatArgs.add("'%'");
                String valueStr = simpleSqlSource.getDialect()
                        .getFunctions()
                        .get("concat")
                        .render(null, concatArgs, null);
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} LIKE {}",
                                columnName,
                                valueStr),
                        getterJdbcType);
            }
            
            //如果存在like后条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionLikeBefore.class)) {
                QueryConditionLikeBefore anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionLikeBefore.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                
                List<String> concatArgs = new ArrayList<String>();
                concatArgs.add("'%'");
                concatArgs.add("?");
                String valueStr = simpleSqlSource.getDialect()
                        .getFunctions()
                        .get("concat")
                        .render(null, concatArgs, null);
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} LIKE {}",
                                columnName,
                                valueStr),
                        getterJdbcType);
            }
            
            //如果存在>条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionGreater.class)) {
                QueryConditionGreater anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionGreater.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} > ?", columnName),
                        getterJdbcType);
            }
            
            //如果存在>=条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionGreaterOrEqual.class)) {
                QueryConditionGreaterOrEqual anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionGreaterOrEqual.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} >= ?", columnName),
                        getterJdbcType);
            }
            
            //如果存在<条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionLess.class)) {
                QueryConditionLess anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionLess.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} < ?", columnName),
                        getterJdbcType);
            }
            
            //如果存在<=条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryConditionLessOrEqual.class)) {
                QueryConditionLessOrEqual anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionLessOrEqual.class);
                String keyTemp = StringUtils.isBlank(anno.key()) ? getterNameTemp
                        : anno.key();
                
                simpleSqlSource.addQueryConditionProperty2SqlMapping(keyTemp,
                        MessageUtils.createMessage("{} <= ?", columnName),
                        getterJdbcType);
            }
        }
    }
    
    /**
      * 根据注解添加可更新属性集合<br/>
      *<功能详细描述>
      * @param type
      * @param simpleSqlSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private <T> void addUpdateAblePropertys(Class<T> type,
            JpaMetaClass<T> jpaMetaClass, SqlSource<T> simpleSqlSource) {
        for (String getterNameTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .keySet()) {
            Class<?> getterType = jpaMetaClass.getGetterType(getterNameTemp);
            
            //如果为直接支持存储的字段，则开始解析
            if (JdbcUtils.isSupportedSimpleType(getterType)) {
                if (ReflectionUtils.isHasAnnotationForGetter(type,
                        getterNameTemp,
                        UpdateAble.class)) {
                    simpleSqlSource.addUpdateAblePropertyNames(getterNameTemp);
                }
            }
        }
    }
    
    /**
      * 添加属性与字段的映射关联<br/>
      *<功能详细描述>
      * @param jpaMetaClass
      * @param simpleSqlSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private <T> void addGetter2ColumnMapping(JpaMetaClass<T> jpaMetaClass,
            SqlSource<T> simpleSqlSource) {
        for (Entry<String, JpaColumnInfo> entryTemp : jpaMetaClass.getGetter2columnInfoMapping()
                .entrySet()) {
            JpaColumnInfo jpaColumnInfo = entryTemp.getValue();
            
            //如果为直接支持存储的字段，则开始解析
            simpleSqlSource.addGetter2columnMapping(jpaColumnInfo.getRealGetterName(),
                    jpaColumnInfo.getColumnName(),
                    jpaColumnInfo.getRealGetterType());
        }
    }
}
