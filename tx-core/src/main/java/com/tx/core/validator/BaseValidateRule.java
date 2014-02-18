/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.core.validator;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 基础验证规则<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseValidateRule implements ValidateRule {
    
    private ValidateRule nextValidateRule;
    
    /**
     * @param nextRule
     * @return
     */
    @Override
    public void wrap(ValidateRule nextValidateRule) {
        AssertUtils.notNull(nextValidateRule, "nextValidateRule is null.");
        if (this.nextValidateRule != null) {
            this.nextValidateRule.wrap(nextValidateRule);
        } else {
            this.nextValidateRule = nextValidateRule;
        }
    }
    
    /**
     * @param message
     * @param placeHolder
     * @return
     */
    @Override
    public ValidateResult validate(Object message, String placeHolder) {
        ValidateResult res = doValidate(message, placeHolder);
        if (res.isVerify() && this.nextValidateRule != null) {
            res = this.nextValidateRule.validate(message, placeHolder);
        }
        return res;
    }
    
    /**
      * 对信息进行验证<br/>
      *<功能详细描述>
      * @param message
      * @param placeHolder
      * @return [参数说明]
      * 
      * @return ValidateResult [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract ValidateResult doValidate(Object message,
            String placeHolder);
    
}
