/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月14日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel.cellrowreader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.reflection.ReflectionUtils;
import com.tx.core.support.poi.excel.CellRowReader;
import com.tx.core.support.poi.excel.annotation.ExcelCell;
import com.tx.core.support.poi.excel.annotation.ExcelCellInfo;
import com.tx.core.support.poi.excel.exception.ExcelReadException;
import com.tx.core.util.ObjectUtils;

/**
 * MapCellReader行读取器 for Map<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TypeCellRowReader<T> implements CellRowReader<T> {
    
    private Class<T> type;
    
    private Map<Integer, ExcelCellInfo<?>> key2excelCellInfoMapping = new HashMap<>();
    
    /** <默认构造函数> */
    public TypeCellRowReader(Class<T> type) {
        super();
        AssertUtils.notNull(type, "type is empty.");
        this.type = type;
        
        MetaClass metaClass = MetaClass.forClass(type);
        List<String> getterList = ReflectionUtils.getGetterNamesByAnnotationType(type,
                ExcelCell.class);
        for (String fieldName : getterList) {
            ExcelCell excelCellAnno = ReflectionUtils.getGetterAnnotation(type,
                    fieldName,
                    ExcelCell.class);
            Class<?> fieldType = metaClass.getGetterType(fieldName);
            
            ExcelCellInfo<?> excelCellInfo = new ExcelCellInfo<>(fieldName,
                    fieldType, excelCellAnno);
            
            if (key2excelCellInfoMapping.containsKey(excelCellAnno.index())) {
                throw new ExcelReadException("重复的字段索引值:{}。请核对。",
                        new Object[] { excelCellAnno.index() });
            }
            key2excelCellInfoMapping.put(excelCellAnno.index(), excelCellInfo);
        }
    }
    
    /**
     * @param row
     * @param rowNum
     * @param skip
     * @return
     */
    @Override
    public T read(Row row, int rowNum, boolean ignoreError,
            boolean ignoreBlank, boolean ignoreTypeUnmatch, int numberOfCells) {
        T obj = ObjectUtils.newInstance(type);
        MetaObject mo = MetaObject.forObject(obj);
        int cellsLength = numberOfCells != 0 ? numberOfCells : row.getPhysicalNumberOfCells();
        for (ExcelCellInfo<?> cellInfo : key2excelCellInfoMapping.values()) {
            AssertUtils.isTrue(cellInfo.getIndex() <= cellsLength,
                    "index:{} is not exist.cellsLength:{} ",
                    new Object[] { cellInfo.getIndex(), cellsLength });
            
            Cell cell = row.getCell(cellInfo.getIndex());
            Object value = null;
            if (cellInfo.getCellType() < 0) {
                value = cellInfo.getCellReader().read(cell,
                        rowNum,
                        cellInfo.getIndex(),
                        cellInfo.getFieldName(),
                        ignoreError,
                        ignoreBlank,
                        ignoreTypeUnmatch);
            } else {
                value = cellInfo.getCellReader().read(cellInfo.getCellType(),
                        cell,
                        rowNum,
                        cellInfo.getIndex(),
                        cellInfo.getFieldName(),
                        ignoreError,
                        ignoreBlank,
                        ignoreTypeUnmatch);
            }
            
            mo.setValue(cellInfo.getFieldName(), value);
        }
        return obj;
    }
}
