/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月3日
 * <修改描述:>
 */
package com.tx.component.task.delegate;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.model.TaskStatus;

/**
 * 任务委托执行环境<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TaskDelegateExecution {
    
    /**
     * 获取任务id<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getTaskId();
    
    /**
     * 获取任务定义<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskDef [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    TaskDef getTask();
    
    /**
     * 任务状态<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TaskStatus [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    TaskStatus getTaskStatus();
    
    /**
     * 获取任务属性Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    JSONObject getTaskAttributeJSONObject();
    
    /**
     * 获取任务状态属性<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getTaskStatusAttributes();
    
    /**
     * 获取任务属性Map<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    JSONObject getTaskStatusAttributeJSONObject();
    
    /**
     * 设置任务状态属性<br/>
     * <功能详细描述>
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void setTaskStatusAttribute(String key, Object value);
    
    /**
     * 获取下次执行时间
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    Date getNextFireDate();
    
    /**
     * 设置下次执行时间<br/>
     * <功能详细描述>
     * @param nextFireDate [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void setNextFireDate(Date nextFireDate);
    
    /**
     * 获取执行期属性<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getAttributes();
    
    /**
     * 设置属性值<br/>
     * <功能详细描述>
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void setAttribute(String key, Object value);
    
    /**
     * 设置属性值<br/>
     * <功能详细描述>
     * @param key
     * @param value [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    <T> void setAttributeValue(String key, T value);
    
    /**
     * 获取属性值<br/>
     * <功能详细描述>
     * @param key
     * @param type
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    <T> T getAttributeValue(String key, Class<T> type);
    
    /**
     * 是否被跳过<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean isSkip();

    /**
     * 设置当前方法执行期间被跳过<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setSkip();
}
