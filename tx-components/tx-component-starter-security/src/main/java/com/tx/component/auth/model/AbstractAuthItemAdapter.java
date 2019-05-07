/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */
package com.tx.component.auth.model;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 抽象类实现<br/>
 *     该类实现便于其他项目在实现adapter期间减少编码<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractAuthItemAdapter<T> implements AuthItemAdapter<T> {
    
    /** 权限对应的对象实例 */
    protected T object;
    
    /** 权限对应对象实例访问句柄 */
    protected BeanWrapper objectBW = PropertyAccessorFactory
            .forBeanPropertyAccess(object);
    
    /** <默认构造函数> */
    public AbstractAuthItemAdapter(T object) {
        super();
        this.object = object;
        
        AssertUtils.notNull(object, "object is null.");
    }
    
    /**
     * @param object
     * @param parentAuth
     * @return
     */
    @Override
    public String getId(T object, Auth parentAuth) {
        AssertUtils.isTrue(objectBW.isReadableProperty("id"),
                "object.id is not readAble.");
        
        StringBuilder sb = new StringBuilder(64);
        sb.append(parentAuth == null ? "" : parentAuth.getId()).append("_");
        try {
            sb.append(objectBW.getPropertyValue("id"));
        } catch (Exception e) {
            throw ExceptionWrapperUtils.wrapperSILException(SILException.class,
                    "getProperty error.");
        }
        return sb.toString();
    }
    
    /**
     * @param object
     * @param parentAuthItem
     * @return
     */
    @Override
    public String getRefId(T object, Auth parentAuth) {
        AssertUtils.isTrue(objectBW.isReadableProperty("id"),
                "object.id is not readAble.");
        
        String refId = String.valueOf(objectBW.getPropertyValue("id"));
        return refId;
    }
    
    /**
     * @param object
     * @param parentAuthItem
     * @return
     */
    @Override
    public String getName(T object, Auth parentAuth) {
        AssertUtils.isTrue(objectBW.isReadableProperty("name"),
                "object.name is not readAble.");
        
        String name = String.valueOf(objectBW.getPropertyValue("name"));
        return name;
    }
    
    /**
     * @param object
     * @param parentAuth
     * @return
     */
    @Override
    public String getRemark(T object, Auth parentAuth) {
        AssertUtils.notNull(object, "object is null.");
        
        AssertUtils.isTrue(
                objectBW.isReadableProperty("name")
                        || objectBW.isReadableProperty("remark")
                        || objectBW.isReadableProperty("description"),
                "object.remark is not readAble.");
        
        String remark = "";
        if (objectBW.isReadableProperty("remark")) {
            remark = String.valueOf(objectBW.getPropertyValue("remark"));
        } else if (objectBW.isReadableProperty("description")) {
            remark = String.valueOf(objectBW.getPropertyValue("description"));
        } else if (objectBW.isReadableProperty("name")) {
            remark = String.valueOf(objectBW.getPropertyValue("name"));
        }
        return remark;
    }
    
    /**
     * @param object
     * @param parentAuthItem
     * @return
     */
    @Override
    public String getAuthType(T object, Auth parentAuth) {
        AssertUtils.notNull(object,
                "parentAuth is null.must overwrite getAuthType().");
        
        return parentAuth.getAuthType();
    }
}
