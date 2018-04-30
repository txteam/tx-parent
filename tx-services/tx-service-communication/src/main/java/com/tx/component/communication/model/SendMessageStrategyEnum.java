/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月15日
 * <修改描述:>
 */
package com.tx.component.communication.model;


 /**
  * 消息发送策略枚举<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年2月15日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum SendMessageStrategyEnum {
    /**
     * 普通策略：
     *    1、错误：不进行错误后重发.
     *    2、定时：不支持定时发送.
     */
    NORMAL,
    /**
     * 自动重发策略
     *    1、错误：进行重发（小于最大重发次数时）
     */
    AUTO_RETRY;
}
