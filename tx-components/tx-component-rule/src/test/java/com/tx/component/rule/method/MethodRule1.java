/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.tx.component.rule.loader.java.annotation.RuleClassMapping;
import com.tx.component.rule.loader.java.annotation.RuleMethodMapping;
import com.tx.component.rule.loader.java.annotation.RuleRequestParam;
import com.tx.component.rule.loader.java.annotation.RuleResultBody;
import com.tx.component.rule.method.model.TestPojo;
import com.tx.component.rule.session.ValueWrapper;
import com.tx.component.rule.session.ValueWrapperUtils;
import com.tx.core.TxConstants;

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
@RuleClassMapping
public class MethodRule1 {
    
    /**
     * <默认构造函数>
     */
    public MethodRule1() {
        System.out.println("调用 MethodRule1 默认构造函数...");
    }
    
    //返回值的使用
    @RuleMethodMapping(value = "method.rule1", serviceType = "test")
    public StringBuffer rule1ReturnString(@RuleRequestParam Map<String, ?> facts) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println("facts.size:" + facts == null ? 0 : facts.size());
        return new StringBuffer("rule1ReturnString");
    }
    
    @RuleMethodMapping(value = "method.rule2", serviceType = "test")
    public void rule2ReturnVoid(@RuleRequestParam Map<String, ?> facts,
            ValueWrapper<StringBuffer> result) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println("facts.size:" + facts == null ? 0 : facts.size());
        
        StringBuffer resultRef = ValueWrapperUtils.getValue(result,
                new StringBuffer(TxConstants.INITIAL_CONLLECTION_SIZE));
        if (resultRef != null) {
            resultRef.append(" rule2ReturnVoid ");
        }
    }
    
    @RuleResultBody
    @RuleMethodMapping(value = "method.rule3", serviceType = "test")
    public TestPojo rule3ReturnTestPojo(TestPojo testPojo,
            @RuleRequestParam("test") String test) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println(testPojo + " : " + test);
        
        TestPojo resTestPojo = new TestPojo();
        resTestPojo.setTest("rule3ReturnTestPojo");
        return resTestPojo;
    }
    
    @RuleMethodMapping(value = "method.rule4", serviceType = "test")
    public void rule4ReturnVoid(TestPojo testPojo,
            @RuleRequestParam("test") String test,
            ValueWrapper<StringBuffer> result) {
        System.out.println("method.rule1ReturnString: MethodRule1.rule1ReturnString.");
        System.out.println(testPojo + " : " + test);
        
        StringBuffer resultRef = ValueWrapperUtils.getValue(result,
                new StringBuffer(TxConstants.INITIAL_CONLLECTION_SIZE));
        if (resultRef != null) {
            resultRef.append(" rule2ReturnVoid ");
        }
    }
    
}
