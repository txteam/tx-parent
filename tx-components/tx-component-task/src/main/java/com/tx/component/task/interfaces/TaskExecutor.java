/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月16日
 * <修改描述:>
 */
package com.tx.component.task.interfaces;

/**
 * 任务接口<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TaskExecutor {
    
    /** 排序值 */
    public int order();
    
    /** 任务关键字 */
    public String code();
    
    /** 父级任务编码 */
    public String parentCode();
    
    /** 任务名 */
    public String name();
    
    /** 任务备注 */
    public String remark();
    
    /**
     * 任务对应的SpringBean名<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String beanName();
    
    /**
     * 类名<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String className();
}
