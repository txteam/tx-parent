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
public class ExcelTest {
    
    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        String path = ExcelTest.class.getResource("./test.xls").getPath();
        //System.out.println(path);
        File file = new File(path);
        
        Workbook wb = ExcelReadUtils.getWorkBook(new FileInputStream(file));
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        
        System.out.println(row.getCell(0).getCellType());
        System.out.println(row.getCell(1).getCellType());
        System.out.println(row.getCell(2).getCellType());
        System.out.println(row.getCell(3).getCellType());
        
        path = ExcelTest.class.getResource("./test.xlsx").getPath();
        //System.out.println(path);
        file = new File(path);
        
        wb = ExcelReadUtils.getWorkBook(new FileInputStream(file));
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        
        System.out.println(row.getCell(0).getCellType());
        System.out.println(row.getCell(1).getCellType());
        System.out.println(row.getCell(2).getCellType());
        System.out.println(row.getCell(3).getCellType());
    }
}
