/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-11]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DaoGeneratorModel {
    
    private String basePackage;
    
    private String entityTypeName;
    
    private String simpleEntityTypeName;
    
    private String lowerCaseEntityTypeName;

    /**
     * @return 返回 lowerCaseEntityTypeName
     */
    public String getLowerCaseEntityTypeName() {
        return lowerCaseEntityTypeName;
    }

    /**
     * @param 对lowerCaseEntityTypeName进行赋值
     */
    public void setLowerCaseEntityTypeName(String lowerCaseEntityTypeName) {
        this.lowerCaseEntityTypeName = lowerCaseEntityTypeName;
    }

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
     * @return 返回 entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * @param 对entityTypeName进行赋值
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    /**
     * @return 返回 simpleEntityTypeName
     */
    public String getSimpleEntityTypeName() {
        return simpleEntityTypeName;
    }

    /**
     * @param 对simpleEntityTypeName进行赋值
     */
    public void setSimpleEntityTypeName(String simpleEntityTypeName) {
        this.simpleEntityTypeName = simpleEntityTypeName;
    }
}
