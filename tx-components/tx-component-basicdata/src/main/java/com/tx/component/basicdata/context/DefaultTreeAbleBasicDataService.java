/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.support.entrysupport.helper.EntryAbleUtils;

/**
 * 默认的基础数据业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultTreeAbleBasicDataService<T extends TreeAbleBasicData<T>>
        extends AbstractTreeAbleBasicDataService<T> {
    
    /** 对应类型 */
    private Class<T> type;
    
    /** 数据字典业务层 */
    @Resource(name = "basicdata.dataDictService")
    protected DataDictService dataDictService;
    
    /** <默认构造函数> */
    public DefaultTreeAbleBasicDataService() {
        super();
    }
    
    /** <默认构造函数> */
    public DefaultTreeAbleBasicDataService(Class<T> type) {
        super();
        this.type = type;
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
    public String tableName() {
        return "bd_basic_data";
    }
    
    /**
     * @param data
     */
    @Override
    @Transactional
    public void insert(T object) {
        AssertUtils.notNull(object, "object is null.");
        
        String basicDataTypeCode = code();
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is null.");
        
        //构建字典对象
        DataDict dd = EntryAbleUtils.toEntryAble(DataDict.class, object);
        if (StringUtils.isEmpty(dd.getBasicDataTypeCode())) {
            dd.setBasicDataTypeCode(basicDataTypeCode);
        }
        
        this.dataDictService.insert(dd);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is null.");
        
        boolean flag = this.dataDictService.deleteById(id);
        return flag;
    }
    
    /**
     * @param basicDataTypeCode
     * @param code
     * @return
     */
    @Override
    @Transactional
    public boolean deleteByCode(String code) {
        String basicDataTypeCode = code();
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is null.");
        AssertUtils.notEmpty(code, "code is null.");
        
        BasicData bd = this.dataDictService.findEntityByCode(basicDataTypeCode,
                code);
        boolean flag = false;
        if (bd != null) {
            flag = this.dataDictService.deleteById(bd.getId());
        }
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
            resObj = EntryAbleUtils.fromEntryAble(this.type, bd);
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
        String basicDataTypeCode = code();
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is null.");
        
        //查询基础数据详情实例
        DataDict bd = this.dataDictService.findByCode(basicDataTypeCode, code);
        
        T resObj = null;
        if (bd != null) {
            resObj = EntryAbleUtils.fromEntryAble(this.type, bd);
        }
        return resObj;
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
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("parentId", parentId);
        
        List<T> resList = queryList(valid, params);
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
    public PagedList<T> queryPagedListByParentId(String parentId,
            Boolean valid, Map<String, Object> params, int pageIndex,
            int pageSize) {
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("parentId", parentId);
        
        PagedList<T> resPagedList = queryPagedList(valid,
                params,
                pageIndex,
                pageSize);
        return resPagedList;
    }
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Boolean valid, Map<String, Object> params) {
        String basicDataTypeCode = code();
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is null.");
        
        List<DataDict> dataDictList = this.dataDictService.queryList(basicDataTypeCode,
                valid,
                params);
        List<T> resList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataDictList)) {
            return resList;
        }
        
        //如果不为空，则开始加载分项属性
        this.dataDictService.setupEntryList(dataDictList);
        for (DataDict dd : dataDictList) {
            T obj = EntryAbleUtils.fromEntryAble(this.type, dd);
            resList.add(obj);
        }
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
        String basicDataTypeCode = code();
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is null.");
        
        PagedList<DataDict> dataDictPageList = this.dataDictService.queryPagedList(basicDataTypeCode,
                valid,
                params,
                pageIndex,
                pageSize);
        
        PagedList<T> resPagedList = new PagedList<>();
        resPagedList.setCount(dataDictPageList.getCount());
        resPagedList.setPageIndex(dataDictPageList.getPageIndex());
        resPagedList.setPageSize(dataDictPageList.getPageSize());
        resPagedList.setQueryPageSize(dataDictPageList.getQueryPageSize());
        if (CollectionUtils.isEmpty(dataDictPageList.getList())) {
            return resPagedList;
        }
        
        //加载数据
        this.dataDictService.setupEntryList(dataDictPageList);
        for (DataDict dd : dataDictPageList.getList()) {
            T obj = EntryAbleUtils.fromEntryAble(this.type, dd);
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
    public boolean isExist(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is null.");
        
        String basicDataTypeCode = code();
        AssertUtils.notEmpty(basicDataTypeCode, "basicDataTypeCode is null.");
        this.dataDictService.isExist(basicDataTypeCode, key2valueMap, excludeId);
        
        return false;
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    @Transactional
    public boolean updateById(T data) {
        AssertUtils.notNull(data, "data is null.");
        
        DataDict obj = EntryAbleUtils.toEntryAble(DataDict.class, data);
        AssertUtils.notEmpty(obj.getId(), "data.id is empty.");
        
        boolean flag = this.dataDictService.updateById(obj);
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean disableById(String id) {
        boolean flag = this.dataDictService.disableById(id);
        return flag;
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean enableById(String id) {
        boolean flag = this.dataDictService.enableById(id);
        return flag;
    }
}
