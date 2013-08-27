/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.util;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;


 /**
  * 生成UUID唯一键工具类<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-27]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class UUIDUtils {
    
    private static final IdentifierGenerator generator = new UUIDHexGenerator();
    
    /**
     * 利用hibernaeUUID生成器，生成唯一键
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public static String generateUUID() {
       return generator.generate(null, null).toString();
   }
}
