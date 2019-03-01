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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.AbstractFileResource;
import com.tx.core.exceptions.resource.ResourceIsExistException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;
import com.tx.core.util.MessageUtils;

/**
 * 默认的文件定义资源的实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年12月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SystemFileResource extends AbstractFileResource {
    
    /** 系统文件资源 */
    private FileSystemResource fileSystemResource;
    
    /** <默认构造函数> */
    public SystemFileResource(FileDefinition fileDefinition,
            FileSystemResource fileSystemResource) {
        super(fileDefinition);
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
        String viewUrl = "/filecontext/resource/" + this.fileDefinition.getId();
        return viewUrl;
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
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = fileSystemResource.getInputStream();
        return inputStream;
    }
    
    /**
     * 存储输入流,如果资源已经存在，则覆盖已存在的资源<br/>
     * @param inputStream
     */
    @Override
    public void doSave(InputStream inputStream) {
        AssertUtils.notNull(inputStream, "inputStream is null.");
        
        //如果对应的文件资源已经存在，则进行替换
        try {
            if (!this.fileSystemResource.exists()) {
                //如果不存在，则检查文件所在的目录是否存在
                File newFile = this.fileSystemResource.getFile();
                File fileFolder = newFile.getParentFile();
                if (!fileFolder.exists()) {
                    //如果目录不存在，则创建对应目录
                    FileUtils.forceMkdir(fileFolder);
                }
                newFile.createNewFile();
            }
            writeFile(this.fileSystemResource, inputStream);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "save file error.id:{},relativePath:{}",
                    new Object[] { this.fileDefinition.getId(),
                            this.fileDefinition.getRelativePath() });
        }
    }
    
    /**
     * 新增保存资源，如果资源已经存在，则抛出资源已经存在的异常<br/>
     * @param inputStream
     */
    @Override
    public void doAdd(InputStream inputStream) {
        AssertUtils.notNull(inputStream, "inputStream is null.");
        
        //如果对应的文件资源已经存在，则进行替换
        try {
            //如果对应的文件资源已经存在，则进行替换
            if (this.fileSystemResource.exists()) {
                throw new ResourceIsExistException(MessageUtils.format(
                        "resource is exist. path:{}",
                        new Object[] { fileDefinition.getRelativePath() }));
            }
            writeFile(this.fileSystemResource, inputStream);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "save file error.id:{},relativePath:{}",
                    new Object[] { this.fileDefinition.getId(),
                            this.fileDefinition.getRelativePath() });
        }
    }
    
    /**
     * 删除对应的文件<br/>
     */
    @Override
    public void doDelete() {
        File file = this.fileSystemResource.getFile();
        file.deleteOnExit();
    }
    
    /**
     * 将输入流写入文件中<br/>
     * @param file 文件
     * @param input 文件流
     * 
     * @return void
     * @exception IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    private void writeFile(FileSystemResource outputResource,
            InputStream inputStream) throws IOException {
        AssertUtils.notNull(outputResource, "outputResource is null.");
        AssertUtils.notNull(inputStream, "inputStream is null.");
        
        try (OutputStream output = outputResource.getOutputStream()) {
            IOUtils.copy(inputStream, output);
        }
    }
}
