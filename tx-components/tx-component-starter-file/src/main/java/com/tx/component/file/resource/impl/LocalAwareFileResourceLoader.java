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
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.component.file.resource.FolderResource;
import com.tx.component.file.util.FileContextUtils;
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
    
    /** 日志记录句柄 */
    protected static Logger logger = LoggerFactory
            .getLogger(FileResourceLoader.class);
    
    /** 真实的资源加载器 */
    private FileResourceLoader resourceLoader;
    
    /** 目录 */
    private File folder;
    
    /** 目录文件资源 */
    private FileSystemResource catalogLocalResource;
    
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
        this.catalogLocalResource = new FileSystemResource(
                this.folder.getPath() + "/");
    }
    
    /**
     * @param fd
     * @return
     */
    @Override
    public FileResource getFile(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        //预处理关联路径
        relativePath = FileContextUtils.handleRelativePath(relativePath);
        //本地文件资源
        FileSystemResource localFileResource = (FileSystemResource) this.catalogLocalResource
                .createRelative(relativePath);
        //远端文件资源
        FileResource fileResource = this.resourceLoader.getFile(relativePath);
        
        //构建环绕资源
        FileResource localAwareFileResource = new LocalAwareFileResource(
                relativePath, localFileResource, fileResource);
        return localAwareFileResource;
    }
    
    /**
     * @param relativePath
     * @return
     */
    @Override
    public FolderResource getFolder(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        //预处理关联路径
        relativePath = FileContextUtils.handleRelativePath(relativePath);
        if (!relativePath.endsWith("/")) {
            //如果不是以"/"结尾，则添加"/"
            relativePath = relativePath + "/";
        }
        
        FileSystemResource localFolderResource = (FileSystemResource) this.catalogLocalResource
                .createRelative(relativePath);
        FolderResource folderResource = this.resourceLoader
                .getFolder(relativePath);
        LocalAwareFolderResource localAwareFolderResource = new LocalAwareFolderResource(
                relativePath, localFolderResource, folderResource);
        return localAwareFolderResource;
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
    public static class LocalAwareFileResource implements FileResource {
        
        /** 文件定义 */
        private String relativePath;
        
        /** 文件资源 */
        private FileSystemResource localFileResource;
        
        /** 文件定义 */
        private FileResource fileResource;
        
        /** <默认构造函数> */
        public LocalAwareFileResource(String relativePath,
                FileSystemResource localFileResource,
                FileResource fileResource) {
            super();
            this.relativePath = relativePath;
            this.localFileResource = localFileResource;
            this.fileResource = fileResource;
        }
        
        /**
         * @return 返回 relativePath
         */
        public String getRelativePath() {
            return this.relativePath;
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
                throw new ResourceAccessException(MessageUtils
                        .format("删除资源异常.path:{}.", this.relativePath));
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
                throw new ResourceAccessException(MessageUtils
                        .format("保存资源异常.path:{}.", this.relativePath));
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
                throw new ResourceAccessException(MessageUtils
                        .format("保存资源异常.path:{}.", this.relativePath));
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
     * 本地缓存文件夹资源<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月19日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class LocalAwareFolderResource implements FolderResource {
        
        /** 文件目录相对路径 */
        private String relativePath;
        
        /** 文件资源 */
        private FileSystemResource localFolderResource;
        
        /** 文件资源 */
        private FolderResource folderResource;
        
        /** <默认构造函数> */
        public LocalAwareFolderResource(String relativePath,
                FileSystemResource localFolderResource,
                FolderResource folderResource) {
            super();
            this.relativePath = relativePath;
            this.localFolderResource = localFolderResource;
            this.folderResource = folderResource;
        }
        
        /**
         * @return
         */
        @Override
        public String getRelativePath() {
            return this.relativePath;
        }
        
        /**
         * 
         */
        @Override
        public void mkdirs() {
            this.folderResource.mkdirs();
            File folder = this.localFolderResource.getFile();
            if (folder.exists()) {
                if (!folder.isDirectory()) {
                    throw new ResourceAccessException(
                            "FolderResource.mkdirs错误. 文件类型不为文件夹.");
                }
                return;
            }
            //如果文件夹不存在
            boolean flag = this.localFolderResource.getFile().mkdirs();
            if (!flag) {
                throw new ResourceAccessException("FolderResource.mkdirs错误.");
            }
        }
        
        /**
         * @param filter
         * @return
         */
        @Override
        public List<FileResource> list() {
            List<FileResource> resList = this.folderResource.list();
            return resList;
        }
        
        /**
         * @param force
         */
        @Override
        public void delete() {
            if (this.localFolderResource.exists()) {
                try {
                    FileUtils.deleteDirectory(
                            this.localFolderResource.getFile());
                } catch (IOException e) {
                    logger.error("FolderResource.delete失败.", e);
                    throw new ResourceAccessException(
                            "FolderResource.delete错误.", e);
                }
            }
            this.folderResource.delete();
        }
        
        /**
         * @param relativePath
         * @return
         */
        @Override
        public FileResource createRelative(String relativePath) {
            AssertUtils.notEmpty(relativePath, "relativePath is empty.");
            
            relativePath = FileContextUtils.handleRelativePath(relativePath);
            String fileRelativePath = this.relativePath + relativePath;
            
            FileSystemResource localFileResource = (FileSystemResource) this.localFolderResource
                    .createRelative(relativePath);
            FileResource fileResource = this.folderResource
                    .createRelative(relativePath);
            LocalAwareFileResource fr = new LocalAwareFileResource(
                    fileRelativePath, localFileResource, fileResource);
            return fr;
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
        FileResource fr1 = loader.getFile("2020/03/10/test1.txt");
        if (fr1.exists()) {
            fr1.delete();
            System.out.println("delete......");
        }
        try (InputStream input = IOUtils.toInputStream("test.add", "UTF-8")) {
            fr1.add(input);
            System.out.println("add......");
        }
        try (InputStream input = fr1.getInputStream()) {
            System.out.println(IOUtils.toString(input, "UTF-8"));
        }
        try (InputStream input = fr1.getInputStream()) {
            System.out.println(IOUtils.toString(input, "UTF-8"));
            System.out.println("get......");
        }
        try (InputStream input = IOUtils.toInputStream("test.save", "UTF-8")) {
            fr1.save(input);
            System.out.println("save......");
        }
        try (InputStream input = fr1.getInputStream()) {
            System.out.println(IOUtils.toString(input, "UTF-8"));
            System.out.println("get......");
        }
        System.out.println("文件资源操作验证完成.compelete.");
        System.out.println("");
        
        FileResource fr2 = loader.getFile("2020/03/10/test2.txt");
        fr2.save(IOUtils.toInputStream("test.save", "UTF-8"));
        FileResource fr3 = loader.getFile("2020/03/10/test3.txt");
        fr3.save(IOUtils.toInputStream("test.save", "UTF-8"));
        FileResource fr4 = loader.getFile("2020/03/11/test4.txt");
        fr4.save(IOUtils.toInputStream("test.save", "UTF-8"));
        FileResource fr5 = loader.getFile("2020/03/11/test5.txt");
        fr5.save(IOUtils.toInputStream("test.save", "UTF-8"));
        FileResource fr6 = loader.getFile("2020/03/11/test6.txt");
        fr6.save(IOUtils.toInputStream("test.save", "UTF-8"));
        
        //目录
        //顺便验证前面加反斜杠，后面不加的情况，即不按照约定定义的情况
        System.out.println("验证遍历文件夹...");
        FolderResource folder = loader.getFolder("/2020/03");
        for (FileResource frTemp : folder.list(null)) {
            System.out.println(frTemp.getRelativePath());
            System.out.println(frTemp.exists());
        }
        
        //System.out.println("删除，存在文件的目录，目标为成功...");
        System.out.println("删除，存在文件的目录，目标为成功...");
        FolderResource folderD = loader.getFolder("2020/03/10");
        System.out.println(folderD.getRelativePath());
        //删除后，查看是否删除成功
        folderD.delete();
        System.out.println("是否删除成功：" + (folderD.list().size() == 0));
        for (FileResource frTemp : folder.list(null)) {
            System.out.println(frTemp.getRelativePath());
            System.out.println(frTemp.exists());
        }
        
        System.out.println("验证通过文件夹创建相对路径.");
        FileResource frRes = folder.createRelative("11/test4.txt");
        System.out.println(frRes.getRelativePath());
        try (InputStream input = frRes.getInputStream()) {
            System.out.println(IOUtils.toString(input, "UTF-8"));
            System.out.println("get......");
        }
    }
}
