/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import java.util.List;

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
public interface RuleLoader{
    
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
    public List<Rule> load();
}
