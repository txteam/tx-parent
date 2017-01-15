/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月14日
 * <修改描述:>
 */
package com.tx.component.file.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.tx.component.file.model.ReadWritePermissionEnum;


 /**
  * 文件模块配置<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2017年1月14日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamAlias("file_module")
public class FileModuleCfg {
    
    /** 模块名 */
    @XStreamAsAttribute
    private String module;
    
    /** 读写权限 */
    private ReadWritePermissionEnum permission;
    
    /** 文件定义资源驱动 */
    private String driver;

    /**
     * @return 返回 module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param 对module进行赋值
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return 返回 permission
     */
    public ReadWritePermissionEnum getPermission() {
        return permission;
    }

    /**
     * @param 对permission进行赋值
     */
    public void setPermission(ReadWritePermissionEnum permission) {
        this.permission = permission;
    }

    /**
     * @return 返回 driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param 对driver进行赋值
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
}
