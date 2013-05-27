/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-24
 * <修改描述:>
 */
package com.tx.core.support.poi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.CellReader;
import com.tx.core.support.poi.CellRowMapper;

/**
 * excel读取生成工具
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelReadUtils {
    
    /**
     * 默认的事件格式字符串
     */
    private static String defaultDateFormatterPattern = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 默认的行读取器
     */
    private static CellReader<String> defaultStringValueCellReader = new CellReader<String>() {
        
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
                    res = cell.getStringCellValue();
                    break;
                
                case Cell.CELL_TYPE_BOOLEAN:
                    res = cell.getBooleanCellValue() ? "true" : "false";
                    break;
                default:
                    res = "";
            }
            return res;
        }
    };
    
    
    
    /**
      * 读取excel数据并写入map中<br/>
      *<功能详细描述>
      * @param sheet
      * @param keys
      * @return [参数说明]
      * 
      * @return List<Map<String,String>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<Map<String, String>> readSheet(final Sheet sheet,
            final String[] keys){
        List<Map<String, String>> resList = readSheet(sheet, keys, 0);
        
        return resList;
    }
    
    /**
      * 读取excel数据并写入map中<br/>
      * <功能详细描述>
      * @param sheet
      * @param keys
      * @return [参数说明]
      * 
      * @return List<Map<String,String>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<Map<String, String>> readSheet(final Sheet sheet,
            final String[] keys, int skips) {
        List<Map<String, String>> resList = readSheet(sheet, keys, skips, null);
        
        return resList;
    }
    
    /**
      * 读取excel数据并写入一个Map中<br/>
      *<功能详细描述>
      * @param sheet
      * @param keys
      * @param cellReader
      * @return [参数说明]
      * 
      * @return List<Map<String,String>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<Map<String, String>> readSheet(final Sheet sheet,
            final String[] keys, CellReader<String> cellReader) {
        List<Map<String, String>> resList = readSheet(sheet,
                keys,
                0,
                cellReader);
        
        return resList;
    }
    
    /**
      * 读取excel数据并写入一个Map中<br/>
      * <功能详细描述>
      * @param sheet
      * @param keys
      * @param cellReader
      * @return [参数说明]
      * 
      * @return List<Map<String,String>> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<Map<String, String>> readSheet(final Sheet sheet,
            final String[] keys, int skips, CellReader<String> cellReader) {
        //行读取器
        final CellReader<String> finalCellReader = cellReader == null ? defaultStringValueCellReader
                : cellReader;
        
        CellRowMapper<Map<String, String>> cellRowMapper = new CellRowMapper<Map<String, String>>() {
            /**
             * @param row
             * @param rowNum
             * @return
             */
            @Override
            public Map<String, String> mapRow(Row row, int rowNum) {
                Map<String, String> res = new HashMap<String, String>();
                int cellsLength = row.getPhysicalNumberOfCells();
                for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
                    if (keyIndex >= cellsLength) {
                        break;//直接跳出本次循环
                    }
                    Cell cell = row.getCell(keyIndex);
                    res.put(keys[keyIndex],
                            finalCellReader.read(cell, keyIndex));
                }
                
                return res;
            }
        };
        List<Map<String, String>> resList = ExcelReadUtils.<Map<String, String>> readSheet(sheet,
                skips,
                cellRowMapper);
        
        return resList;
    }
    
    /**
      * 读取sheet中数据,生成对象集合<br/>
      *<功能详细描述>
      * @param sheet
      * @param rowMapper
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> readSheet(Sheet sheet, CellRowMapper<T> rowMapper) {
        List<T> resList = ExcelReadUtils.<T> readSheet(sheet, 0, rowMapper);
        
        return resList;
    }
    
    /**
      * 读取sheet中数据,生成对象集合<br/>
      *<功能详细描述>
      * @param sheet
      * @param skips 跳过多少行
      * @param rowMapper
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> readSheet(Sheet sheet, int skips,
            CellRowMapper<T> rowMapper) {
        AssertUtils.notNull(rowMapper, "rowMapper is null.");
        AssertUtils.notNull(sheet, "sheet is null.");
        
        //构造返回列表
        List<T> resList = new ArrayList<T>();
        //获取行中有多少列
        int rows = sheet.getPhysicalNumberOfRows();
        for (int r = 0; r < rows; r++) {//遍历多行
            if (r < skips) {
                continue;
            }
            //获取到对应的行数据<br/>
            Row row = sheet.getRow(r);
            //构造对象实例
            T tInsTemp = rowMapper.mapRow(row, r);
            
            //添加到列表中
            resList.add(tInsTemp);
        }
        
        //返回结果
        return resList;
    }
}
