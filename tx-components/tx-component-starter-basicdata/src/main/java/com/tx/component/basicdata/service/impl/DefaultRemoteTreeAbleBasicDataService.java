/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月7日
 * <修改描述:>
 */
package com.tx.component.basicdata.service.impl;

import java.util.List;
import java.util.Map;

import com.tx.component.basicdata.client.BasicDataAPIClient;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.component.basicdata.service.TreeAbleBasicDataService;
import com.tx.component.basicdata.util.BasicDataUtils;
import com.tx.core.exceptions.util.AssertUtils;

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
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        
        AssertUtils.isTrue(
                TreeAbleBasicData.class.isAssignableFrom(this.rawType),
                "rawType:{} is not assign from TreeAbleBasicData.class.",
                new Object[] { this.rawType });
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
        AssertUtils.notEmpty(parentId, "parentId is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<DataDict> dataList = this.client.queryChildrenByParentId(type,
                parentId,
                valid,
                params);
        List<T> objectList = BasicDataUtils.fromDataDictList(dataList,
                getRawType());
        return objectList;
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
        AssertUtils.notEmpty(parentId, "parentId is null.");
        
        String type = type();
        AssertUtils.notEmpty(type, "type is empty.");
        
        List<DataDict> dataList = this.client.queryDescendantsByParentId(type,
                parentId,
                valid,
                params);
        List<T> objectList = BasicDataUtils.fromDataDictList(dataList,
                getRawType());
        return objectList;
    }
}
