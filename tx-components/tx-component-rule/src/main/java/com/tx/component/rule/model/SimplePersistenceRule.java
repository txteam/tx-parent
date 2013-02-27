/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-26
 * <修改描述:>
 */
package com.tx.component.rule.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


 /**
  * 规则基础实现<br/>
  *     用以支持规则写入数据库中<br/>
  * 
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-2-26]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@Entity
@Table(name="ru_rule_def")
public class SimplePersistenceRule implements Serializable,Rule{
    
    /** 注释内容 */
    private static final long serialVersionUID = 3816894065600661189L;
    
    /** 规则在数据库中的id */
    @Id
    private String id;

    /** 规则名 */
    private String rule;
    
    /** 规则类型 */
    private RuleType ruleType;
    
    /** 业务类型 */
    private String serviceType;
    
    /** 规则状态 */
    private RuleState state;
    
    /**
     * <默认构造函数>
     */
    public SimplePersistenceRule() {
        super();
    }
    
    /**
     * <默认构造函数>
     */
    public SimplePersistenceRule(Rule rule) {
        super();
        this.rule = rule.rule();
        this.ruleType = rule.getRuleType();
        this.serviceType = rule.getServiceType();
    } 

    /**
     * @return
     */
    public RuleType getRuleType() {
        return this.ruleType;
    }
    
    /**
     * @return
     */
    public String getServiceType() {
        return this.serviceType;
    }

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
     * @param 对ruleType进行赋值
     */
    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 state
     */
    public RuleState getState() {
        return state;
    }

    /**
     * @param 对state进行赋值
     */
    public void setState(RuleState state) {
        this.state = state;
    }

    /**
     * @return
     */
    @Override
    public String rule() {
        return this.rule;
    }
    
    
}
