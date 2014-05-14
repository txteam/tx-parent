/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel写入工具类<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelWriteUtils {
    
    public static <T> void writeSheet(HSSFSheet sheet, int startRowIndex,
            List<T> objectList, CellRowWriter<T> cellRowWriter, short rowHeight,
            CellStyle cellStyle) {
        
        int index = startRowIndex;
        for(T obj : objectList){
            //创建一行
            HSSFRow row = sheet.createRow(index);  
            //行高
            row.setHeightInPoints(rowHeight);
            //创建一个单元格 
            HSSFCell cell = row.createCell(0); 
            
            index++;
        }
    }
}
