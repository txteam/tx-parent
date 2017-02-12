/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月21日
 * <修改描述:>
 */
package com.tx.component.rule.exceptions.impl;

import com.tx.component.rule.exceptions.RuleAccessException;
import com.tx.component.rule.exceptions.RuleContextErrorCodeEnum;
import com.tx.core.exceptions.ErrorCode;


 /**
  * 规则不存在异常<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleNotExistException extends RuleAccessException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5846406069899477033L;
    
    /**
     * @return
     */
    @Override
    protected ErrorCode error() {
        // TODO Auto-generated method stub
        return RuleContextErrorCodeEnum.RULL_ACCESS_ERROR;
    }

    /** <默认构造函数> */
    public RuleNotExistException() {
        super();
    }

    /** <默认构造函数> */
    public RuleNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    /** <默认构造函数> */
    public RuleNotExistException(String message) {
        super(message);
    }
}
