/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.testmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tx.component.basicdata.annotation.BasicData;
import com.tx.component.basicdata.plugin.impl.SupportDisabled;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionLike;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

/**
 * 测试共表的基础数据模型
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@BasicData()
@Entity
@Table(name = "test_basicdata")
public abstract class BaseTestBasicModel implements SupportDisabled,
        Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -2769834159234589350L;
    
    private String id;
    
    @QueryConditionEqual
    private String code;
    
    @UpdateAble
    @QueryConditionLike
    private String name;
    
    @UpdateAble
    private String remark;
    
    private Date createDate;
    
    @UpdateAble
    private Date lastUpdateDate;
    
    @Column(name = "type_")
    private BasicTypeEnum type;
    
    private boolean valid = true;
    
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
    
    /**
     * @return 返回 type
     */
    public BasicTypeEnum getType() {
        return type;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(BasicTypeEnum type) {
        this.type = type;
    }
}
