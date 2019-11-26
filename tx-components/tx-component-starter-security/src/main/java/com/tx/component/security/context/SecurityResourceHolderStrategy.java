/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年11月25日
 * <修改描述:>
 */
package com.tx.component.security.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 安全资源处理器策略<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年11月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SecurityResourceHolderStrategy {
    
    /** 创建容器 */
    private static ThreadLocal<SecurityResourceHolderContext> context = new ThreadLocal<SecurityResourceHolderContext>() {
        
        /**
         * @return
         */
        @Override
        protected SecurityResourceHolderContext initialValue() {
            return new SecurityResourceHolderContext();
        }
        
        /**
         * 
         */
        @Override
        public void remove() {
            get().close();
        }
    };
    
    /**
     * Clears the current context.
     */
    public static void open() {
        context.get().close();
        //开启线程变量
        context.get().open();
    }
    
    /**
     * Clears the current context.
     */
    public static void close() {
        context.remove();
    }
    
    /**
     * Obtains the current context.
     *
     * @return a context (never <code>null</code> - create a default implementation if
     * necessary)
     */
    @SuppressWarnings("unchecked")
    public static <T extends SecurityResourceHolder> T get(Class<T> type) {
        AssertUtils.notNull(type, "ResourceHolderType is null.");
        
        synchronized (type) {
            boolean binding = context.get().isBinding();
            Map<Class<? extends SecurityResourceHolder>, SecurityResourceHolder> map = context
                    .get().getResourceMap();
            
            //如果在线程变量中有存储
            if (map.containsKey(type) && binding) {
                return (T) map.get(type);
            }
            
            Constructor<T> c = null;
            try {
                c = ConstructorUtils.getAccessibleConstructor(type);
            } catch (Exception e) {
            }
            AssertUtils.notNull(c, "type:{} 不存在无参构造函数.", new Object[] { type });
            T holder = null;
            try {
                holder = ConstructorUtils.invokeConstructor(type);
            } catch (NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException e) {
                throw new SILException("构造SecurityResourceHolder的实现异常.", e);
            }
            holder.init();
            
            if (binding) {
                map.put(type, holder);
            }
            return holder;
        }
    }
    
    /**
     * 安全容器资源Holder容器，用以存放至线程变量中<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年11月26日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    private static class SecurityResourceHolderContext {
        
        /** 是否绑定到线程中 */
        private boolean binding = false;
        
        /** */
        private final Map<Class<? extends SecurityResourceHolder>, SecurityResourceHolder> resourceMap = new HashMap<>();
        
        /** <默认构造函数> */
        public SecurityResourceHolderContext() {
            super();
        }
        
        /**
         * 向线程变量中初始化数据，修改绑定状态为true
         * <功能详细描述> [参数说明]
         * 
         * @return void [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public void open() {
            this.binding = true;
            this.resourceMap.clear();
        }
        
        /**
         * 向线程变量中初始化数据，修改绑定状态为false
         * <功能详细描述> [参数说明]
         * 
         * @return void [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        public void close() {
            this.binding = false;
            for (SecurityResourceHolder holder : this.resourceMap.values()) {
                holder.clear();
            }
            this.resourceMap.clear();
        }
        
        /**
         * @return 返回 binding
         */
        public boolean isBinding() {
            return binding;
        }
        
        /**
         * @return 返回 resourceMap
         */
        public Map<Class<? extends SecurityResourceHolder>, SecurityResourceHolder> getResourceMap() {
            return resourceMap;
        }
    }
}
