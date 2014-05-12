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

/**
 * 默认为String类型的CellREeader实现
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringValueCellReader implements CellReader<String> {
    
    public static final StringValueCellReader INSTANCE = new StringValueCellReader();
    
    /** <默认构造函数> */
    public StringValueCellReader() {
        super();
    }
    
    /** <默认构造函数> */
    public StringValueCellReader(String defaultDateFormatterPattern) {
        super();
        this.defaultDateFormatterPattern = defaultDateFormatterPattern;
    }
    
    /**
     * 默认的事件格式字符串
     */
    private String defaultDateFormatterPattern = "yyyy-MM-dd HH:mm:ss";
    
    @Override
    public String read(Cell cell, int cellNum) {
        String res = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA:
                res = cell.getCellFormula();
                break;
            
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    res = DateFormatUtils.format(cell.getDateCellValue(),
                            defaultDateFormatterPattern);
                } else {
                    res = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                res = cell.getStringCellValue() != null ? cell.getStringCellValue()
                        .trim()
                        : "";
                break;
            
            case Cell.CELL_TYPE_BOOLEAN:
                res = cell.getBooleanCellValue() ? "true" : "false";
                break;
            default:
                res = "";
        }
        return res;
    }
    
    /**
     * @return 返回 defaultDateFormatterPattern
     */
    public String getDefaultDateFormatterPattern() {
        return defaultDateFormatterPattern;
    }
}
