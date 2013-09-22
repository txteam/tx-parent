/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;


 /**
  * 日志实例装饰器<br/>
  *     用于记录日志期间，将一些环境变量，线程变量中数据<br/>
  *     写入日志实例中<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-22]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ServiceLogDecorate {
    
    public Object decorate(Object srcObj);
}
