/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-19
 * <修改描述:>
 */
package com.tx.core.support.cache;

import java.util.Map;

/**
 * 缓存值工厂<br/>
 *     利用该缓存值工厂可支持提供具备弱一致性缓存的缓存实现<br/>
 *     缓存策略可为
 *         当集群中某一台机器缓存发生变动时，其他缓存也一并发生变化<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2014-3-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface LazyCacheValueFactory<K,V> {
    
    /**
      * 根据key创建对应的值<br/>
      *<功能详细描述>
      * @param key
      * @return [参数说明]
      * 
      * @return V [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    V find(K key);
    
    /**
      * 创建值集合
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<V> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    Map<K,V> listMap();
}
