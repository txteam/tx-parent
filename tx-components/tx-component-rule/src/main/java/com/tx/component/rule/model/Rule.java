/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.model;

import java.io.Serializable;


 /**
  * 规则实体<br/>
  *     1、用以定义规则<br/>
  *     2、规则具有一个名字，在系统中唯一,如果规则同名根据Loader优先级别进行覆盖<br/>
  *     3、规则具有一个类型，以便根据规则类型，得到对应的规则会话<br/>
  *     4、规则执行方法，规则会话表达式<br/>
  *             对应到drools可能是，规则名,drl文件等<br/>,
  *             对应到groove规则，可能为，groove表达式<br/>
  *             对应到collection, 可能为，一组规则名<br/>
  * 
  * @author  brady
  * @version  [版本号, 2013-1-24]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface Rule extends Serializable{

    /**
      * 获取规则名
      * 
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String rule();

    /**
      * 获取规则类型
      * 
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public RuleType getRuleType();
    
    /**
      * 获取规则的业务类型<br/>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getServiceType();
}
