/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-18
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.SILException;


 /**
  * 规则容器初始化异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2014-3-18]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleContextInitException extends SILException{

    /** 注释内容 */
    private static final long serialVersionUID = -5532479550244200811L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "RULE_CONTEXT_INIT_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "规则容器初始化错误.";
    }

    /** <默认构造函数> */
    public RuleContextInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
