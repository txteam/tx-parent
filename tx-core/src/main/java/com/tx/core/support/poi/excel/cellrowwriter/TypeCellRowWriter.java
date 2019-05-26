/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellrowwriter;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.TypeDescriptor;

import com.tx.core.exceptions.resource.ResourceReadException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.CellRowWriter;
import com.tx.core.support.poi.excel.annotation.ExcelCell;
import com.tx.core.support.poi.excel.annotation.ExcelCellInfo;
import com.tx.core.util.MessageUtils;
import com.tx.core.util.MetaObjectUtils;

/**
 * 类自动映射类的写入<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TypeCellRowWriter<T> implements CellRowWriter<T> {
    
    /** 类型 */
    @SuppressWarnings("unused")
    private Class<T> type;
    
    /** */
    private Map<Integer, ExcelCellInfo<?>> key2excelCellInfoMapping = new HashMap<>();
    
    /** <默认构造函数> */
    public TypeCellRowWriter(Class<T> type) {
        super();
        AssertUtils.notNull(type, "type is null.");
        this.type = type;
        
        BeanWrapper bw = new BeanWrapperImpl(type);
        for (PropertyDescriptor pdTemp : bw.getPropertyDescriptors()) {
            if(pdTemp.getReadMethod() == null || pdTemp.getWriteMethod() == null){
                continue;
            }
            String propertyName = pdTemp.getName();
            TypeDescriptor td = bw.getPropertyTypeDescriptor(propertyName);
            if(!td.hasAnnotation(ExcelCell.class)){
                continue;
            }
            
            ExcelCell excelCellAnno = td.getAnnotation(ExcelCell.class);
            Class<?> fieldType = td.getType();
            
            ExcelCellInfo<?> excelCellInfo = new ExcelCellInfo<>(propertyName,
                    fieldType, excelCellAnno);
            
            if (key2excelCellInfoMapping.containsKey(excelCellAnno.index())) {
                throw new ResourceReadException(MessageUtils.format("重复的字段索引值:{}。请核对。",
                        new Object[] { excelCellAnno.index() }));
            }
            key2excelCellInfoMapping.put(excelCellAnno.index(), excelCellInfo);
        }
    }
    
    /**
     * @param row
     * @param obj
     * @param rowNum
     * @param rowHeight
     * @param cellStyle
     */
    @Override
    public void write(Sheet sheet, Row row, T obj, int rowNum, int rowHeight,
            CellStyle cellStyle) {
        //设置行高
        row.setHeightInPoints(rowHeight);
        
        MetaObject metaObject = MetaObjectUtils.forObject(obj);
        
        for (ExcelCellInfo<?> cellInfo : key2excelCellInfoMapping.values()) {
            Cell cell = row.createCell(cellInfo.getIndex());
            //如果设置了宽度则使用设定的宽度
            if (cellInfo.getWidth() > 0) {
                sheet.setColumnWidth(cellInfo.getIndex(), cellInfo.getWidth());
            }
            Object value = metaObject.getValue(cellInfo.getFieldName());
            
            cellInfo.getCellWriter().write(cell,
                    value,
                    cellInfo.getWidth(),
                    cellStyle,
                    rowNum,
                    cellInfo.getIndex());
        }
    }
}
