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
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.tx.core.exceptions.resource.ResourceParseException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.support.poi.excel.builder.CellRowReaderBuilder;
import com.tx.core.support.poi.excel.rowreader.MapRowReader;
import com.tx.core.support.poi.excel.rowreader.TypeRowReader;

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
    
    /** 资源读取器 */
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    
    /**
     * 解析获取workbook实例
     *     
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
        try (InputStream in = fileResource.getInputStream()) {
            book = WorkbookFactory.create(in);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e, "excel解析异常.", "");
        } catch (EncryptedDocumentException e) {
            throw ExceptionWrapperUtils.wrapperSILException(
                    ResourceParseException.class, "资源解析异常");
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
        
        try {
            book = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e, "excel解析异常.", "");
        } catch (EncryptedDocumentException e) {
            throw ExceptionWrapperUtils.wrapperSILException(
                    ResourceParseException.class, "资源解析异常");
        }
        return book;
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
    public static List<Map<String, String>> readSheet(final Sheet sheet) {
        AssertUtils.notNull(sheet, "sheet is null.");
        
        Row firstRow = sheet.getRow(0);
        if (firstRow == null) {
            return new ArrayList<>();
        }
        String[] keys = new String[firstRow.getLastCellNum()];
        for (int cellIndex = 0; cellIndex < firstRow
                .getLastCellNum(); cellIndex++) {
            Cell cell = firstRow.getCell(cellIndex);
            AssertUtils.notNull(cell,
                    "cell is null,sheet:{} cellIndex:{}",
                    new Object[] { sheet, cellIndex });
            
            //如果获取不成功说明类型错误
            keys[cellIndex] = cell.getStringCellValue();
        }
        
        @SuppressWarnings("rawtypes")
        CellRowReader cellRowReader = CellRowReaderBuilder
                .build(MapRowReader.class, new Object[] { keys });
        @SuppressWarnings("unchecked")
        List<Map<String, String>> resList = readSheet(sheet,
                1,
                cellRowReader,
                true,
                true,
                true);
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
        AssertUtils.notNull(sheet, "sheet is null.");
        AssertUtils.notEmpty(keys, "keys is null.");
        
        @SuppressWarnings("rawtypes")
        CellRowReader cellRowReader = CellRowReaderBuilder
                .build(MapRowReader.class, new Object[] { keys });
        @SuppressWarnings("unchecked")
        List<Map<String, String>> resList = readSheet(sheet,
                skips,
                cellRowReader,
                true,
                true,
                true);
        
        return resList;
    }
    
    /**
      * 读取行级数据<br/>
      *<功能详细描述>
      * @param sheet
      * @param type
      * @param skips
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> readSheet(final Sheet sheet, Class<T> type,
            int skips) {
        AssertUtils.notNull(type, "type is null.");
        AssertUtils.notEmpty(sheet, "sheet is null.");
        
        @SuppressWarnings("rawtypes")
        CellRowReader cellRowReader = CellRowReaderBuilder
                .build(TypeRowReader.class, new Object[] { type });
        @SuppressWarnings("unchecked")
        List<T> resList = readSheet(sheet,
                skips,
                cellRowReader,
                true,
                true,
                true);
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
    private static <T> List<T> readSheet(Sheet sheet, int skips,
            CellRowReader<T> cellRowReader, boolean ignoreError,
            boolean ignoreBlank, boolean ignoreTypeUnmatch) {
        AssertUtils.notNull(cellRowReader, "cellRowReader is null.");
        AssertUtils.notNull(sheet, "sheet is null.");
        
        //获取第一行
        //int firstRowNum = sheet.getFirstRowNum();
        //Row firstRow = sheet.getRow(firstRowNum);
        //AssertUtils.notNull(firstRow,"firstRow is null");
        //获取第一行列数
        //int numberOfCells = firstRow.getPhysicalNumberOfCells();
        //构造返回列表
        List<T> resList = new ArrayList<T>();
        //获取行中有多少列
        //getPhysicalNumberOfRows()获取的是物理行数，也就是不包括那些空行（隔行）的情况。
        //getLastRowNum()获取的是最后一行的编号（编号从0开始）。
        //int rows = sheet.getPhysicalNumberOfRows();
        int lastRowNum = sheet.getLastRowNum();
        //System.out.println(test);
        for (int r = 0; r <= lastRowNum; r++) {//遍历多行
            if (r < skips) {
                continue;
            }
            //获取到对应的行数据<br/>
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            //构造对象实例
            T tInsTemp = cellRowReader.read(row,
                    r,
                    ignoreError,
                    ignoreBlank,
                    ignoreTypeUnmatch,
                    0);
            //添加到列表中
            resList.add(tInsTemp);
        }
        
        //返回结果
        return resList;
    }
}
