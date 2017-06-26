/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.core.validator.rule;

import com.tx.core.validator.BaseValidateRule;
import com.tx.core.validator.ValidateResult;
import com.tx.core.validator.ValidateRule;

/**
 * 非空检验规则<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RequiredValidateRule extends BaseValidateRule {
    
    @Override
    public String[] validateExpression() {
        return new String[] {"required,notEmpty,notNull"};
    }
    
    @Override
    public ValidateRule newInstance(String expression) {
        return null;
    }
    
    @Override
    protected ValidateResult doValidate(Object message, String placeHolder) {
        
        return null;
    }
}
