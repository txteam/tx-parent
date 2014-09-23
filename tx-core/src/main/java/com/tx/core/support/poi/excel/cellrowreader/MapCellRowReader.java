/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellrowreader;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.CellReader;
import com.tx.core.support.poi.excel.CellRowReader;
import com.tx.core.support.poi.excel.builder.CellReaderBuilder;
import com.tx.core.support.poi.excel.cellreader.CellReader4StringValue;

/**
 * MapCellReader行读取器 for Map<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapCellRowReader implements CellRowReader<Map<String, String>> {
    
    private String[] keys = null;
    
    private CellReader<String> cellReader;
    
    /** <默认构造函数> */
    public MapCellRowReader(String[] keys) {
        super();
        AssertUtils.notEmpty(keys,"keys is empty.");
        
        this.keys = keys;
        this.cellReader = CellReaderBuilder.build(CellReader4StringValue.class, String.class);
    }
    
    /** <默认构造函数> */
    public MapCellRowReader(String[] keys, CellReader<String> cellReader) {
        super();
        AssertUtils.notEmpty(keys,"keys is empty.");
        AssertUtils.notNull(cellReader,"cellReader is null.");
        
        this.keys = keys;
        this.cellReader = cellReader;
    }
    
    /**
     * @param row
     * @param rowNum
     * @param skip
     * @return
     */
    @Override
    public Map<String, String> read(Row row, int rowNum, boolean ignoreError,
            boolean ignoreBlank, boolean ignoreTypeUnmatch) {
        Map<String, String> res = new HashMap<String, String>();
        int cellsLength = row.getPhysicalNumberOfCells();
        for (int cellNum = 0; cellNum < keys.length; cellNum++) {
            if (cellNum >= cellsLength) {
                break;//直接跳出本次循环
            }
            Cell cell = row.getCell(cellNum);
            if (cell == null) {
                res.put(keys[cellNum], "");
            } else {
                res.put(keys[cellNum], cellReader.read(cell,
                        rowNum,
                        cellNum,
                        keys[cellNum],
                        ignoreError,
                        ignoreBlank,
                        ignoreTypeUnmatch));
            }
        }
        
        return res;
    }
}
