/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年11月2日
 * <修改描述:>
 */
package com.tx.component.task.context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tx.component.task.model.TaskDef;
import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
  * 任务容器注册表<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年11月2日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TaskContextRegistry {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory
            .getLogger(TaskContextRegistry.class);
    
    /** 注册表实例 */
    public static TaskContextRegistry INSTANCE = new TaskContextRegistry();
    
    /** 任务定义映射 */
    private Map<String, TaskInstance> taskIdMap = new HashMap<>();
    
    /** 任务定义映射 */
    private Map<String, TaskInstance> taskCodeMap = new HashMap<>();
    
    /** 父级任务编码映射 */
    private MultiValueMap<String, TaskInstance> parentCodeMultiMap = new LinkedMultiValueMap<>();
    
    /** <默认构造函数> */
    private TaskContextRegistry() {
        super();
    }
    
    /** 注册任务定义 */
    public void registeTask(Object bean, Method method, TaskDef taskDef) {
        AssertUtils.notNull(bean, "bean is null.");
        AssertUtils.notNull(method, "method is null.");
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        AssertUtils.notEmpty(taskDef.getCode(), "taskDef.code is empty.");
        AssertUtils.notEmpty(taskDef.getBeanName(),
                "taskDef.beanName is empty.");
        
        AssertUtils.notTrue(taskIdMap.containsKey(taskDef.getId()),
                "taskId:{} duplicate.",
                taskDef.getId());
        AssertUtils.notTrue(taskCodeMap.containsKey(taskDef.getCode()),
                "taskCode:{} duplicate.",
                taskDef.getCode());
        
        TaskInstance taskIns = new TaskInstance(bean, method, taskDef);
        this.taskIdMap.put(taskDef.getId(), taskIns);
        this.taskCodeMap.put(taskDef.getCode(), taskIns);
        if (!StringUtils.isBlank(taskDef.getParentCode())) {
            this.parentCodeMultiMap.add(taskDef.getParentCode(), taskIns);
        }
    }
    
    /**
     * 执行任务异常<br/>
     * <功能详细描述>
     * @param taskDef
     * @param args
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Object doExecute(TaskInstance taskIns, Object... args) {
        AssertUtils.notNull(taskIns, "taskIns is null.");
        
        Object bean = taskIns.getBean();
        Method method = taskIns.getMethod();
        
        Object returnObject = null;
        try {
            //类有可能已经被代理过 所以不用：method.invoke(object, args);
            returnObject = MethodUtils.invokeExactMethod(bean,
                    method.getName(),
                    args);
        } catch (SILException e) {
            throw e;
        } catch (NoSuchMethodException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            logger.error(e.getMessage());
            
            throw new SILException(e.getMessage(), e);
        }
        return returnObject;
    }
    
    /**
     * 启动指定任务<br/>
     * <功能详细描述>
     * @param taskId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void executeByTaskId(String taskId, Object... args) {
        TaskInstance taskInstance = getTaskInstanceById(taskId);
        
        AssertUtils.notNull(taskInstance, "taskId:{} is not exist.", taskId);
        
        doExecute(taskInstance, args);
    }
    
    /**
     * 启动指定任务<br/>
     * <功能详细描述>
     * @param taskId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void executeByTaskCode(String taskCode, Object... args) {
        TaskInstance taskInstance = getTaskInstanceByCode(taskCode);
        
        AssertUtils.notNull(taskInstance,
                "taskCode:{} is not exist.",
                taskCode);
        
        doExecute(taskInstance, args);
    }
    
    /**
     * 获取任务id集合
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<String> getTaskIds() {
        return ListUtils
                .unmodifiableList(new ArrayList<>(this.taskIdMap.keySet()));
    }
    
    /**
     * 获取任务编码集合<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<String> getTaskCodes() {
        return ListUtils
                .unmodifiableList(new ArrayList<>(this.taskCodeMap.keySet()));
    }
    
    /**
     * 获取任务定义集合<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TaskInstance> getTaskInstances() {
        return ListUtils
                .unmodifiableList(new ArrayList<>(this.taskIdMap.values()));
    }
    
    /**
     * 获取任务定义集合<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TaskDef> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TaskInstance> getTaskInstancesByParentCode(String parentCode) {
        AssertUtils.notEmpty(parentCode, "parentCode is empty.");
        
        List<TaskInstance> taskList = null;
        if (this.parentCodeMultiMap.containsKey(parentCode)) {
            taskList = this.parentCodeMultiMap.get(parentCode);
        } else {
            taskList = new ArrayList<>();
        }
        return ListUtils.unmodifiableList(taskList);
    }
    
    /**
     * 根据任务id获取任务定义<br/>
     * <功能详细描述>
     * @param taskId
     * @return [参数说明]
     * 
     * @return TaskDef [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskInstance getTaskInstanceById(String taskId) {
        AssertUtils.notEmpty(taskId, "taskId is empty.");
        if (!this.taskIdMap.containsKey(taskId)) {
            return null;
        }
        TaskInstance taskIns = this.taskIdMap.get(taskId);
        return taskIns;
    }
    
    /**
     * 根据任务code获取任务定义<br/>
     * <功能详细描述>
     * @param taskCode
     * @return [参数说明]
     * 
     * @return TaskDef [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public TaskInstance getTaskInstanceByCode(String taskCode) {
        AssertUtils.notEmpty(taskCode, "taskCode is empty.");
        if (!this.taskCodeMap.containsKey(taskCode)) {
            return null;
        }
        TaskInstance taskIns = this.taskCodeMap.get(taskCode);
        return taskIns;
    }
    
    /**
     * 任务实例<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2018年5月23日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class TaskInstance {
        
        /** 对应的bean实例 */
        private Object bean;
        
        /** 任务对应的方法 */
        private Method method;
        
        /** 任务定义 */
        private TaskDef taskDef;
        
        /** <默认构造函数> */
        public TaskInstance(Object bean, Method method, TaskDef taskDef) {
            super();
            this.bean = bean;
            this.method = method;
            this.taskDef = taskDef;
        }
        
        /**
         * @return 返回 bean
         */
        public Object getBean() {
            return bean;
        }
        
        /**
         * @param 对bean进行赋值
         */
        public void setBean(Object bean) {
            this.bean = bean;
        }
        
        /**
         * @return 返回 method
         */
        public Method getMethod() {
            return method;
        }
        
        /**
         * @param 对method进行赋值
         */
        public void setMethod(Method method) {
            this.method = method;
        }
        
        /**
         * @return 返回 taskDef
         */
        public TaskDef getTaskDef() {
            return taskDef;
        }
        
        /**
         * @param 对taskDef进行赋值
         */
        public void setTaskDef(TaskDef taskDef) {
            this.taskDef = taskDef;
        }
    }
}
