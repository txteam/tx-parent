/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tx.core.generator.annotation.Comment;
import org.apache.commons.lang3.StringUtils;

import com.tx.component.basicdata.annotation.BasicDataType;
import com.tx.core.jdbc.sqlsource.annotation.QueryConditionEqual;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;
import com.tx.core.support.entrysupport.model.EntityEntry;
import com.tx.core.support.entrysupport.model.EntryAble;
import com.tx.core.support.initable.model.ConfigInitAble;

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
@Table(name = "bd_data_dict")
@BasicDataType(name = "数据字典", common = false, viewType = BasicDataViewTypeEnum.PAGEDLIST)
@Comment("数据字典")
public class DataDict implements EntryAble<EntityEntry>, ConfigInitAble,
        BasicData {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3972222691318635864L;
    
    /** 数据字典数据：唯一键 */
    @Id
    @Comment("唯一ID")
    private String id;
    
    /** 父级对象id */
    @UpdateAble
    @QueryConditionEqual
    @Transient
    private DataDict parent;
    
    /** 类型编码 */
    @QueryConditionEqual
    private String basicDataTypeCode;
    
    /** 编码 */
    @QueryConditionEqual
    private String code;
    
    /** 是否有效 */
    @UpdateAble
    @QueryConditionEqual
    private boolean valid = true;
    
    /** 是否可编辑 */
    @UpdateAble
    @QueryConditionEqual
    private boolean modifyAble = true;
    
    /** 名称 */
    @UpdateAble
    @QueryConditionEqual
    private String name;
    
    /** 备注 */
    @UpdateAble
    @QueryConditionEqual
    private String remark;
    
    /** 最后更新时间 */
    @UpdateAble
    private Date lastUpdateDate;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 分项列表 */
    @OneToMany
    @Transient
    private List<EntityEntry> entryList = new ArrayList<>();

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
     * @return 返回 parent
     */
    public DataDict getParent() {
        return parent;
    }
    
    /**
     * @param 对parent进行赋值
     */
    public void setParent(DataDict parent) {
        this.parent = parent;
    }
    
    /**
     * @return 返回 parentId
     */
    public String getParentId() {
        if (this.parent == null) {
            return null;
        }
        return this.parent.getId();
    }
    
    public void setParentId(String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            return;
        }
        if (this.parent == null) {
            this.parent = new DataDict();
        }
        this.parent.setId(parentId);
    }
    
    /**
     * @return 返回 basicDataTypeCode
     */
    public String getBasicDataTypeCode() {
        return basicDataTypeCode;
    }
    
    /**
     * @param 对basicDataTypeCode进行赋值
     */
    public void setBasicDataTypeCode(String basicDataTypeCode) {
        this.basicDataTypeCode = basicDataTypeCode;
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
    
    /**
     * @return 返回 entryList
     */
    public List<EntityEntry> getEntryList() {
        return entryList;
    }
    
    /**
     * @param 对entryList进行赋值
     */
    public void setEntryList(List<EntityEntry> entryList) {
        this.entryList = entryList;
    }
}
