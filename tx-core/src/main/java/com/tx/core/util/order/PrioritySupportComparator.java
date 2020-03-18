/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月24日
 * <修改描述:>
 */
package com.tx.core.util.order;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * OrderedSupport的排序类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PrioritySupportComparator implements Comparator<Object> {
    
    /** Shared default instance of OrderedSupportComparator.*/
    public static final PrioritySupportComparator INSTANCE = new PrioritySupportComparator();
    
    public int compare(Object o1, Object o2) {
        boolean p1 = (o1 instanceof PrioritySupport);
        boolean p2 = (o2 instanceof PrioritySupport);
        if (p1 && !p2) {
            return -1;
        } else if (p2 && !p1) {
            return 1;
        }
        
        //Direct evaluation instead of Integer.compareTo to avoid unnecessary object creation.
        int i1 = getPriority(o1);
        int i2 = getPriority(o2);
        return (i1 < i2) ? -1 : (i1 > i2) ? 1 : 0;
    }
    
    /**
     * Determine the order value for the given object.
     * <p>The default implementation checks against the {@link Ordered}
     * interface. Can be overridden in subclasses.
     * @param obj the object to check
     * @return the order value, or {@code Ordered.LOWEST_PRECEDENCE} as fallback
     */
    protected int getPriority(Object obj) {
        return (obj instanceof PrioritySupport
                ? ((PrioritySupport) obj).getPriority()
                : PrioritySupport.LOWEST_PRECEDENCE);
    }
    
    /**
     * Sort the given List with a default OrderComparator.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     * @param list the List to sort
     * @see java.util.Collections#sort(java.util.List, java.util.Comparator)
     */
    public static void sort(List<?> list) {
        if (list != null && list.size() > 1) {
            Collections.sort(list, INSTANCE);
        }
    }
    
    /**
     * Sort the given array with a default OrderComparator.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     * @param array the array to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sort(Object[] array) {
        if (array != null && array.length > 1) {
            Arrays.sort(array, INSTANCE);
        }
    }
}
