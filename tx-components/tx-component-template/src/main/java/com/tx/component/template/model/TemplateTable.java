/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-7-9
 * <修改描述:>
 */
package com.tx.component.template.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;


 /**
  * 模板表管理<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-7-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Entity
@Table(name="t_tp_table")
public class TemplateTable extends TemplateEngineBaseModel{
    
    /** 注释内容 */
    private static final long serialVersionUID = -5673765290724868175L;

    /** 模板表唯一键 */
    private String id;
    
    /** 模板表名：可为中文 */
    private String name;
    
    /** 模板表明：前缀字符控制在10以内 */
    private String prefixTableName = "t_tp_ins_";
    
    /** 模板表：表名：字符（长度16）唯一键  */
    private String tableName;
    
    /** 模板表描述 */
    private String description;
    
    /** 模板创建时间 */
    private Date createDate;
    
    /** 模板表最后更新时间 */
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
     * @return 返回 description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return 返回 prefixTableName
     */
    public String getPrefixTableName() {
        return prefixTableName;
    }

    /**
     * @param 对prefixTableName进行赋值
     */
    public void setPrefixTableName(String prefixTableName) {
        this.prefixTableName = prefixTableName;
    }
}
