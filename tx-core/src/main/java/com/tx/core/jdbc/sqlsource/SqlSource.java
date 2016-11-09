/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.jdbc.SqlBuilder;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jdbc.model.Getter2ColumnInfo;
import com.tx.core.jdbc.model.QueryConditionInfo;
import com.tx.core.jdbc.model.QueryConditionTypeEnum;
import com.tx.core.reflection.ClassReflector;
import com.tx.core.reflection.JpaMetaClass;
import com.tx.core.util.JdbcUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 简单的sqlMapMapper实现<br/>
 * 仅仅支持，属性对应java类型为简单类型的情况<br/>
 * 关联复杂对象的情况，不进行支持<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SqlSource<T> implements Serializable, Cloneable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3059322593035094214L;
    
    /** 对应类的类型 */
    private Class<T> type;
    
    /** 对应类的反射器 */
    private ClassReflector<T> classReflector;
    
    /** 方言类 */
    private Dialect dialect;
    
    /** 主键属性名 */
    private String pkName;
    
    /** 表名 */
    private String tableName;
    
    /** 属性和字段的映射 */
    private final LinkedHashMap<String, String> getter2columnNameMapping = new LinkedHashMap<String, String>();
    
    /** 属性和类型的映射 */
    private final LinkedHashMap<String, Class<?>> getter2JavaTypeMapping = new LinkedHashMap<String, Class<?>>();
    
    /** 其他字段与表达式的映射:服务于插入语句 */
    private final LinkedHashMap<String, String> otherColumn2expressionMapping = new LinkedHashMap<String, String>();
    
    /** 可查询的属性名 */
    private final LinkedHashMap<String, String> queryConditionKey2SqlMapping = new LinkedHashMap<String, String>();
    
    /** 可查询的属性JDBC类型 */
    private final LinkedHashMap<String, JdbcType> queryConditionKey2JdbcTypeMapping = new LinkedHashMap<String, JdbcType>();
    
    /** 可查询属性的java类型 */
    private final LinkedHashMap<String, Class<?>> queryConditionKey2JavaTypeMapping = new LinkedHashMap<String, Class<?>>();
    
    /** 查询条件信息key与信息映射 */
    private final LinkedHashMap<String, QueryConditionInfo> queryConditionKey2ConditionInfoMapping = new LinkedHashMap<String, QueryConditionInfo>();
    
    /** getter名与getterColumnInfo间的映射关系 */
    private final LinkedHashMap<String, Getter2ColumnInfo> getter2getterColumnInfo = new LinkedHashMap<String, Getter2ColumnInfo>();
    
    /** 添加排序条件 */
    private final List<String> orderList = new ArrayList<String>();
    
    /** 与字段无关的其他条件,直接添加到查询语句中，无需进行setter */
    private final Set<String> otherCondition = new HashSet<String>();
    
    /** 可编辑的属性名 */
    private final Set<String> updateAblePropertyNames = new HashSet<String>();
    
    /** <默认构造函数> */
    public SqlSource(Class<T> type, String tableName, String pkName,
            Dialect dialect) {
        super();
        
        AssertUtils.notNull(type, "type is empty.");
        AssertUtils.notEmpty(pkName, "pkName is empty.");
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.notNull(dialect, "dialect is empty.");
        
        this.type = type;
        this.classReflector = ClassReflector.forClass(type);
        this.pkName = pkName.trim();
        this.tableName = tableName.trim().toUpperCase();
        this.dialect = dialect;
    }
    
    /** <默认构造函数> */
    public SqlSource(String tableName, String pkName, Dialect dialect) {
        super();
        
        AssertUtils.notEmpty(pkName, "pkName is empty.");
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.notNull(dialect, "dialect is empty.");
        
        this.pkName = pkName.trim();
        this.tableName = tableName.trim().toUpperCase();
        this.dialect = dialect;
    }
    
    /** <默认构造函数> */
    public SqlSource(Dialect dialect) {
        super();
        
        AssertUtils.notEmpty(pkName, "pkName is empty.");
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        AssertUtils.notNull(dialect, "dialect is empty.");
        
        this.dialect = dialect;
    }
    
    /** <默认构造函数> */
    public SqlSource(Class<T> type, Dialect dialect) {
        super();
        
        AssertUtils.notNull(type, "type is empty.");
        AssertUtils.notNull(dialect, "dialect is empty.");
        
        this.type = type;
        this.classReflector = ClassReflector.forClass(type);
        this.dialect = dialect;
    }
    
    /**
     * @param 对pkName进行赋值
     */
    public void setPkName(String pkName) {
        AssertUtils.notEmpty(pkName, "pkName is empty.");
        
        this.pkName = pkName.trim();
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        AssertUtils.notEmpty(tableName, "tableName is empty.");
        
        this.tableName = tableName;
    }
    
    /**
     * @param 对property2columnNameMapping进行赋值
     */
    public void addProperty2columnMapping(String propertyName,
            String columnName, Class<?> type) {
        AssertUtils.notEmpty(propertyName, "propertyName is empty.");
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notNull(type, "type is empty.");
        
        this.getter2columnNameMapping.put(propertyName.trim(),
                columnName.trim().toUpperCase());
        this.getter2JavaTypeMapping.put(propertyName, type);
    }
    
    /**
     * 添加getter2ColumnInfo的映射 <功能详细描述>
     * 
     * @param jpaMetaClass
     * @param getterName [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void addGetter2GetterColumnInfoMapping(JpaMetaClass<?> jpaMetaClass,
            String getterName) {
        AssertUtils.notEmpty(getterName, "getterName is empty.");
        AssertUtils.notNull(jpaMetaClass, "jpaMetaClass is empty.");
        
        this.getter2getterColumnInfo.put(getterName, new Getter2ColumnInfo(
                jpaMetaClass, getterName));
    }
    
    /**
     * @param 对property2columnNameMapping进行赋值
     */
    public void addGetter2columnMapping(String getterName, String columnName,
            Class<?> type) {
        AssertUtils.notEmpty(getterName, "getterName is empty.");
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notNull(type, "type is empty.");
        
        this.getter2columnNameMapping.put(getterName.trim(), columnName.trim()
                .toUpperCase());
        this.getter2JavaTypeMapping.put(getterName, type);
    }
    
    /**
     * @param 对otherColumn2expressionMapping进行赋值
     */
    public void addOtherColumn2expressionMapping(String columnName,
            String expression) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(expression, "expression is empty.");
        this.otherColumn2expressionMapping.put(columnName.trim().toUpperCase(),
                expression.trim());
    }
    
    /**
     * 添加属性值与条件的映射关系<br/>
     * <功能详细描述>
     * 
     * @param propertyName
     * @param conditionExpression [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void addQueryConditionKey2SqlMapping(
            QueryConditionTypeEnum queryConditionType, String key,
            String conditionExpression, JdbcType jdbcType, Class<?> javaType) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notEmpty(conditionExpression,
                "conditionExpression is empty.");
        AssertUtils.notNull(jdbcType, "jdbcType is empty.");
        
        this.queryConditionKey2SqlMapping.put(key, conditionExpression);
        this.queryConditionKey2JdbcTypeMapping.put(key, jdbcType);
        this.queryConditionKey2JavaTypeMapping.put(key, javaType);
        
        if (queryConditionType != null) {
            this.queryConditionKey2ConditionInfoMapping.put(key,
                    new QueryConditionInfo(queryConditionType, key, javaType,
                            jdbcType));
        }
    }
    
    /**
     * 添加其他条件例如额外关联等情况 支持在固定表中存在固定条件的查询 <功能详细描述>
     * 
     * @param conditionExpression [参数说明]
     *            
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void addOtherCondition(String conditionExpression) {
        AssertUtils.notEmpty(conditionExpression,
                "conditionExpression is empty.");
        
        this.otherCondition.add(conditionExpression);
    }
    
    /**
     * @param 对modifyAblePropertyNames进行赋值
     */
    public void addUpdateAblePropertyNames(String modifyAblePropertyName) {
        AssertUtils.notEmpty(modifyAblePropertyName,
                "modifyAblePropertyName is empty.");
        this.updateAblePropertyNames.add(modifyAblePropertyName.trim());
    }
    
    public void addOrder(String order) {
        AssertUtils.notEmpty(order, "order is empty.");
        this.orderList.add(order.trim().toUpperCase());
    }
    
    /**
     * 获取对象主键名称<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getPkName() {
        return pkName;
    }
    
    /**
     * 获取对象的主键值
     * 
     * @param 对pkName进行赋值
     */
    public Object getValue(Object obj) {
        AssertUtils.notNull(obj, "obj is null.");
        BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        return metaObject.getPropertyValue(this.pkName);
    }
    
    public Dialect getDialect() {
        return this.dialect;
    }
    
    /**
     * 获取到属性映射到的字段名<br/>
     * <功能详细描述>
     * 
     * @param getterName
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getColumnNameByGetterName(String getterName) {
        AssertUtils.notEmpty(getterName, "getterName is empty.");
        
        return this.getter2columnNameMapping.get(getterName);
    }
    
    /**
     * 获取插入语句 <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String insertSql() {
        //开始构建sql
        SqlBuilder.BEGIN();
        SqlBuilder.INSERT_INTO(this.tableName);
        for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
            SqlBuilder.VALUES(entryTemp.getValue(), "?");
        }
        for (Entry<String, String> entryTemp : otherColumn2expressionMapping.entrySet()) {
            SqlBuilder.VALUES(entryTemp.getKey(), entryTemp.getValue());
        }
        
        String insertSql = SqlBuilder.SQL();
        SqlBuilder.RESET();
        
        return insertSql;
    }
    
    /**
     * 生成setter对象进行设值<br/>
     * <功能详细描述>
     * 
     * @param obj
     * @return [参数说明]
     *         
     * @return PreparedStatementSetter [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PreparedStatementSetter getInsertSetter(final Object obj) {
        //如果插入语句还没有初始化，则需要先初始化插入语句
        final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
                    String propertyTemp = entryTemp.getKey();
                    Class<?> getterType = getter2JavaTypeMapping.get(propertyTemp);
                    JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                            i,
                            metaObject.getPropertyValue(entryTemp.getKey()),
                            getterType);
                    i++;
                }
            }
        };
        return res;
    }
    
    /**
     * 获取设置主键的setter对象<br/>
     * <功能详细描述>
     * 
     * @param obj
     * @return [参数说明]
     *         
     * @return PreparedStatementSetter [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PreparedStatementSetter getPKSetter(final Object obj) {
        AssertUtils.notEmpty(this.pkName, "pkName is empty.");
        AssertUtils.isTrue(this.getter2columnNameMapping.containsKey(this.pkName),
                "property2columnNameMapping not contains pkName:{}.",
                this.pkName);
        
        final String finalPkName = this.pkName;
        final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                Class<?> setterType = getter2JavaTypeMapping.get(finalPkName);
                JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                        1,
                        metaObject.getPropertyValue(finalPkName),
                        setterType);
            }
        };
        return res;
    }
    
    /**
     * 获取删除语句<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String deleteSql() {
        AssertUtils.notEmpty(this.pkName, "pkName is empty.");
        AssertUtils.isTrue(this.getter2columnNameMapping.containsKey(this.pkName),
                "property2columnNameMapping not contains pkName:{}.",
                this.pkName);
        
        //开始构建sql
        SqlBuilder.BEGIN();
        SqlBuilder.DELETE_FROM(this.tableName);
        SqlBuilder.WHERE(this.getter2columnNameMapping.get(this.pkName)
                + " = ? ");
        String deleteSql = SqlBuilder.SQL();
        SqlBuilder.RESET();
        
        return deleteSql;
    }
    
    /**
     * 获取对象的RowMapper <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return RowMapper [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RowMapper<T> getSelectRowMapper() {
        AssertUtils.notNull(this.type, "type is null");
        AssertUtils.notNull(this.classReflector, "classReflector is null");
        
        final Class<T> finalType = this.type;
        final ClassReflector<T> finalClassReflector = this.classReflector;
        RowMapper<T> rowMapper = new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                T newObjInstance = ObjectUtils.newInstance(finalType);
                BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(newObjInstance);
                
                for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
                    String propertyName = entryTemp.getKey();
                    String columnName = entryTemp.getValue();
                    if (finalClassReflector.getSetterNames()
                            .contains(propertyName)) {
                        Class<?> type = getter2JavaTypeMapping.get(propertyName);
                        Object value = JdbcUtils.getResultSetValueForSimpleType(rs,
                                columnName,
                                type);
                        metaObject.setPropertyValue(propertyName, value);
                    }
                }
                return newObjInstance;
            }
        };
        return rowMapper;
    }
    
    /**
     * 获取一个设置的callbackHandler实现 <功能详细描述>
     * 
     * @param newObjInstance
     * @return [参数说明]
     *         
     * @return RowCallbackHandler [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public RowCallbackHandler getSelectRowCallbackHandler(Object newObjInstance) {
        AssertUtils.notNull(this.classReflector, "classReflector is null");
        
        final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(newObjInstance);
        final ClassReflector<T> finalClassReflector = this.classReflector;
        RowCallbackHandler res = new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
                    
                    String propertyName = entryTemp.getKey();
                    String columnName = entryTemp.getValue();
                    
                    if (finalClassReflector.getSetterNames()
                            .contains(propertyName)) {
                        Class<?> type = getter2JavaTypeMapping.get(propertyName);
                        Object value = JdbcUtils.getResultSetValueForSimpleType(rs,
                                columnName,
                                type);
                        metaObject.setPropertyValue(propertyName, value);
                    }
                }
            }
        };
        return res;
    }
    
    /**
     * 获取查询单条数据的sql <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String findSql() {
        AssertUtils.notEmpty(this.pkName, "pkName is empty.");
        AssertUtils.isTrue(this.getter2columnNameMapping.containsKey(this.pkName),
                "property2columnNameMapping not contains pkName:{}.",
                this.pkName);
        
        SqlBuilder.BEGIN();
        for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
            SqlBuilder.SELECT(entryTemp.getValue());
        }
        SqlBuilder.FROM(this.tableName);
        SqlBuilder.WHERE(this.getter2columnNameMapping.get(this.pkName)
                + " = ? ");
        String findSql = SqlBuilder.SQL();
        SqlBuilder.RESET();
        return findSql;
    }
    
    /**
     * 获取查询sql <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String querySql(Object obj) {
        AssertUtils.notEmpty(this.pkName, "pkName is empty.");
        AssertUtils.isTrue(this.getter2columnNameMapping.containsKey(this.pkName),
                "property2columnNameMapping not contains pkName:{}.",
                this.pkName);
        
        //构建query语句
        SqlBuilder.BEGIN();
        for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
            SqlBuilder.SELECT(entryTemp.getValue());
        }
        SqlBuilder.FROM(this.tableName);
        
        if (!ObjectUtils.isEmpty(obj)) {
            BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            for (Entry<String, String> entryTemp : queryConditionKey2SqlMapping.entrySet()) {
                String queryKeyName = entryTemp.getKey();
                Object valueObj = metaObject.getPropertyValue(queryKeyName);
                if (ObjectUtils.isEmpty(valueObj)) {
                    continue;
                }
                SqlBuilder.WHERE(entryTemp.getValue());
            }
        }
        for (String conditionExpressionTemp : otherCondition) {
            SqlBuilder.WHERE(conditionExpressionTemp);
        }
        
        //在不存在排序字段时默认使用主键对应字段作为排序字段<br/>
        if (CollectionUtils.isEmpty(orderList)) {
            SqlBuilder.ORDER_BY(this.getter2columnNameMapping.get(this.pkName));
        } else {
            for (String order : orderList) {
                SqlBuilder.ORDER_BY(order);
            }
        }
        
        String querySql = SqlBuilder.SQL();
        SqlBuilder.RESET();
        
        return querySql;
    }
    
    /**
     * 获取查询sql <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String countSql(Object obj) {
        //构建query语句
        SqlBuilder.BEGIN();
        SqlBuilder.SELECT("COUNT(1)");
        SqlBuilder.FROM(this.tableName);
        
        if (!ObjectUtils.isEmpty(obj)) {
            BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            for (Entry<String, String> entryTemp : queryConditionKey2SqlMapping.entrySet()) {
                String queryKeyName = entryTemp.getKey();
                Object valueObj = metaObject.getPropertyValue(queryKeyName);
                if (ObjectUtils.isEmpty(valueObj)) {
                    continue;
                }
                SqlBuilder.WHERE(entryTemp.getValue());
            }
        }
        for (String conditionExpressionTemp : otherCondition) {
            SqlBuilder.WHERE(conditionExpressionTemp);
        }
        String querySql = SqlBuilder.SQL();
        SqlBuilder.RESET();
        
        return querySql;
    }
    
    /**
     * 获取查询条件的Setter对象 <功能详细描述>
     * 
     * @param obj
     * @return [参数说明]
     *         
     * @return PreparedStatementSetter [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PreparedStatementSetter getQueryCondtionSetter(Object obj) {
        PreparedStatementSetter res = null;
        if (!ObjectUtils.isEmpty(obj)) {
            final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
            res = new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    for (Entry<String, String> entryTemp : queryConditionKey2SqlMapping.entrySet()) {
                        String queryKeyName = entryTemp.getKey();
                        Object valueObj = metaObject.getPropertyValue(queryKeyName);
                        if (ObjectUtils.isEmpty(valueObj)) {
                            continue;
                        }
                        JdbcType jdbcType = queryConditionKey2JdbcTypeMapping.get(queryKeyName);
                        JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                                i,
                                valueObj,
                                jdbcType);
                        i++;
                    }
                }
            };
        } else {
            res = new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                }
            };
        }
        
        return res;
    }
    
    /**
     * 获取动态查询条件的参数映射关系<br/>
     * <功能详细描述>
     * 
     * @param obj
     * @return [参数说明]
     *         
     * @return LinkedHashMap<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public LinkedHashMap<String, Object> getQueryCondtionParamMaps(Object obj) {
        final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        final LinkedHashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
        for (Entry<String, String> entryTemp : queryConditionKey2SqlMapping.entrySet()) {
            String queryKeyName = entryTemp.getKey();
            Object valueObj = metaObject.getPropertyValue(queryKeyName);
            if (ObjectUtils.isEmpty(valueObj)) {
                continue;
            }
            resMap.put(queryKeyName, valueObj);
        }
        return resMap;
    }
    
    /**
     * 获取分页查询sql <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String queryPagedSql(Object obj, int pageIndex, int pageSize) {
        String querySql = querySql(obj);
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        final boolean isSupportsVariableLimit = dialect.supportsVariableLimit();//是否支持物理分页
        final boolean isSupportsLimit = dialect.supportsLimit();//是否支持limit
        final boolean isSupportsLimitOffset = dialect.supportsLimitOffset();//是否支持offset
        
        //如果不支持物理分页，直接返回sql
        if (!isSupportsVariableLimit
                || (!isSupportsLimit && !isSupportsLimitOffset)) {
            return querySql;
        } else {
            //如果支持
            String limitSql = dialect.getLimitString(querySql, offset, limit);
            return limitSql;
        }
    }
    
    /**
     * 获取查询条件的Setter对象 <功能详细描述>
     * 
     * @param obj
     * @return [参数说明]
     *         
     * @return PreparedStatementSetter [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PreparedStatementSetter getPagedQueryCondtionSetter(Object obj,
            int offset, int limit) {
        final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        
        //如果支持isSupportsLimitOffset并且当前需要偏移值
        //final boolean isSupportsVariableLimit = dialect.supportsVariableLimit();//是否支持物理分页
        final boolean isBindOnFirst = dialect.bindLimitParametersFirst();//是否绑定在前
        final boolean isSupportsLimit = dialect.supportsLimit();//是否支持limit
        final boolean isSupportsLimitOffset = dialect.supportsLimitOffset();//是否支持offset
        final boolean bindLimitParametersInReverseOrder = dialect.bindLimitParametersInReverseOrder();
        final boolean isNeedSetOffset = isSupportsLimitOffset
                && (offset > 0 || dialect.forceLimitUsage());
        final boolean isUseMaxForLimit = dialect.useMaxForLimit();
        final int trueOffset = offset;
        final int trueLimit = isUseMaxForLimit ? limit : limit - offset;
        
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (isBindOnFirst) {
                    if (bindLimitParametersInReverseOrder) {
                        if (isSupportsLimit) {
                            ps.setInt(i++, trueLimit);
                        }
                        if (isNeedSetOffset) {
                            ps.setInt(i++, trueOffset);
                        }
                    } else {
                        if (isNeedSetOffset) {
                            ps.setInt(i++, trueOffset);
                        }
                        if (isSupportsLimit) {
                            ps.setInt(i++, trueLimit);
                        }
                    }
                }
                for (Entry<String, String> entryTemp : queryConditionKey2SqlMapping.entrySet()) {
                    String queryKeyName = entryTemp.getKey();
                    Object valueObj = metaObject.getPropertyValue(queryKeyName);
                    if (ObjectUtils.isEmpty(valueObj)) {
                        continue;
                    }
                    JdbcType jdbcType = queryConditionKey2JdbcTypeMapping.get(queryKeyName);
                    JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                            i++,
                            valueObj,
                            jdbcType);
                }
                if (!isBindOnFirst) {
                    if (bindLimitParametersInReverseOrder) {
                        if (isSupportsLimit) {
                            ps.setInt(i++, trueLimit);
                        }
                        if (isNeedSetOffset) {
                            ps.setInt(i++, trueOffset);
                        }
                    } else {
                        if (isNeedSetOffset) {
                            ps.setInt(i++, trueOffset);
                        }
                        if (isSupportsLimit) {
                            ps.setInt(i++, trueLimit);
                        }
                    }
                }
            }
        };
        return res;
    }
    
    /**
     * 如果没有设定可更新字段，则认为该对象不支持更新<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isUpdateAble() {
        if (CollectionUtils.isEmpty(this.updateAblePropertyNames)) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 获取查询sql <功能详细描述>
     * 
     * @return [参数说明]
     *         
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String updateSql(Object obj) {
        AssertUtils.notEmpty(obj, "update obj must not empty.");
        
        //获取当前对象中有哪些属性
        BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Set<String> keySet = new HashSet<String>();
        for (PropertyDescriptor pd : metaObject.getPropertyDescriptors()) {
            if (metaObject.isReadableProperty(pd.getName())) {
                keySet.add(pd.getName());
            }
        }
        AssertUtils.isTrue(keySet.contains(this.pkName),
                "obj:{} must contains pk{}.",
                new Object[] { obj, this.pkName });
        
        //构建query语句
        SqlBuilder.BEGIN();
        SqlBuilder.UPDATE(this.tableName);
        
        for (String propertyName : updateAblePropertyNames) {
            if (!keySet.contains(propertyName)) {
                continue;
            }
            //Object value = metaObject.getValue(propertyName);
            String columnName = getter2columnNameMapping.get(propertyName);
            SqlBuilder.SET(columnName + " = ?");
        }
        SqlBuilder.WHERE(this.getter2columnNameMapping.get(this.pkName)
                + " = ? ");
        String updateSql = SqlBuilder.SQL();
        SqlBuilder.RESET();
        return updateSql;
    }
    
    /**
     * 获取查询条件的Setter对象 <功能详细描述>
     * 
     * @param obj
     * @return [参数说明]
     *         
     * @return PreparedStatementSetter [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PreparedStatementSetter getUpdateSetter(Object obj) {
        AssertUtils.notEmpty(obj, "update obj must not empty.");
        
        //获取当前对象中有哪些属性
        final BeanWrapper metaObject = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        final Set<String> keySet = new HashSet<String>();
        for (PropertyDescriptor pd : metaObject.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            if (metaObject.isReadableProperty(propertyName)) {
                keySet.add(propertyName);
            }
        }
        AssertUtils.isTrue(keySet.contains(this.pkName),
                "obj:{} must contains pk{}.",
                new Object[] { obj, this.pkName });
        final String finalPkName = this.pkName;
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                for (String propertyNameTemp : updateAblePropertyNames) {
                    if (!keySet.contains(propertyNameTemp)) {
                        continue;
                    }
                    Object valueObj = metaObject.getPropertyValue(propertyNameTemp);
                    Class<?> setterType = getter2JavaTypeMapping.get(propertyNameTemp);
                    JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                            i++,
                            valueObj,
                            setterType);
                }
                Class<?> setterType = getter2JavaTypeMapping.get(finalPkName);
                JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                        i,
                        metaObject.getPropertyValue(finalPkName),
                        setterType);
            }
        };
        return res;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @return 返回 getter2columnNameMapping
     */
    public LinkedHashMap<String, String> getGetter2columnNameMapping() {
        return getter2columnNameMapping;
    }
    
    /**
     * @return 返回 getter2JavaTypeMapping
     */
    public LinkedHashMap<String, Class<?>> getGetter2JavaTypeMapping() {
        return getter2JavaTypeMapping;
    }
    
    /**
     * @return 返回 queryConditionProperty2SqlMapping
     */
    public LinkedHashMap<String, String> getQueryConditionKey2SqlMapping() {
        return queryConditionKey2SqlMapping;
    }
    
    /**
     * @return 返回 queryConditionProperty2TypeMapping
     */
    public LinkedHashMap<String, JdbcType> getQueryConditionKey2JdbcTypeMapping() {
        return queryConditionKey2JdbcTypeMapping;
    }
    
    /**
     * @return 返回 queryConditionKey2ConditionInfoMapping
     */
    public LinkedHashMap<String, QueryConditionInfo> getQueryConditionKey2ConditionInfoMapping() {
        return this.queryConditionKey2ConditionInfoMapping;
    }
    
    /**
     * @return 返回 queryConditionKey2JavaTypeMapping
     */
    public LinkedHashMap<String, Class<?>> getQueryConditionKey2JavaTypeMapping() {
        return queryConditionKey2JavaTypeMapping;
    }
    
    /**
     * @return 返回 updateAblePropertyNames
     */
    public Set<String> getUpdateAblePropertyNames() {
        return updateAblePropertyNames;
    }
    
    /**
     * @return 返回 otherCondition
     */
    public Set<String> getOtherCondition() {
        return otherCondition;
    }
    
    /**
     * @return
     * @throws CloneNotSupportedException
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() throws CloneNotSupportedException {
        SqlSource<T> cloneObj = (SqlSource<T>) super.clone();
        return cloneObj;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<T> type) {
        AssertUtils.notNull(type, "type is empty.");
        
        this.type = type;
        this.classReflector = ClassReflector.forClass(type);
    }
}
