/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-6
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.dialect.Dialect;

import com.tx.core.jdbc.exception.SqlSourceBuildException;
import com.tx.core.jdbc.sqlsource.annotation.QueryCondition;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreater;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionGreaterOrEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLess;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLike;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLikeAfter;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLikeBefore;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;
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
public class SimpleSqlSourceBuilder {
    
    /**
     * 本地资源缓存映射,采用弱引用的形式，以便及时回收一些使用不高的sqlSource
     */
    @SuppressWarnings("rawtypes")
    private final static WeakHashMap<Class<?>, SimpleSqlSource> mapping = new WeakHashMap<Class<?>, SimpleSqlSource>();
    
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
    public static <T> SimpleSqlSource<T> build(Class<T> type, Dialect dialect) {
        synchronized (type) {
            SimpleSqlSource<T> simpleSqlSource = null;
            if (mapping.containsKey(type)) {
                return mapping.get(type);
            }
            
            //表名
            String tableName = generateTableName(type);
            String pkName = generatePkPropertyName(type);
            
            //简答的sqlSource源
            simpleSqlSource = new SimpleSqlSource<T>(type, tableName, pkName,
                    dialect);
            //添加属性与字段的映射关系<br/>
            addProperty2ColumnMapping(type, simpleSqlSource);
            //添加可更新字段
            addUpdateAblePropertys(type, simpleSqlSource);
            //添加查询条件
            addQueryCondition(type, simpleSqlSource);
            //添加排序条件
            addOrderBy(type, simpleSqlSource);
            
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
    private static <T> void addOrderBy(Class<T> type,
            SimpleSqlSource<T> simpleSqlSource) {
        MetaClass metaClass = MetaClass.forClass(type);
        
        String[] getterNames = metaClass.getGetterNames();
        for (String getterNameTemp : getterNames) {
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    OrderBy.class)) {
                OrderBy anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        OrderBy.class);
                
                String orderByStr = StringUtils.isBlank(anno.value()) ? simpleSqlSource.getColumnNameByPropertyName(getterNameTemp)
                        : anno.value();
                
                simpleSqlSource.addOrder(orderByStr);
            }
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
    private static <T> void addQueryCondition(Class<T> type,
            SimpleSqlSource<T> simpleSqlSource) {
        if (type.isAnnotationPresent(QueryCondition.class)) {
            QueryCondition qcAnnoTemp = type.getAnnotation(QueryCondition.class);
            simpleSqlSource.addOtherCondition(qcAnnoTemp.condition());
        }
        
        MetaClass metaClass = MetaClass.forClass(type);
        
        String[] getterNames = metaClass.getGetterNames();
        for (String getterNameTemp : getterNames) {
            Class<?> getterType = metaClass.getGetterType(getterNameTemp);
            JdbcType getterJdbcType = JdbcUtils.getJdbcTypeByJavaType(getterType);
            String columnName = simpleSqlSource.getColumnNameByPropertyName(getterNameTemp);
            
            //如果存在queryCondition条件
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    QueryCondition.class)) {
                QueryCondition qcAnnoTemp = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryCondition.class);
                if(StringUtils.isBlank(qcAnnoTemp.key())){
                    simpleSqlSource.addOtherCondition(qcAnnoTemp.condition());
                }else{
                    simpleSqlSource.addQueryConditionProperty2SqlMapping(qcAnnoTemp.key(),
                            qcAnnoTemp.condition(),
                            getterJdbcType);
                }
            }
            
