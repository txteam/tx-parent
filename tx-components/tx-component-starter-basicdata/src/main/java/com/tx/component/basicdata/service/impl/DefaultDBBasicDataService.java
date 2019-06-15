/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.service.AbstractBasicDataService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.core.support.json.JSONAttributesSupportUtils;

/**
 * 默认的基础数据业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultDBBasicDataService<T extends BasicData>
        extends AbstractBasicDataService<T> {
    
    /** 对应类型 */
    private Class<T> rawType;
    
    /** 数据字典业务层 */
    protected DataDictService dataDictService;
    
    /** 事务处理业务逻辑 */
    protected TransactionTemplate transactionTemplate;
    
    /** <默认构造函数> */
    public DefaultDBBasicDataService() {
        super();
    }
    
    /**
     * 覆写该方法保证类型能够被获取到<br/>
     * @return
     */
    @Override
    public Class<T> getRawType() {
        return this.rawType;
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
        DataDict dd = JSONAttributesSupportUtils
                .toJSONAttributesSupport(DataDict.class, object);
        if (StringUtils.isEmpty(dd.getType())) {
            dd.setType(type);
        }
        
        this.dataDictService.insert(dd);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        boolean flag = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        return dataDictService.deleteById(id);
                    }
                });
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
        AssertUtils.notEmpty(type, "type is null.");
        
        boolean flag = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        return dataDictService.deleteByCode(code, type);
                    }
                });
        return flag;
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean updateByCode(T data) {
        AssertUtils.notNull(data, "data is null.");
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        
        DataDict obj = JSONAttributesSupportUtils
                .toJSONAttributesSupport(DataDict.class, data);
        AssertUtils.notEmpty(obj.getCode(), "data.code is empty.");
        obj.setType(type);
        
        boolean flag = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        return dataDictService.update(obj);
                    }
                });
        return flag;
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean updateById(T data) {
        AssertUtils.notNull(data, "data is null.");
        AssertUtils.notEmpty(data.getId(), "data.id is empty.");
        
        DataDict obj = JSONAttributesSupportUtils
                .toJSONAttributesSupport(DataDict.class, data);
        AssertUtils.notEmpty(obj.getId(), "data.id is empty.");
        
        boolean flag = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        return dataDictService.update(obj);
                    }
                });
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean disableById(String id) {
        boolean flag = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        return dataDictService.disableById(id);
                    }
                });
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public boolean enableById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        boolean flag = this.transactionTemplate
                .execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        return dataDictService.enableById(id);
                    }
                });
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public T findById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        DataDict bd = this.dataDictService.findById(id);
        T resObj = null;
        if (bd != null) {
            resObj = JSONAttributesSupportUtils
                    .fromJSONAttributesSupport(getRawType(), bd);
            //装载关联依赖基础数据
            BasicDataContext.getContext().setup(resObj);
        }
        return resObj;
    }
    
    /**
     * @param code
     * @return
     */
    @Override
    public T findByCode(String code) {
        AssertUtils.notEmpty(code, "code is null.");
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        
        //查询基础数据详情实例
        DataDict bd = this.dataDictService.findByCode(code, type);
        T resObj = null;
        if (bd != null) {
            resObj = JSONAttributesSupportUtils
                    .fromJSONAttributesSupport(getRawType(), bd);
        }
        return resObj;
    }
    
    /**
     * @param valid
     * @param querier
     * @return
     */
    @Override
    public List<T> queryList(Boolean valid, Querier querier) {
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        
        List<DataDict> dataDictList = this.dataDictService.queryList(type,
                valid,
                querier);
        List<T> resList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataDictList)) {
            return resList;
        }
        
        //如果不为空，则开始加载分项属性
        for (DataDict dd : dataDictList) {
            T obj = JSONAttributesSupportUtils
                    .fromJSONAttributesSupport(getRawType(), dd);
            resList.add(obj);
        }
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
    public PagedList<T> queryPagedList(Boolean valid, Querier querier,
            int pageIndex, int pageSize) {
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        
        PagedList<DataDict> dataDictPageList = this.dataDictService
                .queryPagedList(type, valid, querier, pageIndex, pageSize);
        
        PagedList<T> resPagedList = new PagedList<>();
        resPagedList.setCount(dataDictPageList.getCount());
        resPagedList.setPageIndex(dataDictPageList.getPageIndex());
        resPagedList.setPageSize(dataDictPageList.getPageSize());
        resPagedList.setQueryPageSize(dataDictPageList.getQueryPageSize());
        if (CollectionUtils.isEmpty(dataDictPageList.getList())) {
            return resPagedList;
        }
        
        //加载数据
        for (DataDict dd : dataDictPageList.getList()) {
            T obj = JSONAttributesSupportUtils
                    .fromJSONAttributesSupport(getRawType(), dd);
            resPagedList.getList().add(obj);
        }
        return resPagedList;
    }
    
    /**
     * 判断数据是否存在<br/>
     * @param key2valueMap
     * @param excludeId
     * @return
     */
    @Override
    public boolean exists(Querier querier, String excludeId) {
        AssertUtils.notNull(querier, "querier is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        return this.dataDictService.exists(type, querier, excludeId);
    }
    
    /**
     * @param 对dataDictService进行赋值
     */
    public void setDataDictService(DataDictService dataDictService) {
        this.dataDictService = dataDictService;
    }
    
    /**
     * @param 对rawType进行赋值
     */
    public void setRawType(Class<T> rawType) {
        this.rawType = rawType;
    }
    
    /**
     * @param 对transactionTemplate进行赋值
     */
    public void setTransactionTemplate(
            TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
