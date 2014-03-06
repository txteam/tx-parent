/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.component.basicdata.dao.TestBasicData2Dao;
import com.tx.component.basicdata.model.TestBasicData2;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TestBasicData2持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testBasicData2Dao")
public class TestBasicData2DaoImpl implements TestBasicData2Dao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertTestBasicData2(TestBasicData2 condition) {
        this.myBatisDaoSupport.insertUseUUID("testBasicData2.insertTestBasicData2", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteTestBasicData2(TestBasicData2 condition) {
        return this.myBatisDaoSupport.delete("testBasicData2.deleteTestBasicData2", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TestBasicData2 findTestBasicData2(TestBasicData2 condition) {
        return this.myBatisDaoSupport.<TestBasicData2> find("testBasicData2.findTestBasicData2", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TestBasicData2> queryTestBasicData2List(Map<String, Object> params) {
        return this.myBatisDaoSupport.<TestBasicData2> queryList("testBasicData2.queryTestBasicData2",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TestBasicData2> queryTestBasicData2List(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<TestBasicData2> queryList("testBasicData2.queryTestBasicData2",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countTestBasicData2(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("testBasicData2.queryTestBasicData2Count",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TestBasicData2> queryTestBasicData2PagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TestBasicData2> queryPagedList("testBasicData2.queryTestBasicData2",
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
    public PagedList<TestBasicData2> queryTestBasicData2PagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TestBasicData2> queryPagedList("testBasicData2.queryTestBasicData2",
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
    public int updateTestBasicData2(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("testBasicData2.updateTestBasicData2", updateRowMap);
    }
}
