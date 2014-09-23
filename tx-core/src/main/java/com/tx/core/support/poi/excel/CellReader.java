/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-27
 * <修改描述:>
 */
package com.tx.core.support.poi.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 读取Cell值<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-5-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CellReader<T> {
    
    /**
      * 读取cell值
      * <功能详细描述>
      * @param cell
      * @param rowNum
      * @param cellNum
      * @param key
      * @param ignoreError 是否忽略Error_Type的字段，如果不忽略，存在这种数据时抛出异常
      * @param ignoreBlank 是否忽略Error_Blank的字段，如果不忽略，存在这种数据时抛出异常
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    T read(Cell cell, int rowNum, int cellNum, String key, boolean ignoreError,
            boolean ignoreBlank, boolean ignoreTypeUnmatch);
    
    /**
      * 根据指定格式进行单元格内容读取<br/>
      *     公式等格式提取中，应当是提取其中的内容，可能为时间，可能为text也可能为一串数字 
      * <功能详细描述>
      * @param cellType
      * @param cell
      * @param rowNum
      * @param cellNum
      * @param key
      * @param ignoreError 是否忽略Error_Type的字段，如果不忽略，存在这种数据时抛出异常
      * @param ignoreBlank 是否忽略Error_Blank的字段，如果不忽略，存在这种数据时抛出异常
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    T read(int cellType, Cell cell, int rowNum, int cellNum, String key,
            boolean ignoreError, boolean ignoreBlank, boolean ignoreTypeUnmatch);
}
