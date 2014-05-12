/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel写入工具类<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelWriteUtils {
    
    public static <T> void writeSheet(Sheet sheet, int skips,
            List<T> objectList, CellRowMapper<T> rowMapper) {
        
    }
}
