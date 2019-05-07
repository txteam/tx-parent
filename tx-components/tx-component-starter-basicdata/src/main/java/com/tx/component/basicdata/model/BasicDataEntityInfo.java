/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.io.Serializable;

/**
 * 基础数据类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataEntityInfo implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7400998214492770823L;
    
    /** 基础数据类型编码 */
    private String type;
    
    /** 基础数据类型 */
    private Class<? extends BasicData> entityClass;
    
    /** 基础数据所属模块 */
    private String module;
    
    /** 基础数据目录 */
    private String catalog;
    
    /** 基础数据对应表名 */
    private String tableName;
    
    /** 基础数据类型名 */
    private String name;
    
    /** 基础数据类型备注 */
    private String remark;
    
    /** 是否分页显示 */
    private BasicDataViewTypeEnum viewType = BasicDataViewTypeEnum.COMMON_LIST;
    
    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return 返回 entityClass
     */
    public Class<? extends BasicData> getEntityClass() {
        return entityClass;
    }
    
    /**
     * @param 对entityClass进行赋值
     */
    public void setEntityClass(Class<? extends BasicData> entityClass) {
        this.entityClass = entityClass;
    }
    
    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
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
     * @return 返回 name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return 返回 catalog
     */
    public String getCatalog() {
        return catalog;
    }
    
    /**
     * @param 对catalog进行赋值
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
    /**
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * @return 返回 viewType
     */
    public BasicDataViewTypeEnum getViewType() {
        return viewType;
    }
    
    /**
     * @param 对viewType进行赋值
     */
    public void setViewType(BasicDataViewTypeEnum viewType) {
        this.viewType = viewType;
    }
}
