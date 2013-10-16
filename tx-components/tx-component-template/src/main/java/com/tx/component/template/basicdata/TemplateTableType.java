/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-12
 * <修改描述:>
 */
package com.tx.component.template.basicdata;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 模板表类型枚举<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-12]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Table(name = "TEMPLATE_TABLETYPE")
public class TemplateTableType implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = -6676531255782869781L;

    /** 模板表类型唯一键 */
    @Id
    private String id;
    
    /** 所属系统id */
    private String systemId;
    
    /** 模板表类型，唯一键，名字不能重复 */
    private String name;
    
    /** 模板表类型后缀名 */
    private String tableSuffix;
    
    /** 模板表类型名描述 */
    private String remark;
    
    /** 模板表类型是否有效 */
    private boolean valid;

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
     * @return 返回 tableSuffix
     */
    public String getTableSuffix() {
        return tableSuffix;
    }

    /**
     * @param 对tableSuffix进行赋值
     */
    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
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
}
