/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.component.basicdata.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

import com.tx.component.basicdata.exception.UnModifyAbleException;
import com.tx.core.paged.model.PagedList;

/**
 * 枚举类型基础数据执行器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EnumBasicDataExecutor<T extends Enum<T>> extends
        BaseBasicDataExecutor<T> {
    
    /** <默认构造函数> */
    public EnumBasicDataExecutor(Class<T> type) {
        super(type);
    }
    
    /**
     * @param pk
     * @return
     */
    @Override
    protected T doGet(String pk) {
        T res = EnumUtils.getEnum(getType(), pk);
        return res;
    }
    
    /**
     * @param obj
     * @return
     */
    @Override
    protected T doFind(T obj) {
        return obj;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected List<T> doQuery(Map<String, Object> params) {
        List<T> resList = EnumUtils.getEnumList(getType());
        return resList;
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    protected PagedList<T> doQueryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        List<T> resList = EnumUtils.getEnumList(getType());
        int offset = pageSize * (pageIndex - 1);
        int limit = pageSize * pageIndex;
        limit = limit < resList.size() ? limit : resList.size();
        PagedList<T> res = new PagedList<T>();
        res.setCount(resList.size());
        res.setPageIndex(pageIndex);
        res.setPageSize(pageSize);
        
        List<T> list = new ArrayList<T>();
        for (int i = offset; i < limit; i++) {
            list.add(resList.get(i));
        }
        res.setList(list);
        
        return res;
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doCount(Map<String, Object> params) {
        List<T> resList = EnumUtils.getEnumList(getType());
        return resList.size();
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doUpdate(Map<String, Object> params) {
        throw new UnModifyAbleException(getType());
    }
    
    /**
     * @param obj
     */
    @Override
    protected void doInsert(T obj) {
        throw new UnModifyAbleException(getType());
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    protected int doDelete(Map<String, Object> params) {
        throw new UnModifyAbleException(getType());
    }
}
