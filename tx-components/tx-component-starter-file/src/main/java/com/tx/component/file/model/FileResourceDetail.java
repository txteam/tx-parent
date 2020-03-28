/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月19日
 * <修改描述:>
 */
package com.tx.component.file.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tx.component.file.catalog.FileCatalogPermissionEnum;
import com.tx.component.file.resource.FileResource;

/**
 * 文件资源详情<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileResourceDetail {
    
    /** 文件归属目录 */
    private String catalog;
    
    /** 访问路径 */
    private String viewUrl;
    
    /** 文件访问授权 */
    @JsonIgnore
    private FileCatalogPermissionEnum permission;
    
    /** 文件访问的真正路径：由FileContext加工后才会生成，非持久化字段 */
    @JsonIgnore
    private FileResource resource;

    /**
     * @return 返回 catalog
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * @param 对catalog进行赋值
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * @return 返回 viewUrl
     */
    public String getViewUrl() {
        return viewUrl;
    }

    /**
     * @param 对viewUrl进行赋值
     */
    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    /**
     * @return 返回 permission
     */
    public FileCatalogPermissionEnum getPermission() {
        return permission;
    }

    /**
     * @param 对permission进行赋值
     */
    public void setPermission(FileCatalogPermissionEnum permission) {
        this.permission = permission;
    }

    /**
     * @return 返回 resource
     */
    public FileResource getResource() {
        return resource;
    }

    /**
     * @param 对resource进行赋值
     */
    public void setResource(FileResource resource) {
        this.resource = resource;
    }
}
