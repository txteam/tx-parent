/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月11日
 * <修改描述:>
 */
package com.tx.component.file.model;

import com.tx.component.file.driver.FileDefinitionResourceDriver;

/**
 * 文件模块配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileModule {
    
    /** 文件所属模块 */
    private String module;
    
    /** 读写权限 */
    private ReadWritePermissionEnum permission;
    
    /** 文件定义资源驱动 */
    private FileDefinitionResourceDriver driver;
    
    /** <默认构造函数> */
    public FileModule() {
        super();
    }
    
    /** <默认构造函数> */
    public FileModule(String module, ReadWritePermissionEnum permission,
            FileDefinitionResourceDriver driver) {
        super();
        this.module = module;
        this.permission = permission;
        this.driver = driver;
    }
    
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
     * @return 返回 driver
     */
    public FileDefinitionResourceDriver getDriver() {
        return driver;
    }
    
    /**
     * @param 对driver进行赋值
     */
    public void setDriver(FileDefinitionResourceDriver driver) {
        this.driver = driver;
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
}
