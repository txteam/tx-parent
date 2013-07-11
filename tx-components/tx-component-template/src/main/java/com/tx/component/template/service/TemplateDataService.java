/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-7-9
 * <修改描述:>
 */
package com.tx.component.template.service;

import java.util.List;
import java.util.Map;


 /**
  * 模板数据业务层
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-7-9]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface TemplateDataService {
    
    /**
      *<功能简述>
      *<功能详细描述>
      * @param templateId
      * @param rowMap [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public void insertTemplateData(String templateId,Map<String, Object> rowMap);
    
    public void batchInsertTemplateData(String templateId,List<Map<String, Object>> rowMapList);
    
    public void deleteTemplateDataByRowDataId(String templateId,String rowDataId);
    
    public void deleteTemplateDataByRowMap(String templateId,Map<String, Object> rowMap);
    
    public void findTemplateDataByRowDataId(String templateId,String rowDataId);
    
    public void findTemplateDataByRowMap(String templateId,Map<String, Object> rowMap);
    
    public void queryTemplateDataByRowMap(String templateId,Map<String, Object> rowMap);
    
    
}
