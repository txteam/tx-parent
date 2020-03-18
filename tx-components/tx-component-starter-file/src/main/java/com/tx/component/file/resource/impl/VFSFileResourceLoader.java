/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月11日
 * <修改描述:>
 */
package com.tx.component.file.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.core.exceptions.resource.ResourceAccessException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 * VFS文件资源加载器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class VFSFileResourceLoader
        implements FileResourceLoader, InitializingBean {
    
    private static FileSystemManager fsManager = null;
    
    static {
        try {
            fsManager = VFS.getManager();
        } catch (FileSystemException e) {
            throw new ResourceAccessException("初始化VFS引擎错误.");
        }
    }
    
    /** vfs路径 */
    private String path;
    
    /** vfs路径 */
    private FileObject catalogFileObject;
    
    /** <默认构造函数> */
    public VFSFileResourceLoader() {
        super();
    }
    
    /** <默认构造函数> */
    public VFSFileResourceLoader(String path) {
        super();
        this.path = path;
        
        afterPropertiesSet();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notEmpty(this.path, "path is empty.");
        
        try {
            this.catalogFileObject = fsManager.resolveFile(path);
            //初始化资源目录
            if (!this.catalogFileObject.exists()) {
                this.catalogFileObject.createFolder();
            }
            if (!this.catalogFileObject.isFolder()) {
                throw new ResourceAccessException("初始化文件资源目录异常，文件或已存在并且不为目录.");
            }
        } catch (FileSystemException e) {
            throw new ResourceAccessException("访问VFS资源异常.");
        }
    }
    
    /**
     * @param fd
     * @return
     */
    @Override
    public FileResource getResource(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileObject fileObject = null;
        try {
            fileObject = catalogFileObject.resolveFile(fd.getRelativePath());
        } catch (FileSystemException e) {
            throw new ResourceAccessException(
                    MessageUtils.format("创建VFS资源异常.Error:{}.", e.getMessage()),
                    e);
        }
        FileResource vfsResource = new VFSFileResource(fd, fileObject);
        return vfsResource;
    }
    
    /**
     * SFSRFileResource实现
     * Spring_FileSystemResource_FileResource
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class VFSFileResource implements FileResource {
        
        /** 文件定义 */
        private FileDefinition fileDefinition;
        
        /** 文件资源 */
        private FileObject fileObject;
        
        /** <默认构造函数> */
        public VFSFileResource(FileDefinition fileDefinition,
                FileObject fileObject) {
            super();
            this.fileDefinition = fileDefinition;
            this.fileObject = fileObject;
        }
        
        /**
         * @return
         */
        @Override
        public FileDefinition getFileDefinition() {
            return this.fileDefinition;
        }
        
        /**
         * @return
         */
        @Override
        public boolean exists() {
            try {
                return this.fileObject.exists();
            } catch (FileSystemException e) {
                throw new ResourceAccessException(
                        MessageUtils.format("通过VFS访问资源异常.path:{}.",
                                this.fileDefinition.getRelativePath()));
            }
        }
        
        /**
         * 
         */
        @Override
        public void delete() {
            //立即删除文件
            try {
                boolean flag = this.fileObject.delete();
                if (!flag && this.fileObject.exists()) {
                    //如果删除文件失败，并且文件还存在时，抛出异常
                    throw new ResourceAccessException(
                            MessageUtils.format("删除资源异常.path:{}.",
                                    this.fileDefinition.getRelativePath()));
                }
            } catch (FileSystemException e) {
                throw new ResourceAccessException(
                        MessageUtils.format("通过VFS删除资源异常.path:{}.",
                                this.fileDefinition.getRelativePath()));
            }
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void save(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            try {
                //如果文件不存在，则自动进行创建
                if (!this.fileObject.exists()) {
                    //如果不存在，则检查文件所在的目录是否存在
                    this.fileObject.createFile();
                }
                try (OutputStream output = this.fileObject.getContent()
                        .getOutputStream()) {
                    IOUtils.copy(inputStream, output);
                }
            } catch (IOException e) {
                throw new ResourceAccessException(
                        MessageUtils.format("保存资源异常.path:{}.",
                                this.fileDefinition.getRelativePath()));
            }
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void add(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            try {
                //如果文件不存在，则自动进行创建
                if (this.fileObject.exists()) {
                    throw new ResourceAccessException(
                            "新增资源错误，对应资源已经存在.如需覆盖请调用save方法.");
                }
                //获取文件对象并对其进行创建
                if (!this.fileObject.exists()) {
                    //如果不存在，则检查文件所在的目录是否存在
                    this.fileObject.createFile();
                }
                //写入文件
                try (OutputStream output = this.fileObject.getContent()
                        .getOutputStream()) {
                    IOUtils.copy(inputStream, output);
                }
            } catch (IOException e) {
                throw new ResourceAccessException(
                        MessageUtils.format("保存资源异常.path:{}.",
                                this.fileDefinition.getRelativePath()));
            }
        }
        
        /**
         * @return
         * @throws IOException
         */
        @Override
        public InputStream getInputStream() throws IOException {
            InputStream ins = this.fileObject.getContent().getInputStream();
            return ins;
        }
    }
    
    //自动创建逻辑应该放到catalog的逻辑里，catalog是可以不考虑创建逻辑的
    public static void main(String[] args) throws IOException {
        String t1 = "E:/TEST/VFS/folder";
        VFSFileResourceLoader tl1 = new VFSFileResourceLoader(t1);
        
        //注意：一下逻辑均未对inputStream进行关闭，实际编码中应该考虑
        //新增
        FileDefinition fd = new FileDefinition("2020/03/10/test.txt");
        FileResource fr = tl1.getResource(fd);
        if (fr.exists()) {
            fr.delete();
            System.out.println("delete......");
        }
        fr.add(IOUtils.toInputStream("test1.add", "UTF-8"));
        System.out.println("add......");
        System.out.println(IOUtils.toString(fr.getInputStream(), "UTF-8"));
        System.out.println("get......");
        fr.save(IOUtils.toInputStream("test1.save", "UTF-8"));
        System.out.println("save......");
        System.out.println(IOUtils.toString(fr.getInputStream(), "UTF-8"));
        System.out.println("get......");
        
        System.out.println("compelete.");
    }
}
