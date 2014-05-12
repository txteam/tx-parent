/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * 行中的一个Column的定义<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CellColumn {
    
    /** 列宽 */
    private int width;
    
    /**
     * 通过该对象设置
     *     边框
     *     边框颜色
     *     方向，倾斜角度
     *     垂直对齐
     *     字体
     *     单元格样式
     */
    private HSSFCellStyle cellStyle;
    
    /**
     * cellType类型
     */
    private int cellType;
    
    /** 对应对象中的key */
    private String field;
    
    /** <默认构造函数> */
    public CellColumn(String field, int cellType, int width) {
        super();
        this.width = width;
        this.cellType = cellType;
        this.field = field;
    }
    
    /** <默认构造函数> */
    public CellColumn(int width, HSSFCellStyle cellStyle, int cellType,
            String field) {
        super();
        this.width = width;
        this.cellStyle = cellStyle;
        this.cellType = cellType;
        this.field = field;
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
     * @return 返回 cellStyle
     */
    public HSSFCellStyle getCellStyle() {
        return cellStyle;
    }
    
    /**
     * @param 对cellStyle进行赋值
     */
    public void setCellStyle(HSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
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
    
    /**
     * @return 返回 field
     */
    public String getField() {
        return field;
    }
    
    /**
     * @param 对field进行赋值
     */
    public void setField(String field) {
        this.field = field;
    }
}
