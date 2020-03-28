/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.file.starter;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
public class DefaultsFileCatalogProperties {
    
    /** 文件容器默认的存放路径 */
    private String path;
    
    /** oss配置 */
    private String ossEndpoint;
    
    /** oss配置 */
    private String ossAccessKeyId;
    
    /** oss配置 */
    private String ossSecretAccessKey;
    
    /** 目录 */
    private String ossBucketName;
    
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
     * @return 返回 ossEndpoint
     */
    public String getOssEndpoint() {
        return ossEndpoint;
    }
    
    /**
     * @param 对ossEndpoint进行赋值
     */
    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }
    
    /**
     * @return 返回 ossAccessKeyId
     */
    public String getOssAccessKeyId() {
        return ossAccessKeyId;
    }
    
    /**
     * @param 对ossAccessKeyId进行赋值
     */
    public void setOssAccessKeyId(String ossAccessKeyId) {
        this.ossAccessKeyId = ossAccessKeyId;
    }
    
    /**
     * @return 返回 ossSecretAccessKey
     */
    public String getOssSecretAccessKey() {
        return ossSecretAccessKey;
    }
    
    /**
     * @param 对ossSecretAccessKey进行赋值
     */
    public void setOssSecretAccessKey(String ossSecretAccessKey) {
        this.ossSecretAccessKey = ossSecretAccessKey;
    }
    
    /**
     * @return 返回 ossBucketName
     */
    public String getOssBucketName() {
        return ossBucketName;
    }
    
    /**
     * @param 对ossBucketName进行赋值
     */
    public void setOssBucketName(String ossBucketName) {
        this.ossBucketName = ossBucketName;
    }
    
}
