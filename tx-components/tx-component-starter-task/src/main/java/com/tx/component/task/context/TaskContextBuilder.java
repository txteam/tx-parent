/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月24日
 * <修改描述:>
 */
package com.tx.component.task.context;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 事务容器构建器<br/>
 * 
 * @author PengQY
 * @version [版本号, 2014年5月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TaskContextBuilder extends TaskContextConfiguration implements BeanNameAware {
    
    /** beanName */
    protected static String beanName;
    
    /** 当前容器的签名 */
    protected String signature;
    
    /**
     * @param name
     */
    @Override
    public void setBeanName(String beanName) {
        TaskContextBuilder.beanName = beanName;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void doBuild() throws Exception {
        
    }
}