            //需要忽略的字段直接不进行条件解析
            if(isNeedSkip(type, getterNameTemp, getterType)){
                continue;
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
                    QueryConditionLess.class)) {
                QueryConditionLess anno = ReflectionUtils.getGetterAnnotation(type,
                        getterNameTemp,
                        QueryConditionLess.class);
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
    private static <T> void addUpdateAblePropertys(Class<T> type,
            SimpleSqlSource<T> simpleSqlSource) {
        MetaClass metaClass = MetaClass.forClass(type);
        
        String[] getterNames = metaClass.getGetterNames();
        for (String getterNameTemp : getterNames) {
            Class<?> getterType = metaClass.getGetterType(getterNameTemp);
            if(isNeedSkip(type, getterNameTemp, getterType)){
                continue;
            }
            
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
      * @param type
      * @param simpleSqlSource [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static <T> void addProperty2ColumnMapping(Class<T> type,
            SimpleSqlSource<T> simpleSqlSource) {
        MetaClass metaClass = MetaClass.forClass(type);
        
        String[] getterNames = metaClass.getGetterNames();
        for (String getterNameTemp : getterNames) {
            //判断对应字段是否需要被忽略
            //设置了不需要持久的注解忽略
            String columnName = getterNameTemp.toUpperCase();
            Class<?> getterType = metaClass.getGetterType(getterNameTemp);
            
            if(isNeedSkip(type, getterNameTemp, getterType)){
                continue;
            }
            
            //如果为直接支持存储的字段，则开始解析
            if (JdbcUtils.isSupportedSimpleType(getterType)) {
                if (ReflectionUtils.isHasAnnotationForGetter(type,
                        getterNameTemp,
                        Column.class)) {
                    Column colAnno = ReflectionUtils.getGetterAnnotation(type,
                            getterNameTemp,
                            Column.class);
                    if (!StringUtils.isEmpty(colAnno.name())) {
                        //如果存在joinColumn并指定了字段名，则在此处直接解析，然后进行下一个属性解析
                        columnName = colAnno.name().toUpperCase();
                    }
                }
                //设置值后然后，解析下一个属性
                simpleSqlSource.addProperty2columnMapping(getterNameTemp,
                        columnName,
                        getterType);
                continue;
            }
            
            //现在提供的simpleSqlSource暂不考虑对象关联的情况，
            //所以OneToOne,ManeyToOne仅考虑关联的对端的主键的情况
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    OneToOne.class)
                    || ReflectionUtils.isHasAnnotationForGetter(type,
                            getterNameTemp,
                            ManyToOne.class)) {
                if (ReflectionUtils.isHasAnnotationForGetter(type,
                        getterNameTemp,
                        JoinColumn.class)) {
                    JoinColumn joinColAnno = ReflectionUtils.getGetterAnnotation(type,
                            getterNameTemp,
                            JoinColumn.class);
                    if (!StringUtils.isEmpty(joinColAnno.name())) {
                        //如果存在joinColumn并指定了字段名，则在此处直接解析，然后进行下一个属性解析
                        columnName = joinColAnno.name();
                        String newGetterNameTemp = getterNames + "."
                                + generatePkPropertyName(getterType);
                        simpleSqlSource.addProperty2columnMapping(newGetterNameTemp,
                                columnName,
                                getterType);
                        continue;
                    }
                    
                }
                //兼容处理，如果存在Column也认为是可以的
                if (ReflectionUtils.isHasAnnotationForGetter(type,
                        getterNameTemp,
                        Column.class)) {
                    Column colAnno = ReflectionUtils.getGetterAnnotation(type,
                            getterNameTemp,
                            Column.class);
                    if (!StringUtils.isEmpty(colAnno.name())) {
                        //如果存在joinColumn并指定了字段名，则在此处直接解析，然后进行下一个属性解析
                        columnName = colAnno.name();
                        String newGetterNameTemp = getterNames + "."
                                + generatePkPropertyName(getterType);
                        simpleSqlSource.addProperty2columnMapping(newGetterNameTemp,
                                columnName,
                                getterType);
                        continue;
                    }
                }
                
                //如果注解不存在
                String newGetterNameTemp = getterNames + "."
                        + generatePkPropertyName(getterType);
                simpleSqlSource.addProperty2columnMapping(newGetterNameTemp,
                        columnName,
                        getterType);
                continue;
            }
            
            throw new SqlSourceBuildException("getterType is not supported.",
                    new Object[] { getterType });
        }
    }
    
    /**
      * 是否需要跳过对应类型<br/>
      *<功能详细描述>
      * @param type
      * @param getterName
      * @param getterType
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static <T> boolean isNeedSkip(Class<T> type,String getterName,Class<?> getterType){
        if (ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                Transient.class)) {
            return true;
        }
        //由于simpleSqlSource不处理过于复杂的对象关联，所以存在oneToManay,ManayToManay也一并忽略
        if (ReflectionUtils.isHasAnnotationForGetter(type,
                getterName,
                OneToMany.class)
                || ReflectionUtils.isHasAnnotationForGetter(type,
                        getterName,
                        ManyToMany.class)) {
            return true;
        }
        
        //如果为直接支持存储的字段，则开始解析
        //if (!JdbcUtils.isSupportedSimpleType(getterType)) {
        //    return true;
        //}
        return false;
    }
    
    /**
      * 获取类型中指定的主键的属性名<br/>
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static String generatePkPropertyName(Class<?> type) {
        //获取对象解析器
        MetaClass metaClass = MetaClass.forClass(type);
        
        String[] getterNames = metaClass.getGetterNames();
        for (String getterNameTemp : getterNames) {
            if (ReflectionUtils.isHasAnnotationForGetter(type,
                    getterNameTemp,
                    Id.class)) {
                return getterNameTemp;
            }
        }
        throw new SqlSourceBuildException("@Id is not exist.");
    }
    
    /**
      * 根据类型，并依赖其中的注解<br/>
      *     获取对象存储数据的表名<br/>
      *<功能详细描述>
      * @param type
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static String generateTableName(Class<?> type) {
        String tableName = type.getSimpleName().toUpperCase();
        if (type.isAnnotationPresent(Table.class)) {
            Table tableAnno = type.getAnnotation(Table.class);
            if (!StringUtils.isBlank(tableAnno.name())) {
                tableName = tableAnno.name().toUpperCase();
            }
        }
        return tableName;
    }
}
