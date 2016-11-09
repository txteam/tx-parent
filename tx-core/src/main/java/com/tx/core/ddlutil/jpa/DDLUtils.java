/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月9日
 * <修改描述:>
 */
package com.tx.core.ddlutil.jpa;

import java.util.ArrayList;
import java.util.List;

import com.tx.core.ddlutil.model.TableColumnDef;
import com.tx.core.ddlutil.model.TableDef;
import com.tx.core.ddlutil.model.TableIndexDef;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * ddlutil模块中工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class DDLUtils {
    
    /**
      * 字段是否需要进行升级<br/>
      * <功能详细描述>
      * @param newCol
      * @param sourceCol
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean isNeedUpdate(TableColumnDef newCol,
            TableColumnDef sourceCol, boolean isIncrementUpdate) {
        AssertUtils.notNull(newCol, "newCol is null.");
        AssertUtils.notNull(sourceCol, "sourceCol is null.");
        AssertUtils.isTrue(newCol.getColumnName()
                .equalsIgnoreCase(sourceCol.getColumnName()),
                "newCol.columnName:{} should equalsIgnoreCase sourceCol.columnName:{}",
                new Object[] { newCol.getColumnName(),
                        sourceCol.getColumnName() });
        
        if (!newCol.getJdbcType().equals(sourceCol.getJdbcType())) {
            return true;
        }
        if (isIncrementUpdate) {
            if (newCol.getSize() > sourceCol.getSize()) {
                return true;
            }
            if (newCol.getScale() > sourceCol.getScale()) {
                return true;
            }
        } else {
            if (newCol.getSize() != sourceCol.getSize()) {
                return true;
            }
            if (newCol.getScale() != sourceCol.getScale()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isNeedUpdate(List<TableColumnDef> newColumns,
            List<TableIndexDef> newIndexes, TableDef sourceTable,
            boolean isIncrementUpdate) {
        AssertUtils.notNull(sourceTable, "sourceTable is null.");
        AssertUtils.notEmpty(newColumns, "sourceCol is null.");
        
        return false;
    }
    
    public static class AlterTableContent{
        
        List<TableColumnDef> needAddColumns = new ArrayList<>();
        
        List<TableColumnDef> needModifyColumns = new ArrayList<>();
        
        List<TableColumnDef> needDeleteColumns = new ArrayList<>();
        
        
    }
    
}
