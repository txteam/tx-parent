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
import org.apache.poi.ss.usermodel.Cell;

import com.tx.core.support.poi.excel.annotation.ExcelCell;

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
                    new String[] { "column1", "column2", "column3", "sum" });
            System.out.println("print map list.");
            for (Map<String, String> rowMap : resMapList) {
                
                MapUtils.debugPrint(System.out, "", rowMap);
            }
            
            List<Test> testList = ExcelReadUtils.<Test> readSheet(sheet,
                    Test.class,
                    0);
            
            for (Test test : testList) {
                System.out.println(test.toString());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static class Test {
        @ExcelCell(index = 0)
        String c1;
        
        @ExcelCell(index = 1)
        String c2;
        
        @ExcelCell(index = 2)
        String c3;
        
        @ExcelCell(index = 3, cellType = Cell.CELL_TYPE_NUMERIC)
        String c4;
        
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
        
        /**
         * @return 返回 c4
         */
        public String getC4() {
            return c4;
        }
        
        /**
         * @param 对c4进行赋值
         */
        public void setC4(String c4) {
            this.c4 = c4;
        }
        
        @Override
        public String toString() {
            return "Test [c1=" + c1 + ", c2=" + c2 + ", c3=" + c3 + " c4=" + c4
                    + "]";
        }
    }
    
}
