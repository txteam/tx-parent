/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年12月7日
 * <修改描述:>
 */
package com.tx.component.command.context;

/**
 * 注入感知器支撑类<br/>
 * <功能详细描述>
 * 
 * @author  PengQY
 * @version  [版本号, 2016年12月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface InjectHandler {
    
    /**
      * 是否支撑当前请求<br/>
      * <功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean supports(Class<?> requestType);
    
    /**
      * 对对象进行注入<br/>
      * <功能详细描述>
      * @param request [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void inject(CommandRequest request);
}
