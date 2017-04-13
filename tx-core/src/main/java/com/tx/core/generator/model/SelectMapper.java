/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.generator.util.GeneratorUtils;
import com.tx.core.jdbc.sqlsource.SqlSource;
import com.tx.core.reflection.JpaMetaClass;

/**
 * select相关语句生成器<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SelectMapper {
    
    private String resultMapId;
    
    private String parameterType;
    
    private List<SqlMapColumn> sqlMapColumnList;
    
    private String findId;
    
    private String queryId;
    
    private String tableName;
    
    private String simpleTableName;
    
    private String idColumnName;
    
    private String idPropertyName;
    
    private Map<String, String> queryConditionMap;
    
    private Set<String> otherCondition = new HashSet<String>();
    
    public SelectMapper() {
        super();
    }
    
    public SelectMapper(JpaMetaClass<?> jpaMetaClass, SqlSource<?> sqlSource) {
        super();
        this.resultMapId = StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName())
                + "Map";
        this.parameterType = jpaMetaClass.getEntityTypeName();
        this.findId = "find" + jpaMetaClass.getEntitySimpleName();
        this.queryId = "query" + jpaMetaClass.getEntitySimpleName();
        
        this.tableName = jpaMetaClass.getTableName().toUpperCase();
        this.simpleTableName = jpaMetaClass.getSimpleTableName().toUpperCase();
        this.idPropertyName = jpaMetaClass.getPkGetterName();
        this.idColumnName = jpaMetaClass.getGetter2columnInfoMapping().get(this.idPropertyName).getColumnName().toUpperCase();
        this.sqlMapColumnList = GeneratorUtils.generateSqlMapColumnList(jpaMetaClass,false);
        
        this.otherCondition.addAll(sqlSource.getOtherCondition());
        this.queryConditionMap = GeneratorUtils.generateQueryConditionMap(jpaMetaClass,
                sqlSource);
        
    }
    
    /**
     * @return 返回 queryId
     */
    public String getQueryId() {
        return queryId;
    }
    
    /**
     * @param 对queryId进行赋值
     */
    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }
    
    /**
     * @return 返回 resultMapId
     */
    public String getResultMapId() {
        return resultMapId;
    }
    
    /**
     * @param 对resultMapId进行赋值
     */
    public void setResultMapId(String resultMapId) {
        this.resultMapId = resultMapId;
    }
    
    /**
     * @return 返回 parameterType
     */
    public String getParameterType() {
        return parameterType;
    }
    
    /**
     * @param 对parameterType进行赋值
     */
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
    
    /**
     * @return 返回 sqlMapColumnList
     */
    public List<SqlMapColumn> getSqlMapColumnList() {
        return sqlMapColumnList;
    }
    
    /**
     * @param 对sqlMapColumnList进行赋值
     */
    public void setSqlMapColumnList(List<SqlMapColumn> sqlMapColumnList) {
        this.sqlMapColumnList = sqlMapColumnList;
    }
    
    /**
     * @return 返回 findId
     */
    public String getFindId() {
        return findId;
    }
    
    /**
     * @param 对findId进行赋值
     */
    public void setFindId(String findId) {
        this.findId = findId;
    }
    
    /**
     * @return 返回 tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    /**
     * @param 对tableName进行赋值
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * @return 返回 simpleTableName
     */
    public String getSimpleTableName() {
        return simpleTableName;
    }
    
    /**
     * @param 对simpleTableName进行赋值
     */
    public void setSimpleTableName(String simpleTableName) {
        this.simpleTableName = simpleTableName;
    }
    
    /**
     * @return 返回 idColumnName
     */
    public String getIdColumnName() {
        return idColumnName;
    }
    
    /**
     * @param 对idColumnName进行赋值
     */
    public void setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
    }
    
    /**
     * @return 返回 idPropertyName
     */
    public String getIdPropertyName() {
        return idPropertyName;
    }
    
    /**
     * @param 对idPropertyName进行赋值
     */
    public void setIdPropertyName(String idPropertyName) {
        this.idPropertyName = idPropertyName;
    }
    
    /**
     * @return 返回 queryConditionMap
     */
    public Map<String, String> getQueryConditionMap() {
        return queryConditionMap;
    }
    
    /**
     * @param 对queryConditionMap进行赋值
     */
    public void setQueryConditionMap(Map<String, String> queryConditionMap) {
        this.queryConditionMap = queryConditionMap;
    }
    
    /**
     * @return 返回 otherCondition
     */
    public Set<String> getOtherCondition() {
        return otherCondition;
    }
    
    /**
     * @param 对otherCondition进行赋值
     */
    public void setOtherCondition(Set<String> otherCondition) {
        this.otherCondition = otherCondition;
    }
}
