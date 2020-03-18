/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月2日
 * <修改描述:>
 */
package com.tx.component.file.resource.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.core.exceptions.resource.ResourceAccessException;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.MessageUtils;

/**
 * 基于spring的Resource实现的资源加载器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LocalFileResourceLoader
        implements FileResourceLoader, InitializingBean {
    
    /** 目录 */
    private File folder;
    
    /** 目录文件资源 */
    private FileSystemResource catalogResource;
    
    /** <默认构造函数> */
    public LocalFileResourceLoader() {
        super();
    }
    
    /** <默认构造函数> */
    public LocalFileResourceLoader(File folder) {
        super();
        this.folder = folder;
        
        afterPropertiesSet();
    }
    
    /** <默认构造函数> */
    public LocalFileResourceLoader(String path) {
        super();
        this.folder = new File(path);
        
        afterPropertiesSet();
    }
    
    /**
     * @throws IOException 
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notNull(this.folder, "folder is null.");
        
        //初始化资源目录
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
        if (!this.folder.isDirectory()) {
            throw new ResourceAccessException("初始化文件资源目录异常，文件或已存在并且不为目录.");
        }
        this.catalogResource = new FileSystemResource(
                this.folder.getPath() + "/");
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
        
        FileSystemResource resource = (FileSystemResource) this.catalogResource
                .createRelative(fd.getRelativePath());
        FileResource fileResource = new LocalFileResource(fd, resource);
        return fileResource;
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
    public static class LocalFileResource implements FileResource {
        
        /** 文件定义 */
        private FileDefinition fileDefinition;
        
        /** 文件资源 */
        private FileSystemResource resource;
        
        /** <默认构造函数> */
        public LocalFileResource(FileDefinition fileDefinition,
                FileSystemResource resource) {
            super();
            this.fileDefinition = fileDefinition;
            this.resource = resource;
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
            return this.resource.exists();
        }
        
        /**
         * 
         */
        @Override
        public void delete() {
            File file = this.resource.getFile();
            //立即删除文件
            boolean flag = file.delete();
            if (!flag && file.exists()) {
                //如果删除文件失败，并且文件还存在时，抛出异常
                throw new ResourceAccessException(
                        MessageUtils.format("删除资源异常.path:{}.",
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
                if (!this.resource.exists()) {
                    //如果不存在，则检查文件所在的目录是否存在
                    File file = this.resource.getFile();
                    File folder = file.getParentFile();
                    if (!folder.exists()) {
                        //如果目录不存在，则创建对应目录
                        folder.mkdirs();
                    }
                    file.createNewFile();
                }
                try (OutputStream output = resource.getOutputStream()) {
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
                if (this.resource.exists()) {
                    throw new ResourceAccessException(
                            "新增资源错误，对应资源已经存在.如需覆盖请调用save方法.");
                }
                //获取文件对象并对其进行创建
                File file = this.resource.getFile();
                File folder = file.getParentFile();
                if (!folder.exists()) {
                    //如果目录不存在，则创建对应目录
                    folder.mkdirs();
                }
                file.createNewFile();
                //写入文件
                try (OutputStream output = resource.getOutputStream()) {
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
            InputStream ins = this.resource.getInputStream();
            return ins;
        }
    }
    
    /**
     * @param 对folder进行赋值
     */
    public void setFolder(File folder) {
        this.folder = folder;
    }
    
    //自动创建逻辑应该放到catalog的逻辑里，catalog是可以不考虑创建逻辑的
    public static void main(String[] args) throws IOException {
        String t1 = "E:/TEST/LOCAL/folder";
        File tf1 = new File(t1);
        LocalFileResourceLoader tl1 = new LocalFileResourceLoader(tf1);
        
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
