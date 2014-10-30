/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年5月27日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 支撑优先级数据提取的列表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年5月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SupportPriorityList<T> {
    
    private List<TargetObject> targetObjects = new LinkedList<>();
    
    public SupportPriorityList() {
        super();
    }
    
    public void addObject(T object, double rate) {
        targetObjects.add(new TargetObject(object, rate));
    }
    
    public void reset() {
        for (TargetObject target : targetObjects) {
            target.reset();
        }
    }
    
    public T getObject() {
        Collections.sort(targetObjects, instance);
        
        return targetObjects.get(0).getTarget();
    }
    
    public TargetObjectComparator instance = new TargetObjectComparator();
    
    public class TargetObjectComparator implements Comparator<TargetObject> {
        
        /**
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(TargetObject o1, TargetObject o2) {
            if (o1.getPriority() == o2.getPriority()) {
                return 0;
            } else if (o1.getPriority() - o2.getPriority() > 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    
    /**
      * 内部对象
      * <功能详细描述>
      * 
      * @author  Administrator
      * @version  [版本号, 2014年5月27日]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    public class TargetObject {
        
        /** 代理对象 */
        private T target;
        
        /** 引用次数 */
        private int refCount;
        
        /** 优先级 */
        private double rate;
        
        /** 优先级 */
        private double priority;
        
        /** <默认构造函数> */
        public TargetObject(T target, double rate) {
            super();
            this.target = target;
            this.refCount = 0;
            this.rate = rate;
            rebuildPriority();
        }
        
        /**
         * @return 返回 target
         */
        public T getTarget() {
            addRefCount();
            return target;
        }
        
        /**
          * xxxx
          *<功能详细描述> [参数说明]
          * 
          * @return void [返回类型说明]
          * @exception throws [异常类型] [异常说明]
          * @see [类、类#方法、类#成员]
         */
        private void rebuildPriority() {
            this.priority = (this.rate / (1 + refCount)) * 1000 + this.rate;
        }
        
        public void reset() {
            this.refCount = 0;
            rebuildPriority();
        }
        
        public void addRefCount() {
            this.refCount++;
            rebuildPriority();
        }
        
        /**
         * @return 返回 priority
         */
        public double getPriority() {
            return priority;
        }
    }
    
    public static void main(String[] args) {
        SupportPriorityList<String> t = new SupportPriorityList<>();
        
        t.addObject("A", 4);
        t.addObject("B", 3);
        t.addObject("C", 2);
        t.addObject("D", 1);
        
        MultiValueMap<String, String> tt = new LinkedMultiValueMap<>();
        
        int count = 25;
        for (int i = 0; i < count; i++) {
            String t1 = t.getObject();
            tt.add(t1, t1);
        }
        
        for(Entry<String, List<String>> entryTemp : tt.entrySet()){
            System.out.println(entryTemp.getKey() + " : " + entryTemp.getValue().size());
        }
    }
}
