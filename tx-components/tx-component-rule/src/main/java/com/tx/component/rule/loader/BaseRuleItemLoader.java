/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-18
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 基础规则项目加载器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class BaseRuleItemLoader implements RuleItemLoader {
    
    protected static Logger logger = LoggerFactory.getLogger(RuleItemLoader.class);
    
    protected ApplicationContext applicationContext;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
