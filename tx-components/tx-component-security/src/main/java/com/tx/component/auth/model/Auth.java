package com.tx.component.auth.model;

import java.io.Serializable;
import java.util.List;

import com.tx.core.support.json.JSONAttributesSupport;
import com.tx.core.tree.model.TreeAble;

/**
  * 权限项接口<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2012-12-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface Auth extends JSONAttributesSupport, TreeAble<List<Auth>, Auth>,
        Serializable {
    
    /**
     * 权限项唯一键key 
     * 约定权限项目分割符为"_"
     * 如权限为"wd_"<br/>
     * <功能详细描述>
     * 
     * @return 返回 id
     */
    String getId();
    
    /**
     * 父级权限id<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getParentId();
    
    /**
     * 获取权限类型<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getAuthType();
    
    /**
     * 获取权限项模块<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getModule();
    
    /**
     * 社区权限所属系统<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     */
    void setModule(String module);
    
    /**
     * 获取权限版本<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    int getVersion();
    
    /**
     * 设置权限版本<br/>
     * <功能详细描述>
     * @param version [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void setVersion(int version);
    
    /**
     * 获取权限关联项id<br/>
     * <功能详细描述>
     * 
     * @return String [返回类型说明]
     */
    String getRefId();
    
    /**
     * 权限项目引用类型  <br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getRefType();
    
    /**
     * 权限名<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getName();
    
    /**
     * 获取权限描述<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    String getRemark();
    
    /**
     * 该权限是否有效<br/>
     * <功能详细描述>
     * 
     * @return [参数说明]
     */
    boolean isValid();
    
    /**
     * 是否可编辑<br/>
     * <功能详细描述>
     * 
     * @return boolean 
     */
    boolean isModifyAble();
    
    /**
     * 是否能进行配置<br/>
     * <功能详细描述>
     * 
     * @return boolean 
     */
    boolean isConfigAble();
    
    /**
     * 获取子权限<br/>
     * <功能详细描述>
     * 
     * @return String [返回类型说明]
     */
    List<Auth> getChilds();
}