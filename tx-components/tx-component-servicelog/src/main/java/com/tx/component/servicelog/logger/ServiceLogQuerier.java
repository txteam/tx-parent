/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.logger;

import java.util.Date;

import com.tx.core.paged.model.PagedList;

/**
 * 业务日志查询器<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ServiceLogQuerier<T> {
    
    /**
     * 分页查询业务日志<br/> 
     *<功能详细描述>
     * @param minCreateDate
     * @param maxCreateDate
     * @return [参数说明]
     * 
     * @return List<PagedList<T>> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<T> queryPagedList(Date minCreateDate,
            Date maxCreateDate);
}
