/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tx.component.rule.loader.java.annotation.RuleClassMapping;
import com.tx.component.rule.loader.java.annotation.RuleMethodMapping;
import com.tx.component.rule.loader.java.annotation.RuleRequestParam;
import com.tx.component.rule.loader.java.annotation.RuleResultParam;
import com.tx.component.rule.method.model.ProcessRule;
import com.tx.component.rule.method.model.TestPojo;
import com.tx.component.rule.method.model.TestPojoDao;
import com.tx.component.rule.transation.RuleSessionContext;

/**
 * 申请单审核规则
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-2-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("LoanBillProcessRules")
@RuleClassMapping
public class LoanBillProcessRules {
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param testPojo
      * @param facts [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RuleMethodMapping(value = "minAgeRule", serviceType = "processLoanBill", name = "年龄不能小于18岁")
    public void minAgeRule(TestPojo testPojo, TestPojoDao testPojoDao,
            @RuleRequestParam Map<String, ?> facts,
            @RuleResultParam ArrayList<ProcessRule> violateProcessRuleList) {
        Object globalValue1 = RuleSessionContext.getContext()
                .getGlobals()
                .get("globalKey1");
        System.out.println(globalValue1);
        System.out.println(testPojo != null ? testPojo.getTest() : "null");
        System.out.println(testPojoDao != null ? testPojoDao.call() : "null");
        //如果违背某规则，则将对应规则的实例放入
        violateProcessRuleList.add(new ProcessRule("年龄不能小于18岁"));
    }
    
    /**
      * 年龄不能大于60岁
      *<功能详细描述>
      * @param testPojo
      * @param facts
      * @param violateProcessRuleList [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RuleMethodMapping(value = "maxAgeRule", serviceType = "processLoanBill", name = "年龄不能大于60岁")
    public void maxAgeRule(TestPojo testPojo,
            @RuleRequestParam Map<String, ?> facts,
            @RuleResultParam ArrayList<ProcessRule> violateProcessRuleList) {
        //如果没有违背，则不压入，当然也可以把豁免的逻辑也放入规则中，
        //不同的产品可能存在，同一规则，在某产品中仅是提示，某产品中则直接拒绝的情况
        //当前获得的豁免可以一并放入传入的事实中
        System.out.println("maxAgeRule");
        
        //如果违背某规则，则将对应规则的实例放入
        //violateProcessRuleList.add(new ProcessRule("年龄不能大于60岁"));
    }
}
