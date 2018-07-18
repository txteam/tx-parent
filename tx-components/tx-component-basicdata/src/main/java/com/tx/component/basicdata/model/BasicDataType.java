/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;
import com.tx.core.support.initable.model.ConfigInitAble;

/**
 * 基础数据类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "bd_basic_data_type")
public class BasicDataType implements ConfigInitAble, Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7400998214492770823L;
    
    /** 基础数据id */
    @Id
    private String id;
    
    /** 基础数据类型 */
    @QueryConditionEqual
    private Class<? extends BasicData> type;
    
    /** 基础数据所属模块 */
    @UpdateAble
    @QueryConditionEqual
    private String module;
    
    /** 基础数据类型编码 */
    @UpdateAble
    @QueryConditionEqual
    private String code;
    
    /** 基础数据对应表名 */
    @UpdateAble
    @QueryConditionEqual
    private String tableName;
    
    /** 基础数据类型名 */
    @UpdateAble
    @QueryConditionEqual
    private String name;
    
    @UpdateAble
    @QueryConditionEqual
    private boolean valid = true;
    
    @UpdateAble
    @QueryConditionEqual
    private boolean modifyAble = false;
    
    /** 基础数据类型备注 */
    @UpdateAble
    private String remark;
    
    /** 是否在通用的界面中进行数据维护 */
    @UpdateAble
    @QueryConditionEqual
    private boolean common = true;
    
    /** 是否分页显示 */
    @UpdateAble
    @QueryConditionEqual
    private BasicDataViewTypeEnum viewType = BasicDataViewTypeEnum.LIST;
    
    /** 最后更新时间 */
    @UpdateAble
    private Date lastUpdateDate;
    
    /** 基础数据类型创建时间 */
    private Date createDate;
    
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
     * @return 返回 type
     */
    public Class<? extends BasicData> getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<? extends BasicData> type) {
        this.type = type;
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
     * @return 返回 code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param 对code进行赋值
     */
    public void setCode(String code) {
        this.code = code;
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
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }
    
    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    /**
     * @return 返回 modifyAble
     */
    public boolean isModifyAble() {
        return modifyAble;
    }
    
    /**
     * @param 对modifyAble进行赋值
     */
    public void setModifyAble(boolean modifyAble) {
        this.modifyAble = modifyAble;
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
     * @return 返回 common
     */
    public boolean isCommon() {
        return common;
    }
    
    /**
     * @param 对common进行赋值
     */
    public void setCommon(boolean common) {
        this.common = common;
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
    
    /**
     * @return 返回 createDate
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    /**
     * @param 对lastUpdateDate进行赋值
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
