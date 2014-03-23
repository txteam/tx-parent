/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月21日
 * <修改描述:>
 */
package com.tx.component.rule.exceptions.impl;

import com.tx.component.rule.exceptions.RuleAccessException;


 /**
  * 规则状态非运营态<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleStateNotOperationException extends RuleAccessException{
    
    /** 注释内容 */
    private static final long serialVersionUID = -5206650988132657426L;
    
    /**
     * @return
     */
    @Override
    public String doGetErrorCode() {
        return "RULE_STATE_NOT_OPERATION_ERROR";
    }

    /**
     * @return
     */
    @Override
    public String doGetErrorMessage() {
        return "错误的规则状态";
    }

    /** <默认构造函数> */
    public RuleStateNotOperationException(String message, Object[] parameters) {
        super(message, parameters);
    }

    /** <默认构造函数> */
    public RuleStateNotOperationException(String message) {
        super(message);
    }
}
