/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.List;

import org.springframework.core.Ordered;

import com.tx.component.rule.model.Rule;


/**
 * 规则加载器<br/>
 *     
 *     
 * 
 * @author  brady
 * @version  [版本号, 2013-1-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RuleLoader extends Ordered{
    
    public List<Rule> load();
}
