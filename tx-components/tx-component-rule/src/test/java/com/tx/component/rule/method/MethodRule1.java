/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.tx.component.rule.method.annotation.RuleMethod;
import com.tx.component.rule.method.annotation.RuleMethodClass;
import com.tx.component.rule.method.annotation.RuleMethodParam;
import com.tx.component.rule.method.annotation.RuleMethodResult;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-2-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("MethodRule1")
@RuleMethodClass
public class MethodRule1 {
    
    /**
     * <默认构造函数>
     */
    public MethodRule1(){
        System.out.println("调用 MethodRule1 默认构造函数...");
    }
    
    //返回值的使用
    @RuleMethod(rule = "method.rule1", serviceType = "test")
    public StringBuffer rule1ReturnString(@RuleMethodParam Map<String, ?> facts) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println("facts.size:" + facts == null ? 0 : facts.size());
        return new StringBuffer("rule1ReturnString");
    }
    
    @RuleMethod(rule = "method.rule2", serviceType = "test")
    public void rule2ReturnVoid(@RuleMethodParam Map<String, ?> facts,
            @RuleMethodResult StringBuffer result) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println("facts.size:" + facts == null ? 0 : facts.size());
        
        result.append("rule2ReturnVoid");
    }
    
    @RuleMethod(rule = "method.rule3", serviceType = "test")
    public TestPojo rule3ReturnTestPojo(TestPojo testPojo,
            @RuleMethodParam("test") String test) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println(testPojo + " : " + test);
        
        TestPojo resTestPojo = new TestPojo();
        resTestPojo.setTest("rule3ReturnTestPojo");
        return resTestPojo;
    }
    
    @RuleMethod(rule = "method.rule4", serviceType = "test")
    public void rule4ReturnVoid(TestPojo testPojo,
            @RuleMethodParam("test") String test,
            @RuleMethodResult TestPojo resTestPojo) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println(testPojo + " : " + test);
        
        resTestPojo.setTest("rule4ReturnVoid");
    }
    
}
