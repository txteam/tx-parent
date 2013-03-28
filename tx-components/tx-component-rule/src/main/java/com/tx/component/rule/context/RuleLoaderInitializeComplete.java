/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-30
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.springframework.context.ApplicationEvent;

/**
 * 规则加载器初始化完成事件<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleLoaderInitializeComplete extends ApplicationEvent {
    
    /** 注释内容 */
    private static final long serialVersionUID = -974486400924338446L;
    
    private RuleLoader ruleLoader;
    
    private String ruleLoaderName;
    
    /** <默认构造函数> */
    public RuleLoaderInitializeComplete(Object source, RuleLoader ruleLoader,String ruleLoaderName) {
        super(source);
        this.ruleLoader = ruleLoader;
        this.ruleLoaderName = ruleLoaderName;
    }

    /**
     * @return 返回 ruleLoader
     */
    public RuleLoader getRuleLoader() {
        return ruleLoader;
    }

    /**
     * @param 对ruleLoader进行赋值
     */
    public void setRuleLoader(RuleLoader ruleLoader) {
        this.ruleLoader = ruleLoader;
    }

    /**
     * @return 返回 ruleLoaderName
     */
    public String getRuleLoaderName() {
        return ruleLoaderName;
    }

    /**
     * @param 对ruleLoaderName进行赋值
     */
    public void setRuleLoaderName(String ruleLoaderName) {
        this.ruleLoaderName = ruleLoaderName;
    }
    
}
