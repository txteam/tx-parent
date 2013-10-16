/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-14
 * <修改描述:>
 */
package com.tx.component.template.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.tx.component.template.dao.TemplateDataDao;
import com.tx.component.template.model.TemplateColumn;
import com.tx.component.template.model.TemplateTable;
import com.tx.component.template.service.TemplateTableService;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TemplateDataService implements com.tx.component.template.service.TemplateDataService{
    
    private TemplateTableService templateTableService;
    
    private TemplateDataDao templateDataDao;
    
    
    
    /** <默认构造函数> */
    public TemplateDataService(TemplateTableService templateTableService,
            TemplateDataDao templateDataDao) {
        super();
        this.templateTableService = templateTableService;
        this.templateDataDao = templateDataDao;
    }


    public void insert(String templateId,Map<String, Object> rowMap){
        //TemplateTable t = templateTableService.findTemplateTableById(templateId);
        TemplateTable templateTable = null;
        
        //过滤掉非法的字段
        Map<String, Object> params = insertFilteNotExistColumn(rowMap, templateTable);
        
        //验证字段合法性
         
        //具体的数据插入
        //templateDataDao.insert(templateTable,params);
    }

    
     /** 
      *<功能简述>
      *<功能详细描述>
      * @param rowMap
      * @param templateTable [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    private Map<String, Object> insertFilteNotExistColumn(Map<String, Object> rowMap,
            TemplateTable templateTable) {
        //过滤掉非法字段
        Map<String, Object> params = new HashMap<String, Object>();
        Set<String> columnNameSet = templateTable.getColumnNames();
        for(Entry<String, Object> entryTemp : rowMap.entrySet()){
            if(columnNameSet.contains(entryTemp.getKey())){
                params.put(entryTemp.getKey(), entryTemp.getValue());
            }
        }
        return params;
    }
}
