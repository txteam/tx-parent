/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * 基础数据字典<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
public abstract class AbstractDataDict implements BasicData {
    
    /** 注释内容 */
    private static final long serialVersionUID = 1866886683462471572L;
    
    /** 数据字典数据：唯一键 */
    @Id
    @ApiModelProperty(value = "唯一键")
    private String id;
    
    /** 类型编码 */
    @QueryConditionEqual
    @ApiModelProperty(value = "类型", required = true)
    private String type;
    
    /** 编码 */
    @QueryConditionEqual
    @ApiModelProperty(value = "编码", required = true)
    private String code;
    
    /** 是否有效 */
    @UpdateAble
    @QueryConditionEqual
    @ApiModelProperty(value = "是否有效")
    private boolean valid = true;
    
    /** 是否可编辑 */
    @UpdateAble
    @QueryConditionEqual
    @ApiModelProperty(value = "是否可编辑")
    private boolean modifyAble = true;
    
    /** 名称 */
    @UpdateAble
    @QueryConditionEqual
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    
    /** 备注 */
    @UpdateAble
    @QueryConditionEqual
    @ApiModelProperty(value = "备注")
    private String remark;
    
    /** 最后更新时间 */
    @UpdateAble
    @ApiModelProperty(value = "最后更新时间", accessMode = AccessMode.READ_ONLY)
    private Date lastUpdateDate;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", accessMode = AccessMode.READ_ONLY)
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
}
