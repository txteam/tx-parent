/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;
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
        extends DefaultRemoteBasicDataService<T>
        implements TreeAbleBasicDataService<T> {
    
    /** <默认构造函数> */
    public DefaultRemoteTreeAbleBasicDataService() {
        super();
    }
    
    /** <默认构造函数> */
    public DefaultRemoteTreeAbleBasicDataService(Class<T> rawType,
            BasicDataAPIClient client) {
        super(rawType, client);
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
    public PagedList<T> queryPagedListByParentId(String parentId, Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("parentId", parentId);
        
        PagedList<T> resPagedList = queryPagedList(valid,
                params,
                pageIndex,
                pageSize);
        return resPagedList;
    }
}
