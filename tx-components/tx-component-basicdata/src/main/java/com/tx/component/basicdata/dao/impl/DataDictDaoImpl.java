/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.model.DataDict;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * DataDict持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DataDictDaoImpl implements DataDictDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public DataDictDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public DataDictDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }

    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<DataDict> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID("dataDict.insertDataDict",
                condition,
                "id",
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate("dataDict.updateDataDict",
                updateRowMapList,
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(DataDict condition) {
        this.myBatisDaoSupport.insertUseUUID("dataDict.insertDataDict",
                condition,
                "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(DataDict condition) {
        return this.myBatisDaoSupport.delete("dataDict.deleteDataDict",
                condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public DataDict find(DataDict condition) {
        return this.myBatisDaoSupport.<DataDict> find("dataDict.findDataDict",
                condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<DataDict> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<DataDict> queryList("dataDict.queryDataDict",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<DataDict> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<DataDict> queryList("dataDict.queryDataDict",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("dataDict.queryDataDictCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<DataDict> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<DataDict> queryPagedList("dataDict.queryDataDict",
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
    public PagedList<DataDict> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<DataDict> queryPagedList("dataDict.queryDataDict",
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
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("dataDict.updateDataDict",
                updateRowMap);
    }
}
