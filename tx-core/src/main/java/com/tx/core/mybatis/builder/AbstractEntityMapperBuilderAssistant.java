/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月17日
 * <修改描述:>
 */
package com.tx.core.mybatis.builder;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
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

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

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
public abstract class AbstractEntityMapperBuilderAssistant
        extends MapperBuilderAssistant {
    
    /** <if test=\"{} != null\"><if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ AND {} {} #{{}} ]]></if></if>*/
    protected static final String FORMATTER_OF_NESTED_WHERE_ITEM = "<if test=\"{} != null\"><if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ {} {} {} #{{}} ]]></if></if>";
    
    /** <if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ AND {} {} #{{}} ]]></if>*/
    protected static final String FORMATTER_OF_WHERE_ITEM = "<if test=\"@com.tx.core.util.OgnlUtils@isNotEmpty({})\"><![CDATA[ {} {} {} #{{}} ]]></if>";
    
    /** <if test=\"_parameter.containsKey('{}')\"> {} = #{{},jdbcType={}}, </if> */
    protected static final String FORMATTER_OF_SET_ITEM = "<if test=\"_parameter.containsKey('{}')\"> {} = #{{},javaType={}}, </if>";
    
    /** <choose><when test="@com.tx.core.util.OgnlUtils@isNotEmpty(orderSql)">ORDER BY ${orderSql}</when><otherwise>ORDER BY ID</otherwise></choose> */
    protected static final String FORMATTER_OF_ORDER = "<choose><when test=\"@com.tx.core.util.OgnlUtils@isNotEmpty(orderSql)\">ORDER BY ${orderSql}</when><otherwise>ORDER BY {}</otherwise></choose>";
    
    /** <script>{}</script> 格式化器*/
    protected static final String FORMATTER_OF_SCRIPT = "<script>{}</script>";
    
    /** #{}属性格式化器 */
    protected static final String FORMATTER_OF_PROPERTY = "#{{}}";
    
    /** =條件 */
    protected static final String FORMATTER_OF_CONDITION_EQUAL = "=";
    
    /** <>條件 */
    protected static final String FORMATTER_OF_CONDITION_UNEQUAL = "<>";
    
    /** >=條件 */
    protected static final String FORMATTER_OF_CONDITION_GREAT_OR_EQUAL = ">=";
    
    /** >條件 */
    protected static final String FORMATTER_OF_CONDITION_GREAT = ">";
    
    /** <=條件 */
    protected static final String FORMATTER_OF_CONDITION_LESS_OR_EQUAL = "<=";
    
    /** <條件 */
    protected static final String FORMATTER_OF_CONDITION_LESS = "<";
    
    /** mybatis自动注册日志打印 */
    protected Logger logger = LoggerFactory.getLogger("mybatis.auto.sqlmap");
    
    /** 对应的类类型 */
    protected Class<?> beanType;
    
    private String currentNamespace;
    
    /** 已经存在mappedStatements */
    private Map<String, MappedStatement> mappedStatements;
    
    /** 已经存在resultMaps */
    private Map<String, ResultMap> resultMaps;
    
    /** <默认构造函数> */
    @SuppressWarnings("unchecked")
    public AbstractEntityMapperBuilderAssistant(Configuration configuration,
            Class<?> beanType) {
        super(configuration, beanType.getName());
        
        AssertUtils.notNull(configuration, "configuration is null.");
        AssertUtils.notNull(beanType, "type is null.");
        
        try {
            this.mappedStatements = (Map<String, MappedStatement>) FieldUtils
                    .readDeclaredField(configuration, "mappedStatements", true);
            this.resultMaps = (Map<String, ResultMap>) FieldUtils
                    .readDeclaredField(configuration, "resultMaps", true);
        } catch (IllegalAccessException e) {
            throw new SILException("IllegalAccessException.e", e);
        }
        
        this.beanType = beanType;
        //设置namespace
        this.currentNamespace = beanType.getName();
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
        
        return MessageFormatter
                .arrayFormat(FORMATTER_OF_SCRIPT, new Object[] { sql })
                .getMessage();
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
    protected String formatProperty(String property) {
        AssertUtils.notEmpty(property, "property is empty.");
        
        return MessageFormatter
                .arrayFormat(FORMATTER_OF_PROPERTY, new Object[] { property })
                .getMessage();
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
    protected String formatWhereAndItem(String columnName, String condition,
            String nestedPropertyName) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(condition, "condition is empty.");
        AssertUtils.notEmpty(nestedPropertyName,
                "nestedPropertyName is empty.");
        
        return MessageFormatter.arrayFormat(FORMATTER_OF_WHERE_ITEM,
                new Object[] { nestedPropertyName, "AND", columnName, condition,
                        nestedPropertyName })
                .getMessage();
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
    protected String formatWhereOrItem(String columnName, String condition,
            String nestedPropertyName) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(condition, "condition is empty.");
        AssertUtils.notEmpty(nestedPropertyName, "propertyName is empty.");
        
        return MessageFormatter.arrayFormat(FORMATTER_OF_WHERE_ITEM,
                new Object[] { nestedPropertyName, "OR", columnName, condition,
                        nestedPropertyName })
                .getMessage();
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
    protected String formatNestedWhereAndItem(String columnName,
            String condition, String propertyName, String nestedPropertyName) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(condition, "condition is empty.");
        AssertUtils.notEmpty(propertyName, "propertyName is empty.");
        AssertUtils.notEmpty(nestedPropertyName,
                "nestedPropertyName is empty.");
        
        return MessageFormatter
                .arrayFormat(FORMATTER_OF_NESTED_WHERE_ITEM,
                        new Object[] { propertyName, nestedPropertyName, "AND",
                                columnName, condition, nestedPropertyName })
                .getMessage();
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
    protected String formatNestedWhereOrItem(String columnName,
            String condition, String propertyName, String nestedPropertyName) {
        AssertUtils.notEmpty(columnName, "columnName is empty.");
        AssertUtils.notEmpty(condition, "condition is empty.");
        AssertUtils.notEmpty(propertyName, "propertyName is empty.");
        AssertUtils.notEmpty(nestedPropertyName,
                "nestedPropertyName is empty.");
        
        return MessageFormatter
                .arrayFormat(FORMATTER_OF_NESTED_WHERE_ITEM,
                        new Object[] { propertyName, nestedPropertyName, "OR",
                                columnName, condition, nestedPropertyName })
                .getMessage();
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
        Map<String, String> customizeColumn2PropertyMap = getCustomizeColumn2PropertyMap();
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
                .uncapitalize(this.beanType.getSimpleName()) + "Map";
        addResultMap(resultMapId, this.beanType, resultMappings);
        
        String insertSQL = getInsertSQL();
        if (!StringUtils.isBlank(insertSQL)) {
            insertSQL = formatScript(insertSQL);
            
            logger.info("<!-- ---insertSQL:--- -->");
            logger.info(insertSQL);
            addInsertMappedStatement(getInsertStatementName(),
                    insertSQL,
                    this.beanType);
        }
        
        String deleteSQL = getDeleteSQL();
        if (!StringUtils.isBlank(deleteSQL)) {
            deleteSQL = formatScript(deleteSQL);
            
            logger.info("<!-- ---deleteSQL:--- -->");
            logger.info(deleteSQL);
            addDeleteMappedStatement(getDeleteStatementName(),
                    deleteSQL,
                    this.beanType);
        }
        
        String updateSQL = getUpdateSQL();
        if (!StringUtils.isBlank(updateSQL)) {
            updateSQL = formatScript(updateSQL);
            
            logger.info("<!-- ---updateSQL:--- -->");
            logger.info(updateSQL);
            addUpdateMappedStatement(getUpdateStatementName(),
                    updateSQL,
                    Map.class);
        }
        
        String findSQL = getFindSQL();
        if (!StringUtils.isBlank(findSQL)) {
            findSQL = formatScript(findSQL);
            
            logger.info("<!-- ---findSQL:--- -->");
            logger.info(findSQL);
            addFindMappedStatement(getFindStatementName(),
                    findSQL,
                    this.beanType,
                    resultMapId);
        }
        
        String querySQL = getQuerySQL();
        if (!StringUtils.isBlank(querySQL)) {
            querySQL = formatScript(querySQL);
            
            logger.info("<!-- ---querySQL:--- -->");
            logger.info(querySQL);
            addFindMappedStatement(getQueryStatementName(),
                    querySQL,
                    Map.class,
                    resultMapId);
        }
        
        String countSQL = getCountSQL();
        if (!StringUtils.isBlank(countSQL)) {
            countSQL = formatScript(countSQL);
            
            logger.info("<!-- ---queryCountSQL:--- -->");
            logger.info(countSQL);
            addCountMappedStatement(getCountStatmentName(),
                    countSQL,
                    Map.class,
                    Integer.class);
        }
        
        logger.info(
                "<!-- --------------- 自动注册SqlMap:'{}'   END --------------- -->",
                namespace);
        logger.info("");
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
    protected abstract Map<String, String> getCustomizeColumn2PropertyMap();
    
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
    protected ResultMapping buildResultMapping(String column, String property) {
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
                property,
                column,
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
     * 构建ResultMapping
     * <功能详细描述>
     * @param resultType
     * @param property
     * @param javaType
     * @param column
     * @param jdbcType
     * @return [参数说明]
     * 
     * @return ResultMapping [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected ResultMapping buildResultMapping(Class<?> resultType,
            String property, Class<?> javaType, String column,
            JdbcType jdbcType) {
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
                property,
                column,
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
        LanguageDriver lang = getLanguageDriver(null);
        
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
        LanguageDriver lang = getLanguageDriver(null);
        
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
        LanguageDriver lang = getLanguageDriver(null);
        
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
        LanguageDriver lang = getLanguageDriver(null);
        
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
        LanguageDriver lang = getLanguageDriver(null);
        
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
        LanguageDriver lang = getLanguageDriver(null);
        
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
    public MappedStatement saveMappedStatement(String id,
            SqlCommandType sqlCommandType, String sql, Class<?> parameterType) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean useCache = isSelect;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        String resultMap = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = (configuration.isUseGeneratedKeys()
                && SqlCommandType.INSERT.equals(sqlCommandType))
                        ? new Jdbc3KeyGenerator() : new NoKeyGenerator();
        LanguageDriver lang = getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        if (configuration.hasStatement(id, false)) {
            //如果已经含有了，需要将对应的statement移除
            this.mappedStatements.remove(id);
        }
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                sqlCommandType,
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
    public MappedStatement saveMappedStatement(String id,
            SqlCommandType sqlCommandType, String sql, Class<?> parameterType,
            Class<?> resultType) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        AssertUtils.notNull(resultType, "resultType is null.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean useCache = isSelect;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        String resultMap = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = (configuration.isUseGeneratedKeys()
                && SqlCommandType.INSERT.equals(sqlCommandType))
                        ? new Jdbc3KeyGenerator() : new NoKeyGenerator();
        LanguageDriver lang = getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        if (configuration.hasStatement(id, false)) {
            //如果已经含有了，需要将对应的statement移除
            this.mappedStatements.remove(id);
        }
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                sqlCommandType,
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
     * 保存MappedStatement
     * <功能详细描述>
     * @param id
     * @param sqlCommandType
     * @param sql
     * @param parameterType
     * @param resultMapId
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public MappedStatement saveMappedStatement(String id,
            SqlCommandType sqlCommandType, String sql, Class<?> parameterType,
            String resultMapId) {
        AssertUtils.notEmpty(id, "id is empty.");
        AssertUtils.notEmpty(sql, "sql is empty.");
        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.");
        AssertUtils.notNull(parameterType, "parameterType is null.");
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
        
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Integer fetchSize = null;
        Integer timeout = null;
        
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean useCache = isSelect;
        boolean resultOrdered = false;
        
        String keyProperty = null;
        String keyColumn = null;
        String databaseId = null;
        String resultSets = null;
        Class<?> resultType = null;
        
        String parameterMap = null;
        
        KeyGenerator keyGenerator = (configuration.isUseGeneratedKeys()
                && SqlCommandType.INSERT.equals(sqlCommandType))
                        ? new Jdbc3KeyGenerator() : new NoKeyGenerator();
        LanguageDriver lang = getLanguageDriver(null);
        
        SqlSource sqlSource = lang.createSqlSource(configuration,
                sql,
                parameterType);
        
        id = applyCurrentNamespace(id, false);//获取对应的statement的id
        if (configuration.hasStatement(id, false)) {
            //如果已经含有了，需要将对应的statement移除
            this.mappedStatements.remove(id);
        }
        MappedStatement statement = addMappedStatement(id,
                sqlSource,
                statementType,
                sqlCommandType,
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
     * 保存MappedStatement
     * <功能详细描述>
     * @param resultMapId
     * @param resultType
     * @param resultMappings
     * 
     * @return MappedStatement [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public ResultMap saveResultMap(String resultMapId, Class<?> resultType,
            List<ResultMapping> resultMappings) {
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
        AssertUtils.notNull(resultType, "resultType is null.");
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
        AssertUtils.notNull(resultMappings, "resultMappings is null.");
        
        resultMapId = applyCurrentNamespace(resultMapId, false);//获取对应的statement的id
        if (configuration.hasResultMap(resultMapId)) {
            //如果已经含有了，需要将对应的statement移除
            this.resultMaps.remove(resultMapId);
        }
        ResultMap resultMap = addResultMap(resultMapId,
                resultType,
                null,
                null,
                resultMappings,
                true);
        
        return resultMap;
    }
}