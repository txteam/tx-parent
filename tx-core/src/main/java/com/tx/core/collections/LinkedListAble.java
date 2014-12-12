/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年11月20日
 * <修改描述:>
 */
package com.tx.core.collections;

import java.io.Serializable;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年11月20日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface LinkedListAble<T,PK extends Serializable> {
    
    public T getPreObjectId();
    
    public T getNextObjectId();
}
