/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.service;

import java.util.List;


 /**
  * 规则容器业务层<br/>
  * 1、用以支持各种规则容器的创建，通过创建容器内部类的实例嵌入实现
  * 规则容器的自然应用，兼容，编码形式以及drools规则的自然切换
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RuleContextService {
    
    /**
     * 创建返回boolean结果的规则容器<br/>
     * 1、定义这样的容器实现drools规则和java注解形式或接口形式的借口的自然切换
     * <功能详细描述>
     * @param ruleExpression
     * @param concurrent
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public static boolean createEvaluateBooleanRuleContext(String ruleType,
           String ruleExpression, boolean concurrent) {
       
       return true;
   }
   
   public static List<?> createEvaluateListRuleContext(String ruleType,
           String ruleExpression, boolean concurrent){
       
       return null;
   }
}
