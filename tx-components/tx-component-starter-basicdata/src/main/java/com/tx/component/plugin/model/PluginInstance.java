/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.plugin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 插件实例<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "bd_plugin_instance")
public class PluginInstance implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5855434622179526250L;
    
    /** 插件实例唯一键 */
    private String id;
    
    /** 插件配置前置参数 */
    private String catalog;
    
    /** 插件配置前置参数 */
    private String prefix;
    
    /** 插件实例唯一名 */
    private String name;
    
    /** 插件版本 */
    private String version;
    
    /** 插件作者 */
    private String author;
    
    /** 插件是否已经进行安装 */
    private boolean installed = false;
    
    /** 是否已启用： 安装完成后需要完成配置后才能够启用，启动期间需要检查配置是否合理，如果为启用的 */
    private boolean valid;
    
    /** 优先级 */
    private int priority;
    
    /** 备注 */
    private String remark;
    
    /** 获取插件描述的url */
    private String describeUrl;
    
    /** 获取插件安装的url */
    private String installUrl;
    
    /** 获取插件卸载的url */
    private String uninstallUrl;
    
    /** 获取插件配置的url */
    private String settingUrl;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;
    
    /** 创建时间 */
    private Date createDate;

    /**
     * @return 返回 id
     */
    public String getId() {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * @return 返回 prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param 对prefix进行赋值
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return 返回 name
     */
    public String getName() {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 返回 version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param 对version进行赋值
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return 返回 author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param 对author进行赋值
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return 返回 installed
     */
    public boolean isInstalled() {
        return installed;
    }

    /**
     * @param 对installed进行赋值
     */
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    /**
     * @return 返回 valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param 对valid进行赋值
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return 返回 priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param 对priority进行赋值
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return 返回 remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param 对remark进行赋值
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return 返回 describeUrl
     */
    public String getDescribeUrl() {
        return describeUrl;
    }

    /**
     * @param 对describeUrl进行赋值
     */
    public void setDescribeUrl(String describeUrl) {
        this.describeUrl = describeUrl;
    }

    /**
     * @return 返回 installUrl
     */
    public String getInstallUrl() {
        return installUrl;
    }

    /**
     * @param 对installUrl进行赋值
     */
    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    /**
     * @return 返回 uninstallUrl
     */
    public String getUninstallUrl() {
        return uninstallUrl;
    }

    /**
     * @param 对uninstallUrl进行赋值
     */
    public void setUninstallUrl(String uninstallUrl) {
        this.uninstallUrl = uninstallUrl;
    }

    /**
     * @return 返回 settingUrl
     */
    public String getSettingUrl() {
        return settingUrl;
    }

    /**
     * @param 对settingUrl进行赋值
     */
    public void setSettingUrl(String settingUrl) {
        this.settingUrl = settingUrl;
    }

    /**
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param 对lastUpdateDate进行赋值
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * @return 返回 createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
