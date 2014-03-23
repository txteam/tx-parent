/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.loader;

import java.util.List;

import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * 规则加载器<br/>
 *     用以加载单个规则<br/>
 *     实现了该接口的springBean对应方法被调用时，对应规则将写入规则容器中<br/>
 *     根据order值进行校验<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleItemLoader extends Ordered, ApplicationContextAware {
    
    /**
     * 加载规则项<br/>
     *     提供该方法用以加载所有的规则<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<Rule> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public List<RuleItem> load();
}
