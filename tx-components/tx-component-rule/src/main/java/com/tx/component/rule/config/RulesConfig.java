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
import com.tx.component.rule.model.RuleTypeEnum;
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
    @XStreamImplicit(itemFieldName="rule")
    private List<RuleItemConfig> ruleItemConfig = new ArrayList<RuleItemConfig>();
    
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
        
        rr.setRule("test1");
        rr.setRuleExpression("/xxx");
        rr.setRuleType(RuleTypeEnum.DROOLS_DRL_BYTE);
        rr.setServiceType("test");
        
        XStream x = XstreamUtils.getXstream(RulesConfig.class);
        System.out.println(x.toXML(r));
    }
}
