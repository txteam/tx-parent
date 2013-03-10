/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-2-28
 * <修改描述:>
 */
package com.tx.component.rule.drools;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.tx.component.rule.context.RuleLoader;
import com.tx.component.rule.model.Rule;


 /**
  * drools规则加载器
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-2-28]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public abstract class BaseDroolsRuleLoader implements RuleLoader,ApplicationContextAware{
    
    /** spring容器句柄 */
    protected ApplicationContext applicationContext;
    
    /** ruleLoader order值 */
    private int order;
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * @return
     */
    @Override
    public int getOrder() {
        return this.order;
    }
}
