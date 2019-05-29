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

import com.tx.generator2test.dao.TestModeDao;
import com.tx.generator2test.model.TestMode;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * TestMode持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testModeDao")
public class TestModeDaoImpl implements TestModeDao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insert(TestMode testMode) {
        this.myBatisDaoSupport.insertUseUUID("testMode.insert", testMode, "id");
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<TestMode> testMode){
        this.myBatisDaoSupport.batchInsertUseUUID("testMode.insert", testMode, "id",true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(TestMode testMode) {
        return this.myBatisDaoSupport.delete("testMode.delete", testMode);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("testMode.update", updateRowMap);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String,Object>> updateRowMapList){
        this.myBatisDaoSupport.batchUpdate("testMode.update", updateRowMapList,true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public TestMode find(TestMode testMode) {
        return this.myBatisDaoSupport.<TestMode> find("testMode.find", testMode);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<TestMode> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<TestMode> queryList("testMode.query",
                params);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("testMode.queryCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<TestMode> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<TestMode> queryPagedList("testMode.query",
                params,
                pageIndex,
                pageSize);
    }
}
