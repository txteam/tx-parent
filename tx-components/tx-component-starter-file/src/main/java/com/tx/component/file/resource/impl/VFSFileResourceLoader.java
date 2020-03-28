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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.component.file.resource.FolderResource;
import com.tx.component.file.util.FileContextUtils;
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
    
    /** 日志记录句柄 */
    protected static Logger logger = LoggerFactory
            .getLogger(FileResourceLoader.class);
    
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
    public FileResource getFile(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        //预处理关联路径
        relativePath = FileContextUtils.handleRelativePath(relativePath);
        FileObject fileObject = null;
        try {
            fileObject = catalogFileObject.resolveFile(relativePath);
        } catch (FileSystemException e) {
            logger.error("FileResourceLoader.getFile错误.", e);
            throw new ResourceAccessException(MessageUtils.format(
                    "FileResourceLoader.getFile错误:{}.", e.getMessage()), e);
        }
        FileResource vfsResource = new VFSFileResource(relativePath,
                fileObject);
        return vfsResource;
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
        FileObject fileObject = null;
        try {
            fileObject = this.catalogFileObject.resolveFile(relativePath);
        } catch (FileSystemException e) {
            logger.error("FileResourceLoader.getFolder错误.", e);
            throw new ResourceAccessException(MessageUtils.format(
                    "FileResourceLoader.getFolder错误:{}.", e.getMessage()), e);
        }
        FolderResource folderResource = new VFSFolderResource(
                this.catalogFileObject, relativePath, fileObject);
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
    public static class VFSFileResource implements FileResource {
        
        /** 文件定义 */
        private String relativePath;
        
        /** 文件资源 */
        private FileObject fileObject;
        
        /** <默认构造函数> */
        public VFSFileResource(String relativePath, FileObject fileObject) {
            super();
            this.relativePath = relativePath;
            this.fileObject = fileObject;
        }
        
        /**
         * @return 返回 relativePath
         */
        public String getRelativePath() {
            return relativePath;
        }
        
        /**
         * @return
         */
        @Override
        public boolean exists() {
            try {
                return this.fileObject.exists();
            } catch (FileSystemException e) {
                logger.error("FileResource.exists错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FileResource.exists错误.relativePath:{}.",
                        this.relativePath));
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
                    throw new ResourceAccessException(MessageUtils
                            .format("删除资源异常.path:{}.", this.relativePath));
                }
            } catch (FileSystemException e) {
                logger.error("FileResource.delete错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FileResource.delete错误.relativePath:{}.",
                        this.relativePath));
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
                logger.error("FileResource.save错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FileResource.save错误.relativePath:{}.",
                        this.relativePath));
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
                logger.error("FileResource.add错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FileResource.add错误.relativePath:{}.",
                        this.relativePath));
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
    
    /**
     * 本地文件夹资源<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月18日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class VFSFolderResource implements FolderResource {
        
        /** 目录文件资源 */
        private FileObject catalogFileObject;
        
        /** 文件目录相对路径 */
        private String relativePath;
        
        /** 文件资源 */
        private FileObject folderFileObject;
        
        /** <默认构造函数> */
        public VFSFolderResource(FileObject catalogFileObject,
                String relativePath, FileObject folderFileObject) {
            super();
            this.catalogFileObject = catalogFileObject;
            this.relativePath = relativePath;
            this.folderFileObject = folderFileObject;
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
            try {
                if (this.folderFileObject.exists()) {
                    if (!this.folderFileObject.isFolder()) {
                        throw new ResourceAccessException(
                                "FolderResource.mkdirs错误. 文件类型不为文件夹.");
                    }
                    return;
                }
                
                //如果文件夹不存在
                this.folderFileObject.createFolder();
            } catch (FileSystemException e) {
                logger.error("FolderResource.mkdirs错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FolderResource.mkdirs错误.relativePath:{}.",
                        this.relativePath));
            }
        }
        
        /**
         * @param filter
         * @return
         */
        @Override
        public List<FileResource> list() {
            List<FileResource> resList = new ArrayList<FileResource>();
            try {
                if (!this.folderFileObject.exists()
                        || !this.folderFileObject.isFolder()) {
                    //如果对应的文件资源不存在，或文件夹资源不为目录则直接返回空列表
                    return resList;
                }
                
                FileName catalogFileName = this.catalogFileObject.getName();
                for (FileObject fTemp : this.folderFileObject
                        .findFiles(new FileSelector() {
                            
                            @Override
                            public boolean traverseDescendents(
                                    FileSelectInfo fileInfo) throws Exception {
                                //深度查询
                                return true;
                            }
                            
                            @Override
                            public boolean includeFile(FileSelectInfo fileInfo)
                                    throws Exception {
                                //仅查询文件
                                return fileInfo.getFile().isFile();
                            }
                        })) {
                    
                    String relativePath = catalogFileName
                            .getRelativeName(fTemp.getName());
                    //System.out.println(relativePath);
                    VFSFileResource fr = new VFSFileResource(relativePath,
                            fTemp);
                    resList.add(fr);
                }
            } catch (FileSystemException e) {
                logger.error("FolderResource.list错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FolderResource.list错误.relativePath:{}.",
                        this.relativePath));
            }
            return resList;
        }
        
        /**
         * @param force
         */
        @Override
        public void delete() {
            try {
                if (!this.folderFileObject.exists()) {
                    return;
                }
                this.folderFileObject.delete(new FileSelector() {
                    @Override
                    public boolean traverseDescendents(FileSelectInfo fileInfo)
                            throws Exception {
                        //深度查询
                        return true;
                    }
                    
                    @Override
                    public boolean includeFile(FileSelectInfo fileInfo)
                            throws Exception {
                        //文件及文件夹均进行删除
                        return true;
                    }
                });
            } catch (FileSystemException e) {
                logger.error("FolderResource.delete错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FolderResource.delete错误.relativePath:{}.",
                        this.relativePath));
            }
        }
        
        /**
         * @param relativePath
         * @return
         */
        @Override
        public FileResource createRelative(String relativePath) {
            AssertUtils.notEmpty(relativePath, "relativePath is empty.");
            
            try {
                relativePath = FileContextUtils
                        .handleRelativePath(relativePath);
                FileObject fileObject = this.folderFileObject
                        .resolveFile(relativePath);
                VFSFileResource fr = new VFSFileResource(relativePath,
                        fileObject);
                return fr;
            } catch (FileSystemException e) {
                logger.error("FolderResource.createRelative错误.", e);
                throw new ResourceAccessException(MessageUtils.format(
                        "FolderResource.createRelative错误.relativePath:{}.",
                        this.relativePath));
            }
            
        }
        
    }
    
    //自动创建逻辑应该放到catalog的逻辑里，catalog是可以不考虑创建逻辑的
    public static void main(String[] args) throws IOException {
        String t1 = "E:/TEST/VFS/folder";
        VFSFileResourceLoader loader = new VFSFileResourceLoader(t1);
        
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
        for (FileResource frTemp : folder.list()) {
            System.out.println(frTemp.getRelativePath());
            System.out.println(frTemp.exists());
        }
        
        System.out.println("");
        System.out.println("验证通过文件夹创建相对路径.");
        FileResource frRes = folder.createRelative("11/test4.txt");
        System.out.println(frRes.getRelativePath());
        try (InputStream input = frRes.getInputStream()) {
            System.out.println(IOUtils.toString(input, "UTF-8"));
            System.out.println("get......");
        }
    }
}
