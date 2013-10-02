/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-4
 * <修改描述:>
 */
package com.tx.core.jdbc.sqlsource;

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
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.dialect.Dialect;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.ClassReflector;
import com.tx.core.util.JdbcUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 简单的sqlMapMapper实现<br/>
 *     仅仅支持，属性对应java类型为简单类型的情况<br/>
 *     关联复杂对象的情况，不进行支持<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
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
    private final LinkedHashMap<String, String> queryConditionProperty2SqlMapping = new LinkedHashMap<String, String>();
    
    /** 可查询的属性名 */
    private final LinkedHashMap<String, JdbcType> queryConditionProperty2TypeMapping = new LinkedHashMap<String, JdbcType>();
    
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
      *<功能详细描述>
      * @param propertyName
      * @param conditionExpression [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addQueryConditionProperty2SqlMapping(String propertyName,
            String conditionExpression, JdbcType jdbcType) {
        AssertUtils.notEmpty(propertyName, "propertyName is empty.");
        AssertUtils.notEmpty(conditionExpression,
                "conditionExpression is empty.");
        AssertUtils.notNull(jdbcType, "jdbcType is empty.");
        
        this.queryConditionProperty2SqlMapping.put(propertyName,
                conditionExpression);
        this.queryConditionProperty2TypeMapping.put(propertyName, jdbcType);
    }
    
    /**
      * 添加其他条件例如额外关联等情况
      * 支持在固定表中存在固定条件的查询
      *<功能详细描述>
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
      *<功能详细描述>
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
     * @param 对pkName进行赋值
     */
    public Object getValue(Object obj) {
        AssertUtils.notNull(obj, "obj is null.");
        MetaObject metaObject = MetaObject.forObject(obj);
        return metaObject.getValue(this.pkName);
    }
    
    public Dialect getDialect() {
        return this.dialect;
    }
    
    /**
      * 获取到属性映射到的字段名<br/>
      *<功能详细描述>
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
     * 获取插入语句
     *<功能详细描述>
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
      *<功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return PreparedStatementSetter [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public PreparedStatementSetter getInsertSetter(final Object obj) {
        //如果插入语句还没有初始化，则需要先初始化插入语句
        final MetaObject metaObject = MetaObject.forObject(obj);
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
                    String propertyTemp = entryTemp.getKey();
                    Class<?> getterType = getter2JavaTypeMapping.get(propertyTemp);
                    JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                            i,
                            metaObject.getValue(entryTemp.getKey()),
                            getterType);
                    i++;
                }
            }
        };
        return res;
    }
    
    /**
     * 获取设置主键的setter对象<br/>
     *<功能详细描述>
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
        final MetaObject metaObject = MetaObject.forObject(obj);
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                Class<?> setterType = getter2JavaTypeMapping.get(finalPkName);
                JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                        1,
                        metaObject.getValue(finalPkName),
                        setterType);
            }
        };
        return res;
    }
    
    /**
      * 获取删除语句<br/>
      *<功能详细描述>
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
      * 获取对象的RowMapper
      *<功能详细描述>
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
            /**
             * @param rs
             * @param rowNum
             * @return
             * @throws SQLException
             */
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                T newObjInstance = ObjectUtils.newInstance(finalType);
                MetaObject metaObject = MetaObject.forObject(newObjInstance);
                
                for (Entry<String, String> entryTemp : getter2columnNameMapping.entrySet()) {
                    String propertyName = entryTemp.getKey();
                    String columnName = entryTemp.getValue();
                    if (finalClassReflector.getSetterNames()
                            .contains(propertyName)) {
                        Class<?> type = getter2JavaTypeMapping.get(propertyName);
                        Object value = JdbcUtils.getResultSetValueForSimpleType(rs,
                                columnName,
                                type);
                        metaObject.setValue(propertyName, value);
                    }
                }
                return newObjInstance;
            }
        };
        return rowMapper;
    }
    
    /**
     * 获取一个设置的callbackHandler实现
     *<功能详细描述>
     * @param newObjInstance
     * @return [参数说明]
     * 
     * @return RowCallbackHandler [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public RowCallbackHandler getSelectRowCallbackHandler(Object newObjInstance) {
        AssertUtils.notNull(this.classReflector, "classReflector is null");
        
        final MetaObject metaObject = MetaObject.forObject(newObjInstance);
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
                        metaObject.setValue(propertyName, value);
                    }
                }
            }
        };
        return res;
    }
    
    /**
      * 获取查询单条数据的sql
      *<功能详细描述>
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
      * 获取查询sql
      *<功能详细描述>
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
            MetaObject metaObject = MetaObject.forObject(obj);
            for (Entry<String, String> entryTemp : queryConditionProperty2SqlMapping.entrySet()) {
                String queryPropertyName = entryTemp.getKey();
                Object valueObj = metaObject.getValue(queryPropertyName);
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
     * 获取查询sql
     *<功能详细描述>
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
            MetaObject metaObject = MetaObject.forObject(obj);
            for (Entry<String, String> entryTemp : queryConditionProperty2SqlMapping.entrySet()) {
                String queryPropertyName = entryTemp.getKey();
                Object valueObj = metaObject.getValue(queryPropertyName);
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
      * 获取查询条件的Setter对象
      *<功能详细描述>
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
            final MetaObject metaObject = MetaObject.forObject(obj);
            res = new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    int i = 1;
                    for (Entry<String, String> entryTemp : queryConditionProperty2SqlMapping.entrySet()) {
                        String queryPropertyName = entryTemp.getKey();
                        Object valueObj = metaObject.getValue(queryPropertyName);
                        if (ObjectUtils.isEmpty(valueObj)) {
                            continue;
                        }
                        JdbcType jdbcType = queryConditionProperty2TypeMapping.get(queryPropertyName);
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
      *<功能详细描述>
      * @param obj
      * @return [参数说明]
      * 
      * @return LinkedHashMap<String,Object> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public LinkedHashMap<String, Object> getQueryCondtionParamMaps(Object obj) {
        final MetaObject metaObject = MetaObject.forObject(obj);
        final LinkedHashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
        for (Entry<String, String> entryTemp : queryConditionProperty2SqlMapping.entrySet()) {
            String queryPropertyName = entryTemp.getKey();
            Object valueObj = metaObject.getValue(queryPropertyName);
            if (ObjectUtils.isEmpty(valueObj)) {
                continue;
            }
            resMap.put(queryPropertyName, valueObj);
        }
        return resMap;
    }
    
    /**
      * 获取分页查询sql
      *<功能详细描述> [参数说明]
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
     * 获取查询条件的Setter对象
     *<功能详细描述>
     * @param obj
     * @return [参数说明]
     * 
     * @return PreparedStatementSetter [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PreparedStatementSetter getPagedQueryCondtionSetter(Object obj,
            int pageIndex, int pageSize) {
        final MetaObject metaObject = MetaObject.forObject(obj);
        final int offset = pageSize * (pageIndex - 1);
        final int limit = pageSize * pageIndex;
        
        //如果支持isSupportsLimitOffset并且当前需要偏移值
        //final boolean isSupportsVariableLimit = dialect.supportsVariableLimit();//是否支持物理分页
        final boolean isBindOnFirst = dialect.bindLimitParametersFirst();//是否绑定在前
        final boolean isSupportsLimit = dialect.supportsLimit();//是否支持limit
        final boolean isSupportsLimitOffset = dialect.supportsLimitOffset();//是否支持offset
        final boolean bindLimitParametersInReverseOrder = dialect.bindLimitParametersInReverseOrder();
        final boolean isNeedSetOffset = isSupportsLimitOffset
                && (offset > 0 || dialect.forceLimitUsage());
        
        PreparedStatementSetter res = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (isBindOnFirst) {
                    if (bindLimitParametersInReverseOrder) {
                        if (isSupportsLimit) {
                            ps.setInt(i++, limit);
                        }
                        if (isNeedSetOffset) {
                            ps.setInt(i++, offset);
                        }
                    } else {
                        if (isNeedSetOffset) {
                            ps.setInt(i++, offset);
                        }
                        if (isSupportsLimit) {
                            ps.setInt(i++, limit);
                        }
                    }
                }
                for (Entry<String, String> entryTemp : queryConditionProperty2SqlMapping.entrySet()) {
                    String queryPropertyName = entryTemp.getKey();
                    Object valueObj = metaObject.getValue(queryPropertyName);
                    if (ObjectUtils.isEmpty(valueObj)) {
                        continue;
                    }
                    JdbcType jdbcType = queryConditionProperty2TypeMapping.get(queryPropertyName);
                    JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                            i++,
                            valueObj,
                            jdbcType);
                }
                if (!isBindOnFirst) {
                    if (bindLimitParametersInReverseOrder) {
                        if (isSupportsLimit) {
                            ps.setInt(i++, limit);
                        }
                        if (isNeedSetOffset) {
                            ps.setInt(i++, offset);
                        }
                    } else {
                        if (isNeedSetOffset) {
                            ps.setInt(i++, offset);
                        }
                        if (isSupportsLimit) {
                            ps.setInt(i++, limit);
                        }
                    }
                }
            }
        };
        return res;
    }
    
    /**
      * 如果没有设定可更新字段，则认为该对象不支持更新<br/>
      *<功能详细描述>
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
     * 获取查询sql
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String updateSql(Object obj) {
        AssertUtils.notEmpty(obj, "update obj must not empty.");
        
        //获取当前对象中有哪些属性
        MetaObject metaObject = MetaObject.forObject(obj);
        String[] getterNames = metaObject.getGetterNames();
        Set<String> keySet = new HashSet<String>();
        for (String keyTemp : getterNames) {
            keySet.add(keyTemp);
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
     * 获取查询条件的Setter对象
     *<功能详细描述>
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
        final MetaObject metaObject = MetaObject.forObject(obj);
        String[] getterNames = metaObject.getGetterNames();
        final Set<String> keySet = new HashSet<String>();
        for (String keyTemp : getterNames) {
            keySet.add(keyTemp);
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
                    Object valueObj = metaObject.getValue(propertyNameTemp);
                    Class<?> setterType = getter2JavaTypeMapping.get(propertyNameTemp);
                    JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                            i++,
                            valueObj,
                            setterType);
                }
                Class<?> setterType = getter2JavaTypeMapping.get(finalPkName);
                JdbcUtils.setPreparedStatementValueForSimpleType(ps,
                        i,
                        metaObject.getValue(finalPkName),
                        setterType);
            }
        };
        return res;
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
