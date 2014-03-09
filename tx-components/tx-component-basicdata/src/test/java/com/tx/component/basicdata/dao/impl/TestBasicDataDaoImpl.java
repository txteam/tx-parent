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

import com.tx.component.basicdata.dao.TestBasicDataDao;
import com.tx.component.basicdata.model.TestBasicData;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TestBasicData持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testBasicDataDao")
public class TestBasicDataDaoImpl implements TestBasicDataDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insertTestBasicData(TestBasicData condition) {
        this.myBatisDaoSupport.insertUseUUID("testBasicData.insertTestBasicData", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int deleteTestBasicData(TestBasicData condition) {
        return this.myBatisDaoSupport.delete("testBasicData.deleteTestBasicData", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TestBasicData findTestBasicData(TestBasicData condition) {
        return this.myBatisDaoSupport.<TestBasicData> find("testBasicData.findTestBasicData", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TestBasicData> queryTestBasicDataList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<TestBasicData> queryList("testBasicData.queryTestBasicData",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<TestBasicData> queryTestBasicDataList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<TestBasicData> queryList("testBasicData.queryTestBasicData",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int countTestBasicData(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("testBasicData.queryTestBasicDataCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TestBasicData> queryTestBasicDataPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TestBasicData> queryPagedList("testBasicData.queryTestBasicData",
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
    public PagedList<TestBasicData> queryTestBasicDataPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<TestBasicData> queryPagedList("testBasicData.queryTestBasicData",
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
    public int updateTestBasicData(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("testBasicData.updateTestBasicData", updateRowMap);
    }
}
