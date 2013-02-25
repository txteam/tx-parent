/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-2-25
 * <修改描述:>
 */
package com.tx.core.support.cache.ehcache;

import net.sf.ehcache.Ehcache;

import com.tx.core.support.cache.KeyValueCache;


 /**
  * ehcache类型的keyValue型缓存
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-2-25]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class EhcacheKeyValueCache<K> implements KeyValueCache<K> {

    private String id;
    
    private Ehcache ehcache;
    
    /**
     * ehcacheKeyValue缓存实例化工具
     */
    private EhcacheKeyValueCache(String id,Ehcache ehcache) {
        super();
        this.id = id;
        this.ehcache = ehcache;
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public void put(String key, K value) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param key
     * @return
     */
    @Override
    public K get(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param key
     */
    @Override
    public void removeByKey(String key) {
        // TODO Auto-generated method stub
        
    }

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 返回 ehcache
     */
    public Ehcache getEhcache() {
        return ehcache;
    }

    /**
     * @param 对ehcache进行赋值
     */
    public void setEhcache(Ehcache ehcache) {
        this.ehcache = ehcache;
    }
}
