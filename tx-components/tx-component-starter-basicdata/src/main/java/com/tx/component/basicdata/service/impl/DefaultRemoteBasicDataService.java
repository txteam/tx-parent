/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.service.BasicDataService;
import com.tx.component.basicdata.util.BasicDataUtils;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

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
        implements BasicDataService<T>, InitializingBean {
    
    protected Class<T> rawType;
    
    /** 基础数据远程调用客户端 */
    protected BasicDataAPIClient client;
    
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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(this.rawType, "rawType is null.");
        AssertUtils.notNull(this.client, "client is null.");
        
        AssertUtils.isTrue(BasicData.class.isAssignableFrom(this.rawType),
                "rawType:{} is not assign from BasicData.class.",
                new Object[] { this.rawType });
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
        DataDict data = BasicDataUtils.toDataDict(object);
        this.client.insert(type, data);
    }
    
    /**
     * @param dataList
     */
    @Override
    public void batchInsert(List<T> objectList) {
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        this.client.batchInsert(type, dataList);
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
        
        DataDict data = this.client.findById(type, id);
        T res = BasicDataUtils.fromDataDict(data, getRawType());
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
        DataDict data = this.client.findByCode(type, code);
        T res = BasicDataUtils.fromDataDict(data, getRawType());
        return res;
    }
    
    /**
     * @param valid
     * @param querier
     * @return
     */
    @Override
    public List<T> queryList(Boolean valid, Querier querier) {
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<DataDict> dataList = this.client.queryList(type, valid, querier);
        List<T> resList = BasicDataUtils.fromDataDictList(dataList,
                getRawType());
        return resList;
    }
    
    /**
     * @param valid
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<T> queryPagedList(Boolean valid,
            Querier querier, int pageIndex, int pageSize) {
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        PagedList<DataDict> dataPagedList = this.client.queryPagedList(type,
                valid,
                querier,
                pageIndex,
                pageSize);
        PagedList<T> resPagedList = BasicDataUtils
                .fromDataDictPagedList(dataPagedList, getRawType());
        return resPagedList;
    }
    
    /**
     * 判断数据是否存在<br/>
     * @param querier
     * @param excludeId
     * @return
     */
    @Override
    public boolean exists(Querier querier, String excludeId) {
        AssertUtils.notNull(querier, "querier is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        boolean flag = this.client.exists(type, querier, excludeId);
        return flag;
        
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean updateById(T object) {
        AssertUtils.notNull(object, "data is null.");
        AssertUtils.notEmpty(object.getId(), "data.id is empty.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        DataDict data = BasicDataUtils.toDataDict(object);
        boolean flag = this.client.updateById(type, object.getId(), data);
        return flag;
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean updateByCode(T object) {
        AssertUtils.notNull(object, "data is null.");
        AssertUtils.notEmpty(object.getCode(), "data.code is empty.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        DataDict data = BasicDataUtils.toDataDict(object);
        boolean flag = this.client.updateByCode(type, object.getCode(), data);
        return flag;
    }
    
    /**
     * @param dataList
     */
    @Override
    public void batchUpdate(List<T> objectList) {
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<DataDict> dataList = BasicDataUtils.toDataDictList(objectList);
        this.client.batchUpdate(type, dataList);
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
