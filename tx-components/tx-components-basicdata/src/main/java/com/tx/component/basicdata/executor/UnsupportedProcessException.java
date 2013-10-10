/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import com.tx.core.exceptions.SILException;


 /**
  * 不支持的操作异常<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class UnsupportedProcessException extends SILException {
    
    /** 注释内容 */
    private static final long serialVersionUID = 7915292404791869273L;

    private String process;
    
    /**
     * @return
     */
    @Override
    protected String doGetErrorCode() {
        return "UNSUPPORTED_PROCESS_ERROR";
    }

    /**
     * @return
     */
    @Override
    protected String doGetErrorMessage() {
        return "不支持的操作";
    }

    /** <默认构造函数> */
    public UnsupportedProcessException(String process) {
        super("不支持的操作：{}",new Object[]{process});
        this.process = process;
    }

    /**
     * @return 返回 process
     */
    protected String getProcess() {
        return process;
    }

    /**
     * @param 对process进行赋值
     */
    protected void setProcess(String process) {
        this.process = process;
    }
}
