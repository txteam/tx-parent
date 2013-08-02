/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-7-31
 * <修改描述:>
 */
package com.tx.component.template.dao;

import java.util.Map;


 /**
  * 模板数据持久层<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-7-31]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */

public interface TemplateDataDao {
    
    /**
      * 动态插入模板数据<br/>
      *<功能详细描述>
      * @param sql
      * @param params [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insertTemplateData(String sql,Map<String, Object> params);
    
    public int deleteTemplateDataById(String tableName,String id);
}
