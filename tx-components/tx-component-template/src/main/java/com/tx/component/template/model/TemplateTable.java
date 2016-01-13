/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.template.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;

import com.tx.component.template.basicdata.TemplateTableStatusEnum;
import com.tx.component.template.basicdata.TemplateTableType;

/**
 * 模板表<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Table(name="tp_table")
public class TemplateTable implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 8275499359664274226L;

    /** 模板表id:具体的一张模板表，id唯一对应唯一的tableName */
    private String id;
    
    /** 模板名，与tableType为模板表的联合唯一键，名字一旦确定不能进行修改 */
    private String name;
    
    /** 模板表类型：主表，备份表，历史表... */
    private TemplateTableType tableType;
    
    /** 模板表版本<br/> V + _YYYYMMDD_ + x x首次为0，当表从运营态切换时，将自动+1，原0态表自动，重命名后作为备份存在，表明切换为tp_bak_... */
    private String version;
    
    /** 与id一起同为模板表的唯一键 */
    private String tableName;
    
    /** 模板表状态 */
    private TemplateTableStatusEnum tableStatus = TemplateTableStatusEnum.配置态;
    
    /** 真正的表名 */
    private String realTableName;
    
    /** 所属系统id:与name,tableType一起作为联合唯一键 */
    private String systemId;
    
    /** 
     * 模板表字段集
     * 每个模板表都有独立的一整套表字段
     * 模板表中公共字段并不影响该字段
     * 仅在模板表创建初期（创建时，配置态），将由公共字段拷贝一份增加到当前表字段中 
     */
    @OneToMany
    private Set<TemplateColumn> columns;
    
    @OneToMany
    private Set<String> columnNames;
    
    /** 模板表备注描述 */
    private String remark;
    
    /** 创建人 */
    private String createOperatorId;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;

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
     * @return 返回 systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
     * @return 返回 tableType
     */
    public TemplateTableType getTableType() {
        return tableType;
    }

    /**
     * @param 对tableType进行赋值
     */
    public void setTableType(TemplateTableType tableType) {
        this.tableType = tableType;
    }

    /**
     * @return 返回 tableStatus
     */
    public TemplateTableStatusEnum getTableStatus() {
        return tableStatus;
    }

    /**
     * @param 对tableStatus进行赋值
     */
    public void setTableStatus(TemplateTableStatusEnum tableStatus) {
        this.tableStatus = tableStatus;
    }

    /**
     * @return 返回 columns
     */
    public Set<TemplateColumn> getColumns() {
        return columns;
    }

    /**
     * @param 对columns进行赋值
     */
    public void setColumns(Set<TemplateColumn> columns) {
        this.columns = columns;
        this.columnNames = new HashSet<String>();
        if(!CollectionUtils.isEmpty(columns)){
            for(TemplateColumn columnTemp : columns){
                this.columnNames.add(columnTemp.getColumnName());
            }
        }
    }
    
    /**
     * @return 返回 columnNames
     */
    public Set<String> getColumnNames() {
        return columnNames;
    }

    /**
     * @return 返回 createOperatorId
     */
    public String getCreateOperatorId() {
        return createOperatorId;
    }

    /**
     * @param 对createOperatorId进行赋值
     */
    public void setCreateOperatorId(String createOperatorId) {
        this.createOperatorId = createOperatorId;
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
}
