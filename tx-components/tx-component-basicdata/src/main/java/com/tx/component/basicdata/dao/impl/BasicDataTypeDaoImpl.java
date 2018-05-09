/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.basicdata.dao.BasicDataTypeDao;
import com.tx.component.basicdata.model.BasicDataType;
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * BasicDataType持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BasicDataTypeDaoImpl implements BasicDataTypeDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public BasicDataTypeDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataTypeDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<BasicDataType> condition) {
        this.myBatisDaoSupport.batchInsertUseUUID(
                "basicDataType.insertBasicDataType", condition, "id", true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String, Object>> updateRowMapList) {
        this.myBatisDaoSupport.batchUpdate("basicDataType.updateBasicDataType",
                updateRowMapList,
                true);
    }
    
    /**
     * @param condition
     */
    @Override
    public void insert(BasicDataType condition) {
        this.myBatisDaoSupport.insertUseUUID(
                "basicDataType.insertBasicDataType", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(BasicDataType condition) {
        return this.myBatisDaoSupport
                .delete("basicDataType.deleteBasicDataType", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public BasicDataType find(BasicDataType condition) {
        return this.myBatisDaoSupport.<BasicDataType> find(
                "basicDataType.findBasicDataType", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<BasicDataType> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<BasicDataType> queryList(
                "basicDataType.queryBasicDataType", params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<BasicDataType> queryList(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<BasicDataType> queryList(
                "basicDataType.queryBasicDataType", params, orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find(
                "basicDataType.queryBasicDataTypeCount", params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<BasicDataType> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<BasicDataType> queryPagedList(
                "basicDataType.queryBasicDataType",
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
    public PagedList<BasicDataType> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<BasicDataType> queryPagedList(
                "basicDataType.queryBasicDataType",
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
        return this.myBatisDaoSupport
                .update("basicDataType.updateBasicDataType", updateRowMap);
    }
}
