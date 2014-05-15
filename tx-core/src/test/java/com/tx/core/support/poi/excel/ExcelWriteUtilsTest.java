/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
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
public class ExcelWriteUtilsTest {
    
    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        /** 
         * 创建excel工作薄 
         */
        HSSFWorkbook workBook = new HSSFWorkbook();
        /** 
         * 创建一个sheet 
         */
        HSSFSheet sheet = workBook.createSheet("sheet1");
        
        List<Map<String, String>> rowMapList = new ArrayList<>();
        rowMapList.add(new HashMap<String, String>());
        rowMapList.get(0).put("column1", "column1Name");
        rowMapList.get(0).put("column2", "column2Name");
        rowMapList.get(0).put("column3", "column3Name");
        ExcelWriteUtils.writeSheet(sheet, 0, rowMapList, new String[] {
                "column1", "column2", "column3" });
        
        List<Test> testList = new ArrayList<>();
        testList.add(new Test("1", "2", "3", "4"));
        testList.add(new Test("11", "22", "33", "44"));
        testList.add(new Test("111", "222", "333", "444"));
        
        ExcelWriteUtils.writeSheet(sheet, 1, testList, Test.class);
        
        File file = new File("d:\\workbook.xls");
        FileOutputStream fileOut = new FileOutputStream(file);
        workBook.write(fileOut);
        fileOut.close();
    }
    
    public static void tttt(String[] args) throws FileNotFoundException,
            IOException {
        try {
            /** 
             * 创建excel工作薄 
             */
            HSSFWorkbook workBook = new HSSFWorkbook();
            /** 
             * 创建一个sheet 
             */
            HSSFSheet sheet1 = workBook.createSheet("sheet1");
            
            /** 
             * 合并单元格 
             */
            sheet1.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            
            /** 
             * 第0列列宽500 
             */
            sheet1.setColumnWidth(0, 500);
            /** 
             * 创建一行 
             */
            HSSFRow row = sheet1.createRow(0);
            /** 
             * 行高 
             */
            row.setHeightInPoints((short) 100);
            /** 
             * 创建一个单元格 
             */
            HSSFCell cell = row.createCell(0);
            /** 
             * 设置单元格的类型 
             */
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            
            /** 
             * 设置单元格字符串内容 
             */
            HSSFRichTextString rts = new HSSFRichTextString("测试");
            /** 
             * 单元格赋值 
             */
            cell.setCellValue(1);
            /** 
             * 生成一个字体 
             */
            HSSFFont font = workBook.createFont();
            /** 
             * 字体颜色 
             */
            font.setColor(HSSFColor.VIOLET.index);
            /** 
             * 字大小 
             */
            font.setFontHeightInPoints((short) 16);
            /** 
             * 加粗 
             */
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            /** 
             * 字体 
             */
            font.setFontName("宋体");
            /** 
             * 是否斜体 
             */
            font.setItalic(true);
            /** 
             * 是否有删除线 
             */
            font.setStrikeout(true);
            /** 
             * 设置上标，下标 
             */
            font.setTypeOffset(HSSFFont.SS_NONE);
            /** 
             * 下划线 
             */
            font.setUnderline(HSSFFont.U_NONE);
            
            HSSFCellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            /** 
             * 设置边框 
             */
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            /** 
             * 边框颜色 
             */
            cellStyle.setLeftBorderColor(HSSFColor.GREEN.index);
            cellStyle.setRightBorderColor(HSSFColor.YELLOW.index);
            cellStyle.setBottomBorderColor(HSSFColor.GOLD.index);
            cellStyle.setTopBorderColor(HSSFColor.SEA_GREEN.index);
            /** 
             * 方向倾斜多少度 
             */
            cellStyle.setRotation((short) 0);
            /** 
             * 垂直对齐 
             */
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
            /** 
             * 设置字体 
             */
            cellStyle.setFont(font);
            /** 
             * 设置单元格样式 
             */
            cell.setCellStyle(cellStyle);
            /** 
             * 设置行样式 
             */
            //row.setRowStyle(cellStyle);  
            HSSFCell cell1 = row.createCell(1);
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell1.setCellValue(new Double(1));
            HSSFCell cell2 = row.createCell(2);
            /** 
            * 设置公式 
            */
            cell2.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell2.setCellFormula("a1+b1");
            //cell2.setCellFormula("sum(a1:b1)");//这样设置也可以  
            File file = new File("d:\\workbook.xls");
            
            FileOutputStream fileOut = new FileOutputStream(file);
            workBook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
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
        
        /** <默认构造函数> */
        public Test() {
            super();
        }
        
        /** <默认构造函数> */
        public Test(String c1, String c2, String c3, String c4) {
            super();
            this.c1 = c1;
            this.c2 = c2;
            this.c3 = c3;
            this.c4 = c4;
        }
        
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
