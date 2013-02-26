/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.definition.KnowledgePackage;

import com.tx.component.rule.model.Rule;
import com.tx.component.rule.model.RuleType;


 /**
  * drools规则<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class DroolsRule implements Rule {
    
    /** 注释内容 */
    private static final long serialVersionUID = -6764646531144979549L;

    /** 规则名 */
    private String rule;
    
    /** 规则的业务类型属性 */
    private String serviceType;
    
    /** 规则基础规则包 */
    private Collection<KnowledgePackage> basePackages;
    
    //private
    private KnowledgeBase knowledgeBase;
    
    /**
     * @return
     */
    @Override
    public String rule() {
        return this.rule;
    }
    
    /**
     * @return
     */
    @Override
    public RuleType getRuleType() {
        return RuleType.DROOLS;
    }
    
    /**
     * @return
     */
    @Override
    public String getServiceType() {
        return null;
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
     * @return 返回 basePackages
     */
    public Collection<KnowledgePackage> getBasePackages() {
        return basePackages;
    }

    /**
     * @param 对basePackages进行赋值
     */
    public void setBasePackages(Collection<KnowledgePackage> basePackages) {
        this.basePackages = basePackages;
    }

    /**
     * @return 返回 knowledgeBase
     */
    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    /**
     * @param 对knowledgeBase进行赋值
     */
    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    /**
     * @param 对serviceType进行赋值
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
