/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */
package com.tx.component.auth.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

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
public class AuthItemWraperByAdapter<T> implements Auth{
    
    /** 注释内容 */
    private static final long serialVersionUID = 2463898805204658642L;

    /** 被代理的对象本身 */
    private T object;
    
    /** 上级权限 */
    private Auth parentAuthItem;
    
    /** 被代理对象到权限的适配器 */
    private AuthItemAdapter<T> adapter;
    
    /** 系统唯一id */
    private String systemId;
    
    /** 子权限列表 */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Auth> childs = new ArrayList<Auth>();
    
    /** xml中attribute的其他属性 */
    private Map<String, String> data = new HashMap<>();
    
    /** <默认构造函数> */
    public AuthItemWraperByAdapter(AuthItemAdapter<T> adapter, T object,Auth parentAuthItem) {
        super();
        AssertUtils.notNull(adapter,"adapter is null.");
        AssertUtils.notNull(adapter,"object is null.");
        this.adapter = adapter;
        this.object = object;
        this.parentAuthItem = parentAuthItem;
    }
    
    /**
     * @return
     */
    @Override
    public String getId() {
        return this.adapter.getId(this.object, this.parentAuthItem);
    }

    /**
     * @return
     */
    @Override
    public String getRefId() {
        return this.adapter.getRefId(this.object, this.parentAuthItem);
    }

    /**
     * @return
     */
    @Override
    public String getRefType() {
        return this.adapter.getRefType(this.object, this.parentAuthItem);
    }
    
    /**
     * @return
     */
    @Override
    public String getParentId() {
        return this.adapter.getParentId(this.object, this.parentAuthItem);
    }
    
    /**
     * @return
     */
    @Override
    public String getName() {
        return this.adapter.getName(this.object, this.parentAuthItem);
    }

    /**
     * @return
     */
    @Override
    public String getRemark() {
        return this.adapter.getRemark(this.object, this.parentAuthItem);
    }
    
    /**
     * @return
     */
    @Override
    public String getAuthType() {
        return this.adapter.getAuthType(this.object, this.parentAuthItem);
    }

    /**
     * @return
     */
    @Override
    public boolean isModifyAble() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isConfigAble() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isVirtual() {
        return false;
    }

    /**
     * @return 返回 systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param 对systemId进行赋值
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * @return 返回 childs
     */
    public List<Auth> getChilds() {
        return childs;
    }

    /**
     * @param 对childs进行赋值
     */
    public void setChilds(List<Auth> childs) {
        this.childs = childs;
    }

    /**
     * @return 返回 data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * @param 对data进行赋值
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
