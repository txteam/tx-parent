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

import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;
import com.tx.core.exceptions.util.AssertUtils;
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
public class DefaultDBTreeAbleBasicDataService<T extends TreeAbleBasicData<T>>
        extends DefaultDBBasicDataService<T>
        implements TreeAbleBasicDataService<T> {
    
    /** <默认构造函数> */
    public DefaultDBTreeAbleBasicDataService() {
        super();
    }
    
    /**
     * @param parentId
     * @param querier
     * @param querier
     * @return
     */
    @Override
    public List<T> queryChildrenByParentId(String parentId, Boolean valid,
            Querier querier) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        
        List<DataDict> dataDictList = this.dataDictService
                .queryChildrenByParentId(type, parentId, valid, querier);
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
     * @param parentId
     * @param valid
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<T> queryDescendantsByParentId(String parentId, Boolean valid,
            Querier querier) {
        AssertUtils.notEmpty(parentId, "parentId is empty.");
        String type = type();
        AssertUtils.notEmpty(type, "type is null.");
        
        List<DataDict> dataDictList = this.dataDictService
                .queryDescendantsByParentId(type, parentId, valid, querier);
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
}
