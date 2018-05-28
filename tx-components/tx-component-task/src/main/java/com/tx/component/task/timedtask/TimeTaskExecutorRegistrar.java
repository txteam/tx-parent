/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月23日
 * <修改描述:>
 */
package com.tx.component.task.timedtask;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.transaction.PlatformTransactionManager;

import com.tx.component.task.exeception.TaskInitException;
import com.tx.component.task.timedtask.executor.BatchTimedTaskExecutor;
import com.tx.component.task.timedtask.executor.ConcurrentBatchTimedTaskExecutor;
import com.tx.component.task.timedtask.executor.IterableTimedTaskExecutor;
import com.tx.component.task.timedtask.executor.SimpleTimedTaskExecutor;
import com.tx.component.task.timedtask.task.BatchTimedTask;
import com.tx.component.task.timedtask.task.ConcurrentBatchTimedTask;
import com.tx.component.task.timedtask.task.IterableTimedTask;
import com.tx.component.task.timedtask.task.SimpleTimedTask;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 * 任务执行器注册器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TimeTaskExecutorRegistrar implements BeanPostProcessor, //ApplicationContextAware, 
        BeanFactoryAware {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory
            .getLogger(TimeTaskExecutorRegistrar.class);
    
    /** 事务管理器 */
    private PlatformTransactionManager transactionManager;
    
    /** Bean定义注册机 */
    protected BeanDefinitionRegistry beanDefinitionRegistry;
    
    /**
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
                beanFactory,
                "beanFactory is not BeanDefinitionRegistry instance.");
        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
    
    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        TimedTask timedTask = null;
        if (bean instanceof TimedTask) {
            //定时任务
            timedTask = (TimedTask) bean;
            
            //为定时任务注册任务执行器
            registeTimedTaskExecutorByTimedTask(beanName, timedTask);
        }
        return bean;
    }
    
    /**
    * 构建默认的基础数据业务类<br/>
    * <功能详细描述>
    * @param type [参数说明]
    * 
    * @return void [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("rawtypes")
    protected void registeTimedTaskExecutorByTimedTask(String taskBeanName,
            TimedTask timedTask) {
        //生成定时执行器Bean名称
        String beanName = generateTimedTaskExecutorBeanName(taskBeanName,
                timedTask);
        
        Class<? extends AbstractTimedTaskExecutor> timedTaskExecutorClass = getTimedTaskExecutorClass(
                timedTask);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .genericBeanDefinition(timedTaskExecutorClass);
        builder.addPropertyValue("taskBeanName", taskBeanName);
        builder.addPropertyValue("task", timedTask);
        builder.addPropertyValue("transactionManager", transactionManager);
        
        registerBeanDefinition(beanName, builder.getBeanDefinition());
        
        logger.info("完成任务执行器注册：task:{}", new Object[] { timedTask.getClass() });
    }
    
    /**
    * @desc 向spring容器注册BeanDefinition
    * @param beanName
    * @param beanDefinition
    */
    protected void registerBeanDefinition(String beanName,
            BeanDefinition beanDefinition) {
        //if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
        this.beanDefinitionRegistry.registerBeanDefinition(beanName,
                beanDefinition);
        //}
    }
    
    /**
     * 构建定时任务<br/>
     * <功能详细描述>
     * @param task
     * @return [参数说明]
     * 
     * @return TimedTaskExecutor [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    private Class<? extends AbstractTimedTaskExecutor> getTimedTaskExecutorClass(
            TimedTask task) {
        AssertUtils.notNull(task, "task is null.");
        
        Class<? extends AbstractTimedTaskExecutor> executorType = null;
        if (task instanceof SimpleTimedTask) {
            executorType = SimpleTimedTaskExecutor.class;
        } else if (task instanceof BatchTimedTask) {
            executorType = BatchTimedTaskExecutor.class;
        } else if (task instanceof IterableTimedTask) {
            executorType = IterableTimedTaskExecutor.class;
        } else if (task instanceof ConcurrentBatchTimedTask) {
            executorType = ConcurrentBatchTimedTaskExecutor.class;
        } else {
            throw new TaskInitException(MessageUtils.format(
                    "task init exception.TaskExecutor not exist,task type:{}",
                    new Object[] { task.getClass().getName() }));
        }
        
        return executorType;
    }
    
    /** 
     * 生成对应的业务层Bean名称<br/>
     * <功能详细描述>
     * @param type
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String generateTimedTaskExecutorBeanName(String taskBeanName,
            TimedTask timedTask) {
        String taskExecutorBeanName = "taskContext.timedTaskExecutor_["
                + StringUtils.uncapitalize(taskBeanName) + "]";
        
        return taskExecutorBeanName;
    }
}
