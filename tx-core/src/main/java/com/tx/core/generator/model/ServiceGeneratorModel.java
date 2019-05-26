/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-11
 * <修改描述:>
 */
package com.tx.core.generator.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.springframework.util.ClassUtils;

import com.tx.core.generator.util.GeneratorUtils;
import com.tx.core.generator.util.JpaMetaClass;
import com.tx.core.generator.util.SqlSource;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public class ServiceGeneratorModel {
    
    private String basePackage;
    
    private String entitySimpleName;
    
    private String lowerCaseEntitySimpleName;
    
    private String upCaseIdPropertyName;
    
    private String idPropertyName;
    
    private List<SqlMapColumn> sqlMapColumnList;
    
    /** 查询条件名与类型映射 */
    private Map<String, String> queryConditionName2TypeNameMapping = new HashMap<String, String>();
    
    /** 可更新字段的键值关系 */
    private Map<String, String> updateKey2ValueMapping = new HashMap<String, String>();
    
    /** 可更新字段映射 */
    private Map<String, SqlMapColumn> updateAbleName2SqlMapColumnMapping = new HashMap<String, SqlMapColumn>();
    
    /** 扩展类型名集合 */
    private Set<String> extentionTypeNames = new HashSet<String>();
    
    public ServiceGeneratorModel() {
        super();
    }
    
    /** <默认构造函数> */
    public ServiceGeneratorModel(JpaMetaClass<?> jpaMetaClass,
            SqlSource<?> sqlSource, Dialect dialect) {
        super();
        String basePath = ClassUtils.convertClassNameToResourcePath(jpaMetaClass.getEntityTypeName())
                + "/../..";
        basePath = org.springframework.util.StringUtils.cleanPath(basePath);
        this.basePackage = ClassUtils.convertResourcePathToClassName(basePath);
        
        this.entitySimpleName = jpaMetaClass.getEntitySimpleName();
        this.lowerCaseEntitySimpleName = StringUtils.uncapitalize(jpaMetaClass.getEntitySimpleName());
        this.upCaseIdPropertyName = StringUtils.capitalize(jpaMetaClass.getPkGetterName());
        this.idPropertyName = sqlSource.getPkName();
        this.sqlMapColumnList = GeneratorUtils.generateSqlMapColumnList(jpaMetaClass);
        Map<String, SqlMapColumn> sqlMapColumnMap = new HashMap<String, SqlMapColumn>();
        for(SqlMapColumn sqlMapColumnTemp : this.sqlMapColumnList){
            sqlMapColumnMap.put(sqlMapColumnTemp.getPropertyName(), sqlMapColumnTemp);
        }
        
        Map<String, Class<?>> key2JavaTypeMapping = sqlSource.getQueryConditionKey2JavaTypeMapping();
        for (Entry<String, Class<?>> key2JavaTypeEntryTemp : key2JavaTypeMapping.entrySet()) {
            Class<?> queryConditionType = key2JavaTypeEntryTemp.getValue();
            String typeName = queryConditionType.getName();
            if (typeName.indexOf("java.lang") < 0
                    && !"java.util.List".equals(typeName)
                    && !"java.util.HashMap".equals(typeName)
                    && !"java.util.Map".equals(typeName)
                    && !"java.util.Set".equals(typeName)
                    && !"java.util.HashSet".equals(typeName)
                    && !com.tx.core.util.ClassUtils.isCommonSimpleClass(typeName)) {
                extentionTypeNames.add(typeName);
            }
            this.queryConditionName2TypeNameMapping.put(key2JavaTypeEntryTemp.getKey(),
                    queryConditionType.getSimpleName());
        }
        
        Set<String> updatePropertyNameSet = sqlSource.getUpdateAblePropertyNames();
        for (String updateAblePropertyNameTemp : updatePropertyNameSet) {
            Class<?> updateAblePropertyType = jpaMetaClass.getGetterType(updateAblePropertyNameTemp);
            if (updateAblePropertyType == null && updateAbleName2SqlMapColumnMapping.containsKey(updateAblePropertyNameTemp)) {
                continue;
            }
            updateAbleName2SqlMapColumnMapping.put(updateAblePropertyNameTemp, sqlMapColumnMap.get(updateAblePropertyNameTemp));
            String typeName = updateAblePropertyType.getName();
            if (boolean.class.equals(typeName)) {
                updateKey2ValueMapping.put(updateAblePropertyNameTemp, "is"
                        + StringUtils.capitalize(updateAblePropertyNameTemp)
                        + "()");
            } else {
                updateKey2ValueMapping.put(updateAblePropertyNameTemp, "get"
                        + StringUtils.capitalize(updateAblePropertyNameTemp)
                        + "()");
            }
        }
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
    
    /**
     * @return 返回 queryConditionName2TypeNameMapping
     */
    public Map<String, String> getQueryConditionName2TypeNameMapping() {
        return queryConditionName2TypeNameMapping;
    }
    
    /**
     * @param 对queryConditionName2TypeNameMapping进行赋值
     */
    public void setQueryConditionName2TypeNameMapping(
            Map<String, String> queryConditionName2TypeNameMapping) {
        this.queryConditionName2TypeNameMapping = queryConditionName2TypeNameMapping;
    }
    
    /**
     * @return 返回 updateKey2ValueMapping
     */
    public Map<String, String> getUpdateKey2ValueMapping() {
        return updateKey2ValueMapping;
    }
    
    /**
     * @param 对updateKey2ValueMapping进行赋值
     */
    public void setUpdateKey2ValueMapping(
            Map<String, String> updateKey2ValueMapping) {
        this.updateKey2ValueMapping = updateKey2ValueMapping;
    }
    
    /**
     * @return 返回 extentionTypeNames
     */
    public Set<String> getExtentionTypeNames() {
        return extentionTypeNames;
    }
    
    /**
     * @param 对extentionTypeNames进行赋值
     */
    public void setExtentionTypeNames(Set<String> extentionTypeNames) {
        this.extentionTypeNames = extentionTypeNames;
    }

    /**
     * @return 返回 updateAbleName2SqlMapColumnMapping
     */
    public Map<String, SqlMapColumn> getUpdateAbleName2SqlMapColumnMapping() {
        return updateAbleName2SqlMapColumnMapping;
    }

    /**
     * @param 对updateAbleName2SqlMapColumnMapping进行赋值
     */
    public void setUpdateAbleName2SqlMapColumnMapping(
            Map<String, SqlMapColumn> updateAbleName2SqlMapColumnMapping) {
        this.updateAbleName2SqlMapColumnMapping = updateAbleName2SqlMapColumnMapping;
    }
}
