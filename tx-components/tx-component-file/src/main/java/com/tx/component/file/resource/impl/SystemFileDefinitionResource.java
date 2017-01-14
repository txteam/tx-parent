/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.resource.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileDefinitionResource;
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
public class SystemFileDefinitionResource implements FileDefinitionResource {
    
    /** 文件定义 */
    private FileDefinition fileDefinition;
    
    /** 系统文件资源 */
    private FileSystemResource fileSystemResource;
    
    /** <默认构造函数> */
    public SystemFileDefinitionResource(FileDefinition fileDefinition,
            FileSystemResource fileSystemResource) {
        super();
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notNull(fileSystemResource, "fileSystemResource is null.");
        
        this.fileDefinition = fileDefinition;
        this.fileSystemResource = fileSystemResource;
    }
    
    /**
     * @return
     */
    @Override
    public String getViewUrl() {
        return "fileId=" + fileDefinition.getId();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isWritable() {
        return fileSystemResource.isWritable();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        return fileSystemResource.getOutputStream();
    }
    
    /**
     * @return
     */
    @Override
    public boolean exists() {
        return fileSystemResource.exists();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isReadable() {
        return fileSystemResource.isReadable();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isOpen() {
        return fileSystemResource.isOpen();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public URL getURL() throws IOException {
        return fileSystemResource.getURL();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public URI getURI() throws IOException {
        return fileSystemResource.getURI();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public File getFile() throws IOException {
        return fileSystemResource.getFile();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public long contentLength() throws IOException {
        return fileSystemResource.contentLength();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public long lastModified() throws IOException {
        return fileSystemResource.lastModified();
    }
    
    /**
     * @param relativePath
     * @return
     * @throws IOException
     */
    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return fileSystemResource.createRelative(relativePath);
    }
    
    /**
     * @return
     */
    @Override
    public String getFilename() {
        return fileSystemResource.getFilename();
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return fileSystemResource.getDescription();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return fileSystemResource.getInputStream();
    }
}
