/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-30
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.tx.component.rule.model.Rule;

/**
 * 加载规则事件<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LoadRuleEvent extends ApplicationEvent {
    
    /** 注释内容 */
    private static final long serialVersionUID = -974486400924338446L;
    
    private List<Rule> ruleList;
    
    /** <默认构造函数> */
    public LoadRuleEvent(Object source, List<Rule> rules) {
        super(source);
    }

    /**
     * @return 返回 ruleList
     */
    public List<Rule> getRuleList() {
        return ruleList;
    }

    /**
     * @param 对ruleList进行赋值
     */
    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }
    
}
