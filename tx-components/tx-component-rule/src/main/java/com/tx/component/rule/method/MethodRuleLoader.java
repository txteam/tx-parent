/*
 * 描          述:  <描述>
 * 修  改   人:  pengqingyang
 * 修改时间:  2013-1-27
 * <修改描述:>
 */
package com.tx.component.rule.method;

import java.util.List;

import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.model.Rule;


 /**
  * 方法类型的规则加载器<br/>
  * 
  * @author  pengqingyang
  * @version  [版本号, 2013-1-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MethodRuleLoader implements RuleLoader{
    
    /** 规则存在的包 */
    private String rulePackage;

    /**
     * @param rule
     * @param isCoverWhenSame
     */
    @Override
    public void load(Rule rule, boolean isCoverWhenSame) {
        // TODO Auto-generated method stub
        
    }
    
//    /**
//     * @return
//     */
//    @Override
//    public List<Rule> load() {
//        return null;
//    }
    
    
}
