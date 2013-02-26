/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-26
 * <修改描述:>
 */
package com.tx.component.rule.model;


 /**
  * 代理规则类
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-2-26]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DelegateRule implements Rule{
    
    /** 注释内容 */
    private static final long serialVersionUID = -8738219157375449489L;
    
    private Rule delegateRule;
    
    /** <默认构造函数> */
    public DelegateRule(Rule delegateRule) {
        this.delegateRule = delegateRule;
    }

    /**
     * @return
     */
    @Override
    public String rule() {
        return this.delegateRule.rule();
    }

    /**
     * @return
     */
    @Override
    public RuleType getRuleType() {
        return this.delegateRule.getRuleType();
    }

    /**
     * @return
     */
    @Override
    public String getServiceType() {
        return this.delegateRule.getServiceType();
    }
}
