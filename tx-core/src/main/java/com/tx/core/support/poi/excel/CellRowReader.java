/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-27
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;

/**
 * Cell行映射关联接口<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CellRowReader<T> {
    
    /**
      * excel中行数据如何映射到对象实体中
      * <功能详细描述>
      * @param row
      * @param rowNum
      * @return
      * @throws SQLException [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    T read(Row row, int rowNum, boolean ignoreError, boolean ignoreBlank,
            boolean ignoreTypeUnmatch, int numberOfCells);
}
