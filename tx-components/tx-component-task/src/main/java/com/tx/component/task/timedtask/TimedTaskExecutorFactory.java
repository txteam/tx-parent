///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2017年10月16日
// * <修改描述:>
// */
//package com.tx.component.task.timedtask;
//
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.SingletonBeanRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//import com.tx.component.task.exeception.TaskInitException;
//import com.tx.component.task.timedtask.executor.BatchTimedTaskExecutor;
//import com.tx.component.task.timedtask.executor.ConcurrentBatchTimedTaskExecutor;
//import com.tx.component.task.timedtask.executor.IterableTimedTaskExecutor;
//import com.tx.component.task.timedtask.executor.SimpleTimedTaskExecutor;
//import com.tx.component.task.timedtask.task.BatchTimedTask;
//import com.tx.component.task.timedtask.task.ConcurrentBatchTimedTask;
//import com.tx.component.task.timedtask.task.IterableTimedTask;
//import com.tx.component.task.timedtask.task.SimpleTimedTask;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.MessageUtils;
//
///**
//  * 定时任务执行器工厂<br/>
//  * <功能详细描述>
//  * 
//  * @author  Administrator
//  * @version  [版本号, 2017年10月16日]
//  * @see  [相关类/方法]
//  * @since  [产品/模块版本]
//  */
//public class TimedTaskExecutorFactory implements ApplicationContextAware, InitializingBean, BeanFactoryAware {
//    
//    /** 定时任务执行器工厂 */
//    private Logger logger = LoggerFactory.getLogger(TimedTaskExecutorFactory.class);
//    
//    /** spring容器 */
//    private ApplicationContext applicationContext;
//    
//    /** Bean定义注册机 */
//    protected BeanDefinitionRegistry beanDefinitionRegistry;
//    
//    /** 单例对象注册方法 */
//    protected SingletonBeanRegistry singletonBeanRegistry;
//    
//    /**
//     * @param applicationContext
//     * @throws BeansException
//     */
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//    
//    /**
//     * @param beanFactory
//     * @throws BeansException
//     */
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        AssertUtils.isInstanceOf(BeanDefinitionRegistry.class,
//                beanFactory,
//                "beanFactory is not BeanDefinitionRegistry instance.");
//        this.beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
//        
//        AssertUtils.isInstanceOf(SingletonBeanRegistry.class,
//                beanFactory,
//                "beanFactory is not SingletonBeanRegistry instance.");
//        this.singletonBeanRegistry = (SingletonBeanRegistry) beanFactory;
//    }
//    
//    /**
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        Map<String, TimedTask> taskMap = this.applicationContext.getBeansOfType(TimedTask.class);
//        
//        if (MapUtils.isEmpty(taskMap)) {
//            return;
//        }
//        for (Entry<String, TimedTask> timedTaskEntry : taskMap.entrySet()) {
//            registeTimedTaskExecutorByTimedTask(timedTaskEntry.getKey(), timedTaskEntry.getValue());
//        }
//    }
//    
//    /**
//     * @desc 向spring容器注册BeanDefinition
//     * @param beanName
//     * @param beanDefinition
//     */
//    protected Object registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
//        if (!this.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
//            this.beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
//        }
//        Object bean = this.applicationContext.getBean(beanName);
//        return bean;
//    }
//    
//    /**
//     * 构建默认的基础数据业务类<br/>
//     * <功能详细描述>
//     * @param type [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    //@SuppressWarnings("rawtypes")
//    protected void registeTimedTaskExecutorByTimedTask(String taskBeanName, TimedTask timedTask) {
//        String beanName = generateTimedTaskExecutorBeanName(taskBeanName, timedTask);
//        
//        Class<? extends TimedTaskExecutor> timedTaskExecutorClass = buildTimedTaskExecutorClass(timedTask);
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(timedTaskExecutorClass);
//        builder.addPropertyValue("task", timedTask);
//        
//        logger.info("完成任务执行器注册：task:{}", new Object[] { timedTask.getClass() });
//        registerBeanDefinition(beanName, builder.getBeanDefinition());
//    }
//    
//    /**
//     * 构建定时任务<br/>
//     * <功能详细描述>
//     * @param task
//     * @return [参数说明]
//     * 
//     * @return TimedTaskExecutor [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    private Class<? extends TimedTaskExecutor> buildTimedTaskExecutorClass(TimedTask task) {
//        AssertUtils.notNull(task, "task is null.");
//        
//        Class<? extends TimedTaskExecutor> executorType = null;
//        if (task instanceof SimpleTimedTask) {
//            executorType = SimpleTimedTaskExecutor.class;
//        } else if (task instanceof BatchTimedTask) {
//            executorType = BatchTimedTaskExecutor.class;
//        } else if (task instanceof IterableTimedTask) {
//            executorType = IterableTimedTaskExecutor.class;
//        } else if (task instanceof ConcurrentBatchTimedTask) {
//            executorType = ConcurrentBatchTimedTaskExecutor.class;
//        } else {
//            throw new TaskInitException(MessageUtils.format("task init exception.TaskExecutor not exist,task type:{}",
//                    new Object[] { task.getClass().getName() }));
//        }
//        
//        return executorType;
//    }
//    
//    /** 
//    * 生成对应的业务层Bean名称<br/>
//    * <功能详细描述>
//    * @param type
//    * @return [参数说明]
//    * 
//    * @return String [返回类型说明]
//    * @exception throws [异常类型] [异常说明]
//    * @see [类、类#方法、类#成员]
//    */
//    private String generateTimedTaskExecutorBeanName(String taskBeanName, TimedTask timedTask) {
//        String taskExecutorBeanName = "taskContext.timedTaskExecutor[" + StringUtils.uncapitalize(taskBeanName) + "]";
//        return taskExecutorBeanName;
//    }
//    
//}
