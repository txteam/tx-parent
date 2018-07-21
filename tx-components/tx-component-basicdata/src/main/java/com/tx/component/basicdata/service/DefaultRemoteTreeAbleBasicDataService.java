/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.tx.component.basicdata.context.AbstractTreeAbleBasicDataService;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * 默认的基础数据业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultRemoteTreeAbleBasicDataService<T extends TreeAbleBasicData<T>>
        extends AbstractTreeAbleBasicDataService<T> {
    
    /** 所属模块 */
    private String module;
    
    /** 对应类型 */
    private Class<T> type;
    
    /** 基础数据远程调用客户端 */
    private BasicDataRemoteService client;
    
    /** <默认构造函数> */
    public DefaultRemoteTreeAbleBasicDataService() {
        super();
    }
    
    /**
     * @return
     */
    @Override
    public Class<T> type() {
        return this.type;
    }
    
    /**
     * @return
     */
    @Override
    public String module() {
        return this.module;
    }
    
    /**
     * @return
     */
    @Override
    public String tableName() {
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        String tableName = this.client.tableName(type());
        return tableName;
    }
    
    /**
     * @param data
     */
    @Override
    public void insert(T object) {
        AssertUtils.notNull(object, "object is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        //构建字典对象
        this.client.insert(type, object);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        boolean flag = this.client.deleteById(type, id);
        return flag;
    }
    
    /**
     * @param basicDataTypeCode
     * @param code
     * @return
     */
    @Override
    public boolean deleteByCode(String code) {
        AssertUtils.notEmpty(code, "code is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        boolean flag = this.client.deleteByCode(type, code);
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public T findById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        T res = this.client.findById(type, id);
        return res;
    }
    
    /**
     * @param code
     * @return
     */
    @Override
    public T findByCode(String code) {
        AssertUtils.notEmpty(code, "code is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        //查询基础数据详情实例
        T res = this.client.findByCode(type, code);
        return res;
    }
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Boolean valid, Map<String, Object> params) {
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        List<T> resList = this.client.queryList(type, valid, params);
        return resList;
    }
    
    /**
     * @param valid
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        PagedList<T> resPageList = this.client.queryPagedList(type,
                valid,
                params,
                pageIndex,
                pageSize);
        return resPageList;
    }
    
    /**
     * 判断数据是否存在<br/>
     * @param key2valueMap
     * @param excludeId
     * @return
     */
    @Override
    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        boolean flag = this.client.isExist(type, key2valueMap, excludeId);
        return flag;
        
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean updateById(T data) {
        AssertUtils.notNull(data, "data is null.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        boolean flag = this.client.updateById(type, data);
        return flag;
    }
    
    /**
     * @param dataList
     */
    @Override
    public void batchInsert(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        this.client.batchInsert(type, dataList);
    }
    
    /**
     * @param dataList
     */
    @Override
    public void batchUpdate(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        this.client.batchUpdate(type, dataList);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean disableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        boolean flag = this.client.disableById(type(), id);
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean enableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        boolean flag = this.client.enableById(type(), id);
        return flag;
    }
    
    /**
     * @param parentId
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<T> queryListByParentId(String parentId, Boolean valid,
            Map<String, Object> params) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        List<T> resList = this.client.queryListByParentId(type,
                parentId,
                valid,
                params);
        return resList;
    }
    
    /**
     * @param parentId
     * @param valid
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedListByParentId(String parentId, Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        
        Class<T> type = type();
        AssertUtils.notNull(type, "type is null.");
        
        PagedList<T> resPageList = this.client.queryPagedListByParentId(type,
                parentId,
                valid,
                params,
                pageIndex,
                pageSize);
        return resPageList;
    }
    
    /**
     * @param 对type进行赋值
     */
    public void setType(Class<T> type) {
        this.type = type;
    }
    
    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }
    
    /**
     * @param 对client进行赋值
     */
    public void setClient(BasicDataRemoteService client) {
        this.client = client;
    }
}
