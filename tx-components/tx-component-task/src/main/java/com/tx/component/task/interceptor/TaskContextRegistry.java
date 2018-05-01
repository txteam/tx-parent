/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年11月2日
 * <修改描述:>
 */
package com.tx.component.task.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class TaskContextRegistry implements ApplicationContextAware {
    
    /** 日志记录器 */
    private static final Logger logger = LoggerFactory.getLogger(TaskContextRegistry.class);
    
    /** 注册表实例 */
    public static TaskContextRegistry INSTANCE = new TaskContextRegistry();
    
    private ApplicationContext applicationContext;
    
    /** 任务定义映射 */
    private Map<String, Method> taskId2MethodMap = new HashMap<>();
    
    /** 任务定义映射 */
    private Map<String, TaskDef> taskIdMap = new HashMap<>();
    
    /** 任务定义映射 */
    private Map<String, TaskDef> taskCodeMap = new HashMap<>();
    
    /** 父级任务编码映射 */
    private MultiValueMap<String, TaskDef> parentCodeMultiMap = new LinkedMultiValueMap<>();
    
    /** <默认构造函数> */
    private TaskContextRegistry() {
        super();
    }
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /** 注册任务定义 */
    void registeTask(TaskDef taskDef, Method method) {
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        AssertUtils.notEmpty(taskDef.getCode(), "taskDef.code is empty.");
        AssertUtils.notEmpty(taskDef.getBeanName(), "taskDef.beanName is empty.");
        AssertUtils.notNull(method, "method is null.");
        
        AssertUtils.notTrue(taskIdMap.containsKey(taskDef.getId()), "taskId:{} duplicate.", taskDef.getId());
        AssertUtils.notTrue(taskCodeMap.containsKey(taskDef.getCode()), "taskCode:{} duplicate.", taskDef.getCode());
        
        this.taskIdMap.put(taskDef.getId(), taskDef);
        this.taskCodeMap.put(taskDef.getCode(), taskDef);
        this.parentCodeMultiMap.add(taskDef.getParentCode(), taskDef);
        
        this.taskId2MethodMap.put(taskDef.getId(), method);
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
        TaskDef taskDef = getTaskDefById(taskId);
        
        AssertUtils.notNull(taskDef, "taskDef:{} is not exist.", taskId);
        
        doExecute(taskDef, args);
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
        TaskDef taskDef = getTaskDefByCode(taskCode);
        
        AssertUtils.notNull(taskDef, "taskCode:{} is not exist.", taskCode);
        
        doExecute(taskDef, args);
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
        return ListUtils.unmodifiableList(new ArrayList<>(this.taskIdMap.keySet()));
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
        return ListUtils.unmodifiableList(new ArrayList<>(this.taskCodeMap.keySet()));
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
    public List<TaskDef> getTaskDefs() {
        return ListUtils.unmodifiableList(new ArrayList<>(this.taskIdMap.values()));
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
    public List<TaskDef> getTaskDefsByParentCode(String parentCode) {
        AssertUtils.notEmpty(parentCode, "parentCode is empty.");
        
        List<TaskDef> taskList = null;
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
    public TaskDef getTaskDefById(String taskId) {
        AssertUtils.notEmpty(taskId, "taskId is empty.");
        if (!this.taskIdMap.containsKey(taskId)) {
            return null;
        }
        TaskDef task = this.taskIdMap.get(taskId);
        return task;
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
    public TaskDef getTaskDefByCode(String taskCode) {
        AssertUtils.notEmpty(taskCode, "taskCode is empty.");
        if (!this.taskCodeMap.containsKey(taskCode)) {
            return null;
        }
        TaskDef task = this.taskCodeMap.get(taskCode);
        return task;
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
    private Object doExecute(TaskDef taskDef, Object... args) {
        AssertUtils.notNull(taskDef, "taskDef is null.");
        
        Object object = getBeanByTaskDef(taskDef);
        Method method = getMethodByTaskDef(taskDef);
        
        Object returnObject = null;
        try {
            //类有可能已经被代理过 所以不用：method.invoke(object, args);
            returnObject = MethodUtils.invokeExactMethod(object, method.getName(), args);
        } catch (SILException e) {
            throw e;
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            logger.error(e.getMessage());
            throw new SILException(e.getMessage(), e);
        }
        return returnObject;
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
    private Method getMethodByTaskDef(TaskDef taskDef) {
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        AssertUtils.isTrue(this.taskId2MethodMap.containsKey(taskDef.getId()),
                "method is not exist.taskId:{}",
                taskDef.getId());
        
        Method method = this.taskId2MethodMap.get(taskDef.getId());
        return method;
    }
    
    private Object getBeanByTaskDef(TaskDef taskDef) {
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        AssertUtils.notEmpty(taskDef.getBeanName(), "taskDef.beanName is empty.");
        
        Object bean = this.applicationContext.getBean(taskDef.getBeanName());
        AssertUtils.notNull(bean, "bean:'{}' is not exist.", taskDef.getBeanName());
        
        return bean;
    }
    
}
