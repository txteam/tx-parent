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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

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
public class LocalFileResourceLoader
        implements FileResourceLoader, InitializingBean {
    
    /** 日志记录句柄 */
    protected static Logger logger = LoggerFactory
            .getLogger(FileResourceLoader.class);
    
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
     * @param relativePath
     * @return
     */
    @Override
    public FileResource getFile(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        //预处理关联路径
        relativePath = FileContextUtils.handleRelativePath(relativePath);
        //创建关联资源
        FileSystemResource resource = (FileSystemResource) this.catalogResource
                .createRelative(relativePath);
        
        FileResource fileResource = new LocalFileResource(relativePath,
                resource);
        return fileResource;
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
        
        FileSystemResource resource = (FileSystemResource) this.catalogResource
                .createRelative(relativePath);
        FolderResource folderResource = new LocalFolderResource(
                this.catalogResource, relativePath, resource);
        return folderResource;
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
        private String relativePath;
        
        /** 文件资源 */
        private FileSystemResource resource;
        
        /** <默认构造函数> */
        public LocalFileResource(String relativePath,
                FileSystemResource resource) {
            super();
            this.relativePath = relativePath;
            this.resource = resource;
        }
        
        /**
         * @return
         */
        @Override
        public String getRelativePath() {
            return this.relativePath;
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
            //获取文件
            File file = this.resource.getFile();
            //立即删除文件
            boolean flag = file.delete();
            if (!flag && file.exists()) {
                //如果删除文件失败，并且文件还存在时，抛出异常
                throw new ResourceAccessException(MessageUtils
                        .format("删除资源异常.path:{}.", this.relativePath));
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
            InputStream ins = this.resource.getInputStream();
            return ins;
        }
    }
    
    /**
     * 本地文件夹资源<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月18日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class LocalFolderResource implements FolderResource {
        
        /** 目录文件资源 */
        private FileSystemResource catalogResource;
        
        /** 文件目录相对路径 */
        private String relativePath;
        
        /** 文件资源 */
        private FileSystemResource folderResource;
        
        /** <默认构造函数> */
        public LocalFolderResource(FileSystemResource catalogResource,
                String relativePath, FileSystemResource folderResource) {
            super();
            this.catalogResource = catalogResource;
            this.relativePath = relativePath;
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
            File folder = this.folderResource.getFile();
            if (folder.exists()) {
                if (!folder.isDirectory()) {
                    throw new ResourceAccessException(
                            "FolderResource.mkdirs错误. 文件类型不为文件夹.");
                }
                return;
            }
            //如果文件夹不存在
            boolean flag = folder.mkdirs();
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
            List<FileResource> resList = new ArrayList<FileResource>();
            
            if (!this.folderResource.exists()
                    || !this.folderResource.getFile().isDirectory()) {
                //如果对应的文件资源不存在，或文件夹资源不为目录则直接返回空列表
                return resList;
            }
            
            String catalogResourcePath = this.catalogResource.getPath();
            for (File fTemp : FileUtils.listFiles(this.folderResource.getFile(),
                    null,
                    true)) {
                String fPath = StringUtils.cleanPath(fTemp.getPath());
                //System.out.println(fPath);
                String relativePath = fPath
                        .substring(catalogResourcePath.length());
                //System.out.println(relativePath);
                LocalFileResource fr = new LocalFileResource(relativePath,
                        new FileSystemResource(fTemp));
                resList.add(fr);
            }
            return resList;
        }
        
        /**
         * @param force
         */
        @Override
        public void delete() {
            if (!this.folderResource.exists()) {
                return;
            }
            try {
                FileUtils.deleteDirectory(this.folderResource.getFile());
            } catch (IOException e) {
                logger.error("FolderResource.delete失败.", e);
                throw new ResourceAccessException("FolderResource.delete错误.",
                        e);
            }
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
            FileSystemResource resource = (FileSystemResource) this.folderResource
                    .createRelative(relativePath);
            LocalFileResource fr = new LocalFileResource(fileRelativePath,
                    resource);
            return fr;
        }
    }
    
    /**
     * @param 对folder进行赋值
     */
    public void setFolder(File folder) {
        this.folder = folder;
    }
    
    //自动创建逻辑应该放到catalog的逻辑里，catalog是可以不考虑创建逻辑的
    public static void main(String[] args)
            throws IOException, InterruptedException {
        String t1 = "E:/TEST/LOCAL/folder";
        LocalFileResourceLoader loader = new LocalFileResourceLoader(t1);
        
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
