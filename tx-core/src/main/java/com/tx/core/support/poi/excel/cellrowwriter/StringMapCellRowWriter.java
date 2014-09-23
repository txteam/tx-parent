/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellrowwriter;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.tx.core.support.poi.excel.CellRowWriter;
import com.tx.core.support.poi.excel.CellWriter;
import com.tx.core.support.poi.excel.builder.CellWriterBuilder;
import com.tx.core.support.poi.excel.cellwriter.CellWriterForString;

/**
 * 行写入器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringMapCellRowWriter implements
        CellRowWriter<Map<String, String>> {
    
    //由于map实际上是无序的需要指定写入的key顺序
    private String[] keys;
    
    private CellWriter<String> cellWriter;
    
    /** <默认构造函数> */
    public StringMapCellRowWriter(String[] keys) {
        super();
        this.keys = keys;
        this.cellWriter = (CellWriter<String>) CellWriterBuilder.build(CellWriterForString.class,
                String.class);
    }
    
    /** <默认构造函数> */
    public StringMapCellRowWriter(String[] keys, CellWriter<String> cellWriter) {
        super();
        this.keys = keys;
        this.cellWriter = cellWriter;
    }
    
    /**
     * @param row
     * @param obj
     * @param rowNum
     * @param rowHeight
     * @param cellStyle
     */
    @Override
    public void write(Sheet sheet, Row row, Map<String, String> obj,
            int rowNum, int rowHeight, CellStyle cellStyle) {
        //设置行高
        row.setHeightInPoints(rowHeight);
        
        for (int i = 0; i < keys.length; i++) {
            Cell cell = row.createCell(i);
            String value = obj.get(keys[i]);
            
            this.cellWriter.write(cell,
                    value,
                    Cell.CELL_TYPE_STRING,
                    -1,
                    cellStyle,
                    rowNum,
                    i);
        }
    }
}
