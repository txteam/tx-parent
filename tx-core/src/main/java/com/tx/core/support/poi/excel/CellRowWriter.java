/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月8日
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

/**
 * excel行写入工具
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CellRowWriter<T> {
    
    /**
      * Cell单元格行内容写入<br/>
      *<功能详细描述>
      * @param obj
      * @param row
      * @param rowHeight
      * @param rowNum
      * @param cellStyle [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void write(T obj, Row row, int rowHeight, int rowNum, CellStyle cellStyle);
}
