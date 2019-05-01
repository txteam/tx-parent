/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.model.BasicData;
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
public abstract class AbstractBasicDataService<T extends BasicData>
        extends ParameterizedTypeReference<T>
        implements BasicDataService<T> {
    
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
}
