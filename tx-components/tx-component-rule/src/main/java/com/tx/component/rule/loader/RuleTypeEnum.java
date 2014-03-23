/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.component.rule.loader;

/**
 * 规则类型<br/>
 *     系统支持的规则类型<br/>
 *     drools,method,collection<br/>
 *     后期计划增加groovy,guel,javascript的支持<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-2-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public enum RuleTypeEnum {
    
    /**
     * 方法类型规则
     */
    JAVA_METHOD("JAVA方法类型规则"),

    /**
     * DROOLS规则
     */
    DROOLS_DRL_BYTE("DRL_BYTE_DROOLS"),
    /**
     * DROOLS规则
     */
    DROOLS_PKG_URL("DRL_URL_DROOLS");
    
    private String name;
    
    private String remark;
    
    /**
     * 规则类型
     */
    private RuleTypeEnum(String name) {
        this.name = name;
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
}
