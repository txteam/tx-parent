/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-23
 * <修改描述:>
 */
package com.tx.component.rule.context;

import com.tx.component.rule.support.RuleSession;



 /**
  * 规则容器<br/>
  *     系统启动时通过该规则容器加载已有的规则<br/>
  *     可以通过该容器实现规则重加载，添加新的规则<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-23]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface RuleContext {
    
    /**
      * 初始化规则容器
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void load();
    
    /**
      * <功能简述>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void reLoad();
    
    /**
      * 增加或更新规则
      * <功能详细描述>
      * @param rule [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void saveRule(RuleSession rule);
    
    /**
      * 判断容器中是否含有对应的规则<br/>
      *     1、如果存在规则则返回true,不存在返回false
      * <功能详细描述>
      * @param rule
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean contains(String rule);
    
    /**
      * 获取规则实体
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Rule [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleSession getRule(String rule);
}
