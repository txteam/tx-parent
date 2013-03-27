/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-1-24
 * <修改描述:>
 */
package com.tx.component.rule.transation;


/**
 * 规则会话容器<br/>
 * 
 * @author  brady
 * @version  [版本号, 2013-1-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RuleSessionTransaction {
    
    /**
      * 打开方法规则会话容器<br/>
      *     1、初始化容器<br/>
      *     
      * @param globals [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void open(){
        if(!RuleSessionContext.isOpen()){
            RuleSessionContext.open();
        }
    }
    
    /**
      * 关闭方法规则会话容器
      *     1、移除方法线程变量 [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void close(){
        RuleSessionContext.close();
    }
}
