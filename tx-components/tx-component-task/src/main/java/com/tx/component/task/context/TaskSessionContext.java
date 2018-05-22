/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月25日
 * <修改描述:>
 */
package com.tx.component.task.context;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.component.task.delegate.TaskDelegateExecution;
import com.tx.component.task.delegate.impl.TaskDelegateExecutionImpl;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.model.TaskStatus;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 操作会话容器<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年4月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskSessionContext {
    
    /** 下次执行时间 */
    public static final String STATUS_ATTRIBUTE_KEY_NEXT_FIRE_DATE = "nextFireDate";
    
    /** 日志记录句柄 */
    private static Logger logger = LoggerFactory
            .getLogger(TaskSessionContext.class);
    
    /** 当前线程中的操作容器实例 */
    private static ThreadLocal<Stack<TaskDelegateExecution>> context = new ThreadLocal<Stack<TaskDelegateExecution>>() {
        /**
         * @return
         */
        @Override
        protected Stack<TaskDelegateExecution> initialValue() {
            logger.info(
                    "TaskSessionContext: current thread: {}. task session start.",
                    String.valueOf(Thread.currentThread().getId()));
            
            //该会话必须在事务中进行执行,存在此逻辑，可写入会话执行完毕后强制清理线程变量
            AssertUtils.isTrue(
                    TransactionSynchronizationManager.isSynchronizationActive(),
                    "必须在事务中进行执行");
            
            //new 堆栈
            Stack<TaskDelegateExecution> stack = new Stack<TaskDelegateExecution>();
            
            //注册自动会话结束期间强制回收
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        /**
                         * @param status
                         */
                        @Override
                        public void afterCompletion(int status) {
                            //清空堆栈
                            stack.clear();
                            //移除线程变量
                            remove();
                        }
                    });
                    
            return stack;
        }
        
        /**
         * 清空
         */
        @Override
        public void remove() {
            logger.debug(
                    "TaskSessionContext: current thread: {}. task session end.",
                    String.valueOf(Thread.currentThread().getId()));
            
            super.remove();
        }
    };
    
    /**
     * 打开一个操作请求会话<br/>
     * <功能详细描述>
     * @param request
     * @param response [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void open(TaskDef taskDef, TaskStatus taskStatus) {
        //获取堆栈
        logger.debug("TaskSessionContext: open.");
        Stack<TaskDelegateExecution> stack = TaskSessionContext.context.get();
        
        //构建新的堆栈
        TaskDelegateExecution execution = new TaskDelegateExecutionImpl(taskDef,
                taskStatus);
        stack.push(execution);
    }
    
    /**
      * 关闭一个操作请求会话<br/> 
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static TaskDelegateExecution close() {
        Stack<TaskDelegateExecution> stack = TaskSessionContext.context.get();
        
        //将出栈的会话进行清理
        TaskDelegateExecution execution = stack.pop();
        
        //如果堆栈顶部仍然存在交易，则对该交易重新进行持久
        if (stack.isEmpty()) {
            TaskSessionContext.context.remove();
        } else {
            stack.peek();
        }
        logger.debug("TaskSessionContext: close.");
        
        return execution;
    }
    
    /**
      * 获取当前的操作会话<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return ProcessSessionContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static TaskDelegateExecution getExecution() {
        Stack<TaskDelegateExecution> stack = TaskSessionContext.context.get();
        if (stack.isEmpty()) {
            return null;
        }
        TaskDelegateExecution execution = stack.peek();
        return execution;
    }
    
}
