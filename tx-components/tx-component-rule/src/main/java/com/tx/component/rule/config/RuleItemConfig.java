/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-19
 * <修改描述:>
 */
package com.tx.component.rule.config;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


 /**
  * 规则项配置
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-19]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("rule_config")
public class RuleItemConfig {
    
    /** 规则名 */
    @XStreamAsAttribute()
    private String rule;
    
    /** 
     * 规则执行容器，ruleSessionContext接口的springBean名，<br/>
     * 用以一些通用化的规则压入<br/>
     *      比如：压入权限，压入贯穿于系统的一些配置
     *              压入自定义的一些容器，或值
     * 暂不实现全类路径的功能
     * 以后再进行扩展
     * 可以为空，具体的规则容器，如果在容器运行时自行制定了，该设置将会失效
     */
    @XStreamAsAttribute()
    private String gloabalContext;
    
    /** rule规则项 */
    @XStreamImplicit(itemFieldName="rule_element")
    private List<RuleElementConfig> ruleElements = new ArrayList<RuleElementConfig>();

    /**
     * @return 返回 rule
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param 对rule进行赋值
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    

    /**
     * @return 返回 gloabalContextBean
     */
    public String getGloabalContext() {
        return gloabalContext;
    }

    /**
     * @param 对gloabalContextBean进行赋值
     */
    public void setGloabalContext(String gloabalContext) {
        this.gloabalContext = gloabalContext;
    }

    /**
     * @return 返回 ruleElements
     */
    public List<RuleElementConfig> getRuleElements() {
        return ruleElements;
    }

    /**
     * @param 对ruleElements进行赋值
     */
    public void setRuleElements(List<RuleElementConfig> ruleElements) {
        this.ruleElements = ruleElements;
    }
}
