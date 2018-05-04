/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-11-20
 * <修改描述:>
 */
package com.tx.core.support.jsoup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Element;

import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * jsoup模板类处理方法<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-11-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultJsoupSupport implements JsoupSupport {
    
    /** <默认构造函数> */
    DefaultJsoupSupport() {
        super();
    }
    
    /**
     * @param element
     * @param rowMapper
     * @return
     */
    @Override
    public <T> T evaluateForObject(Element element, RowMapper<T> rowMapper) {
        AssertUtils.notNull(element, "element is null");
        AssertUtils.notNull(rowMapper, "rowMapper is null");
        
        T resObj = rowMapper.mapRow(element, 0);
        return resObj;
    }
    
    /**
     * @param elements
     * @param rowMapper
     * @return
     */
    @Override
    public <T> List<T> evaluateForList(List<Element> elements,
            RowMapper<T> rowMapper) {
        AssertUtils.notNull(rowMapper, "rowMapper is null");
        
        List<T> resList = new ArrayList<T>(TxConstants.INITIAL_CONLLECTION_SIZE);
        if (CollectionUtils.isEmpty(elements)) {
            return resList;
        }
        for (int i = 0; i < elements.size(); i++) {
            resList.add(rowMapper.mapRow(elements.get(i), i));
        }
        
        return resList;
    }
}
