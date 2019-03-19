/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */
package com.tx.component.auth.model;

import org.apache.commons.beanutils.PropertyUtils;

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
    
    /**
     * @param object
     * @param parentAuth
     * @return
     */
    @Override
    public String getId(T object, Auth parentAuth) {
        AssertUtils.notNull(parentAuth, "parentAuth is null.");
        AssertUtils.notNull(object, "object is null.");
        AssertUtils.isTrue(PropertyUtils.isReadable(object, "id"),
                "object.id is not readAble.");
        
        StringBuilder sb = new StringBuilder(64);
        sb.append(parentAuth.getId()).append("_");
        try {
            sb.append(PropertyUtils.getProperty(object, "id"));
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
        AssertUtils.notNull(parentAuth, "parentAuth is null.");
        AssertUtils.notNull(object, "object is null.");
        AssertUtils.isTrue(PropertyUtils.isReadable(object, "id"),
                "object.id is not readAble.");
        
        StringBuilder sb = new StringBuilder(64);
        try {
            sb.append(PropertyUtils.getProperty(object, "id"));
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
    public String getName(T object, Auth parentAuth) {
        AssertUtils.notNull(parentAuth, "parentAuth is null.");
        AssertUtils.notNull(object, "object is null.");
        AssertUtils.isTrue(PropertyUtils.isReadable(object, "name"),
                "object.name is not readAble.");
        
        StringBuilder sb = new StringBuilder(64);
        try {
            sb.append(PropertyUtils.getProperty(object, "name"));
        } catch (Exception e) {
            throw ExceptionWrapperUtils.wrapperSILException(SILException.class,
                    "getProperty error.");
        }
        return sb.toString();
    }
    
    /**
     * @param object
     * @param parentAuth
     * @return
     */
    @Override
    public String getRemark(T object, Auth parentAuth) {
        AssertUtils.notNull(parentAuth, "parentAuths is null.");
        AssertUtils.notNull(object, "object is null.");
        AssertUtils.isTrue(
                PropertyUtils.isReadable(object, "description")
                        || PropertyUtils.isReadable(object, "remark")
                        || PropertyUtils.isReadable(object, "name"),
                "object.name is not readAble.");
        
        StringBuilder sb = new StringBuilder(255);
        if (PropertyUtils.isReadable(object, "description")) {
            try {
                sb.append(PropertyUtils.getProperty(object, "description"));
            } catch (Exception e) {
                throw ExceptionWrapperUtils.wrapperSILException(
                        SILException.class, "getProperty error.");
            }
        } else if (PropertyUtils.isReadable(object, "remark")) {
            try {
                sb.append(PropertyUtils.getProperty(object, "remark"));
            } catch (Exception e) {
                throw ExceptionWrapperUtils.wrapperSILException(
                        SILException.class, "getProperty error.");
            }
        } else if (PropertyUtils.isReadable(object, "name")) {
            try {
                sb.append(PropertyUtils.getProperty(object, "name"));
            } catch (Exception e) {
                throw ExceptionWrapperUtils.wrapperSILException(
                        SILException.class, "getProperty error.");
            }
        }
        return sb.toString();
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
