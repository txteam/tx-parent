package com.tx.core.tree.model;

import java.util.Set;

/**
 * <定义树基本结构，子节点集合无序，节点不可重复>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SetTreeAble<T> extends TreeAble<Set<T>, T> {
    /** 获取树节点id */
    public String getId();
    
    /** 获取树父节点  */
    public String getParentId();
    
    /** 获取子节点列表 */
    public Set<T> getChilds();
    
    /** 设置子节点列表 */
    public void setChilds(Set<T> childs);
}
