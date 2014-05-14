/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.annotation;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.CellReader;
import com.tx.core.support.poi.excel.CellWriter;
import com.tx.core.support.poi.excel.builder.CellReaderBuilder;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelCellInfo<T> {
    
    /** 对应列序 */
    private int index;
    
    /** 行类型 */
    private int cellType = -1;
    
    /** 对应字段名 */
    private String fieldName;
    
    /** 对应字段上的注解 */
    private ExcelCell excelCellAnno;
    
    /** 行宽 */
    private int width;
    
    /** 行读取器 */
    private CellReader<T> cellReader;
    
    /** 行写入器 */
    private CellWriter<T> cellWriter;
    
    /** 类型 */
    private Class<T> fieldType;
    
    /** <默认构造函数> */
    @SuppressWarnings("unchecked")
    public ExcelCellInfo(String fieldName, Class<T> fieldType,
            ExcelCell excelCellAnno) {
        super();
        AssertUtils.notNull(fieldType, "fieldType is null.");
        AssertUtils.notNull(excelCellAnno, "excelCellAnno is null.");
        
        this.excelCellAnno = excelCellAnno;
        this.fieldType = fieldType;
        this.cellReader = (CellReader<T>) CellReaderBuilder.build(excelCellAnno.reader(),
                fieldType);
        this.index = excelCellAnno.index();
        this.width = excelCellAnno.width();
        this.fieldName = fieldName;
        this.cellType = excelCellAnno.cellType();
    }
    
    /**
     * @return 返回 index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * @param 对index进行赋值
     */
    public void setIndex(int index) {
        this.index = index;
    }
    
    /**
     * @return 返回 fieldName
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * @param 对fieldName进行赋值
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    /**
     * @return 返回 fieldType
     */
    public Class<T> getFieldType() {
        return fieldType;
    }
    
    /**
     * @param 对fieldType进行赋值
     */
    public void setFieldType(Class<T> fieldType) {
        this.fieldType = fieldType;
    }
    
    /**
     * @return 返回 excelCellAnno
     */
    public ExcelCell getExcelCellAnno() {
        return excelCellAnno;
    }
    
    /**
     * @param 对excelCellAnno进行赋值
     */
    public void setExcelCellAnno(ExcelCell excelCellAnno) {
        this.excelCellAnno = excelCellAnno;
    }
    
    /**
     * @return 返回 width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * @param 对width进行赋值
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * @return 返回 cellReader
     */
    public CellReader<T> getCellReader() {
        return cellReader;
    }
    
    /**
     * @param 对cellReader进行赋值
     */
    public void setCellReader(CellReader<T> cellReader) {
        this.cellReader = cellReader;
    }
    
    /**
     * @return 返回 cellWriter
     */
    public CellWriter<T> getCellWriter() {
        return cellWriter;
    }
    
    /**
     * @param 对cellWriter进行赋值
     */
    public void setCellWriter(CellWriter<T> cellWriter) {
        this.cellWriter = cellWriter;
    }

    /**
     * @return 返回 cellType
     */
    public int getCellType() {
        return cellType;
    }

    /**
     * @param 对cellType进行赋值
     */
    public void setCellType(int cellType) {
        this.cellType = cellType;
    }
}
