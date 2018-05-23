/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月15日
 * <修改描述:>
 */
package com.tx.component.task.delegate.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tx.component.task.delegate.TaskDelegateExecution;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.model.TaskStatus;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 任务执行时环境实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskDelegateExecutionImpl implements TaskDelegateExecution {
    
    /** 任务定义 */
    private final TaskDef taskDef;
    
    /** 任务状态 */
    private final TaskStatus taskStatus;
    
    /** 会话中传递的参数实例 */
    private final Map<String, Object> attributes;
    
    /** 跳过标志位 */
    private boolean skipFlag = false;
    
    /** <默认构造函数> */
    public TaskDelegateExecutionImpl(TaskDef taskDef, TaskStatus taskStatus) {
        super();
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notNull(taskStatus, "taskStatus is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        
        this.taskDef = taskDef;
        this.taskStatus = taskStatus;
        this.attributes = new HashMap<>();
    }
    
    /**
     * @return
     */
    @Override
    public String getTaskId() {
        return this.taskDef.getId();
    }
    
    /**
     * @return
     */
    @Override
    public TaskDef getTask() {
        return this.taskDef;
    }
    
    /**
     * @return
     */
    @Override
    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }
    
    /**
     * @return
     */
    @Override
    public JSONObject getTaskAttributeJSONObject() {
        JSONObject jsonObject = JSONObject
                .parseObject(this.taskDef.getAttributes());
        
        if (jsonObject == null) {
            return new JSONObject();
        }
        
        return jsonObject;
    }
    
    /**
     * @return
     */
    @Override
    public JSONObject getTaskStatusAttributeJSONObject() {
        JSONObject jsonObject = JSONObject
                .parseObject(this.taskStatus.getAttributes());
        
        if (jsonObject == null) {
            return new JSONObject();
        }
        
        return jsonObject;
    }
    
    /**
     * @return
     */
    @Override
    public String getTaskStatusAttributes() {
        JSONObject jsonObject = getTaskAttributeJSONObject();
        
        String taskStatusAttributes = jsonObject.toJSONString();
        return taskStatusAttributes;
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void setTaskStatusAttribute(String key, Object value) {
        AssertUtils.notEmpty(key, "key is empty.");
        AssertUtils.notNull(value, "value is null.");
        
        JSONObject jsonObject = getTaskAttributeJSONObject();
        jsonObject.put(key, value);
        
        String taskStatusAttributes = jsonObject.toJSONString();
        this.taskStatus.setAttributes(taskStatusAttributes);
    }
    
    /**
     * @return
     */
    @Override
    public Date getNextFireDate() {
        return this.taskStatus.getNextFireDate();
    }
    
    /**
     * @param nextFireDate
     */
    @Override
    public void setNextFireDate(Date nextFireDate) {
        this.taskStatus.setNextFireDate(nextFireDate);
    }
    
    /**
     * @return 返回 attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void setAttribute(String key, Object value) {
        AssertUtils.notEmpty(key, "key is empty.");
        
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
    @Override
    public <T> void setAttributeValue(String key, T value) {
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
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttributeValue(String key, Class<T> type) {
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
     * @return 返回 skipFlag
     */
    @Override
    public boolean isSkip() {
        return skipFlag;
    }
    
    /**
     * @param 对skipFlag进行赋值
     */
    @Override
    public void setSkip() {
        this.skipFlag = true;
    }
}
