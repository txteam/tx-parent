/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellreader;

import java.math.BigDecimal;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.tx.core.support.poi.excel.CellReader;
import com.tx.core.support.poi.excel.exception.ExcelReadException;

/**
 * 默认为String类型的CellREeader实现
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CellReader4BigDecimal implements CellReader<BigDecimal> {
    
    /** 实例 */
    public static final CellReader4BigDecimal INSTANCE = new CellReader4BigDecimal();
    
    /** <默认构造函数> */
    public CellReader4BigDecimal() {
        super();
    }
    
    /** <默认构造函数> */
    public CellReader4BigDecimal(String defaultDateFormatterPattern) {
        super();
    }
    
    /**
     * 当设置不忽略类型不匹配时，如果对应单元格与指定类型不能自动转型时抛出异常<br/>
     *<功能详细描述>
     * @param ignoreTypeUnmatch [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private void throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(
            boolean ignoreTypeUnmatch, String cellType, int rowNum,
            int cellNum, String key) {
        if (!ignoreTypeUnmatch) {
            throw new ExcelReadException(
                    "cell rowNum:{} cellNum:{} key:{} type is:{} unable change to BigDecimal.",
                    new Object[] { rowNum, cellNum, key });
        }
    }
    
    @Override
    public BigDecimal read(Cell cell, int rowNum, int cellNum, String key,
            boolean ignoreError, boolean ignoreBlank, boolean ignoreTypeUnmatch) {
        BigDecimal resBigDecimal = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_ERROR:
                if (!ignoreError) {
                    throw new ExcelReadException(
                            "cell rowNum:{} cellNum:{} key:{} cellType is error.",
                            new Object[] { rowNum, cellNum, key });
                }
                resBigDecimal = null;
                break;
            case Cell.CELL_TYPE_BLANK:
                if (!ignoreBlank) {
                    throw new ExcelReadException(
                            "cell rowNum:{} cellNum:{} key:{} cellType is blank.",
                            new Object[] { rowNum, cellNum, key });
                }
                resBigDecimal = null;
                break;
            case Cell.CELL_TYPE_FORMULA:
                //如果Cell类型一定要匹配
                throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                        "CELL_TYPE_FORMULA",
                        rowNum,
                        cellNum,
                        key);
                
                //如果Cell类型不需要匹配
                resBigDecimal = null;
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果Cell类型一定要匹配
                    throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                            "CELL_TYPE_NUMERIC_DATE",
                            rowNum,
                            cellNum,
                            key);
                    
                    //如果Cell类型不需要匹配
                    resBigDecimal = null;
                } else {
                    resBigDecimal = new BigDecimal(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                //如果Cell类型一定要匹配
                throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                        "CELL_TYPE_STRING",
                        rowNum,
                        cellNum,
                        key);
                
                String context = cell.getStringCellValue() != null ? cell.getStringCellValue()
                        .trim()
                        : "";
                //如果Cell类型不需要匹配
                if (NumberUtils.isNumber(context)) {
                    resBigDecimal = new BigDecimal(context);
                } else {
                    resBigDecimal = null;
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                //如果Cell类型一定要匹配
                throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                        "CELL_TYPE_BOOLEAN",
                        rowNum,
                        cellNum,
                        key);
                
                //如果不忽略
                resBigDecimal = null;
                break;
            default:
                resBigDecimal = null;
        }
        return resBigDecimal;
    }
    
    /**
     * @param cellType
     * @param cell
     * @param rowNum
     * @param cellNum
     * @param key
     * @param ignoreError
     * @param ignoreBlank
     * @return
     */
    @Override
    public BigDecimal read(int cellType, Cell cell, int rowNum, int cellNum,
            String key, boolean ignoreError, boolean ignoreBlank,
            boolean ignoreTypeUnmatch) {
        BigDecimal resBigDecimal = null;
        if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
            resBigDecimal = new BigDecimal(cell.getNumericCellValue());
        } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
            String stringValue = cell.getStringCellValue();
            if (NumberUtils.isNumber(stringValue)) {
                resBigDecimal = new BigDecimal(cell.getNumericCellValue());
            } else {
                throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                        "CELL_TYPE_STRING",
                        rowNum,
                        cellNum,
                        key);
            }
        } else {
            resBigDecimal = read(cell,
                    rowNum,
                    cellNum,
                    key,
                    ignoreError,
                    ignoreBlank,
                    ignoreTypeUnmatch);
        }
        return resBigDecimal;
    }
}
