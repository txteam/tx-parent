/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月6日
 * <修改描述:>
 */
package com.tx.component.statistical.context;

import java.util.Map;

import com.tx.component.statistical.dataresource.QueryDataResource;

/**
 * 统计资源接口<br/>
 *     描述统计的资源的基本属性<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface StatisticalResource {
    
    public Map<String, QueryDataResource> dataResourceMap();
}
