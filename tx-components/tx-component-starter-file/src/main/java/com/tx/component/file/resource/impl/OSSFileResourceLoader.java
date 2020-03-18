/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年2月28日
 * <修改描述:>
 */
package com.tx.component.file.resource.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObject;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
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
    public FileResource getResource(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileResource fr = new OSSFileResource(fd, this.ossClient,
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
        private FileDefinition fileDefinition;
        
        /** <默认构造函数> */
        public OSSFileResource(FileDefinition fileDefinition, OSS ossClient,
                String bucketName) {
            super();
            AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
            AssertUtils.notEmpty(bucketName, "bucketName is empty.");
            AssertUtils.notNull(ossClient, "ossClient is null.");
            
            this.fileDefinition = fileDefinition;
            this.bucketName = bucketName;
            this.ossClient = ossClient;
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
            boolean isExists = this.ossClient.doesObjectExist(this.bucketName,
                    this.fileDefinition.getRelativePath());
            return isExists;
        }
        
        /**
         * 
         */
        @Override
        public void delete() {
            this.ossClient.deleteObject(this.bucketName,
                    this.fileDefinition.getRelativePath());
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void save(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            //其返回结果中Response为空,所以只能依赖oss中抛出的异常来判断
            this.ossClient.putObject(this.bucketName,
                    this.fileDefinition.getRelativePath(),
                    inputStream);
        }
        
        /**
         * @param inputStream
         */
        @Override
        public void add(InputStream inputStream) {
            AssertUtils.notNull(inputStream, "inputStream is null.");
            
            if (this.ossClient.doesObjectExist(this.bucketName,
                    this.fileDefinition.getRelativePath())) {
                throw new ResourceAccessException(
                        "新增资源错误，对应资源已经存在.如需覆盖请调用save方法.");
            }
            //其返回结果中Response为空,所以只能依赖oss中抛出的异常来判断
            this.ossClient.putObject(this.bucketName,
                    this.fileDefinition.getRelativePath(),
                    inputStream);
        }
        
        /**
         * @return
         * @throws IOException
         */
        @Override
        public InputStream getInputStream() throws IOException {
            OSSObject ossObject = this.ossClient.getObject(this.bucketName,
                    this.fileDefinition.getRelativePath());
            
            return ossObject.getObjectContent();
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
        //新增
        FileDefinition fd = new FileDefinition("2020/03/10/test.txt");
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
