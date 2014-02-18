/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.core.validator;


/**
 * 验证结果<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidateResult {
    
    /** 是否验证通过 */
    private boolean isVerify = true;
    
    /** 验证错误信息 */
    private String errorMessage;

    /**
     * @return 返回 isVerify
     */
    public boolean isVerify() {
        return isVerify;
    }

    /**
     * @param 对isVerify进行赋值
     */
    public void setVerify(boolean isVerify) {
        this.isVerify = isVerify;
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
}
