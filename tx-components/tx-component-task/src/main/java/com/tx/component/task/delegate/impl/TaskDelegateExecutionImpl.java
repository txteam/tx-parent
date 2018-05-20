/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月15日
 * <修改描述:>
 */
package com.tx.component.task.delegate.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    public Map<String, Object> getTaskAttributeMap() {
        JSONObject json = JSONObject.parseObject(this.taskDef.getAttributes());
        
        Map<String, Object> resMap = new HashMap<>();
        if (json == null) {
            return resMap;
        }
        
        for (Entry<String, Object> entryTemp : json.entrySet()) {
            resMap.put(entryTemp.getKey(), entryTemp.getValue());
        }
        return resMap;
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public Object getTaskAttribute(String key) {
        AssertUtils.notEmpty(key, "key is empty.");
        
        Map<String, Object> resMap = getTaskAttributeMap();
        return resMap.get(key);
    }
    
    /**
     * @return
     */
    @Override
    public String getTaskStatusAttributes() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public Map<String, Object> getTaskStatusAttributeMap() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param key
     * @return
     */
    @Override
    public Object getTaskStatusAttribute(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void setTaskStatusAttribute(String key, String value) {
        // TODO Auto-generated method stub
        
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
}
