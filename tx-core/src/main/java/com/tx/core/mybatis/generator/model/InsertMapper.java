/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-9
 * <修改描述:>
 */
package com.tx.core.mybatis.generator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 插入的映射器描述
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-12-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class InsertMapper {
    
    private String id;
    
    private String parameterType;
    
    private boolean isUseSelectKey = false;
    
    private SqlMapSelectKey selectKey;
    
    private String tableName;
    
    private List<SqlMapColumn> sqlMapColumnList = new ArrayList<SqlMapColumn>();

    /**
     * @return 返回 isUseSelectKey
     */
    public boolean isUseSelectKey() {
        return isUseSelectKey;
    }

    /**
     * @param 对isUseSelectKey进行赋值
     */
    public void setUseSelectKey(boolean isUseSelectKey) {
        this.isUseSelectKey = isUseSelectKey;
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
     * @return 返回 selectKey
     */
    public SqlMapSelectKey getSelectKey() {
        return selectKey;
    }

    /**
     * @param 对selectKey进行赋值
     */
    public void setSelectKey(SqlMapSelectKey selectKey) {
        this.selectKey = selectKey;
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
