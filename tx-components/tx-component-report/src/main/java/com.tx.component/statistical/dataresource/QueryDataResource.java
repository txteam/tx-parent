/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月6日
 * <修改描述:>
 */
package com.tx.component.statistical.dataresource;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * 查询数据集资源<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface QueryDataResource extends DataResource {
    
    /**
      * 获取对应的数据源实例<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public DataSource dataSource();
    
    public List<Map<String, Object>> query(Map<String, String> condition);
}
