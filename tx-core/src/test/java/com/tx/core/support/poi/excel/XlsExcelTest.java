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

import org.apache.poi.ss.usermodel.Cell;
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
public class XlsExcelTest {
    
    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        String path = XlsExcelTest.class.getResource("./test.xlsx").getPath();
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
            Cell cell = row.getCell(i);
            
            if(cell == null){
                continue;
            }
            String val = "";
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    val = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    val = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    val = cell.getCellFormula();//String.valueOf(cell.getNumericCellValue());
                    break;
                default:
                    break;
            }
            System.out.println(val);
            
//            String res = "";
//            switch (cell.getCellType()) {
//                case Cell.CELL_TYPE_ERROR:
//                    res = null;
//                    break;
//                case Cell.CELL_TYPE_BLANK:
//                    res = "";
//                    break;
//                case Cell.CELL_TYPE_FORMULA:
//                    //如果为计算公式，将计算公司进行提取
//                    res = cell.getCellFormula();
//                    break;
//                case Cell.CELL_TYPE_NUMERIC:
//                    //如果Cell类型不需要匹配
//                    //如果为数字，将计算公司进行提取
//                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                        //如果为时间的处理逻辑
//                        resString = DateFormatUtils.format(cell.getDateCellValue(),
//                                dateFormatterPattern);
//                    }else {
//                        //如果为非时间
//                        resString = String.valueOf(cell.getNumericCellValue());
//                    }
//                    break;
//                case Cell.CELL_TYPE_STRING:
//                    resString = cell.getStringCellValue() != null ? cell.getStringCellValue()
//                            .trim()
//                            : "";
//                    break;
//                case Cell.CELL_TYPE_BOOLEAN:
//                    //如果Cell类型一定要匹配
//                    throwTypeUnmatchExceptionWhenNotIgnoreTypeUnmatch(ignoreTypeUnmatch,
//                            "CELL_TYPE_BOOLEAN",
//                            rowNum,
//                            cellNum,
//                            key);
//                    //如果Cell类型不需要匹配
//                    resString = cell.getBooleanCellValue() ? "true" : "false";
//                    break;
//                default:
//                    resString = null;
//            }
//            return resString;
        }
        
    }
}
