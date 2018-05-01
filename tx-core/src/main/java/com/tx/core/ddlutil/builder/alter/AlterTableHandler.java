/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.ddlutil.model.TableDef;

/**
 * 编辑表辅助类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AlterTableHandler {
    
    /** 原表定义 */
    private TableDef tableDef;
    
    /**  */
    private Map<String, AlterTableColumn> columnName2AlterColumnMap = new HashMap<>();
    
}
