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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;


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
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
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
    
}
