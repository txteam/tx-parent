/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.context.driver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import com.tx.component.file.context.FileDefinitionResource;
import com.tx.component.file.context.FileDefinitionResourceDriver;
import com.tx.component.file.context.resource.DefaultFileDefinitionResource;
import com.tx.component.file.model.FileDefinition;
import com.tx.core.exceptions.io.ResourceIsExistException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 默认文件定义资源驱动<br/>
 * 
 * @author Administrator
 * @version [版本号, 2014年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DefaultFileDefinitionResourceDriver implements
        FileDefinitionResourceDriver {
    
    private String path;
    
    /**
     * <默认构造函数><br/>
     * <构造功能简述>
     * 
     * @param path
     * @throws IOException
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public DefaultFileDefinitionResourceDriver(String path) {
        super();
        this.path = path;
        AssertUtils.notEmpty(path, "location is empty.");
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            try {
                FileUtils.forceMkdir(new File(path).getParentFile());
            } catch (IOException e) {
                throw ExceptionWrapperUtils.wrapperIOException(e,
                        e.getMessage());
            }
        }
    }
    
    @Override
    public FileDefinitionResource getResource(FileDefinition fileDefinition) {
        validateFileDefinition(fileDefinition);
        
        String relativePath = fileDefinition.getRelativePath();
        
        FileSystemResource resource = createRelative(relativePath);
        
        FileDefinitionResource resResource = new DefaultFileDefinitionResource(
                resource);
        fileDefinition.setResource(resResource);
        return resResource;
    }
    
    @Override
    public FileDefinitionResource add(FileDefinition fileDefinition,
            InputStream input) {
        validateFileDefinition(fileDefinition);
        
        String relativePath = fileDefinition.getRelativePath();
        FileSystemResource resource = createRelative(relativePath);
        try {
            //如果对应的文件资源已经存在，则进行替换
            if (resource.exists()) {
                throw new ResourceIsExistException(
                        "resource is exist. path:{}",
                        new Object[] { relativePath });
            }
            writeFile(resource.getFile(), input);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "save file error.filePath:{}",
                    relativePath);
        }
        FileDefinitionResource resFileDefinitionResource = new DefaultFileDefinitionResource(
                resource);
        return resFileDefinitionResource;
    }
    
    @Override
    public FileDefinitionResource save(FileDefinition fileDefinition,
            InputStream input) {
        validateFileDefinition(fileDefinition); // 检查文件合法性
        
        String relativePath = fileDefinition.getRelativePath(); // 存储路径
        FileSystemResource resource = createRelative(relativePath);
        //如果对应的文件资源已经存在，则进行替换
        try {
            if (!resource.exists()) {
                //如果不存在，则检查文件所在的目录是否存在
                File newFile = resource.getFile();
                File fileFolder = newFile.getParentFile();
                if (!fileFolder.exists()) {
                    //如果目录不存在，则创建对应目录
                    FileUtils.forceMkdir(fileFolder);
                }
                newFile.createNewFile();
            }
            writeFile(resource.getFile(), input);
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "save file error.filePath:{}",
                    relativePath);
        }
        FileDefinitionResource resFileDefinitionResource = new DefaultFileDefinitionResource(
                resource);
        return resFileDefinitionResource;
    }
    
    @Override
    public void delete(FileDefinition fileDefinition) {
        validateFileDefinition(fileDefinition);
        
        String relativePath = fileDefinition.getRelativePath();
        FileSystemResource resource = createRelative(relativePath);
        FileUtils.deleteQuietly(resource.getFile());
    }
    
    /**
     * 将输入流写入文件中<br/>
     * 
     * @param file 文件
     * @param input 文件流
     * 
     * @return void
     * @exception IOException IO异常
     * @see [类、类#方法、类#成员]
     */
    private void writeFile(File file, InputStream input) throws IOException {
        AssertUtils.notNull(file, "file is null.");
        AssertUtils.isExist(file, "file is not exist.");
        
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(output);
        }
    }
    
    /**
     * 校验文件定义的合法性<br/>
     * 
     * @param fileDefinition 文件定义
     * 
     * @return void
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private void validateFileDefinition(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getRelativePath(),
                "fileDefinition.relativePath is empty.");
    }
    
    /**
     * 
     * 根据相对容器的绝对路径创建文件资源<br/>
     * <功能详细描述>
     * 
     * @param relativePath 相对容器的绝对路
     * 
     * @return FileSystemResource 文件资源
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileSystemResource createRelative(String relativePath) {
        String pathToUse = StringUtils.applyRelativePath(this.path,
                relativePath);
        return new FileSystemResource(pathToUse);
    }
}
