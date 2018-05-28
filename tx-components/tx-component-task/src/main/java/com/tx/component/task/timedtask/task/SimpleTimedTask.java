/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年10月15日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.task;

import java.util.Date;

import com.tx.component.task.timedtask.AbstractTimedTask;

/**
 * 事务接口<br/>
 *     该接口的实现为一个固定的夜间事务<br/>
 *     在该接口实现中，执行时能够自动核对当前执行时间，与容器中存放的下一次执行时间是否一致<br/>
 *     如果一致，将对应事务记录，更行为执行中<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SimpleTimedTask extends AbstractTimedTask {
    
    /**
      * 事务执行句柄<br/>
      *     传入当前执行时间
      *     返回下次最早可执行时间<br/>
      * <功能详细描述>
      * @param executeDate [执行时间]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract Date executeAdapter(Object... args);
}
