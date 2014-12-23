/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月4日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.ExcelTypeEnum;

/**
 * excel导出辅助类接口<br/>
 *     实现该接口用以辅助excel的生成及导出<br/>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ExportExcelModel<T> {
    
    private ExcelTypeEnum excelType;
    
    private Workbook workbook;
    
    private List<T> dataList;
    
    private Sheet sheet1;
    
    private String sheet1Name;
    
    /** <默认构造函数> */
    public ExportExcelModel(List<T> dataList) {
        this(ExcelTypeEnum.XSSF, dataList);
    }
    
    /** <默认构造函数> */
    public ExportExcelModel(ExcelTypeEnum excelType, List<T> dataList) {
        super();
        this.excelType = excelType;
        this.dataList = dataList;
        if (this.excelType == null) {
            this.excelType = ExcelTypeEnum.XSSF;
        }
    }
    
    /**
      * 调用该方法时model才会真正生成对应的excel
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public final void buildExcel() {
        // 生成workbook实例
        doCreateWorkbook();
        // 生成sheet1
        doCreateSheet1();
        // 写入sheet1的header
        int startRowIndex = writeSheet1Header(this.sheet1);
        writeSheet1Content(this.sheet1, startRowIndex);
        // 写入sheet1后的后续逻辑
        afterWriteSheet1(this.workbook);
    }
    
    /**
      * 写入sheet1后的后续逻辑<br/>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void afterWriteSheet1(Workbook workbook) {
        
    }
    
    /**
      * 创建Sheet1
      *<功能简述>
      *<功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doCreateSheet1() {
        if (this.sheet1Name == null) {
            this.sheet1 = this.workbook.createSheet();
        } else {
            this.sheet1 = this.workbook.createSheet(this.sheet1Name);
        }
    }
    
    /**
      * 写入sheet1Content
      * [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void writeSheet1Content(Sheet sheet, int startRowIndex) {
        if (CollectionUtils.isEmpty(this.dataList)) {
            return;
        }
        for (int i = 0; i < this.dataList.size(); i++) {
            Row rowTemp = sheet.createRow(startRowIndex + i);
            T dataTemp = this.dataList.get(i);
            
            doWriteSheet1Row(i, rowTemp, dataTemp);
        }
    }
    
    /**
      * 向单行中写入内容
      * <功能详细描述>
      * @param row
      * @param data [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract void doWriteSheet1Row(int index, Row row, T data);
    
    /**
      * 写入sheet1的header
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract int writeSheet1Header(Sheet sheet);
    
    /**
      * 构建生成Workbook实例<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void doCreateWorkbook() {
        switch (this.excelType) {
            case HSSF:
                this.workbook = new HSSFWorkbook();
                break;
            case XSSF:
                this.workbook = new XSSFWorkbook();
                break;
            default:
                AssertUtils.isTrue(false,
                        "不支持的workbook类型：请检查.excelType:{}",
                        new Object[] { this.excelType });
                break;
        }
    }
    
    /**
     * @param 对excelType进行赋值
     */
    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }
    
    /**
     * @param 对sheet1Name进行赋值
     */
    public void setSheet1Name(String sheet1Name) {
        this.sheet1Name = sheet1Name;
    }
    
    /**
      * 返回对应excel的workbook实例 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Workbook [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Workbook getWorkbook() {
        return this.workbook;
    }
}
