/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 基础数据字典<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
public abstract class AbstractTreeAbleDataDict<T extends TreeAbleBasicData<T>>
        extends AbstractDataDict implements TreeAbleBasicData<T> {
    
    /** 注释内容 */
    private static final long serialVersionUID = 3988566660941785675L;
    
    /** 数据字典父级节点 */
    private T parent;
    
    /** 子级节点 */
    @Transient
    @OneToMany
    private List<T> childs;
    
    /**
     * @return
     */
    @Override
    public String getParentId() {
        if (this.parent == null) {
            return null;
        }
        return this.parent.getId();
    }
    
    /**
     * @return 返回 parent
     */
    public T getParent() {
        return parent;
    }
    
    /**
     * @param 对parent进行赋值
     */
    public void setParent(T parent) {
        this.parent = parent;
    }
    
    /**
     * @return
     */
    @Override
    public List<T> getChilds() {
        return this.childs;
    }
    
    /**
     * @param childs
     */
    @Override
    public void setChilds(List<T> childs) {
        this.childs = childs;
    }
}
