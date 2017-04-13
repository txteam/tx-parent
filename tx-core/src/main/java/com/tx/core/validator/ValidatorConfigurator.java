/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-20
 * <修改描述:>
 */
package com.tx.core.validator;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ClassScanUtils;
import com.tx.core.util.ObjectUtils;

/**
 * 验证器配置<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidatorConfigurator implements InitializingBean {
    
    /** 验证规则所在包 */
    private String validateRulePackages;
    
    /** 验证规则映射 */
    private Map<String, ValidateRule> validateRuleMap = new HashMap<String, ValidateRule>();
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        //根据配置扫描包得到ValidateRule的实现类
        String[] packages = StringUtils.split(this.validateRulePackages, ";");
        Set<Class<? extends ValidateRule>> validateRuleClassSet = new HashSet<Class<? extends ValidateRule>>();
        for (String packageTemp : packages) {
            if (StringUtils.isBlank(packageTemp)) {
                continue;
            }
            packageTemp = packageTemp.trim();
            validateRuleClassSet.addAll(ClassScanUtils.scanByParentClass(ValidateRule.class,
                    packageTemp));
        }
        
        //将ValidateRule实现类实例化放入validateRuleMap中key为validateExpressionTemp.trim().toUpperCase();
        for (Class<? extends ValidateRule> validateRuleClass : validateRuleClassSet) {
            int modifier = validateRuleClass.getModifiers();
            if (Modifier.isInterface(modifier) || Modifier.isAbstract(modifier)) {
                continue;
            }
            
            ValidateRule validateRuleTemp = ObjectUtils.newInstance(ValidateRule.class);
            for (String validateExpressionTemp : validateRuleTemp.validateExpression()) {
                validateRuleMap.put(validateExpressionTemp.trim().toUpperCase(),
                        validateRuleTemp);
            }
        }
    }
    
    /**
     * 解析验证表达式<br/>
     *<功能详细描述>
     * @param validateExpression
     * @return [参数说明]
     * 
     * @return ValidateRule [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public ValidateRule parseValidateExpression(String validateExpression) {
        AssertUtils.notEmpty(validateExpression, "validateExpression is empty.");
        String[] validateExpressions = StringUtils.split(validateExpression,
                ";");
        
        ValidateRule validateRuleHandler = null;
        for (String validateExpressionTemp : validateExpressions) {
            if (StringUtils.isBlank(validateExpressionTemp)) {
                continue;
            }
            
            String veKeyTemp = validateExpressionTemp;
            if (veKeyTemp.indexOf("(") != -1) {
                veKeyTemp = veKeyTemp.substring(0, veKeyTemp.indexOf("("));
            }
            if (veKeyTemp.indexOf("[") != -1) {
                veKeyTemp = veKeyTemp.substring(0, veKeyTemp.indexOf("["));
            }
            veKeyTemp = veKeyTemp.toUpperCase();
            
            if (!validateRuleMap.containsKey(veKeyTemp)) {
                continue;
            }
            
            if (validateRuleHandler == null) {
                validateRuleHandler = validateRuleMap.get(veKeyTemp)
                        .newInstance(validateExpression);
            } else {
                validateRuleHandler.wrap(validateRuleMap.get(veKeyTemp)
                        .newInstance(validateExpression));
            }
        }
        
        return validateRuleHandler;
    }
}
