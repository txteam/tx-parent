/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;

import java.util.List;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-11]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class ServiceGeneratorModel {
    
    private String basePackage;
    
    private String entitySimpleName;
    
    private String lowerCaseEntitySimpleName;
    
    private String upCaseIdPropertyName;
    
    private String idPropertyName;
    
    private List<SqlMapColumn> sqlMapColumnList;

    /**
     * @return 返回 basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * @param 对basePackage进行赋值
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * @return 返回 entitySimpleName
     */
    public String getEntitySimpleName() {
        return entitySimpleName;
    }

    /**
     * @param 对entitySimpleName进行赋值
     */
    public void setEntitySimpleName(String entitySimpleName) {
        this.entitySimpleName = entitySimpleName;
    }

    /**
     * @return 返回 lowerCaseEntitySimpleName
     */
    public String getLowerCaseEntitySimpleName() {
        return lowerCaseEntitySimpleName;
    }

    /**
     * @param 对lowerCaseEntitySimpleName进行赋值
     */
    public void setLowerCaseEntitySimpleName(String lowerCaseEntitySimpleName) {
        this.lowerCaseEntitySimpleName = lowerCaseEntitySimpleName;
    }

    /**
     * @return 返回 upCaseIdPropertyName
     */
    public String getUpCaseIdPropertyName() {
        return upCaseIdPropertyName;
    }

    /**
     * @param 对upCaseIdPropertyName进行赋值
     */
    public void setUpCaseIdPropertyName(String upCaseIdPropertyName) {
        this.upCaseIdPropertyName = upCaseIdPropertyName;
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
}
