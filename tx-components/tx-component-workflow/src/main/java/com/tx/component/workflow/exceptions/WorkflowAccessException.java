/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-6
 * <修改描述:>
 */
package com.tx.component.workflow.exceptions;

import com.tx.core.exceptions.ErrorCode;
import com.tx.core.exceptions.SILException;

/**
 * 流程访问异常<br/>
 *     用于记录所有的工作流相关异常<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WorkflowAccessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5085470848631474509L;
    
    /**
     * <默认构造函数>
     */
    public WorkflowAccessException(String errorMessage, String... parameters) {
        super(ErrorCode.WORKFLOW_EXCEPTION_ERROR_CODE.getCode(), errorMessage,
                parameters);
    }
    
    /**
     * <默认构造函数>
     */
    public WorkflowAccessException(String errorMessage, Throwable cause,
            String... parameters) {
        super(ErrorCode.WORKFLOW_EXCEPTION_ERROR_CODE.getCode(), errorMessage,
                cause, parameters);
    }
    
}
