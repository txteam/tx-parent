package com.tx.core.tree.model;

/**
 * <对象转换为树型代理对象适配器>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TreeAbleAdapter<T> {
    
    /**
     * @return
     */
    public abstract String getId(T t);
    
    /**
     * @return
     */
    public abstract String getParentId(T t);
}
