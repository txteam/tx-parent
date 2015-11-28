/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月20日
 * 项目： tx-core
 */
package com.tx.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Tx 集合工具
 * 
 * @author rain
 * @version [版本号, 2015年11月20日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TxCollectionUtils {
    
    /**
     * 
     * 分割集合<br />
     * 把一个完整集合分割成多个集合,每个集合中拥有 maxSize 个集合元素,最后一个集合拥有小于 maxSize 个元素<br />
     * 分割完成的列表不是被分割列表的视图,而是一个新的列表,可以进行所有方法调用而不影响被分割的列表.<br />
     * 分割完成后的列表中的元素与被分割中的元素是引用关系,而非 clone 出来的新元素.
     *
     * @param cols 被分割的集合
     * @param maxSize 每个集合拥有的最大元素数量
     *            
     * @return List<Collection<T>> 分割后的集合
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月20日]
     * @author rain
     */
    public static <T> List<Collection<T>> splitSize(Iterable<T> iterables, int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must > 0");
        }
        List<Collection<T>> list = new ArrayList<Collection<T>>();
        if (iterables == null || !iterables.iterator().hasNext()) {
            return list;
        }
        
        int pos = 0;
        Collection<T> col = new ArrayList<T>();
        Iterator<T> iterator = iterables.iterator();
        while (iterator.hasNext()) {
            if (pos == maxSize) {
                list.add(col);
                col = new ArrayList<T>();
                pos = 0;
            }
            col.add(iterator.next());
            pos++;
        }
        list.add(col);
        return list;
    }
}
