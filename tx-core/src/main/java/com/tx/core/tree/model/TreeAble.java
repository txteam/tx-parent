package com.tx.core.tree.model;

import java.util.Collection;

/**
 * <定义树基本结构>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TreeAble<C extends Collection<T>, T> {
    /** 获取树节点id */
    public String getId();
    
    /** 获取树父节点  */
    public String getParentId();
    
    /** 获取子节点列表 */
    public C getChilds();
    
    /** 设置子节点列表 */
    public void setChilds(C childs);
}
