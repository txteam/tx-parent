/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月24日
 * <修改描述:>
 */
package com.tx.component.rule.exceptions;

import com.tx.core.exceptions.SILException;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleRegisterException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2529436573826445229L;

    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "REGISTER_RULE_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "规则项注册异常";
    }

    /** <默认构造函数> */
    public RuleRegisterException(String message) {
        super(message);
    }
    
    /** <默认构造函数> */
    public RuleRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public RuleRegisterException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
