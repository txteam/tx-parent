/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;

/**
 * 默认的基础数据业务层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultDBTreeAbleBasicDataService<T extends TreeAbleBasicData<T>>
        extends DefaultDBBasicDataService<T>
        implements TreeAbleBasicDataService<T> {
    
    /** <默认构造函数> */
    public DefaultDBTreeAbleBasicDataService() {
        super();
    }
    
    /**
     * @param parentId
     * @param valid
     * @param params
     * @return
     */
    @Override
    public List<T> queryChildrenByParentId(String parentId, Boolean valid,
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
    public List<T> queryDescendantsByParentId(String parentId, Boolean valid,
            Map<String, Object> params) {
        Set<String> ids = new HashSet<>();
        Set<String> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<T> resList = doNestedQueryList(valid, ids, parentIds, params);
        return resList;
    }
    
    /**
     * 查询嵌套列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param params
     * @return [参数说明]
     * 
     * @return List<ConfigPropertyItem> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<T> doNestedQueryList(Boolean valid, Set<String> ids,
            Set<String> parentIds, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<T>();
        }
        
        //ids避免数据出错时导致无限循环
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(params);
        queryParams.put("parentIds", parentIds);
        List<T> resList = queryList(valid, params);
        
        Set<String> newParentIds = new HashSet<>();
        for (T bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryList(valid, ids, newParentIds, params));
        return resList;
    }
}
