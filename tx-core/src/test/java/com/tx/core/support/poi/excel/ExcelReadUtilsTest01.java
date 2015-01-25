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

/**
 * 
 * <功能详细描述>
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelReadUtilsTest01 {
    
    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        try {
            String classPath = ExcelReadUtilsTest01.class.getResource("/").getPath() ;
            File file = new File(classPath + "excel/aa.xls");
            //获取sheet
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            System.out.println("path:"+classPath);
            //map的形式读入
            List<Map<String, String>> resMapList = ExcelReadUtils.readSheet(sheet,
                    new String[] {"客户账号编号",
                    "clientName", 
                    "clientBankId",
                    "clientDistrictId", 
                    "clientTelePhoneNumber",
                    "currency",
                    "loanBankAccountId",
                    "expectLoanDate", 
                    "amount"});
            for (Map<String, String> rowMap : resMapList) {
                MapUtils.debugPrint(System.out, "", rowMap);
//                System.out.println(rowMap);
            }
            
            //类形式读入
//            List<LoanFileRequestMessage> testList = ExcelReadUtils.<LoanFileRequestMessage> readSheet(sheet,
//                    LoanFileRequestMessage.class,
//                    0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
