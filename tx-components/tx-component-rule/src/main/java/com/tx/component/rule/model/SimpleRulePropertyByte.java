/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-28
 * <修改描述:>
 */
package com.tx.component.rule.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 简单规则的属性值
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-28]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "ru_rule_pro_byte")
public class SimpleRulePropertyByte extends SimpleRuleProperty {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7481620291473787606L;
    
    /** 对应属性值 */
    private byte[] paramValue;
    
    /**
     * @return 返回 paramValue
     */
    public byte[] getParamValue() {
        return paramValue;
    }
    
    /**
     * @param 对paramValue进行赋值
     */
    public void setParamValue(byte[] paramValue) {
        this.paramValue = paramValue;
    }
}
