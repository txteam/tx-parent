/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.template.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.template.dao.TemplateTableDao;
import com.tx.component.template.model.TemplateTable;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TemplateTable持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("templateTableDao")
public class TemplateTableDaoImpl implements TemplateTableDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertTemplateTable(TemplateTable condition) {
        this.myBatisDaoSupport.insertUseUUID("templateTable.insertTemplateTable", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteTemplateTable(TemplateTable condition) {
        return this.myBatisDaoSupport.delete("templateTable.deleteTemplateTable", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TemplateTable findTemplateTable(TemplateTable condition) {
        return this.myBatisDaoSupport.<TemplateTable> find("templateTable.findTemplateTable", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TemplateTable> queryTemplateTableList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<TemplateTable> queryList("templateTable.queryTemplateTable",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TemplateTable> queryTemplateTableList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<TemplateTable> queryList("templateTable.queryTemplateTable",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countTemplateTable(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("templateTable.queryTemplateTableCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TemplateTable> queryTemplateTablePagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TemplateTable> queryPagedList("templateTable.queryTemplateTable",
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
    public PagedList<TemplateTable> queryTemplateTablePagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TemplateTable> queryPagedList("templateTable.queryTemplateTable",
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
    public int updateTemplateTable(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("templateTable.updateTemplateTable", updateRowMap);
    }
}
