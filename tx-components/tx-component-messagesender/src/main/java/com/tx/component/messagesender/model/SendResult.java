/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月18日
 * <修改描述:>
 */
package com.tx.component.messagesender.model;

import java.util.HashMap;
import java.util.Map;


 /**
  * 发送结果<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年12月18日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SendResult {
    
    /** 发送是否成功 */
    private boolean success;
    
    /** 错误编码 */
    private String errorCode;
    
    /** 错误消息 */
    private String errorMessage;
    
    /** 其他消息 */
    private Map<String, String> data = new HashMap<>();

    /**
     * @return 返回 success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param 对success进行赋值
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return 返回 errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param 对errorCode进行赋值
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return 返回 errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param 对errorMessage进行赋值
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return 返回 data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * @param 对data进行赋值
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
