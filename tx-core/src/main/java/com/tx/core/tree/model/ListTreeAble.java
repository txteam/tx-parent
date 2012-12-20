package com.tx.core.tree.model;

import java.util.List;

/**
 * <定义树基本结构,子节点集合有序，节点可重复>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ListTreeAble<T> extends TreeAble<List<T>, T> {
    /** 获取树节点id */
    public String getId();
    
    /** 获取树父节点  */
    public String getParentId();
    
    /** 获取子节点列表 */
    public List<T> getChilds();
    
    /** 设置子节点列表 */
    public void setChilds(List<T> childs);
}
