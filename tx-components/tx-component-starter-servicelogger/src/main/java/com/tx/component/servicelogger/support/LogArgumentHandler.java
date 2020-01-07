/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年1月8日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support;

import org.springframework.beans.BeanWrapper;
import org.springframework.core.Ordered;

/**
 * 日志参数处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface LogArgumentHandler extends Ordered {
    
    /**
     * 判断是否执行该handler<br/>
     * <功能详细描述>
     * @param bw
     * @param pd
     * @param td
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean support(BeanWrapper bw);
    
    /**
     * 执行参数处理逻辑
     * <功能详细描述>
     * @param bw
     * @param pd
     * @param td [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void handle(BeanWrapper bw);
    
    /**
     * 默认的优先级<br/>
     * @return
     */
    default int getOrder() {
        return 0;
    }
}
