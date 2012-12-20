/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;

import java.util.List;


 /**
  * <功能简述>
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
}
