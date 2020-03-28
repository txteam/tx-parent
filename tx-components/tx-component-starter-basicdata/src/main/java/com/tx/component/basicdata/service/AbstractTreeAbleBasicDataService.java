/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.model.TreeAbleBasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.typereference.ParameterizedTypeReference;

/**
 * 基础数据业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractTreeAbleBasicDataService<T extends TreeAbleBasicData<T>>
        extends ParameterizedTypeReference<T>
        implements TreeAbleBasicDataService<T> {
    
    /**
     * @param dataList
     */
    @Override
    @Transactional
    public void batchInsert(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        for (T dataTemp : dataList) {
            insert(dataTemp);
        }
    }
    
    /**
     * @param dataList
     */
    @Override
    @Transactional
    public void batchUpdate(List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        for (T dataTemp : dataList) {
            updateById(dataTemp);
        }
    }
    
    /**
     * @param data
     * @return
     */
    @Override
    @Transactional
    public boolean updateByCode(T data) {
        if (!StringUtils.isEmpty(data.getId())) {
            updateById(data);
        }
        AssertUtils.notEmpty(data.getCode(), "code is empty.");
        T dbData = findByCode(data.getCode());
        if (dbData == null) {
            return false;
        }
        
        data.setId(dbData.getId());
        boolean flag = updateById(data);
        return flag;
    }
}
