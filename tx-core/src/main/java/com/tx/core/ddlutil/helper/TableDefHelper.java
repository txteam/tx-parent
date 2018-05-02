/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月9日
 * <修改描述:>
 */
package com.tx.core.ddlutil.helper;

import java.util.ArrayList;
import java.util.List;

import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.util.order.OrderedSupportComparator;

/**
 * ddlutil模块中工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class TableDefHelper {
    
    /**
     * 解析表主键字段名称<br/>
     * <功能详细描述>
     * @param columns
     * @param indexes
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String parsePrimaryKeyColumnNames(
            List<? extends TableColumnDef> columns) {
        //根据索引获取主键名
        StringBuffer pkColumnNames = new StringBuffer();
        List<TableColumnDef> pkColumns = new ArrayList<>();
        for (TableColumnDef columnTemp : columns) {
            if (columnTemp.isPrimaryKey()) {
                pkColumns.add(columnTemp);
            }
        }
        OrderedSupportComparator.sort(pkColumns);
        
        int index2 = 0;
        for (TableColumnDef columnTemp : pkColumns) {
            pkColumnNames.append(columnTemp.getColumnName());
            if ((++index2) < pkColumns.size()) {
                pkColumnNames.append(",");
            }
        }
        
        String columnNames = pkColumnNames.toString();
        return columnNames;
    }
}
