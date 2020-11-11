/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年7月4日
 * <修改描述:>
 */
package com.tx.component.file.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;

import com.tx.core.support.json.JSONAttributesSupport;

/**
 * 文件定义<br/>
 * <功能详细描述>
 * 
 * @author Tim.pqy
 * @version [版本号, 2018年7月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Entity
@Table(name = "FC_FILE_DEFINITION")
public class FileDefinition implements JSONAttributesSupport {
    
    /** 文件的存储id */
    @Id
    private String id;
    
    /** 所属模块:module */
    @Column(nullable = false, length = 64)
    private String module;
    
    /** 所属目录 */
    @Column(nullable = false, length = 64)
    private String catalog;
    
    /** 相对于文件容器的相对路径 */
    @Column(nullable = false, length = 256)
    private String relativePath;
    
    /** 所属目录 */
    @Column(name = "folderId", nullable = true, length = 64)
    private FolderDefinition folder;
    
    /** 文件名称 */
    @Column(nullable = true, length = 128)
    private String filename;
    
    /** 文件的后缀名 */
    @Column(nullable = true, length = 128)
    private String filenameExtension;
    
    /** 关联业务类型 */
    @Column(nullable = true, length = 64)
    private String refType;
    
    /** 关联业务类型的数据项id */
    @Column(nullable = true, length = 64)
    private String refId;
    
    /** 文件的额外存储属性 */
    @Column(nullable = true, length = 500)
    private String attributes;
    
    /** 是否删除：文件的删除包含两种，一种是弱删除，及修改deleted=true,然后等待系统回收，另外一种是直接真正删除 */
    private boolean deleted = false;
    
    /** 删除时间 */
    private Date deleteDate;
    
    /** 最后跟新时间 */
    @Column(nullable = false, length = 500)
    private Date lastUpdateDate;
    
    /** 创建时间 */
    @Column(nullable = false, length = 500)
    private Date createDate;
    
    /** <默认构造函数> */
    public FileDefinition() {
        super();
    }
    
    /** <默认构造函数> */
    public FileDefinition(String relativePath) {
        this(null, relativePath);
    }
    
    /** <默认构造函数> */
    public FileDefinition(String catalog, String relativePath) {
        super();
        this.catalog = catalog;
        this.relativePath = relativePath;
    }
    
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
        if (StringUtils.isEmpty(this.filename)) {
            return StringUtils.getFilename(this.relativePath);
        }
        return this.filename;
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
        if (StringUtils.isEmpty(this.filenameExtension)) {
            return StringUtils.getFilenameExtension(this.relativePath);
        }
        return this.filenameExtension;
    }
    
    /**
     * @param 对filenameExtension进行赋值
     */
    public void setFilenameExtension(String filenameExtension) {
        this.filenameExtension = filenameExtension;
    }
    
    /**
     * @return 返回 refType
     */
    public String getRefType() {
        return refType;
    }
    
    /**
     * @param 对refType进行赋值
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
    
    /**
     * @return 返回 refId
     */
    public String getRefId() {
        return refId;
    }
    
    /**
     * @param 对refId进行赋值
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * @return 返回 attributes
     */
    @Override
    public String getAttributes() {
        return attributes;
    }
    
    /**
     * @param 对attributes进行赋值
     */
    @Override
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    /**
     * @return 返回 deleted
     */
    public boolean isDeleted() {
        return deleted;
    }
    
    /**
     * @param 对deleted进行赋值
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
