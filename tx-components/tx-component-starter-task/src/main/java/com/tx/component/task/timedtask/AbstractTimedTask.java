/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年7月21日
 * <修改描述:>
 */
package com.tx.component.task.timedtask;

/**
 * 抽象任务<br/>
 * 
 * @author  Tim.PengQY
 * @version  [版本号, 2017年10月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractTimedTask implements TimedTask {
    
    /**
     * @return
     */
    @Override
    public String getParentCode() {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return getCode();
    }
    
    /**
     * @return
     */
    @Override
    public String getRemark() {
        String className = getClass().getName();
        return className;
    }
}
