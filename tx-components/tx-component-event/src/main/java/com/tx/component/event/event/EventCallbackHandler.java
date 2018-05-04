/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月19日
 * <修改描述:>
 */
package com.tx.component.event.event;

import java.util.Map;

/**
 * 事件回调接口实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface EventCallbackHandler {
    
    /**
      * 事件回调接口实现<br/>
      *<功能详细描述>
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void handle(Map<String, Object> params);
}
