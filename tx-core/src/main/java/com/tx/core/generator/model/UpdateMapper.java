/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.generator.model;

import java.util.List;

import com.tx.core.generator.util.GeneratorUtils;
import com.tx.core.reflection.JpaMetaClass;

/**
 * 更新映射生成器
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdateMapper {
    
    private String id;
    
    private List<SqlMapColumn> sqlMapColumnList;
    
    private String tableName;
    
    private String simpleTableName;
    
    private String idColumnName;
    
    private String idPropertyName;
    
    private List<SqlMapColumn> updateSqlMapColumnList;
    
    /** <默认构造函数> */
    public UpdateMapper() {
        super();
    }
    
    /** <默认构造函数> */
    public UpdateMapper(JpaMetaClass<?> jpaMetaClass) {
        super();
        this.id = "update" + jpaMetaClass.getEntitySimpleName();
        
        this.idPropertyName = jpaMetaClass.getPkGetterName();
        this.idColumnName = jpaMetaClass.getGetter2columnInfoMapping()
                .get(this.idPropertyName)
                .getColumnName();
                //.toUpperCase();
        this.simpleTableName = jpaMetaClass.getSimpleTableName().toUpperCase();
        this.tableName = jpaMetaClass.getTableName().toUpperCase();
        this.sqlMapColumnList = GeneratorUtils.generateSqlMapColumnList(jpaMetaClass,false);
    }
    
    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
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
     * @return 返回 updateSqlMapColumnList
     */
    public List<SqlMapColumn> getUpdateSqlMapColumnList() {
        return updateSqlMapColumnList;
    }
    
    /**
     * @param 对updateSqlMapColumnList进行赋值
     */
    public void setUpdateSqlMapColumnList(
            List<SqlMapColumn> updateSqlMapColumnList) {
        this.updateSqlMapColumnList = updateSqlMapColumnList;
    }
}
