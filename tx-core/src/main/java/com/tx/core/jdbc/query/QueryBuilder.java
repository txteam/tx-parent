/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-2
 * <修改描述:>
 */
package com.tx.core.jdbc.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建查询条件<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryBuilder {
    
    public List<QueryCondition> queryCondtionList = new ArrayList<QueryCondition>();
    
    /**
      * 添加查询条件<br/>
      *<功能详细描述>
      * @param queryCondition [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void addCondition(QueryCondition queryCondition) {
        queryCondtionList.add(queryCondition);
    }
}
