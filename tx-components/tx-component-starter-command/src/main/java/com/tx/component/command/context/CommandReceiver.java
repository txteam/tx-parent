package com.tx.component.command.context;

/**
  * 请求对应的处理器<br/>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年1月5日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface CommandReceiver<PR extends CommandRequest> {
    
    /**
     * 获取支持的操作请求类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Class<?> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<? extends CommandRequest> getRequestType();
    
    /**
      * 是否支撑处理<br/>
      * <功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean supports(CommandRequest request);
    
    /**
     * 前置处理<br/>
     *     可做有一些比如校验请求合法性的功能<br/>
     * <功能详细描述>
     * @param request
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public boolean preHandle(PR request, CommandResponse response);
    
    /**
     * 后置处理<br/>
     *     在该逻辑中触发相关事件<br/>
     * <功能详细描述>
     * @param request
     * @param e [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public void afterCompletion(PR request, CommandResponse response,
            Throwable e);
}
