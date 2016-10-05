/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class XlsxExcelTest {
    
    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        String path = XlsxExcelTest.class.getResource("./test.xlsx").getPath();
        //System.out.println(path);
        File file = new File(path);
        
        Workbook wb = ExcelReadUtils.getWorkBook(new FileInputStream(file));
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        
        //getPhysicalNumberOfCells 是获取不为空的列个数。 
        //getLastCellNum 是获取最后一个不为空的列是第几个。
        System.out.println("LastCellNum:" + row.getLastCellNum());
        System.out.println("PhysicalNumberOfCells:" + row.getPhysicalNumberOfCells());
        
        for(int i = 0 ; i < row.getLastCellNum() ; i++){
            System.out.println(i + " : " + (row.getCell(i) == null ? "null cell" : row.getCell(i).getCellType()));
        }
        
    }
}
