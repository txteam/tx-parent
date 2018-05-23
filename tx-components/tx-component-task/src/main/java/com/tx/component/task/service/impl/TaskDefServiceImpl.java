/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.task.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.task.dao.TaskDefDao;
import com.tx.component.task.model.TaskDef;
import com.tx.component.task.service.TaskDefService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TaskDef的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TaskDefServiceImpl implements TaskDefService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TaskDefServiceImpl.class);
    
    private TaskDefDao taskDefDao;
    
    /** <默认构造函数> */
    public TaskDefServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public TaskDefServiceImpl(TaskDefDao taskDefDao) {
        super();
        this.taskDefDao = taskDefDao;
    }
    
    /**
     * @param taskDef
     */
    @Override
    @Transactional
    public void insert(TaskDef taskDef) {
        //验证参数是否合法
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getCode(), "taskDef.code is empty.");
        AssertUtils.notEmpty(taskDef.getBeanName(),
                "taskDef.beanName is empty.");
        AssertUtils.notEmpty(taskDef.getClassName(),
                "taskDef.className is empty.");
        AssertUtils.notEmpty(taskDef.getMethodName(),
                "taskDef.methodName is empty.");
        AssertUtils.notEmpty(taskDef.getName(), "taskDef.name is empty.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        Date now = new Date();
        taskDef.setCreateDate(now);
        taskDef.setLastUpdateDate(now);
        
        //调用数据持久层对实体进行持久化操作
        this.taskDefDao.insert(taskDef);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskDef condition = new TaskDef();
        condition.setId(id);
        int resInt = this.taskDefDao.delete(condition);
        
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public TaskDef findAndlockById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskDef condition = new TaskDef();
        condition.setId(id);
        
        TaskDef res = this.taskDefDao.findAndlock(condition);
        return res;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public TaskDef findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TaskDef condition = new TaskDef();
        condition.setId(id);
        
        TaskDef res = this.taskDefDao.find(condition);
        return res;
    }
    
    /**
     * @param code
     * @return
     */
    @Override
    public TaskDef findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TaskDef condition = new TaskDef();
        condition.setCode(code);
        
        TaskDef taskDef = this.taskDefDao.find(condition);
        return taskDef;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TaskDef> queryList(Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TaskDef> resList = this.taskDefDao.queryList(params);
        
        return resList;
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TaskDef> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TaskDef> resPagedList = this.taskDefDao.queryPagedList(params,
                pageIndex,
                pageSize);
        
        return resPagedList;
    }
    
    /**
     * @param key2valueMap
     * @param excludeId
     * @return
     */
    @Override
    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.taskDefDao.count(params);
        
        return res > 0;
    }
    
    /**
     * @param taskDef
     * @return
     */
    @Override
    @Transactional
    public boolean updateById(TaskDef taskDef) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(taskDef, "taskDef is null.");
        AssertUtils.notEmpty(taskDef.getId(), "taskDef.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", taskDef.getId());
        
        //需要更新的字段
        updateRowMap.put("name", taskDef.getName());
        updateRowMap.put("remark", taskDef.getRemark());
        updateRowMap.put("attributes", taskDef.getAttributes());
        
        updateRowMap.put("executable", taskDef.isExecutable());
        updateRowMap.put("valid", taskDef.isValid());
        updateRowMap.put("orderPriority", taskDef.getOrderPriority());
        
        updateRowMap.put("parentCode", taskDef.getParentCode());
        updateRowMap.put("module", taskDef.getModule());
        updateRowMap.put("beanName", taskDef.getBeanName());
        updateRowMap.put("className", taskDef.getClassName());
        updateRowMap.put("methodName", taskDef.getMethodName());
        updateRowMap.put("lastUpdateDate", new Date());
        
        int updateRowCount = this.taskDefDao.update(updateRowMap);
        
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
}
