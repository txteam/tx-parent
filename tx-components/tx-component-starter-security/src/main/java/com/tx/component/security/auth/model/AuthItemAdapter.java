/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月8日
 * <修改描述:>
 */
package com.tx.component.security.auth.model;

import org.springframework.beans.BeanWrapper;

import com.tx.core.exceptions.SILException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 权限项适配器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2015年11月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AuthItemAdapter<T> {
    
    /**
     * 获取Id的Prefix<br/>
     * <功能详细描述>
     * @param targetBW
     * @param parent
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String getIdPrefix(BeanWrapper targetBW, Auth parent) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getAuthType(targetBW, parent)).append("_");
        return sb.toString();
    }
    
    /**
     * 权限项唯一键key<br/>
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String getId(BeanWrapper targetBW, Auth parent) {
        AssertUtils.isTrue(targetBW.isReadableProperty("id"),
                "object.id is not readAble.");
        
        StringBuilder sb = new StringBuilder(64);
        sb.append(getIdPrefix(targetBW, parent)).append("_");
        try {
            sb.append(targetBW.getPropertyValue("id"));
        } catch (Exception e) {
            throw ExceptionWrapperUtils.wrapperSILException(SILException.class,
                    "getProperty error.");
        }
        return sb.toString();
    }
    
    /**
     * 获取权限描述<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String getRemark(BeanWrapper targetBW, Auth parent) {
        AssertUtils.isTrue(
                targetBW.isReadableProperty("name")
                        || targetBW.isReadableProperty("remark")
                        || targetBW.isReadableProperty("description"),
                "object.remark is not readAble.");
        
        String remark = "";
        if (targetBW.isReadableProperty("remark")) {
            remark = String.valueOf(targetBW.getPropertyValue("remark"));
        } else if (targetBW.isReadableProperty("description")) {
            remark = String.valueOf(targetBW.getPropertyValue("description"));
        } else if (targetBW.isReadableProperty("name")) {
            remark = String.valueOf(targetBW.getPropertyValue("name"));
        }
        return remark;
    }
    
    /**
     * 获取权限项名<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String getName(BeanWrapper targetBW, Auth parent) {
        AssertUtils.isTrue(targetBW.isReadableProperty("name"),
                "object.name is not readAble.");
        
        String name = String.valueOf(targetBW.getPropertyValue("name"));
        return name;
    }
    
    /**
     * 获取权限关联项id <br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String getRefId(BeanWrapper targetBW, Auth parent) {
        AssertUtils.isTrue(targetBW.isReadableProperty("id"),
                "object.id is not readAble.");
        
        String refId = String.valueOf(targetBW.getPropertyValue("id"));
        return refId;
    }
    
    /**
     * 获取权限描述<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public default String getAttributes(BeanWrapper targetBW, Auth parent) {
        return "{}";
    }
    
    /**
     * 父级权限id<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getParentId(BeanWrapper targetBW, Auth parent);
    
    /**
     * 权限项目引用类型<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getRefType(BeanWrapper targetBW, Auth parent);
    
    /**
     * 获取权限类型<br/>
     * <功能详细描述>
     * @param object
     * @param parentAuth
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getAuthType(BeanWrapper targetBW, Auth parent);
    
    /**
     * 获取所属模块<br/>
     * <功能详细描述>
     * @param targetBW
     * @param parent
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getModule(BeanWrapper targetBW, Auth parent);
    
    /**
     * 获取是否能够配置<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean isConfigAble(BeanWrapper targetBW, Auth parent);
}
