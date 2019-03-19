/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月24日
 * <修改描述:>
 */
package com.tx.component.task.timedtask.task;

import java.util.Date;
import java.util.List;

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
public abstract class IterableTimedTask<T> extends AbstractTimedTask {
    
    /**
     * 如果为迭代任务，可能会出现内部系统错误造成无限循环，利用该值计数大道该值后抛出异常<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int getMaxBatchCount() {
        return 1000;
    }
    
    /**
     * 获取单个业务元处理的数目<br/>
     *     默认单个任务元处理10个
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public int getBatchSize() {
        return 100;
    }
    
    /**
     * 是否存在下一批任务
     *<功能详细描述>
     * @param executeDate
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract boolean hasNextAdapter(Object... args);
    
    /**
     * 下一批数据<br/> 
     * <功能详细描述>
     * @param executeDate
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract List<T> nextAdapter(int batchSize, Object... args);
    
    /**
     * 获取下次可执行时间<br/>
     * <功能详细描述>
     * @param currentDate 当前事务执行的对应的时间，非当前时间
     * @return [参数说明]
     * 
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract Date getNextDateAdapter(Object... args);
    
    /**
      * 事务执行句柄<br/>
      *     单个数据执行，在该数据执行期间，事务独立<br/>
      * <功能详细描述>
      * @param data [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public abstract void executeAdapter(T data, Object... args);
}
