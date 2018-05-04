/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月23日
 * <修改描述:>
 */
package com.tx.component.task.context;

import org.springframework.beans.factory.FactoryBean;

/**
 * 事务容器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskContextFactory extends TaskContext implements
        FactoryBean<TaskContext> {
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public TaskContext getObject() throws Exception {
        if (TaskContextFactory.context == null) {
            return this;
        } else {
            return TaskContextFactory.context;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return TaskContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
