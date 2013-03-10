/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.tx.core.util.XstreamUtils;


 /**
  * rule配置对应rules....xml
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("rules_config")
public class RulesConfig {
    
    /** 规则项目 对应具体的节点ruleItemConfig */
    @XStreamImplicit(itemFieldName="rule_config")
    private List<RuleItemConfig> ruleItemConfig = new ArrayList<RuleItemConfig>();

    /** 
     * 规则类型 可以为drools规则，可以为其他规则的实现<br/>
     * 可以为ognl<br/>
     * 可以为
     */
    @XStreamAlias("rule_type")
    private String ruleType="";
    
    /**
     * 规则描述，
     * 可以为ongl表达式
     * 可以为groovy代码<需要再进行添加，暂不支持>
     * 可以为drools规则名
     */
    private String ruleExpression = "";
    
    /**
     * @return 返回 ruleItemConfig
     */
    public List<RuleItemConfig> getRuleItemConfig() {
        return ruleItemConfig;
    }

    /**
     * @param 对ruleItemConfig进行赋值
     */
    public void setRuleItemConfig(List<RuleItemConfig> ruleItemConfig) {
        this.ruleItemConfig = ruleItemConfig;
    }
    
    public static void main(String[] args) {
        RulesConfig r = new RulesConfig();
        r.getRuleItemConfig().add(new RuleItemConfig());
        RuleItemConfig rr = r.getRuleItemConfig().get(0);
        rr.setGloabalContext("testGloabalContext");
        rr.setRule("testRule");
        rr.getRuleElements().add(new RuleElementConfig());
        RuleElementConfig rec = rr.getRuleElements().get(0);
        rec.setRuleExpression("testRuleExpression");
        rec.setRuleType("testRuleType");
        
        XStream x = XstreamUtils.getXstream(RulesConfig.class);
        System.out.println(x.toXML(r));
    }
}
