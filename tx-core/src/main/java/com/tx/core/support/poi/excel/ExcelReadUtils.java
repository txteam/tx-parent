/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-24
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.cellreader.StringValueCellReader;

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
    
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    /**
     * 解析获取workbook实例
     * <功能详细描述>
     * @param inputStream
     * @return
     * @throws IOException [参数说明]
     * 
     * @return Workbook [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static Workbook getWorkBook(String filePath) {
        AssertUtils.notEmpty(filePath, "filePath is empty.");
        
        Resource fileResource = resourceLoader.getResource(filePath);
        
        AssertUtils.isTrue(fileResource != null && fileResource.exists(),
                "file is not exist.");
        
        Workbook book = null;
        InputStream in = null;
        try {
            in = fileResource.getInputStream();
            book = new HSSFWorkbook(in);
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(in);
        }
        
        return book;
    }
    
    /**
      * 解析获取workbook实例
      * <功能详细描述>
      * @param inputStream
      * @return
      * @throws IOException [参数说明]
      * 
      * @return Workbook [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Workbook getWorkBook(InputStream inputStream)
            throws IOException {
        Workbook book = null;
        book = new HSSFWorkbook(inputStream);
        return book;
    }
    
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
            final String[] keys) {
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
        final CellReader<String> finalCellReader = cellReader == null ? StringValueCellReader.INSTANCE
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
                    if(cell == null){
                        res.put(keys[keyIndex],
                                "");
                    }else{
                        res.put(keys[keyIndex],
                                finalCellReader.read(cell, keyIndex));
                    }
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
            if(row == null){
                continue;
            }
            //构造对象实例
            T tInsTemp = rowMapper.mapRow(row, r);
            
            //添加到列表中
            resList.add(tInsTemp);
        }
        
        //返回结果
        return resList;
    }
}
