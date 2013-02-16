/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.workflow.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.workflow.dao.ProcessDefinitionDao;
import com.tx.component.workflow.model.ProcessDefinition;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * ProcessDefinition持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("processDefinitionDao")
public class ProcessDefinitionDaoImpl implements ProcessDefinitionDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertProcessDefinition(ProcessDefinition condition) {
        this.myBatisDaoSupport.insertUseUUID("processDefinition.insertProcessDefinition", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteProcessDefinition(ProcessDefinition condition) {
        return this.myBatisDaoSupport.delete("processDefinition.deleteProcessDefinition", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public ProcessDefinition findProcessDefinition(ProcessDefinition condition) {
        return this.myBatisDaoSupport.<ProcessDefinition> find("processDefinition.findProcessDefinition", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<ProcessDefinition> queryProcessDefinitionList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<ProcessDefinition> queryList("processDefinition.queryProcessDefinition",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<ProcessDefinition> queryProcessDefinitionList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<ProcessDefinition> queryList("processDefinition.queryProcessDefinition",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countProcessDefinition(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("processDefinition.queryProcessDefinitionCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<ProcessDefinition> queryProcessDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<ProcessDefinition> queryPagedList("processDefinition.queryProcessDefinition",
                params,
                pageIndex,
                pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<ProcessDefinition> queryProcessDefinitionPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<ProcessDefinition> queryPagedList("processDefinition.queryProcessDefinition",
                params,
                pageIndex,
                pageSize,
                orderList);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int updateProcessDefinition(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("processDefinition.updateProcessDefinition", updateRowMap);
    }
}
