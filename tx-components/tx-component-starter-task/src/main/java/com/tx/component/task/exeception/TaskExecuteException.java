/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月19日
 * <修改描述:>
 */
package com.tx.component.task.exeception;

import com.tx.core.exceptions.SILException;

/**
 * 任务容器执行异常<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskExecuteException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2179503189376269644L;
    
    /**
     * @return
     */
    @Override
    protected Integer errorCode() {
        return 281000;
    }
    
    /**
     * @return
     */
    @Override
    protected String errorMessage() {
        return "任务调度容器初始化异常.";
    }
    
    /** <默认构造函数> */
    public TaskExecuteException() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /** <默认构造函数> */
    public TaskExecuteException(String message) {
        super(message);
    }
    
}
