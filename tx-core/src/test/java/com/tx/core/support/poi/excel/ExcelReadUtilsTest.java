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
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import com.tx.core.support.poi.excel.cellreader.StringValueCellReader;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelReadUtilsTest {
    
    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        try {
            File file = new File("d:/test.xls");
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            System.out.println(sheet.getPhysicalNumberOfRows());
            System.out.println(sheet.getFirstRowNum());
            System.out.println(sheet.getLastRowNum());
            List<Map<String, String>> resMapList = ExcelReadUtils.readSheet(sheet,
                    new String[] { "column1", "column2", "column3" });
            System.out.println("print map list.");
            for (Map<String, String> rowMap : resMapList) {
                
                MapUtils.debugPrint(System.out, "", rowMap);
            }
            
            List<Test> testList = ExcelReadUtils.<Test> readSheet(sheet, new CellRowMapper<Test>() {
                
                @Override
                public Test mapRow(Row row, int rowNum) {
                    Test test = new Test();
                    test.setC1(StringValueCellReader.INSTANCE.read(row.getCell(0), 0));
                    test.setC2(StringValueCellReader.INSTANCE.read(row.getCell(1), 1));
                    test.setC3(StringValueCellReader.INSTANCE.read(row.getCell(2), 2));
                    return test;
                }
            });
            
            for (Test test : testList) {
                System.out.println(test.toString());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static class Test {
        String c1;
        
        String c2;
        
        String c3;
        
        public String getC1() {
            return c1;
        }
        
        public void setC1(String c1) {
            this.c1 = c1;
        }
        
        public String getC2() {
            return c2;
        }
        
        public void setC2(String c2) {
            this.c2 = c2;
        }
        
        public String getC3() {
            return c3;
        }
        
        public void setC3(String c3) {
            this.c3 = c3;
        }
        
        @Override
        public String toString() {
            return "Test [c1=" + c1 + ", c2=" + c2 + ", c3=" + c3 + "]";
        }
    }
    
}
