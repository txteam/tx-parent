/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellreader;

import org.apache.commons.lang.time.DateFormatUtils;
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
public class CellReader4StringValue implements CellReader<String> {
    
    public static final CellReader4StringValue INSTANCE = new CellReader4StringValue();
    
    /** <默认构造函数> */
    public CellReader4StringValue() {
        super();
    }
    
    /** <默认构造函数> */
    public CellReader4StringValue(String dateFormatterPattern) {
        super();
        this.dateFormatterPattern = dateFormatterPattern;
    }
    
    /**
     * 默认的事件格式字符串
     */
    private String dateFormatterPattern = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 当设置不忽略类型不匹配时，如果对应单元格与指定类型不能自动转型时抛出异常<br/>
     * <功能详细描述>
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
    
    /**
     * cell的读取逻辑
     * @param cell
     * @param cellNum
     * @return
     */
    @Override
    public String read(Cell cell, int rowNum, int cellNum, String key,
            boolean ignoreError, boolean ignoreBlank, boolean ignoreTypeUnmatch) {
        String resString = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_ERROR:
                if (!ignoreError) {
                    throw new ExcelReadException(
                            "cell rowNum:{} cellNum:{} key:{} cellType is error.",
                            new Object[] { rowNum, cellNum, key });
                }
                resString = null;
                break;
            case Cell.CELL_TYPE_BLANK:
                if (!ignoreBlank) {
                    throw new ExcelReadException(
                            "cell rowNum:{} cellNum:{} key:{} cellType is blank.",
                            new Object[] { rowNum, cellNum, key });
                }
                resString = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                //如果为计算公式，将计算公司进行提取
                resString = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                //如果Cell类型一定要匹配
                throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                        "CELL_TYPE_NUMERIC",
                        rowNum,
                        cellNum,
                        key);
                //如果Cell类型不需要匹配
                //如果为数字，将计算公司进行提取
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //如果为时间的处理逻辑
                    resString = DateFormatUtils.format(cell.getDateCellValue(),
                            dateFormatterPattern);
                } else {
                    //如果为非时间
                    resString = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                resString = cell.getStringCellValue() != null ? cell.getStringCellValue()
                        .trim()
                        : "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                //如果Cell类型一定要匹配
                throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
                        "CELL_TYPE_BOOLEAN",
                        rowNum,
                        cellNum,
                        key);
                //如果Cell类型不需要匹配
                resString = cell.getBooleanCellValue() ? "true" : "false";
                break;
            default:
                resString = null;
        }
        return resString;
    }
    
    /**
     * @param cellType
     * @param cell
     * @param rowNum
     * @param cellNum
     * @param key
     * @return
     */
    @Override
    public String read(int cellType, Cell cell, int rowNum, int cellNum,
            String key, boolean ignoreError, boolean ignoreBlank,
            boolean ignoreTypeUnmatch) {
        String resString = null;
        if (cellType == Cell.CELL_TYPE_STRING) {
            resString = cell.getStringCellValue();
        } else if(cellType == Cell.CELL_TYPE_NUMERIC){
            resString = String.valueOf(cell.getNumericCellValue());
        }else {
            resString = read(cell,
                    rowNum,
                    cellNum,
                    key,
                    ignoreError,
                    ignoreBlank,
                    ignoreTypeUnmatch);
        }
        return resString;
    }
    
    /**
     * @return 返回 defaultDateFormatterPattern
     */
    public String getDateFormatterPattern() {
        return dateFormatterPattern;
    }
}
