/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.builder.CellRowWriterBuilder;
import com.tx.core.support.poi.excel.cellrowwriter.StringMapCellRowWriter;
import com.tx.core.support.poi.excel.cellrowwriter.TypeCellRowWriter;

/**
 * Excel写入工具类<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelWriteUtils {
    
    /**
      * 写excel,通过该方法写入的字段类型均为String
      *<功能详细描述>
      * @param sheet
      * @param startRowIndex
      * @param rowMapList
      * @param keys [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void writeSheet(Sheet sheet, int startRowIndex,
            List<Map<String, String>> rowMapList, String[] keys) {
        AssertUtils.notNull(sheet, "sheet is null.");
        AssertUtils.notEmpty(rowMapList, "rowMapList is null.");
        AssertUtils.notEmpty(keys, "keys is null.");
        
        //生成写入器对信息进行写入,
        @SuppressWarnings("unchecked")
        CellRowWriter<Map<String, String>> stringMapCellRowWriter = CellRowWriterBuilder.build(StringMapCellRowWriter.class,
                new Object[] { keys });
        writeSheet(sheet,
                startRowIndex,
                rowMapList,
                stringMapCellRowWriter,
                (short) -1,
                null);
    }
    
    /**
      * 
      *<功能详细描述>
      * @param sheet
      * @param startRowIndex
      * @param objList
      * @param type [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> void writeSheet(Sheet sheet, int startRowIndex,
            List<T> objList, Class<T> type) {
        AssertUtils.notNull(sheet, "sheet is null.");
        AssertUtils.notEmpty(objList, "objList is null.");
        AssertUtils.notNull(type, "type is null.");
        
        //生成写入器对信息进行写入,
        @SuppressWarnings("unchecked")
        CellRowWriter<T> typeCellRowWriter = CellRowWriterBuilder.build(TypeCellRowWriter.class,
                new Object[] { type });
        writeSheet(sheet,
                startRowIndex,
                objList,
                typeCellRowWriter,
                (short) -1,
                null);
    }
    
    /**
      * 写excel
      *<功能详细描述>
      * @param sheet
      * @param startRowIndex
      * @param objectList
      * @param cellRowWriter
      * @param rowHeight
      * @param cellStyle [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static <T> void writeSheet(Sheet sheet, int startRowIndex,
            List<T> objectList, CellRowWriter<T> cellRowWriter,
            short rowHeight, CellStyle cellStyle) {
        int index = startRowIndex;
        for (T obj : objectList) {
            //创建一行
            Row row = sheet.createRow(index);
            cellRowWriter.write(sheet, row, obj, index, rowHeight, cellStyle);
            index++;
        }
    }
}
