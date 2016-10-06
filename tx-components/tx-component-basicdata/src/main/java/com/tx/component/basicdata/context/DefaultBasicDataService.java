/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.List;
import java.util.Map;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.DataDict;
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
public class DefaultBasicDataService<T extends BasicData> extends
        AbstractBasicDataService<T> {
    
    /** 对应类型 */
    private Class<T> type;
    
    private DataDictService dataDictService;
    
    /**
     * @param data
     */
    @Override
    public void insert(T object) {
        AssertUtils.notNull(object, "object is null.");
        
        DataDict dd = EntryAbleUtils.toEntryAble(DataDict.class, object);
        
        this.dataDictService.insert(dd);
    }
    
    /**
     * @param id
     * @return
     */
    @Override
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
    public boolean deleteByCode(String basicDataTypeCode, String code) {
        
        return false;
    }
    
    /**
     * @param basicDataTypeCode
     */
    @Override
    public void deleteByBasicDataTypeCode(String basicDataTypeCode) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param id
     * @return
     */
    @Override
    public T findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param code
     * @return
     */
    @Override
    public T findByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<T> queryList(Boolean valid, Map<String, Object> params) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    public boolean updateById(T data) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
