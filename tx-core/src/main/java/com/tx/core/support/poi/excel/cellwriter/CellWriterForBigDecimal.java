/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellwriter;

import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.CellWriter;

/**
 * 写入Cell值
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CellWriterForBigDecimal extends CellWriter<BigDecimal> {
    
    /**
     * @param cell
     * @param value
     * @param cellType
     * @param width
     * @param cellStyle
     * @param rowNum
     * @param cellNum
     */
    @Override
    public void write(Cell cell, Object value, int cellType, int width,
            CellStyle cellStyle, int rowNum, int cellNum) {
        if (value == null) {
            cell.setCellType(HSSFCell.LAST_COLUMN_NUMBER);
            cell.setCellValue("");
            return;
        }
        
        AssertUtils.isInstanceOf(BigDecimal.class,
                value,
                "value is not BigDecimal");
        
        BigDecimal cellValue = (BigDecimal) value;
        if (cellType < 0 || HSSFCell.CELL_TYPE_NUMERIC == cellType) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(cellValue.toString());
        } else {
            cell.setCellType(cellType);
            cell.setCellValue(cellValue.toString());
        }
    }
}
