/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年5月17日
 * <修改描述:>
 */
package com.tx.core.spring.cache;

import java.io.Serializable;
import java.util.StringJoiner;

import com.tx.core.util.ObjectUtils;

/**
 * 参考Mybatis的CacheKey实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年5月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CacheKey implements Cloneable, Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = 4422970140161914057L;
    
    private static final int DEFAULT_MULTIPLYER = 37;
    
    private static final int DEFAULT_HASHCODE = 17;
    
    private final int multiplier;
    
    private int hashcode;
    
    private long checksum;
    
    private int count;
    
    public CacheKey() {
        this.hashcode = DEFAULT_HASHCODE;
        this.multiplier = DEFAULT_MULTIPLYER;
        this.count = 0;
    }
    
    public void update(Object object) {
        int baseHashCode = object == null ? 1 : ObjectUtils.hashCode(object);
        
        count++;
        checksum += baseHashCode;
        baseHashCode *= count;
        
        hashcode = multiplier * hashcode + baseHashCode;
    }
    
    public void updateAll(Object[] objects) {
        for (Object o : objects) {
            update(o);
        }
    }
    
    /**
     * equals方法<br/>
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CacheKey)) {
            return false;
        }
        
        final CacheKey cacheKey = (CacheKey) object;
        if (hashcode != cacheKey.hashcode) {
            return false;
        }
        if (checksum != cacheKey.checksum) {
            return false;
        }
        if (count != cacheKey.count) {
            return false;
        }
        return true;
    }
    
    /**
     * CacheKey的hashCode方法<br/>
     * @return
     */
    @Override
    public int hashCode() {
        return hashcode;
    }
    
    /**
     * CacheKey的toString方法
     * @return
     */
    @Override
    public String toString() {
        StringJoiner returnValue = new StringJoiner(":");
        returnValue.add(String.valueOf(hashcode));
        returnValue.add(String.valueOf(checksum));
        return returnValue.toString();
    }
    
    /**
     * 克隆CacheKey对象<br/>
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public CacheKey clone() throws CloneNotSupportedException {
        CacheKey clonedCacheKey = (CacheKey) super.clone();
        return clonedCacheKey;
    }
}
