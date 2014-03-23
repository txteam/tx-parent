/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.loader;

/**
 * 规则的属性值
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleItemByteParam extends BaseRuleItemParam {
    
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
