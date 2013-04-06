/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-5
 * <修改描述:>
 */
package com.tx.core.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;


 /**
  * 类类型初始化事件
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-4-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BeansInitializedEvent<T> extends ApplicationContextEvent{

    /** 注释内容 */
    private static final long serialVersionUID = -1881411273980396406L;
    
    private Class<T> type;
    
    public BeansInitializedEvent(ApplicationContext source,Class<T> type) {
        super(source);
        this.type = type;
    }

    /**
     * @return 返回 type
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(Class<T> type) {
        this.type = type;
    }
}
