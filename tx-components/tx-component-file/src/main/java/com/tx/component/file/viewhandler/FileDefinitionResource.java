/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月19日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 文件定义资源<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
class FileDefinitionResource implements Resource {
    
    /** 文件资源 */
    private FileResource fileResource;
    
    /** 文件定义 */
    private FileDefinition fileDefinition;
    
    /** <默认构造函数> */
    public FileDefinitionResource(FileDefinition fileDefinition,
            FileResource fileResource) {
        super();
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notNull(fileResource, "fileResource is null.");
        
        this.fileDefinition = fileDefinition;
        this.fileResource = fileResource;
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return this.fileResource.getInputStream();
    }
    
    /**
     * @return
     */
    @Override
    public boolean exists() {
        return this.fileResource.exists();
    }
    
    /**
     * @return
     */
    @Override
    public String getFilename() {
        return this.fileDefinition.getFilename();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public long lastModified() throws IOException {
        return this.fileDefinition.getLastUpdateDate().getTime();
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public long contentLength() throws IOException {
        InputStream is = this.getInputStream();
        Assert.state(is != null, "resource input stream must not be null");
        try {
            long size = 0;
            byte[] buf = new byte[255];
            int read;
            while ((read = is.read(buf)) != -1) {
                size += read;
            }
            return size;
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public String getDescription() {
        return "";
    }
    
    /**
     * @return
     */
    @Override
    public boolean isReadable() {
        return true;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isOpen() {
        return false;
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public URL getURL() throws IOException {
        return null;
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public URI getURI() throws IOException {
        return null;
    }
    
    /**
     * @param relativePath
     * @return
     * @throws IOException
     */
    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return null;
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public File getFile() throws IOException {
        return null;
    }
}
