/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月31日
 * <修改描述:>
 */
package com.tx.component.communication.context;



 /**
  * 消息发送构建器<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2015年12月31日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class MessageSenderContextBuilder extends MessageSenderContextConfigurator {
    
    /**
     * @throws Exception
     */
    @Override
    protected final void initMessageSenderContext() throws Exception {
        //调用初始化容器
        initContext();
    }
    
    /**
      * 提供给子类初始化容器时使用<br/>
      * <功能详细描述>
      * @throws Exception [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void initContext() throws Exception {
        logger.info("消息路由服务 配置容器启动...");
        
        logger.info("消息路由服务 配置容器启动完毕...");
    }
}
