/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月16日
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import java.io.Serializable;

import javax.persistence.Id;

/**
 * 规则的属性值
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年3月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseRuleItemParam implements Serializable,
        Comparable<BaseRuleItemParam> {
    
    /** 注释内容 */
    private static final long serialVersionUID = -9023550864701139335L;
    
    /** 对应规则id */
    @Id
    private String ruleKey;
    
    /** 对应属性key */
    private String paramKey;
    
    /** 参数排序值 */
    private int paramOrder;
    
    /**
     * @return 返回 ruleKey
     */
    public String getRuleKey() {
        return ruleKey;
    }

    /**
     * @param 对ruleKey进行赋值
     */
    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    /**
     * @return 返回 paramKey
     */
    public String getParamKey() {
        return paramKey;
    }
    
    /**
     * @param 对paramKey进行赋值
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }
    
    /**
     * @return 返回 paramOrder
     */
    public int getParamOrder() {
        return paramOrder;
    }

    /**
     * @param 对paramOrder进行赋值
     */
    public void setParamOrder(int paramOrder) {
        this.paramOrder = paramOrder;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public int compareTo(BaseRuleItemParam o) {
        if (o == null) {
            return 1;
        } else if (this.paramOrder == o.getParamOrder()) {
            return 0;
        } else if (this.paramOrder > o.getParamOrder()) {
            return 1;
        } else {
            return -1;
        }
    }
}
