/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.resource.impl;

import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.AbstractFileDefinitionResource;
import com.tx.core.exceptions.io.ResourceIsExistException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 默认的文件定义资源的实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OSSFileDefinitionResource extends AbstractFileDefinitionResource {
    
    /** bucketName */
    private String bucketName;
    
    /** ossClient */
    private OSSClient ossClient;
    
    /** <默认构造函数> */
    public OSSFileDefinitionResource(FileDefinition fileDefinition,
            String bucketName, OSSClient ossClient) {
        super(fileDefinition);
        AssertUtils.notEmpty(bucketName, "bucketName is empty.");
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notNull(ossClient, "ossClient is null.");
        
        this.bucketName = bucketName;
        this.ossClient = ossClient;
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        OSSObject ossObject = this.ossClient.getObject(this.bucketName,
                this.fileDefinition.getRelativePath());
        return ossObject.getObjectContent();
    }
    
    /**
     * @return
     */
    @Override
    public boolean exists() {
        boolean isExists = this.ossClient.doesObjectExist(this.bucketName,
                this.fileDefinition.getRelativePath());
        return isExists;
    }
    
    /**
     * @param inputStream
     */
    @Override
    public void doSave(InputStream inputStream) {
        AssertUtils.notNull(inputStream, "inputStream is null.");
        
        this.ossClient.putObject(this.bucketName,
                this.fileDefinition.getRelativePath(),
                inputStream);
    }
    
    /**
     * @param inputStream
     */
    @Override
    public void doAdd(InputStream inputStream) {
        AssertUtils.notNull(inputStream, "inputStream is null.");
        
        if (this.ossClient.doesObjectExist(this.bucketName,
                this.fileDefinition.getRelativePath())) {
            throw new ResourceIsExistException("resource is exist. path:{}",
                    new Object[] { fileDefinition.getRelativePath() });
        }
        
        this.ossClient.putObject(this.bucketName,
                this.fileDefinition.getRelativePath(),
                inputStream);
    }
    
    /**
     * 
     */
    @Override
    public void doDelete() {
        this.ossClient.deleteObject(this.bucketName,
                this.fileDefinition.getRelativePath());
    }
    
}
