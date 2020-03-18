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

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
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
public class LocalAwareFileResourceLoader
        implements FileResourceLoader, InitializingBean {
    
    /** 真实的资源加载器 */
    private FileResourceLoader resourceLoader;
    
    /** 目录 */
    private File folder;
    
    /** 目录文件资源 */
    private FileSystemResource localResource;
    
    /** <默认构造函数> */
    public LocalAwareFileResourceLoader() {
        super();
    }
    
    /** <默认构造函数> */
    public LocalAwareFileResourceLoader(File folder,
            FileResourceLoader resourceLoader) {
        super();
        this.folder = folder;
        this.resourceLoader = resourceLoader;
        
        afterPropertiesSet();
    }
    
    /** <默认构造函数> */
    public LocalAwareFileResourceLoader(String path,
            FileResourceLoader resourceLoader) {
        super();
        this.folder = new File(path);
        this.resourceLoader = resourceLoader;
        
        afterPropertiesSet();
    }
    
    /**
     * @throws IOException 
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notNull(this.folder, "folder is null.");
        AssertUtils.notNull(this.resourceLoader, "resourceLoader is null.");
        
        //初始化资源目录
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
        if (!this.folder.isDirectory()) {
            throw new ResourceAccessException("初始化文件资源目录异常，文件或已存在并且不为目录.");
        }
        this.localResource = new FileSystemResource(
                this.folder.getPath() + "/");
    }
    
    /**
     * @param fd
     * @return
     */
    @Override
    public FileResource getResource(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        
        //本地文件资源
        FileSystemResource localFileResource = (FileSystemResource) this.localResource
                .createRelative(fd.getRelativePath());
        //远端文件资源
        FileResource fileResource = this.resourceLoader.getResource(fd);
        //构建环绕资源
        FileResource localCacheFileResource = new LocalCacheFileResource(fd,
                localFileResource, fileResource);
        return localCacheFileResource;
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
    public static class LocalCacheFileResource implements FileResource {
        
        /** 文件定义 */
        private FileDefinition fileDefinition;
        
        /** 文件资源 */
        private FileSystemResource localFileResource;
        
        /** 文件定义 */
        private FileResource fileResource;
        
        /** <默认构造函数> */
        public LocalCacheFileResource(FileDefinition fileDefinition,
                FileSystemResource localFileResource,
                FileResource fileResource) {
            super();
            this.fileDefinition = fileDefinition;
            this.localFileResource = localFileResource;
            this.fileResource = fileResource;
        }
        
        /**
         * @return
         */
        @Override
        public FileDefinition getFileDefinition() {
            return this.fileResource.getFileDefinition();
        }
        
        /**
         * @return
         */
        @Override
        public boolean exists() {
            if (this.localFileResource.exists()) {
                return true;
            }
            return this.fileResource.exists();
        }
        
        /**
         * 
         */
        @Override
        public void delete() {
            File file = this.localFileResource.getFile();
            //立即删除文件
            boolean flag = file.delete();
            if (!flag && file.exists()) {
                //如果删除文件失败，并且文件还存在时，抛出异常
                throw new ResourceAccessException(
                        MessageUtils.format("删除资源异常.path:{}.",
                                this.fileDefinition.getRelativePath()));
            }
            //代用被代理方文件资源删除
            this.fileResource.delete();
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void save(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            //调用代理方存储
            this.fileResource.save(inputStream);
            //再调用本地存储
            try {
                //如果文件不存在，则自动进行创建
                if (!this.localFileResource.exists()) {
                    //如果不存在，则检查文件所在的目录是否存在
                    File file = this.localFileResource.getFile();
                    File folder = file.getParentFile();
                    if (!folder.exists()) {
                        //如果目录不存在，则创建对应目录
                        folder.mkdirs();
                    }
                    file.createNewFile();
                }
                inputStream.reset();//因为文件已经被使用了所以需要重新reset
                try (OutputStream output = localFileResource
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
            
            //调用代理方存储
            this.fileResource.add(inputStream);
            //再调用本地存储
            try {
                //如果文件不存在，则自动进行创建
                if (this.localFileResource.exists()) {
                    throw new ResourceAccessException(
                            "新增资源错误，对应资源已经存在.如需覆盖请调用save方法.");
                }
                //获取文件对象并对其进行创建
                File file = this.localFileResource.getFile();
                File folder = file.getParentFile();
                if (!folder.exists()) {
                    //如果目录不存在，则创建对应目录
                    folder.mkdirs();
                }
                file.createNewFile();
                //写入文件
                inputStream.reset();//因为文件已经被使用了所以需要重新reset
                try (OutputStream output = localFileResource
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
            InputStream ins = null;
            if (this.localFileResource.exists()) {
                ins = this.localFileResource.getInputStream();
                return ins;
            }
            ins = this.fileResource.getInputStream();
            
            //获取文件对象并对其进行创建
            File file = this.localFileResource.getFile();
            File folder = file.getParentFile();
            if (!folder.exists()) {
                //如果目录不存在，则创建对应目录
                folder.mkdirs();
            }
            file.createNewFile();
            //写入文件
            try (OutputStream output = localFileResource.getOutputStream()) {
                IOUtils.copy(ins, output);
            }
            ins.reset();//因为文件已经被使用了所以需要重新reset
            return ins;
        }
    }
    
    /**
     * @param 对folder进行赋值
     */
    public void setFolder(File folder) {
        this.folder = folder;
    }
    
    /**
     * @param 对resourceLoader进行赋值
     */
    public void setResourceLoader(FileResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    //自动创建逻辑应该放到catalog的逻辑里，catalog是可以不考虑创建逻辑的
    public static void main(String[] args) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(
                "http://oss-cn-beijing.aliyuncs.com",
                "LTAIqiYuWr9WAkWa",
                "e4tyMfOU9IGiiDwWlc6gidT8siQek1");
        OSSFileResourceLoader ossloader = new OSSFileResourceLoader(ossClient,
                "txwebdemo");
        String t1 = "E:/TEST/LOCAL_AWARE/folder";
        File tf1 = new File(t1);
        LocalAwareFileResourceLoader loader = new LocalAwareFileResourceLoader(
                tf1, ossloader);
        
        //注意：一下逻辑均未对inputStream进行关闭，实际编码中应该考虑
        //新增
        FileDefinition fd = new FileDefinition(
                "localaware/2020/03/10/test.txt");
        FileResource fr = loader.getResource(fd);
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
