/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-14
 * <修改描述:>
 */
package com.tx.component.template.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlBuilder;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 模板表持久化帮助类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TemplateTablePersisterHelper {
    
    private static Map<TemplateTable, TemplateTablePersisterHelper> mapping = new HashMap<TemplateTable, TemplateTablePersisterHelper>();
    
    public static TemplateTablePersisterHelper newInstance(
            TemplateTable templateTable) {
        AssertUtils.notNull(templateTable,"templateTable is null");
        synchronized (templateTable) {
            if(mapping.containsKey(templateTable)){
                return mapping.get(templateTable);
            }else{
                TemplateTablePersisterHelper templateTablePersisterHelper = new TemplateTablePersisterHelper(templateTable);
                mapping.put(templateTable, templateTablePersisterHelper);
                return templateTablePersisterHelper;
            }
        }
    }
    
    private TemplateTable templateTable;
    
    private String insertSql;

    /** <默认构造函数> */
    private TemplateTablePersisterHelper(TemplateTable templateTable) {
        super();
        AssertUtils.notNull(templateTable,"templateTable is null");
        
        this.templateTable = templateTable;
        
        buildInsertSql();
        
    }

    
     /** 
      *<功能简述>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    private void buildInsertSql() {
        SqlBuilder.BEGIN();
        SqlBuilder.INSERT_INTO(this.templateTable.getTableName());
        for(String columnNameTemp : this.templateTable.getColumnNames()){
            SqlBuilder.VALUES(columnNameTemp, "?");
        }
        this.insertSql = SqlBuilder.SQL();
    }


    /**
     * @return 返回 insertSql
     */
    public String getInsertSql() {
        return insertSql;
    }
    
    
}
