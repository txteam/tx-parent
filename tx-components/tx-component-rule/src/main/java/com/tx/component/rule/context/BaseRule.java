/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.rule.loader.RuleItem;
import com.tx.component.rule.loader.RuleTypeEnum;


 /**
  * 规则实例的基础公共实现<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月16日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class BaseRule implements Rule{
    
    /** 注释内容 */
    private static final long serialVersionUID = 945186919801586360L;
    
    /** 日志句柄 */
    protected Logger logger = LoggerFactory.getLogger(Rule.class);
    
    /** 规则项 */
    protected RuleItem ruleItem;
    
    /** <默认构造函数> */
    public BaseRule(RuleItem ruleItem) {
        super();
        this.ruleItem = ruleItem;
    }
    
    /**
     * @return
     */
    @Override
    public String getKey() {
        return this.ruleItem.getKey();
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return this.ruleItem.getName();
    }

    /**
     * @return
     */
    @Override
    public RuleTypeEnum getRuleType() {
        return this.ruleItem.getRuleType();
    }

    /**
     * @return
     */
    @Override
    public String getServiceType() {
        return this.ruleItem.getServiceType();
    }

    /**
     * @return
     */
    @Override
    public String getRemark() {
        return this.ruleItem.getRemark();
    }

    /**
     * @return
     */
    @Override
    public boolean isModifyAble() {
        return this.ruleItem.isModifyAble();
    }
}
