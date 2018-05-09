package com.tx.component.statistical.mybatismapping;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2017/11/22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StatisticalMapperAssistantExtention
        extends MapperBuilderAssistant {
    private Map<String, MappedStatement> mappedStatements;

    private Map<String, ResultMap> resultMaps;

    public StatisticalMapperAssistantExtention(Configuration configuration,
                                               String resource) {
        super(configuration, resource);

        try {
            this.mappedStatements = (Map) FieldUtils.readDeclaredField(configuration, "mappedStatements", true);
            this.resultMaps = (Map) FieldUtils.readDeclaredField(configuration,
                    "resultMaps",
                    true);
        } catch (IllegalAccessException var4) {
            throw new SILException("IllegalAccessException.e", var4);
        }
    }

    public MappedStatement saveMappedStatement(String id,SqlCommandType sqlCommandType,String sql, Class<?> parameterType) {
        AssertUtils.notEmpty(id, "id is empty.", new String[0]);
        AssertUtils.notEmpty(sql, "sql is empty.", new String[0]);
        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.", new String[0]);
        AssertUtils.notNull(parameterType, "parameterType is null.", new String[0]);
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Object fetchSize = null;
        Object timeout = null;
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean resultOrdered = false;
        Object keyProperty = null;
        Object keyColumn = null;
        Object databaseId = null;
        Object resultSets = null;
        Object resultMap = null;
        Object resultType = null;
        Object parameterMap = null;
        Object keyGenerator = this.configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType) ? new Jdbc3KeyGenerator() : new NoKeyGenerator();
        LanguageDriver lang = this.getLanguageDriver((Class) null);
        SqlSource sqlSource = lang.createSqlSource(this.configuration, sql, parameterType);
        id = this.applyCurrentNamespace(id, false);
        if (this.configuration.hasStatement(id, false)) {
            this.mappedStatements.remove(id);
        }
        MappedStatement statement = this.addMappedStatement(id, sqlSource, statementType, sqlCommandType, (Integer) fetchSize, (Integer) timeout, (String) parameterMap, parameterType, (String) resultMap, (Class) resultType, resultSetType, flushCache, isSelect, resultOrdered, (KeyGenerator) keyGenerator, (String) keyProperty, (String) keyColumn, (String) databaseId, lang, (String) resultSets);
        return statement;
    }
    public MappedStatement saveMappedStatement(String id, SqlCommandType sqlCommandType, String sql, Class<?> parameterType, Class<?> resultType) {
        AssertUtils.notEmpty(id, "id is empty.", new String[0]);
        AssertUtils.notEmpty(sql, "sql is empty.", new String[0]);
        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.", new String[0]);
        AssertUtils.notNull(parameterType, "parameterType is null.", new String[0]);
        AssertUtils.notNull(resultType, "resultType is null.", new String[0]);
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Object fetchSize = null;
        Object timeout = null;
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean resultOrdered = false;
        Object keyProperty = null;
        Object keyColumn = null;
        Object databaseId = null;
        Object resultSets = null;
        Object resultMap = null;
        Object parameterMap = null;
        Object keyGenerator = this.configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType)?new Jdbc3KeyGenerator():new NoKeyGenerator();
        LanguageDriver lang = this.getLanguageDriver((Class)null);
        SqlSource sqlSource = lang.createSqlSource(this.configuration, sql, parameterType);
        id = this.applyCurrentNamespace(id, false);
        if(this.configuration.hasStatement(id, false)) {
            this.mappedStatements.remove(id);
        }

        MappedStatement statement = this.addMappedStatement(id, sqlSource, statementType, sqlCommandType, (Integer)fetchSize, (Integer)timeout, (String)parameterMap, parameterType, (String)resultMap, resultType, resultSetType, flushCache, isSelect, resultOrdered, (KeyGenerator)keyGenerator, (String)keyProperty, (String)keyColumn, (String)databaseId, lang, (String)resultSets);
        return statement;
    }

    public MappedStatement saveMappedStatement(String id, SqlCommandType sqlCommandType, String sql, Class<?> parameterType, String resultMapId) {
        AssertUtils.notEmpty(id, "id is empty.", new String[0]);
        AssertUtils.notEmpty(sql, "sql is empty.", new String[0]);
        AssertUtils.notNull(sqlCommandType, "sqlCommandType is null.", new String[0]);
        AssertUtils.notNull(parameterType, "parameterType is null.", new String[0]);
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.", new String[0]);
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;
        Object fetchSize = null;
        Object timeout = null;
        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
        boolean flushCache = !isSelect;
        boolean resultOrdered = false;
        Object keyProperty = null;
        Object keyColumn = null;
        Object databaseId = null;
        Object resultSets = null;
        Object resultType = null;
        Object parameterMap = null;
        Object keyGenerator = this.configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType)?new Jdbc3KeyGenerator():new NoKeyGenerator();
        LanguageDriver lang = this.getLanguageDriver((Class)null);
        SqlSource sqlSource = lang.createSqlSource(this.configuration, sql, parameterType);
        id = this.applyCurrentNamespace(id, false);
        if(this.configuration.hasStatement(id, false)) {
            this.mappedStatements.remove(id);
        }

        MappedStatement statement = this.addMappedStatement(id, sqlSource, statementType, sqlCommandType, (Integer)fetchSize, (Integer)timeout, (String)parameterMap, parameterType, resultMapId, (Class)resultType, resultSetType, flushCache, isSelect, resultOrdered, (KeyGenerator)keyGenerator, (String)keyProperty, (String)keyColumn, (String)databaseId, lang, (String)resultSets);
        return statement;
    }

    public ResultMap saveResultMap(String resultMapId, Class<?> resultType, List<ResultMapping> resultMappings) {
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.", new String[0]);
        AssertUtils.notNull(resultType, "resultType is null.", new String[0]);
        AssertUtils.notEmpty(resultMapId, "resultMapId is empty.", new String[0]);
        AssertUtils.notNull(resultMappings, "resultMappings is null.", new String[0]);
        resultMapId = this.applyCurrentNamespace(resultMapId, false);
        if(this.configuration.hasResultMap(resultMapId)) {
            this.resultMaps.remove(resultMapId);
        }

        ResultMap resultMap = this.addResultMap(resultMapId, resultType, (String)null, (Discriminator)null, resultMappings, Boolean.valueOf(true));
        return resultMap;
    }

    public ResultMapping buildResultMapping(Class<?> resultType, String property, Class<?> javaType, String column, JdbcType jdbcType) {
        Object nestedSelect = null;
        Object nestedResultMap = null;
        Object notNullColumn = null;
        Object columnPrefix = null;
        Object typeHandler = null;
        Object flags = null;
        Object resultSet = null;
        Object foreignColumn = null;
        boolean lazy = true;
        ResultMapping rm = super.buildResultMapping(resultType, property, column, javaType, jdbcType, (String)nestedSelect, (String)nestedResultMap, (String)notNullColumn, (String)columnPrefix, (Class)typeHandler, (List)flags, (String)resultSet, (String)foreignColumn, lazy);
        return rm;
    }

}
