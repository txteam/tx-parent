/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月17日
 * <修改描述:>
 */
package com.tx.core.mybatis.assistant;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeanUtils;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * Mapper构建助手扩展类<br/>
 *    该逻辑的调用，在设计上应该避免在修改statement期间出现业务调用的情况，不然可能出现不可预知的错误<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractBaseDaoMapperBuilderAssistant
        extends MapperBuilderAssistant {
    
    /** mybatis自动注册日志打印 */
    protected Logger logger = LoggerFactory.getLogger("mybatis.auto.sqlmap");
    
    /** <if test=\"{} != null\"><if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ AND {} {} #{{}} ]]></if></if>*/
    protected static final String FORMATTER_OF_WHERE_ITEM = "<if test=\"{} != null\"><if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ {} {} {} #{{}} ]]></if></if>";
    
    /** <if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ AND {} {} #{{}} ]]></if>*/
    protected static final String FORMATTER_OF_WHERE_ITEM_SIMPLE_TYPE = "<if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ {} {} {} #{{}} ]]></if>";
    
    /** <if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ AND {} {} #{{}} ]]></if>*/
    protected static final String FORMATTER_OF_QUERIER = "<if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty(conditions)\">"
            + "<foreach collection=\"conditions\" item=\"conditionTemp\">"
            + "<choose>" + "<when test=\"conditionTemp.withoutValue\">"
            + " AND ${conditionTemp.column} ${conditionTemp.operator}"
            + "</when>" + "<when test=\"conditionTemp.foreach\">  "
            + " AND ${conditionTemp.column} ${conditionTemp.operator} <foreach collection=\"conditionTemp.value\" item=\"valueTemp\" open=\"(\" close=\")\" separator=\",\">#{valueTemp}</foreach>"
            + "</when>" + "<otherwise>"
            + " AND ${conditionTemp.column} ${conditionTemp.operator} #{conditionTemp.value}"
            + "</otherwise>" + "</choose>" + "</foreach>" + "</if>";
    
    /** <if test=\"_parameter.containsKey('{}')\"> {} = #{{},jdbcType={}}, </if> */
    protected static final String FORMATTER_OF_SET_ITEM = "<if test=\"_parameter.containsKey('{}')\"> {} = #{{},javaType={}}, </if>";
    
    /** <choose><when test="@com.tx.core.util.OgnlUtils@isNotEmpty(orderSql)">ORDER BY ${orderSql}</when><otherwise>ORDER BY ID</otherwise></choose> */
    //protected static final String FORMATTER_OF_ORDERBY = "<choose><when test=\"@com.tx.core.util.OgnlUtils@isNotEmpty(orders)\">ORDER BY <foreach collection=\"orders\" item=\"orderTemp\" separator=\",\">${orderTemp.column} ${orderTemp.direction}</foreach></when><otherwise>ORDER BY {}</otherwise></choose>";
    
    /** <script>{}</script> 格式化器*/
    protected static final String FORMATTER_OF_SCRIPT = "<script>{}</script>";
    
    /** #{}属性格式化器 */
    protected static final String FORMATTER_OF_PROPERTY = "#{{}}";
    
    /** =條件 */
    protected static final String FORMATTER_OF_CONDITION_EQUAL = "=";
    
    /** 对应的类类型 */
    protected Class<?> entityType;
    
    /** 当前命名空间 */
    private String currentNamespace;
    
    /** <默认构造函数> */
    public AbstractBaseDaoMapperBuilderAssistant(Configuration configuration,
            Class<?> entityType) {
        super(configuration, entityType.getName());
        
        AssertUtils.notNull(configuration, "configuration is null.");
        AssertUtils.notNull(entityType, "type is null.");
        
        this.entityType = entityType;
        
        //设置namespace
        this.currentNamespace = entityType.getName();
        setCurrentNamespace(this.currentNamespace);
    }
    
    /**
     * 是否是简单属性<br/>
     * <功能详细描述>
     * @param propertyDescriptor
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected boolean hasTypeHandler(PropertyDescriptor propertyDescriptor) {
        AssertUtils.notNull(propertyDescriptor, "propertyDescriptor is null.");
        
        Class<?> type = propertyDescriptor.getPropertyType();
        if (BeanUtils.isSimpleValueType(type)
                || this.typeHandlerRegistry.getTypeHandler(type) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 格式化脚本<br/>
     * <功能详细描述>
     * @param sql
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String formatScript(String sql) {
        AssertUtils.notEmpty(sql, "sql is empty.");
        
        String script = MessageFormatter
                .arrayFormat(FORMATTER_OF_SCRIPT, new Object[] { sql })
                .getMessage();
        return script;
    }
    
    /**
     * 格式化脚本<br/>
     * <功能详细描述>
     * @param sql
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String formatProperty(String columnPropertyName) {
        AssertUtils.notEmpty(columnPropertyName,
                "columnPropertyName is empty.");
        
        String formatProperty = MessageFormatter
                .arrayFormat(FORMATTER_OF_PROPERTY,
                        new Object[] { columnPropertyName })
                .getMessage();
        return formatProperty;
    }
    
    /**
     * 格式化WhereCondition条件<br/>
     * <功能详细描述>
     * @param columnName
     * @param propertyName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected String formatAnd(String columnName, String condition,
            String columnPropertyName) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(condition, "condition is empty.");
        AssertUtils.notEmpty(columnPropertyName,
                "columnPropertyName is empty.");
        
        String sql = MessageFormatter.arrayFormat(FORMATTER_OF_WHERE_ITEM_SIMPLE_TYPE,
                new Object[] { columnPropertyName, "AND", columnName, condition,
                        columnPropertyName })
                .getMessage();
        return sql;
    }
    
    /**
     * 格式化WhereCondition条件<br/>
     * <功能详细描述>
     * @param columnName
     * @param propertyName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String formatAnd(JPAColumnInfo column, String condition) {
        AssertUtils.notNull(column, "column is null.");
        AssertUtils.notEmpty(column.getColumnName(),
                "column.columnName is empty.");
        AssertUtils.notEmpty(column.getColumnPropertyName(),
                "column.columnPropertyName is empty.");
        AssertUtils.notEmpty(column.getPropertyName(),
                "column.propertyName is empty.");
        AssertUtils.notEmpty(condition, "condition is empty.");
        
        String sql = "";
        if (hasTypeHandler(column.getPropertyDescriptor())) {
            sql = MessageFormatter
                    .arrayFormat(FORMATTER_OF_WHERE_ITEM_SIMPLE_TYPE,
                            new Object[] { column.getColumnPropertyName(),
                                    "AND", column.getColumnName(), condition,
                                    column.getColumnPropertyName() })
                    .getMessage();
        } else {
            sql = MessageFormatter
                    .arrayFormat(FORMATTER_OF_WHERE_ITEM,
                            new Object[] { column.getPropertyName(),
                                    column.getColumnPropertyName(), "AND",
                                    column.getColumnName(), condition,
                                    column.getColumnPropertyName() })
                    .getMessage();
        }
        return sql;
    }
    
    /**
     * 格式化WhereCondition条件<br/>
     * <功能详细描述>
     * @param columnName
     * @param propertyName
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected String formatAndEqual(JPAColumnInfo column) {
        AssertUtils.notNull(column, "column is null.");
        AssertUtils.notEmpty(column.getColumnName(),
                "column.columnName is empty.");
        AssertUtils.notEmpty(column.getColumnPropertyName(),
                "column.columnPropertyName is empty.");
        AssertUtils.notEmpty(column.getPropertyName(),
                "column.propertyName is empty.");
        
        String eqSQL = "";
        if (hasTypeHandler(column.getPropertyDescriptor())) {
            eqSQL = MessageFormatter
                    .arrayFormat(FORMATTER_OF_WHERE_ITEM_SIMPLE_TYPE,
                            new Object[] { column.getColumnPropertyName(),
                                    "AND", column.getColumnName(), "=",
                                    column.getColumnPropertyName() })
                    .getMessage();
        } else {
            eqSQL = MessageFormatter
                    .arrayFormat(FORMATTER_OF_WHERE_ITEM,
                            new Object[] { column.getPropertyName(),
                                    column.getColumnPropertyName(), "AND",
                                    column.getColumnName(), "=",
                                    column.getColumnPropertyName() })
                    .getMessage();
        }
        return eqSQL;
    }
    
    /**
     * 格式化Set项<br/>
     * <功能详细描述>
     * @param columnName
     * @param propertyName
     * @param javaType
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected String formatSetItem(String columnName, String propertyName,
            String nestedPropertyName, Class<?> javaType) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(propertyName, "propertyName is empty.");
        AssertUtils.notEmpty(nestedPropertyName,
                "nestedPropertyName is empty.");
        AssertUtils.notNull(javaType, "javaType is null.");
        
        return MessageFormatter
                .arrayFormat(FORMATTER_OF_SET_ITEM,
                        new Object[] { propertyName, columnName,
                                nestedPropertyName, javaType.getName() })
                .getMessage();
    }
    
    /**
     * 调用该方法向Configuration中注册对应的SqlMap<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void registe() {
        String namespace = this.getCurrentNamespace();
        
        logger.info("");
        logger.info(
                "<!-- --------------- 自动注册SqlMap:'{}' START --------------- -->",
                namespace);
        
        //添加resultMap
        Map<String, String> customizeColumn2PropertyMap = getColumn2PropertyMap();
        List<ResultMapping> resultMappings = new ArrayList<>();
        if (!MapUtils.isEmpty(customizeColumn2PropertyMap)) {
            for (Entry<String, String> entryTemp : customizeColumn2PropertyMap
                    .entrySet()) {
                ResultMapping resultMapping = buildResultMapping(
                        entryTemp.getKey(), entryTemp.getValue());
                resultMappings.add(resultMapping);
            }
        }
        String resultMapId = StringUtils
                .uncapitalize(this.entityType.getSimpleName()) + "Map";
        if (!this.configuration.hasResultMap(resultMapId)) {
            addResultMap(resultMapId, this.entityType, resultMappings);
        }
        
        String insertSQL = getInsertSQL();
        if (!StringUtils.isBlank(insertSQL)) {
            insertSQL = formatScript(insertSQL);
            
            if (!this.configuration.hasStatement(getInsertStatementName(),
                    false)) {
                logger.info("<!-- ---insertSQL:--- -->");
                logger.info(insertSQL);
                addInsertMappedStatement(getInsertStatementName(),
                        insertSQL,
                        this.entityType);
            }
        }
        
        String deleteSQL = getDeleteSQL();
        if (!StringUtils.isBlank(deleteSQL)) {
            deleteSQL = formatScript(deleteSQL);
            
            if (!this.configuration.hasStatement(getDeleteStatementName(),
                    false)) {
                logger.info("<!-- ---deleteSQL:--- -->");
                logger.info(deleteSQL);
                addDeleteMappedStatement(getDeleteStatementName(),
                        deleteSQL,
                        this.entityType);
            }
        }
        
        String updateSQL = getUpdateSQL();
        if (!StringUtils.isBlank(updateSQL)) {
            updateSQL = formatScript(updateSQL);
            
            if (!this.configuration.hasStatement(getUpdateStatementName(),
                    false)) {
                logger.info("<!-- ---updateSQL:--- -->");
                logger.info(updateSQL);
                addUpdateMappedStatement(getUpdateStatementName(),
                        updateSQL,
                        Map.class);
            }
        }
        
        String findSQL = getFindSQL();
        if (!StringUtils.isBlank(findSQL)) {
            findSQL = formatScript(findSQL);
            
            if (!this.configuration.hasStatement(getFindStatementName(),
                    false)) {
                logger.info("<!-- ---findSQL:--- -->");
                logger.info(findSQL);
                addFindMappedStatement(getFindStatementName(),
                        findSQL,
                        this.entityType,
                        resultMapId);
            }
        }
        
        String querySQL = getQuerySQL();
        if (!StringUtils.isBlank(querySQL)) {
            querySQL = formatScript(querySQL);
            
            if (!this.configuration.hasStatement(getQueryStatementName(),
                    false)) {
                logger.info("<!-- ---querySQL:--- -->");
                logger.info(querySQL);
                addFindMappedStatement(getQueryStatementName(),
                        querySQL,
                        Map.class,
                        resultMapId);
            }
        }
        
        String countSQL = getCountSQL();
        if (!StringUtils.isBlank(countSQL)) {
            countSQL = formatScript(countSQL);
            
            if (!this.configuration.hasStatement(getCountStatmentName(),
                    false)) {
                logger.info("<!-- ---queryCountSQL:--- -->");
                logger.info(countSQL);
                addCountMappedStatement(getCountStatmentName(),
                        countSQL,
                        Map.class,
                        Integer.class);
            }
        }
        
        logger.info(
                "<!-- --------------- 自动注册SqlMap:'{}'   END --------------- -->",
                namespace);
        logger.info("");
    }
    
    /**
     * 当前命名空间<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getNamespace() {
        return this.currentNamespace;
    }
    
    /**
     * 获取insertSatement的name
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getInsertStatementName() {
        return this.currentNamespace + ".insert";
    }
    
    /**
     * 获取deleteStatement的name
     *<功能简述>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getDeleteStatementName() {
        return this.currentNamespace + ".delete";
    }
    
    /**
     * 获取updateStatement的name
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getUpdateStatementName() {
        return this.currentNamespace + ".update";
    }
    
    /**
     * 获取findStatment的name
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getFindStatementName() {
        return this.currentNamespace + ".find";
    }
    
    /**
     * 获取queryStatment的name
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getQueryStatementName() {
        return this.currentNamespace + ".query";
    }
    
    /**
     * 获取countStatment的name
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getCountStatmentName() {
        return this.currentNamespace + "." + getQueryStatementName() + "Count";
    }
    
    /**
     * 获取插入语句<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String getInsertSQL();
    
    /**
     * 获取插入语句<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String getDeleteSQL();
    
    /**
     * 获取插入语句<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String getUpdateSQL();
    
    /**
     * 获取特殊的字段到属性的映射<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract Map<String, String> getColumn2PropertyMap();
    
    /**
     * 获取插入语句<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String getFindSQL();
    
    /**
     * 获取插入语句<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String getQuerySQL();
    
    /**
     * 获取插入语句<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract String getCountSQL();
    
    /**
     * 构建ResultMapping
     * <功能详细描述>
     * @param column
     * @param property
     * @return [参数说明]
     * 
     * @return ResultMapping [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected ResultMapping buildResultMapping(String propertyName,
            String columnName) {
        Class<?> resultType = null;
        Class<?> javaType = null;
        JdbcType jdbcType = null;
        
        String nestedSelect = null;//是否存在nestedSelect
        String nestedResultMap = null;//
        String notNullColumn = null;
        String columnPrefix = null;
        Class<? extends TypeHandler<?>> typeHandler = null;
        List<ResultFlag> flags = null; //??
        String resultSet = null;
        String foreignColumn = null;
        boolean lazy = true;
        ResultMapping rm = super.buildResultMapping(resultType,
                propertyName,
                columnName,
                javaType,
                jdbcType,
                nestedSelect,
                nestedResultMap,
                notNullColumn,
                columnPrefix,
                typeHandler,
                flags,
                resultSet,
                foreignColumn,
                lazy);
        return rm;
    }
    
    /**
     * 保存MappedStatement
     * <功能详细描述>
     * @param resultMapId
     * @param resultType
     * @param resultMappings
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected ResultMap addResultMap(String resultMapId, Class<?> resultType,
            List<ResultMapping> resultMappings) {
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
        AssertUtils.notNull(resultType, "resultType is null.");
        AssertUtils.notNull(resultMappings, "resultMappings is null.");
        
        resultMapId = applyCurrentNamespace(resultMapId, false);//获取对应的statement的id
        ResultMap resultMap = addResultMap(resultMapId,
                resultType,
                null,
                null,
                resultMappings,
                true);
        
        return resultMap;
    }
    
    /**
     * 保存MappedStatement<br/>
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultType
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected MappedStatement addInsertMappedStatement(String id, String sql,
            Class<?> parameterType) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean flushCache = true;
        boolean useCache = false;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        String resultMap = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = configuration.isUseGeneratedKeys()
                ? new Jdbc3KeyGenerator() : new NoKeyGenerator();
        LanguageDriver lang = configuration.getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                SqlCommandType.INSERT,
                fetchSize,
                timeout,
                parameterMap,
                parameterType,
                resultMap,
                resultType,
                resultSetType,
                flushCache,
                useCache,
                resultOrdered,
                keyGenerator,
                keyProperty,
                keyColumn,
                databaseId,
                lang,
                resultSets);
        return statement;
    }
    
    /**
     * 保存MappedStatement<br/>
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultType
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    protected MappedStatement addDeleteMappedStatement(String id, String sql,
            Class<?> parameterType) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean flushCache = true;
        boolean useCache = false;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        String resultMap = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = new NoKeyGenerator();
        LanguageDriver lang = configuration.getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                SqlCommandType.DELETE,
                fetchSize,
                timeout,
                parameterMap,
                parameterType,
                resultMap,
                resultType,
                resultSetType,
                flushCache,
                useCache,
                resultOrdered,
                keyGenerator,
                keyProperty,
                keyColumn,
                databaseId,
                lang,
                resultSets);
        return statement;
    }
    
    /**
     * 保存MappedStatement<br/>
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultType
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected MappedStatement addUpdateMappedStatement(String id, String sql,
            Class<?> parameterType) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean flushCache = true;
        boolean useCache = false;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        String resultMap = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = new NoKeyGenerator();
        LanguageDriver lang = configuration.getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                SqlCommandType.UPDATE,
                fetchSize,
                timeout,
                parameterMap,
                parameterType,
                resultMap,
                resultType,
                resultSetType,
                flushCache,
                useCache,
                resultOrdered,
                keyGenerator,
                keyProperty,
                keyColumn,
                databaseId,
                lang,
                resultSets);
        return statement;
    }
    
    /**
     * 保存MappedStatement<br/>
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultType
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public MappedStatement addFindMappedStatement(String id, String sql,
            Class<?> parameterType, String resultMapId) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean flushCache = false;
        boolean useCache = true;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = new NoKeyGenerator();
        LanguageDriver lang = configuration.getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                SqlCommandType.SELECT,
                fetchSize,
                timeout,
                parameterMap,
                parameterType,
                resultMapId,
                resultType,
                resultSetType,
                flushCache,
                useCache,
                resultOrdered,
                keyGenerator,
                keyProperty,
                keyColumn,
                databaseId,
                lang,
                resultSets);
        return statement;
    }
    
    /**
     * 保存MappedStatement<br/>
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultType
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public MappedStatement addQueryMappedStatement(String id, String sql,
            Class<?> parameterType, String resultMapId) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean flushCache = false;
        boolean useCache = true;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = new NoKeyGenerator();
        LanguageDriver lang = configuration.getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                SqlCommandType.SELECT,
                fetchSize,
                timeout,
                parameterMap,
                parameterType,
                resultMapId,
                resultType,
                resultSetType,
                flushCache,
                useCache,
                resultOrdered,
                keyGenerator,
                keyProperty,
                keyColumn,
                databaseId,
                lang,
                resultSets);
        return statement;
    }
    
    /**
     * 保存MappedStatement<br/>
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultType
     * @return [参数说明]
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public MappedStatement addCountMappedStatement(String id, String sql,
            Class<?> parameterType, Class<?> resultType) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        AssertUtils.notEmpty(resultType, "resultType is empty.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean flushCache = false;
        boolean useCache = true;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        String resultMapId = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = new NoKeyGenerator();
        LanguageDriver lang = configuration.getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                SqlCommandType.SELECT,
                fetchSize,
                timeout,
                parameterMap,
                parameterType,
                resultMapId,
                resultType,
                resultSetType,
                flushCache,
                useCache,
                resultOrdered,
                keyGenerator,
                keyProperty,
                keyColumn,
                databaseId,
                lang,
                resultSets);
        return statement;
    }
}