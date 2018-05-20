/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月25日
 * <修改描述:>
 */
package com.tx.component.task.context;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.alibaba.fastjson.JSONObject;
import com.tx.component.task.delegate.TaskDelegateExecution;
import com.tx.component.task.delegate.impl.TaskDelegateExecutionImpl;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.model.TaskStatus;
import com.tx.core.TxConstants;
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
        closeProcessSessionContext.clear();
        
        //如果堆栈顶部仍然存在交易，则对该交易重新进行持久
        if (stack.isEmpty()) {
            TaskSessionContext.context.remove();
        } else {
            stack.peek();
        }
        logger.debug("TaskSessionContext: close.");
        
        return jobDataMap;
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
    
    /** 任务定义 */
    private TaskDef taskDef;
    
    /** 请求实例 */
    private Map<String, String> taskDefAttributesMap;
    
    /** 请求实例 */
    private Map<String, String> taskStatusAttributesMap;
    
    /** 下次执行时间 */
    private Date nextFireDate;
    
    /** 会话中传递的参数实例 */
    private Map<String, Object> attributes;
    
    /** <默认构造函数> */
    private TaskSessionContext(TaskDef taskDef, TaskStatus taskStatus) {
        super();
        
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notNull(taskStatus, "taskStatus is null.");
        
        this.taskDef = taskDef;
        String taskDefAtrributes = taskDef.getAttributes();
        JSONObject taskDefAtrributesJsonObject = JSONObject
                .parseObject(taskDefAtrributes);
        Map<String, String> taskDefAttsMap = new HashMap<>(
                TxConstants.INITIAL_MAP_SIZE);
        if (taskDefAtrributesJsonObject != null) {
            for (String keyTemp : taskDefAtrributesJsonObject.keySet()) {
                taskDefAttsMap.put(keyTemp,
                        taskDefAtrributesJsonObject.getString(keyTemp));
            }
        }
        this.taskDefAttributesMap = MapUtils.unmodifiableMap(taskDefAttsMap);
        
        this.taskStatusAttributesMap = new HashMap<String, String>(
                TxConstants.INITIAL_MAP_SIZE);
        String taskStatusAtrributes = taskStatus.getAttributes();
        JSONObject taskStatusAtrributesJsonObject = JSONObject
                .parseObject(taskStatusAtrributes);
        if (taskStatusAtrributesJsonObject != null) {
            for (String keyTemp : taskStatusAtrributesJsonObject.keySet()) {
                this.taskStatusAttributesMap.put(keyTemp,
                        taskStatusAtrributesJsonObject.getString(keyTemp));
            }
        }
        this.nextFireDate = getNextFireDate();
        
        this.attributes = new HashMap<String, Object>(
                TxConstants.INITIAL_MAP_SIZE);
    }
    
    /**
     * 写入下次执行时间<br/>
     * <功能详细描述>
     * @param nextFireDate [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setNextFireDate(Date nextFireDate) {
        this.nextFireDate = nextFireDate;
        if (nextFireDate != null) {
            setTaskStatusAttribute(STATUS_ATTRIBUTE_KEY_NEXT_FIRE_DATE,
                    DateFormatUtils.format(nextFireDate,
                            "yyyy-MM-dd HH:mm:ss"));
        }
    }
    
    /**
     * 获取下次执行时间<br/>
     * <功能详细描述>
     * @param nextFireDate
     * @return [参数说明]
     * 
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Date getNextFireDate() {
        String nextFireDateString = getTaskStatusAttribute(
                STATUS_ATTRIBUTE_KEY_NEXT_FIRE_DATE);
        if (StringUtils.isEmpty(nextFireDateString)) {
            return null;
        } else {
            try {
                this.nextFireDate = DateUtils.parseDate(nextFireDateString,
                        "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                this.nextFireDate = null;
            }
            return this.nextFireDate;
        }
    }
    
    /**
     * 从容器中获取值
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String getTaskStatusAttribute(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        String value = this.taskStatusAttributesMap.get(key);
        return value;
    }
    
    /**
      * 向线程变量中设置值<br/>
      * <功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setTaskStatusAttribute(String key, String value) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        if (value == null) {
            return;
        }
        this.taskStatusAttributesMap.put(key, value);
    }
    
    /**
     * @return 返回 taskStatusAttributesMap
     */
    public Map<String, String> getTaskStatusAttributesMap() {
        return taskStatusAttributesMap;
    }
    
    /**
     * @param 对taskStatusAttributesMap进行赋值
     */
    public void setTaskStatusAttributesMap(
            Map<String, String> taskStatusAttributesMap) {
        this.taskStatusAttributesMap = taskStatusAttributesMap;
    }
    
    /**
     * @return 返回 taskDefAttributesMap
     */
    public Map<String, String> getTaskDefAttributesMap() {
        return taskDefAttributesMap;
    }
    
    /**
     * @return 返回 taskDef
     */
    public TaskDef getTaskDef() {
        return taskDef;
    }
    
    /**
     * @return 返回 attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    /**
     * 从容器中获取值
     * <功能详细描述>
     * @param key
     * @return [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public Object getAttribute(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        if (this.attributes == null) {
            return null;
        }
        Object value = this.attributes.get(key);
        return value;
    }
    
    /**
      * 向线程变量中设置值<br/>
      * <功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void setAttribute(String key, Object value) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        if (value == null) {
            return;
        }
        if (this.attributes == null) {
            return;
        }
        this.attributes.put(key, value);
    }
    
    /**
      * 设置值<br/>
      * <功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> void setValue(String key, T value) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        if (value == null) {
            return;
        }
        if (this.attributes == null) {
            return;
        }
        this.attributes.put(key, value);
    }
    
    /**
     * 从线程中获取对应的Key值
     * <功能详细描述>
     * @param key
     * @param type
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> type) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notNull(type, "type is null.");
        
        if (this.attributes == null) {
            return null;
        }
        Object value = this.attributes.get(key);
        
        if (value != null) {
            AssertUtils.isTrue(type.isInstance(value),
                    "value:{} should be instance of type:{}.",
                    new Object[] { value, type });
        }
        return (T) value;
    }
    
    /**
     * 将当前操作回话容器清空<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void clear() {
        this.attributes = null;
        this.taskStatusAttributesMap = null;
        this.taskDefAttributesMap = null;
        this.taskDef = null;
    }
    
    public static void main(String[] args) {
        System.out.println(JSONObject.parseObject(null) == null);
        System.out.println(JSONObject.parseObject("{}").get("tt"));
        
        System.out.println(JSONObject.toJSONString(new HashMap<>()));
        Map<String, Object> test = new HashMap<>();
        test.put("createDate", new Date());
        System.out.println(JSONObject.toJSONString(test));
    }
}
