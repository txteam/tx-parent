/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.basicdata.util.BasicDataUtils;
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
public class DefaultRemoteBasicDataService<T extends BasicData>
        implements BasicDataService<T> {
    
    private Class<T> rawType;
    
    /** 基础数据远程调用客户端 */
    private BasicDataAPIClient client;
    
    /** <默认构造函数> */
    public DefaultRemoteBasicDataService() {
        super();
    }
    
    /** <默认构造函数> */
    public DefaultRemoteBasicDataService(Class<T> rawType,
            BasicDataAPIClient client) {
        super();
        this.rawType = rawType;
        this.client = client;
    }
    
    /**
     * @return 返回 rawType
     */
    @Override
    public Class<T> getRawType() {
        return rawType;
    }
    
    /**
     * @param data
     */
    @Override
    public void insert(T object) {
        AssertUtils.notNull(object, "object is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        //构建字典对象
        Map<String, Object> map = BasicDataUtils.toMap(object);
        this.client.insert(type, map);
    }
    
    /**
     * @param dataList
     */
    @Override
    public void batchInsert(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<Map<String, Object>> mapList = BasicDataUtils.toMapList(dataList);
        this.client.batchInsert(type, mapList);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
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
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
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
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        Map<String, Object> resMap = this.client.findById(type, id);
        T res = BasicDataUtils.fromMap(resMap, getRawType());
        return res;
    }
    
    /**
     * @param code
     * @return
     */
    @Override
    public T findByCode(String code) {
        AssertUtils.notEmpty(code, "code is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        //查询基础数据详情实例
        Map<String, Object> resMap = this.client.findByCode(type, code);
        T res = BasicDataUtils.fromMap(resMap, getRawType());
        return res;
    }
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Boolean valid, Map<String, Object> params) {
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<Map<String, Object>> mapList = this.client.queryList(type,
                valid,
                params);
        List<T> resList = BasicDataUtils.fromMapList(mapList, getRawType());
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
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        PagedList<Map<String, Object>> mapPagedList = this.client
                .queryPagedList(type, valid, params, pageIndex, pageSize);
        PagedList<T> resPagedList = BasicDataUtils
                .fromMapPagedList(mapPagedList, getRawType());
        return resPagedList;
    }
    
    /**
     * 判断数据是否存在<br/>
     * @param key2valueMap
     * @param excludeId
     * @return
     */
    @Override
    public boolean exist(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        boolean flag = this.client.exist(type, key2valueMap, excludeId);
        return flag;
        
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean update(T data) {
        AssertUtils.notNull(data, "data is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        Map<String, Object> map = BasicDataUtils.toMap(data);
        boolean flag = this.client.update(type, map);
        return flag;
    }
    
    /**
     * @param dataList
     */
    @Override
    public void batchUpdate(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<Map<String, Object>> mapList = BasicDataUtils.toMapList(dataList);
        this.client.batchUpdate(type, mapList);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean disableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        boolean flag = this.client.disableById(type, id);
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean enableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        boolean flag = this.client.enableById(type, id);
        return flag;
    }
    
    /**
     * @param 对rawType进行赋值
     */
    public void setRawType(Class<T> rawType) {
        this.rawType = rawType;
    }
    
    /**
     * @param 对client进行赋值
     */
    public void setClient(BasicDataAPIClient client) {
        this.client = client;
    }
}
