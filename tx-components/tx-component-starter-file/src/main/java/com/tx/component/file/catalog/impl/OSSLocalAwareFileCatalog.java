/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月13日
 * <修改描述:>
 */
package com.tx.component.file.catalog.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tx.component.file.catalog.FileCatalog;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.component.file.resource.impl.LocalAwareFileResourceLoader;
import com.tx.component.file.resource.impl.OSSFileResourceLoader;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 问题文件目录<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OSSLocalAwareFileCatalog implements FileCatalog, InitializingBean {
    
    /** 目录 */
    private String catalog;
    
    /** 本地路径 */
    private String path;
    
    /** 路径 */
    private String endpoint;
    
    /** 路径 */
    private String accessKeyId;
    
    /** 路径 */
    private String secretAccessKey;
    
    /** 目录 */
    private String bucketName;
    
    /** 文件资源加载器 */
    private FileResourceLoader fileResourceLoader;
    
    /** <默认构造函数> */
    public OSSLocalAwareFileCatalog() {
        super();
    }
    
    /** <默认构造函数> */
    public OSSLocalAwareFileCatalog(String catalog, String path,
            String endpoint, String accessKeyId, String secretAccessKey,
            String bucketName) {
        super();
        
        this.catalog = catalog;
        
        this.path = path;
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.bucketName = bucketName;
        
        afterPropertiesSet();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notEmpty(this.catalog, "catalog is empty.");
        AssertUtils.notEmpty(this.path, "path is empty.");
        AssertUtils.notEmpty(this.endpoint, "endpoint is empty.");
        AssertUtils.notEmpty(this.accessKeyId, "accessKeyId is empty.");
        AssertUtils.notEmpty(this.secretAccessKey, "secretAccessKey is empty.");
        AssertUtils.notEmpty(this.bucketName, "bucketName is empty.");
        
        OSS ossClient = new OSSClientBuilder().build(this.endpoint,
                this.accessKeyId,
                this.secretAccessKey);
        FileResourceLoader ossFileResourceLoader = new OSSFileResourceLoader(
                ossClient, this.bucketName);
        this.fileResourceLoader = new LocalAwareFileResourceLoader(this.path,
                ossFileResourceLoader);
    }
    
    /**
     * @return
     */
    @Override
    public String getCatalog() {
        return this.catalog;
    }
    
    /**
     * @return
     */
    @Override
    public FileResourceLoader getFileResourceLoader() {
        return this.fileResourceLoader;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
    
    /**
     * @param 对fileResourceLoader进行赋值
     */
    public void setFileResourceLoader(FileResourceLoader fileResourceLoader) {
        this.fileResourceLoader = fileResourceLoader;
    }
    
    /**
     * @param 对catalog进行赋值
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    
    /**
     * @param 对endpoint进行赋值
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    /**
     * @param 对accessKeyId进行赋值
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    
    /**
     * @param 对secretAccessKey进行赋值
     */
    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
    
    /**
     * @param 对path进行赋值
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    /**
     * @param 对bucketName进行赋值
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
