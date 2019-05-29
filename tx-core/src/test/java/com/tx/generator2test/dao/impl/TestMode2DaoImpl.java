/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.generator2test.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.generator2test.dao.TestMode2Dao;
import com.tx.generator2test.model.TestMode2;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TestMode2持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testMode2Dao")
public class TestMode2DaoImpl implements TestMode2Dao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insert(TestMode2 testMode2) {
        this.myBatisDaoSupport.insertUseUUID("testMode2.insert", testMode2, "id");
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<TestMode2> testMode2){
        this.myBatisDaoSupport.batchInsertUseUUID("testMode2.insert", testMode2, "id",true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(TestMode2 testMode2) {
        return this.myBatisDaoSupport.delete("testMode2.delete", testMode2);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("testMode2.update", updateRowMap);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String,Object>> updateRowMapList){
        this.myBatisDaoSupport.batchUpdate("testMode2.update", updateRowMapList,true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TestMode2 find(TestMode2 testMode2) {
        return this.myBatisDaoSupport.<TestMode2> find("testMode2.find", testMode2);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TestMode2> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<TestMode2> queryList("testMode2.query",
                params);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("testMode2.queryCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TestMode2> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TestMode2> queryPagedList("testMode2.query",
                params,
                pageIndex,
                pageSize);
    }
}
