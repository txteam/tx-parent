/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-27
 * <修改描述:>
 */
package com.tx.core.support.poi.model;

import java.util.List;


 /**
  * Excel页对象<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-5-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SheetData {
    
    /**
     * 获取sheet名
     */
    private String name;
    
    /**
     * 有多少列
     */
    private int numberOfRows;
    
    /**
     * 多行数据
     */
    private List<RowData> rowData;

    /**
     * @return 返回 numberOfRows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @param 对numberOfRows进行赋值
     */
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    /**
     * @return 返回 rowData
     */
    public List<RowData> getRowData() {
        return rowData;
    }

    /**
     * @param 对rowData进行赋值
     */
    public void setRowData(List<RowData> rowData) {
        this.rowData = rowData;
    }

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }
}
