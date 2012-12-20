package com.tx.core.tree.model;

import java.util.Set;

/**
 * <树代理，子节点集合无序，节点不可重复>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-5]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SetTreeAbleProxy<T> implements SetTreeAble<SetTreeAbleProxy<T>> {
    private T object;
    
    private String id;
    
    private String parentId;
    
    private Set<SetTreeAbleProxy<T>> childs;
    
    public SetTreeAbleProxy(TreeAbleAdapter<T> adapter, T object) {
        this.id = adapter.getId(object);
        this.parentId = adapter.getParentId(object);
        this.object = object;
    }
    
    /**
     * @return 返回 object
     */
    public T getObject() {
        return object;
    }
    
    /**
     * @param 对object进行赋值
     */
    public void setObject(T object) {
        this.object = object;
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
     * @return 返回 parentId
     */
    public String getParentId() {
        return parentId;
    }
    
    /**
     * @param 对parentId进行赋值
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @return 返回 childs
     */
    public Set<SetTreeAbleProxy<T>> getChilds() {
        return childs;
    }
    
    /**
     * @param 对childs进行赋值
     */
    public void setChilds(Set<SetTreeAbleProxy<T>> childs) {
        this.childs = childs;
    }
}
