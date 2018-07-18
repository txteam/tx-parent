/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年12月21日
 * <修改描述:>
 */
package com.tx.component.file.driver.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import com.tx.component.file.driver.FileDefinitionResourceDriver;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.impl.SystemFileResource;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * 默认文件定义资源驱动<br/>
 *     file://somehost/someshare/afile.txt 
 *     /home/someuser/somedir
 * @author Administrator
 * @version [版本号, 2014年12月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SystemFileDefinitionResourceDriver implements
        FileDefinitionResourceDriver {
    
    /** 访问域名 */
    private String accessDomain = "";
    
    /** 存储逻辑 */
    private String path;
    
    //    /** 文件夹资源 */
    //    private Resource folderResource;
    
    /** <默认构造函数> */
    public SystemFileDefinitionResourceDriver() {
        super();
    }
    
    /**
     * 系统文件定义资源驱动器<br/>
     * <构造功能简述>
     * 
     * @param path
     * @throws IOException
     * 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public SystemFileDefinitionResourceDriver(String accessDomain,String path) {
        super();
        this.accessDomain = accessDomain;
        this.path = path;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.path, "location is empty.");
        this.path = StringUtils.cleanPath(this.path);//整理path中"\\"为"/"
        while (this.path.endsWith("/")) {
            //去除path中尾部存在的"/"
            this.path = this.path.substring(0, path.length() - 1);
        }
        AssertUtils.notEmpty(path, "path is empty.");
        if (!this.path.endsWith("/")) {
            //追加"/"
            this.path = path + "/";
        }
        
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            try {
                FileUtils.forceMkdir(new File(path));
            } catch (IOException e) {
                throw ExceptionWrapperUtils.wrapperIOException(e,
                        e.getMessage());
            }
        }
        //this.folderResource = new FileSystemResource(file);
    }
    
    /**
     * 获取文件定义对应的资源
     * @param fileDefinition
     * @return
     */
    @Override
    public FileResource getResource(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        String relativePath = fileDefinition.getRelativePath();
        String realPath = StringUtils.applyRelativePath(this.path, relativePath);
        realPath = StringUtils.cleanPath(realPath);
        FileSystemResource fsResource = new FileSystemResource(realPath);
        
        FileResource fdResource = new SystemFileResource(
                fileDefinition, fsResource);
        fileDefinition.setResource(fdResource);
        
        return fdResource;
    }
    
    /**
     * @return 返回 path
     */
    public String getPath() {
        return path;
    }
    
    /**
     * @param 对path进行赋值
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return 返回 accessDomain
     */
    public String getAccessDomain() {
        return accessDomain;
    }

    /**
     * @param 对accessDomain进行赋值
     */
    public void setAccessDomain(String accessDomain) {
        this.accessDomain = accessDomain;
    }
    
    //public static void main(String[] args) {
    //    String path = "d:/test\\test1/test2/test3//";
    //    path = StringUtils.cleanPath(path);
    //    while (path.endsWith("/")) {
    //        path = path.substring(0, path.length() - 1);
    //    }
    //    System.out.println(path);
    //    File file = new File(path);
    //    if (!file.exists() && !file.isDirectory()) {
    //        try {
    //            FileUtils.forceMkdir(new File(path));
    //        } catch (IOException e) {
    //            throw ExceptionWrapperUtils.wrapperIOException(e,
    //                    e.getMessage());
    //        }
    //    }
    //}
}
