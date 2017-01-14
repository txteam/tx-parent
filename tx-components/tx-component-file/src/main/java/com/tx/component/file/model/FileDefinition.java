/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年7月4日
 * <修改描述:>
 */
package com.tx.component.file.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tx.component.file.resource.FileDefinitionResource;
import com.tx.core.jdbc.sqlsource.annotation.UpdateAble;

/**
 * 文件定义<br/>
 * <功能详细描述>
 * 
 * @author Rain.he
 * @version [版本号, 2015年3月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Entity
@Table(name = "core_file_definition")
public class FileDefinition {
    
    /** 文件的存储id */
    @Id
    private String id;
    
    /** 所属系统：容器初始化时唯一确定 */
    private String system;
    
    /** 所属模块:容器初始化时唯一确定，如果一个系统需要支撑多个模块 ,仅需要根据不同的module生成多个FileContext实现即可 */
    private String module;
    
    /** 相对于文件容器的相对路径 */
    @UpdateAble
    private String relativePath;
    
    /** 文件名称 */
    @UpdateAble
    private String filename;
    
    /** 文件的后缀名 */
    @UpdateAble
    private String filenameExtension;
    
    /** 访问应用的url如果：写入的策略不支持url可以为空 */
    private String viewUrl;
    
    /** 删除时间 */
    @UpdateAble
    private Date deleteDate;
    
    /** 最后跟新时间 */
    @UpdateAble
    private Date lastUpdateDate;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 文件访问的真正路径：由FileContext加工后才会生成，非持久化字段 */
    @Transient
    private FileDefinitionResource resource;
    
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
     * @return 返回 system
     */
    public String getSystem() {
        return system;
    }
    
    /**
     * @param 对system进行赋值
     */
    public void setSystem(String system) {
        this.system = system;
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
     * @return 返回 relativePath
     */
    public String getRelativePath() {
        return relativePath;
    }
    
    /**
     * @param 对relativePath进行赋值
     */
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
    
    /**
     * @return 返回 filename
     */
    public String getFilename() {
        return filename;
    }
    
    /**
     * @param 对filename进行赋值
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    /**
     * @return 返回 filenameExtension
     */
    public String getFilenameExtension() {
        return filenameExtension;
    }
    
    /**
     * @param 对filenameExtension进行赋值
     */
    public void setFilenameExtension(String filenameExtension) {
        this.filenameExtension = filenameExtension;
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
    
    /**
     * @return 返回 deleteDate
     */
    public Date getDeleteDate() {
        return deleteDate;
    }
    
    /**
     * @param 对deleteDate进行赋值
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
    
    /**
     * @return 返回 resource
     */
    public FileDefinitionResource getResource() {
        return resource;
    }
    
    /**
     * @param 对resource进行赋值
     */
    public void setResource(FileDefinitionResource resource) {
        this.resource = resource;
    }
}
