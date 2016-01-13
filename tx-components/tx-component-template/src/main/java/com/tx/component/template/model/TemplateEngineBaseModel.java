/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-7-31
 * <修改描述:>
 */
package com.tx.component.template.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


 /**
  * 模板引擎基础模型<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-7-31]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Entity
@Table(name="tp_engine")
public class TemplateEngineBaseModel implements Serializable{
    
    /** 注释内容 */
    private static final long serialVersionUID = -9138821554595146774L;
    /**
     * 模板表版本
     */
    private String version = "V1.0";

    /**
     * @return 返回 version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param 对version进行赋值
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
