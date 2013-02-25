/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-25
 * <修改描述:>
 */
package com.tx.core.support.cache;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-25]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface KeyValueCache<K> {
    
    /**
      * 根据key压入keyValue缓存中
      * <功能详细描述>
      * @param key
      * @param value [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void put(String key,K value);
    
    /**
      * 从缓存中获取key对应的value
      * <功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return K [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    K get(String key);
    
    /**
      * 清空缓存
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void clear();
    
    /**
      * 根据key清除对应的缓存
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    void removeByKey(String key);
}
