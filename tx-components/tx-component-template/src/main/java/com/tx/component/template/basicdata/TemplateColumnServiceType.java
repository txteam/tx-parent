/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-14
 * <修改描述:>
 */
package com.tx.component.template.basicdata;

import java.io.Serializable;


 /**
  * 模板字段业务类型
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TemplateColumnServiceType implements Serializable{

    /** 注释内容 */
    private static final long serialVersionUID = -4170866621472703672L;
    
    /** 模板字段业务类型id */
    private String id;
    
    /** 模板字段业务类型名 */
    private String name;
    
    /** 模板字段业务类型备注 */
    private String remark;

    /**
     * @return 返回 id
     */
    protected String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    protected void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 name
     */
    protected String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * @return 返回 remark
     */
    protected String getRemark() {
        return remark;
    }

    /**
     * @param 对remark进行赋值
     */
    protected void setRemark(String remark) {
        this.remark = remark;
    }
}
