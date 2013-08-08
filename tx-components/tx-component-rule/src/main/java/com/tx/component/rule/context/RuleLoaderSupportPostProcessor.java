/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-1-31
 * <修改描述:>
 */
package com.tx.component.rule.context;

import org.springframework.stereotype.Component;

import com.tx.core.spring.processor.BeansInitializedEventSupportPostProcessor;

/**
 * 用以支持规则加载器，自扩展<br/>
 *     支持插拔式的规则加载器的加入<br/>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-1-31]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("ruleLoaderSupportPostProcessor")
public class RuleLoaderSupportPostProcessor extends
        BeansInitializedEventSupportPostProcessor<RuleLoader> {
    
    /**
     * @return
     */
    @Override
    public Class<RuleLoader> beanType() {
        return RuleLoader.class;
    }
}
