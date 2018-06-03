///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2016年11月17日
// * <修改描述:>
// */
//package com.tx.core.mybatis.builder;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.apache.ibatis.builder.MapperBuilderAssistant;
//import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
//import org.apache.ibatis.executor.keygen.KeyGenerator;
//import org.apache.ibatis.executor.keygen.NoKeyGenerator;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ResultFlag;
//import org.apache.ibatis.mapping.ResultMap;
//import org.apache.ibatis.mapping.ResultMapping;
//import org.apache.ibatis.mapping.ResultSetType;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.mapping.SqlSource;
//import org.apache.ibatis.mapping.StatementType;
//import org.apache.ibatis.scripting.LanguageDriver;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.TypeHandler;
//import org.springframework.beans.factory.InitializingBean;
//
//import com.tx.core.exceptions.SILException;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * MappedStatement注册机<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2016年11月17日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public abstract class MappedStatementRegistry implements InitializingBean{
//    
//    /** mybatis配置句柄 */
//    private Configuration configuration;
//    
//    /** <默认构造函数> */
//    @SuppressWarnings("unchecked")
//    public MappedStatementRegistry(Configuration configuration) {
//        this.configuration = configuration;
//    }
//    
//    /** <默认构造函数> */
//    public MappedStatementRegistry() {
//        super();
//    }
//
//    protected abstract String buildInsertSQL();
//    
//    protected abstract String buildDeleteSQL();
//    
//    protected abstract String buildFindSQL();
//    
//    protected abstract String buildQuerySQL();
//    
//    protected abstract String buildCountSQL();
//    
//    protected abstract String buildUpdateSQL();
//    
//    protected void addInsertMappedStatement(String sql){
//        
//        this.configuration.addMappedStatement(ms);
//    }
//    
//    /**
//     * 保存MappedStatement<br/>
//     * <功能详细描述>
//     * @param id
//     * @param sqlCommandType
//     * @param sql
//     * @param parameterType
//     * @param resultType
//     * @return [参数说明]
//     * 
//     * @return MappedStatement [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public MappedStatement saveMappedStatement(String id,
//            SqlCommandType sqlCommandType, String sql, Class<?> parameterType) {
//        AssertUtils.notEmpty(id, "id is empty.");
//        AssertUtils.notEmpty(sql, "sql is empty.");
//        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.");
//        AssertUtils.notNull(parameterType, "parameterType is null.");
//        
//        StatementType statementType = StatementType.PREPARED;
//        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
//        Integer fetchSize = null;
//        Integer timeout = null;
//        
//        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
//        boolean flushCache = !isSelect;
//        boolean useCache = isSelect;
//        boolean resultOrdered = false;
//        
//        String keyProperty = null;
//        String keyColumn = null;
//        String databaseId = null;
//        String resultSets = null;
//        String resultMap = null;
//        Class<?> resultType = null;
//        
//        String parameterMap = null;
//        
//        KeyGenerator keyGenerator = (configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType)) ? new Jdbc3KeyGenerator()
//                : new NoKeyGenerator();
//        LanguageDriver lang = getLanguageDriver(null);
//        
//        SqlSource sqlSource = lang.createSqlSource(configuration,
//                sql,
//                parameterType);
//        
//        id = applyCurrentNamespace(id, false);//获取对应的statement的id
//        if (configuration.hasStatement(id, false)) {
//            //如果已经含有了，需要将对应的statement移除
//            this.mappedStatements.remove(id);
//        }
//        MappedStatement statement = addMappedStatement(id,
//                sqlSource,
//                statementType,
//                sqlCommandType,
//                fetchSize,
//                timeout,
//                parameterMap,
//                parameterType,
//                resultMap,
//                resultType,
//                resultSetType,
//                flushCache,
//                useCache,
//                resultOrdered,
//                keyGenerator,
//                keyProperty,
//                keyColumn,
//                databaseId,
//                lang,
//                resultSets);
//        return statement;
//    }
//    
//    /**
//      * 保存MappedStatement<br/>
//      * <功能详细描述>
//      * @param id
//      * @param sqlCommandType
//      * @param sql
//      * @param parameterType
//      * @param resultType
//      * @return [参数说明]
//      * 
//      * @return MappedStatement [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public MappedStatement saveMappedStatement(String id,
//            SqlCommandType sqlCommandType, String sql, Class<?> parameterType,
//            Class<?> resultType) {
//        AssertUtils.notEmpty(id, "id is empty.");
//        AssertUtils.notEmpty(sql, "sql is empty.");
//        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.");
//        AssertUtils.notNull(parameterType, "parameterType is null.");
//        AssertUtils.notNull(resultType, "resultType is null.");
//        
//        StatementType statementType = StatementType.PREPARED;
//        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
//        Integer fetchSize = null;
//        Integer timeout = null;
//        
//        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
//        boolean flushCache = !isSelect;
//        boolean useCache = isSelect;
//        boolean resultOrdered = false;
//        
//        String keyProperty = null;
//        String keyColumn = null;
//        String databaseId = null;
//        String resultSets = null;
//        String resultMap = null;
//        
//        String parameterMap = null;
//        
//        KeyGenerator keyGenerator = (configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType)) ? new Jdbc3KeyGenerator()
//                : new NoKeyGenerator();
//        LanguageDriver lang = getLanguageDriver(null);
//        
//        SqlSource sqlSource = lang.createSqlSource(configuration,
//                sql,
//                parameterType);
//        
//        id = applyCurrentNamespace(id, false);//获取对应的statement的id
//        if (configuration.hasStatement(id, false)) {
//            //如果已经含有了，需要将对应的statement移除
//            this.mappedStatements.remove(id);
//        }
//        MappedStatement statement = addMappedStatement(id,
//                sqlSource,
//                statementType,
//                sqlCommandType,
//                fetchSize,
//                timeout,
//                parameterMap,
//                parameterType,
//                resultMap,
//                resultType,
//                resultSetType,
//                flushCache,
//                useCache,
//                resultOrdered,
//                keyGenerator,
//                keyProperty,
//                keyColumn,
//                databaseId,
//                lang,
//                resultSets);
//        return statement;
//    }
//    
//    /**
//      * 保存MappedStatement
//      * <功能详细描述>
//      * @param id
//      * @param sqlCommandType
//      * @param sql
//      * @param parameterType
//      * @param resultMapId
//      * @param resultType
//      * @param resultMappings
//      * @return [参数说明]
//      * 
//      * @return MappedStatement [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public MappedStatement saveMappedStatement(String id,
//            SqlCommandType sqlCommandType, String sql, Class<?> parameterType,
//            String resultMapId) {
//        AssertUtils.notEmpty(id, "id is empty.");
//        AssertUtils.notEmpty(sql, "sql is empty.");
//        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.");
//        AssertUtils.notNull(parameterType, "parameterType is null.");
//        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
//        
//        StatementType statementType = StatementType.PREPARED;
//        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
//        Integer fetchSize = null;
//        Integer timeout = null;
//        
//        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
//        boolean flushCache = !isSelect;
//        boolean useCache = isSelect;
//        boolean resultOrdered = false;
//        
//        String keyProperty = null;
//        String keyColumn = null;
//        String databaseId = null;
//        String resultSets = null;
//        Class<?> resultType = null;
//        
//        String parameterMap = null;
//        
//        KeyGenerator keyGenerator = (configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType)) ? new Jdbc3KeyGenerator()
//                : new NoKeyGenerator();
//        LanguageDriver lang = getLanguageDriver(null);
//        
//        SqlSource sqlSource = lang.createSqlSource(configuration,
//                sql,
//                parameterType);
//        
//        id = applyCurrentNamespace(id, false);//获取对应的statement的id
//        if (configuration.hasStatement(id, false)) {
//            //如果已经含有了，需要将对应的statement移除
//            this.mappedStatements.remove(id);
//        }
//        MappedStatement statement = addMappedStatement(id,
//                sqlSource,
//                statementType,
//                sqlCommandType,
//                fetchSize,
//                timeout,
//                parameterMap,
//                parameterType,
//                resultMapId,
//                resultType,
//                resultSetType,
//                flushCache,
//                useCache,
//                resultOrdered,
//                keyGenerator,
//                keyProperty,
//                keyColumn,
//                databaseId,
//                lang,
//                resultSets);
//        return statement;
//    }
//    
//    /**
//     * 保存MappedStatement
//     * <功能详细描述>
//     * @param id
//     * @param sqlCommandType
//     * @param sql
//     * @param parameterType
//     * @param resultMapId
//     * @param resultType
//     * @param resultMappings
//     * @return [参数说明]
//     * 
//     * @return MappedStatement [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    public ResultMap saveResultMap(String resultMapId, Class<?> resultType,
//            List<ResultMapping> resultMappings) {
//        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
//        AssertUtils.notNull(resultType, "resultType is null.");
//        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.");
//        AssertUtils.notNull(resultMappings, "resultMappings is null.");
//        
//        resultMapId = applyCurrentNamespace(resultMapId, false);//获取对应的statement的id
//        if (configuration.hasResultMap(resultMapId)) {
//            //如果已经含有了，需要将对应的statement移除
//            this.resultMaps.remove(resultMapId);
//        }
//        ResultMap resultMap = addResultMap(resultMapId,
//                resultType,
//                null,
//                null,
//                resultMappings,
//                true);
//        
//        return resultMap;
//    }
//    
//    /**
//      * 构建ResultMapping
//      * <功能详细描述>
//      * @param resultType
//      * @param property
//      * @param javaType
//      * @param column
//      * @param jdbcType
//      * @return [参数说明]
//      * 
//      * @return ResultMapping [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    public ResultMapping buildResultMapping(Class<?> resultType,
//            String property, Class<?> javaType, String column, JdbcType jdbcType) {
//        String nestedSelect = null;//是否存在nestedSelect
//        String nestedResultMap = null;//
//        String notNullColumn = null;
//        String columnPrefix = null;
//        Class<? extends TypeHandler<?>> typeHandler = null;
//        List<ResultFlag> flags = null; //??
//        String resultSet = null;
//        String foreignColumn = null;
//        boolean lazy = true;
//        ResultMapping rm = super.buildResultMapping(resultType,
//                property,
//                column,
//                javaType,
//                jdbcType,
//                nestedSelect,
//                nestedResultMap,
//                notNullColumn,
//                columnPrefix,
//                typeHandler,
//                flags,
//                resultSet,
//                foreignColumn,
//                lazy);
//        return rm;
//    }
//}
