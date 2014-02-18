/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.core.validator.rule;

import java.util.HashMap;
import java.util.Map;

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
    
    private Map<String, RequiredValidateRule> instanceMap = new HashMap<String, RequiredValidateRule>();
    
    /**
     * @return
     */
    @Override
    public String[] validateExpression() {
        return new String[] {"required,notEmpty,notNull"};
    }
    
    /**
     * required        //不能为空
     * required(xxx)   //满足xxx规则，才验证required
     * required(not, xxx) //如果值为空，或者xxx也认为是空
     * @param expression
     * @return
     */
    @Override
    public ValidateRule newInstance(String expression) {
        return null;
    }
    
    /**
     * @param message
     * @param placeHolder
     * @return
     */
    @Override
    protected ValidateResult doValidate(Object message, String placeHolder) {
        // TODO Auto-generated method stub
        return null;
    }
}
