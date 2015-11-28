/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.context.logger;

import java.util.Map;

import com.tx.core.paged.model.PagedList;

/**
 * 业务日志查询器<br/>
 * 
 * @author brady
 * @version [版本号, 2013-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ServiceLogQuerier<T> {
    
    /**
     * 分页查询业务日志<br/>
     *
     * @param params 参数
     * @param pageIndex 页数
     * @param pageSize 每页数据条数
     *            
     * @return PagedList<T> 翻页对象
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public PagedList<T> queryPagedList(Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
     * 根据 id 查询业务日志
     *
     * @param id
     *            
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月23日]
     * @author rain
     */
    public T find(String id);
}
