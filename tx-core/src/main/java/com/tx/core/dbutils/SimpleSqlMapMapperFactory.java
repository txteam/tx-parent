/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-5
 * <修改描述:>
 */
package com.tx.core.dbutils;

import java.util.WeakHashMap;


 /**
  * SimpleSqlMapMapper构建工厂
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-9-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SimpleSqlMapMapperFactory {
    
    /** 弱引用映射 */
    private WeakHashMap<Class<?>, SimpleSqlMapMapper> mapping = new WeakHashMap<Class<?>, SimpleSqlMapMapper>();
    
}
