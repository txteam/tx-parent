/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.core.exceptions.context.UnsupportedOperationException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 权限包装器ByAdapter
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthItemWraper<T> implements Auth {
    
    /** 注释内容 */
    private static final long serialVersionUID = 2463898805204658642L;
    
    /** 被代理的对象本身 */
    private T target;
    
    /** target的BeanWrapper对象 */
    private BeanWrapper targetBeanWrapper;
    
    /** 被代理对象到权限的适配器 */
    private AuthItemAdapter<T> adapter;
    
    /** 上级权限 */
    private Auth parent;
    
    /** 子权限列表 */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Auth> children = new ArrayList<Auth>();
    
    /** <默认构造函数> */
    public AuthItemWraper(AuthItemAdapter<T> adapter, T target, Auth parent) {
        super();
        AssertUtils.notNull(adapter, "adapter is null.");
        AssertUtils.notNull(adapter, "object is null.");
        
        this.adapter = adapter;
        this.target = target;
        this.targetBeanWrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(this.target);
        
        this.parent = parent;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return this.adapter.getId(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getResourceId() {
        return this.adapter.getRefId(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getResourceType() {
        return this.adapter.getRefType(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getParentId() {
        return this.adapter.getParentId(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.adapter.getName(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getRemark() {
        return this.adapter.getRemark(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthTypeId() {
        return this.adapter.getAuthTypeId(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public boolean isConfigAble() {
        return this.adapter.isConfigAble(this.targetBeanWrapper, this.parent);
    }
    
    /**
     * @return
     */
    @Override
    public String getAttributes() {
        return null;
    }
    
    /**
     * @return 返回 childs
     */
    public List<Auth> getChildren() {
        return children;
    }
    
    /**
     * @param 对childs进行赋值
     */
    public void setChildren(List<Auth> children) {
        this.children = children;
    }
    
    /**
     * @param attributes
     */
    @Override
    public void setAttributes(String attributes) {
        throw new UnsupportedOperationException(
                "属性attributes(AuthItemWraper)不能被修改.");
    }
}
