/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月5日
 * <修改描述:>
 */
package com.tx.component.task.timedtask;

/**
 * 定时任务接口实现<br/>
 * <功能详细描述>
 * 
 * @author  Tim.PengQY
 * @version  [版本号, 2017年10月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TimedTask {
    
    /**
     * 事务唯一key<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String getCode();
    
    /**
     * 事务唯一key<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String getParentCode();
    
    /**
     * 事务名称<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getName();
    
    /**
     * 事务的描述信息<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getRemark();
    
    /**
     * 任务排序值<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int getOrder();
}
