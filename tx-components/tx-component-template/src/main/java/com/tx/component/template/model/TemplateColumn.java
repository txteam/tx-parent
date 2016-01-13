/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.template.model;

import java.io.Serializable;

import javax.persistence.Table;

import com.tx.component.template.basicdata.TemplateColumnServiceType;
import com.tx.component.template.basicdata.TemplateColumnTypeEnum;
import com.tx.component.template.basicdata.TemplateColumnValidator;
import com.tx.core.util.ObjectUtils;

/**
 * 模板表字段<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Table(name = "tp_column")
public class TemplateColumn implements Serializable, Cloneable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5052803226029030696L;
    
    /** 模板字段唯一id */
    private String id;
    
    /** label的作用，字段名 */
    private String name;
    
    /** 字段对应的实际字段名：实际的数据库字段名 */
    private String columnName;
    
    /** 模板表所属模板表id */
    private String templateTableId;
    
    /** 
     * 模板表字段类型 
     * 由该类型决定对应数据的表中结构
     */
    private TemplateColumnTypeEnum columnType;
    
    /** 模板字段业务类型 */
    private TemplateColumnServiceType columnServiceType;
    
    /** 模板字段验证器 */
    private TemplateColumnValidator templateColumnValidator;
    
    
    
    /** <默认构造函数> */
    public TemplateColumn() {
    }

    /** <默认构造函数> */
    public TemplateColumn(String id) {
        super();
        this.id = id;
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
     * @return 返回 columnName
     */
    public String getColumnName() {
        return columnName;
    }
    
    /**
     * @param 对columnName进行赋值
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * @return 返回 templateTableId
     */
    public String getTemplateTableId() {
        return templateTableId;
    }
    
    /**
     * @param 对templateTableId进行赋值
     */
    public void setTemplateTableId(String templateTableId) {
        this.templateTableId = templateTableId;
    }
    
    /**
     * @return 返回 columnType
     */
    public TemplateColumnTypeEnum getColumnType() {
        return columnType;
    }
    
    /**
     * @param 对columnType进行赋值
     */
    public void setColumnType(TemplateColumnTypeEnum columnType) {
        this.columnType = columnType;
    }
    
    /**
     * @return 返回 columnServiceType
     */
    public TemplateColumnServiceType getColumnServiceType() {
        return columnServiceType;
    }
    
    /**
     * @param 对columnServiceType进行赋值
     */
    public void setColumnServiceType(TemplateColumnServiceType columnServiceType) {
        this.columnServiceType = columnServiceType;
    }
    
    /**
     * @return 返回 templateColumnValidator
     */
    public TemplateColumnValidator getTemplateColumnValidator() {
        return templateColumnValidator;
    }
    
    /**
     * @param 对templateColumnValidator进行赋值
     */
    public void setTemplateColumnValidator(
            TemplateColumnValidator templateColumnValidator) {
        this.templateColumnValidator = templateColumnValidator;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = ObjectUtils.equals(this, obj, "id");
        return flag;
    }
    
    /**
     * @return
     */
    @Override
    public int hashCode() {
        int hashCode = ObjectUtils.generateHashCode(super.hashCode(),
                this,
                "id");
        return hashCode;
    }
}
