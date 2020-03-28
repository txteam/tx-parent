/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年2月28日
 * <修改描述:>
 */
package com.tx.component.file.resource.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.component.file.resource.FolderResource;
import com.tx.component.file.util.FileContextUtils;
import com.tx.core.exceptions.resource.ResourceAccessException;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 阿里SSO文件资源加载器<br/>
 * https://help.aliyun.com/document_detail/32008.html?spm=a2c4g.11186623.6.762.61fd2841I5HX6g
 * https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.765.5d2a6328lnIe1R
 * 初始化：             https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.4.6.369ec06dImMkZE
 * 快速入门:    https://help.aliyun.com/document_detail/32011.html?spm=a2c4g.11186623.4.3.369ec06dImMkZE
 * 
 * @author  Administrator
 * @version  [版本号, 2020年2月28日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class OSSFileResourceLoader
        implements FileResourceLoader, InitializingBean {
    
    /** 日志记录句柄 */
    protected static Logger logger = LoggerFactory
            .getLogger(FileResourceLoader.class);
    
    /** ossClient */
    private OSS ossClient;
    
    /** bucketName */
    private String bucketName;
    
    /** <默认构造函数> */
    public OSSFileResourceLoader() {
        super();
    }
    
    /** <默认构造函数> */
    public OSSFileResourceLoader(OSS ossClient, String bucketName) {
        super();
        this.ossClient = ossClient;
        this.bucketName = bucketName;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(this.ossClient, "ossClient is null.");
        AssertUtils.notEmpty(this.bucketName, "bucketName is empty.");
        
        if (!this.ossClient.doesBucketExist(this.bucketName)) {
            //如果创建过程中抛出异常，理应无法启动
            //默认创建公共毒
            CreateBucketRequest cbRequest = new CreateBucketRequest(
                    this.bucketName);
            cbRequest.setCannedACL(CannedAccessControlList.PublicRead);//设置为公共读
            this.ossClient.createBucket(cbRequest);
        }
        
        //如果存在则读取bucket信息，如果已被占用，则此处会抛出异常
        BucketInfo bucketInfo = this.ossClient.getBucketInfo(this.bucketName);
        String location = bucketInfo.getBucket().getLocation();
        System.out.println(location);
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
        FileResource fr = new OSSFileResource(relativePath, this.ossClient,
                this.bucketName);
        return fr;
    }
    
    /**
     * @param fd
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
        //预处理关联路径
        FolderResource fr = new OSSFolderResource(relativePath, this.ossClient,
                this.bucketName);
        return fr;
    }
    
    /**
     * OSS文件资源<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月11日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class OSSFileResource implements FileResource {
        
        /** ossClient */
        private OSS ossClient;
        
        /** bucketName */
        private String bucketName;
        
        /** 文件定义 */
        private String relativePath;
        
        /** <默认构造函数> */
        public OSSFileResource(String relativePath, OSS ossClient,
                String bucketName) {
            super();
            AssertUtils.notEmpty(relativePath, "relativePath is empty.");
            AssertUtils.notEmpty(bucketName, "bucketName is empty.");
            AssertUtils.notNull(ossClient, "ossClient is null.");
            
            this.relativePath = relativePath;
            this.bucketName = bucketName;
            this.ossClient = ossClient;
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
            boolean isExists = this.ossClient.doesObjectExist(this.bucketName,
                    this.relativePath);
            return isExists;
        }
        
        /**
         * 
         */
        @Override
        public void delete() {
            this.ossClient.deleteObject(this.bucketName, this.relativePath);
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void save(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            //其返回结果中Response为空,所以只能依赖oss中抛出的异常来判断
            this.ossClient.putObject(this.bucketName,
                    this.relativePath,
                    inputStream);
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void add(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            if (this.ossClient.doesObjectExist(this.bucketName,
                    this.relativePath)) {
                throw new ResourceAccessException(
                        "新增资源错误，对应资源已经存在.如需覆盖请调用save方法.");
            }
            //其返回结果中Response为空,所以只能依赖oss中抛出的异常来判断
            this.ossClient.putObject(this.bucketName,
                    this.relativePath,
                    inputStream);
        }
        
        /**
         * @return
         * @throws IOException
         */
        @Override
        public InputStream getInputStream() throws IOException {
            OSSObject ossObject = this.ossClient.getObject(this.bucketName,
                    this.relativePath);
            
            return ossObject.getObjectContent();
        }
    }
    
    /**
     * OSS文件夹资源<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2020年3月18日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    public static class OSSFolderResource implements FolderResource {
        
        /** ossClient */
        private OSS ossClient;
        
        /** bucketName */
        private String bucketName;
        
        /** 文件定义 */
        private String relativePath;
        
        /** <默认构造函数> */
        public OSSFolderResource(String relativePath, OSS ossClient,
                String bucketName) {
            super();
            AssertUtils.notEmpty(relativePath, "relativePath is empty.");
            AssertUtils.notEmpty(bucketName, "bucketName is empty.");
            AssertUtils.notNull(ossClient, "ossClient is null.");
            
            this.relativePath = relativePath;
            this.bucketName = bucketName;
            this.ossClient = ossClient;
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
            //oss实现中无需真正创建文件夹
            return;
        }
        
        /**
         * @return
         */
        @Override
        public List<FileResource> list() {
            List<FileResource> resList = new ArrayList<FileResource>();
            ObjectListing listing = this.ossClient.listObjects(this.bucketName,
                    this.relativePath);
            if (listing == null || listing.getObjectSummaries().size() == 0) {
                return resList;
            }
            List<OSSObjectSummary> ossObjectSummaryList = listing
                    .getObjectSummaries();
            if (CollectionUtils.isEmpty(ossObjectSummaryList)) {
                return resList;
            }
            for (OSSObjectSummary sTemp : ossObjectSummaryList) {
                OSSFileResource ossFileResource = new OSSFileResource(
                        sTemp.getKey(), this.ossClient, this.bucketName);
                resList.add(ossFileResource);
            }
            return resList;
        }
        
        /**
         * @param force
         * @return
         */
        @Override
        public void delete() {
            List<FileResource> frs = list();
            if (CollectionUtils.isEmpty(frs)) {
                return;
            }
            this.ossClient.deleteObjects(
                    (new DeleteObjectsRequest(bucketName)).withKeys(frs.stream()
                            .map(fr -> fr.getRelativePath())
                            .collect(Collectors.toList())));
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
            
            OSSFileResource fr = new OSSFileResource(fileRelativePath,
                    this.ossClient, this.bucketName);
            return fr;
        }
    }
    
    /**
     * @param 对ossClient进行赋值
     */
    public void setOssClient(OSS ossClient) {
        this.ossClient = ossClient;
    }
    
    /**
     * @param 对bucketName进行赋值
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
    public static void main(String[] args) throws IOException {
        //OSSFileDefinitionResourceDriver driver1 = new OSSFileDefinitionResourceDriver();
        //driver1.setEndpoint("http://oss-cn-shenzhen.aliyuncs.com");
        //driver1.setAccessKeyId("LTAIqiYuWr9WAkWa");
        //driver1.setSecretAccessKey("e4tyMfOU9IGiiDwWlc6gidT8siQek1");
        //driver1.setBucketName("txwebdemo");
        //driver1.afterPropertiesSet();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                "http://oss-cn-beijing.aliyuncs.com",
                "LTAIqiYuWr9WAkWa",
                "e4tyMfOU9IGiiDwWlc6gidT8siQek1");
        OSSFileResourceLoader loader = new OSSFileResourceLoader(ossClient,
                "txwebdemo");
        
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
