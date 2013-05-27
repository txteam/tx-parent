/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-27
 * <修改描述:>
 */
package com.tx.core.support.poi;

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
      * 读取cell值<br/>
      * <功能详细描述>
      * @param cell
      * @param cellNum
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    T read(Cell cell,int cellNum);
}
