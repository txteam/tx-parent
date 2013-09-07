/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-7
 * <修改描述:>
 */
package com.tx.component.basicdata.valuegenerator;

import java.util.Date;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-9-7]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class NowDateValueGenerator implements ValueGenerator<Date>{

    /**
     * @return
     */
    @Override
    public Date generate() {
        Date now = new Date();
        return now;
    }
}
