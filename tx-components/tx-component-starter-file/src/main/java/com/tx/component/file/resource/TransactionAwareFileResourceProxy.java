/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年2月24日
 * <修改描述:>
 */
package com.tx.component.file.resource;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务环绕文件资源代理<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年2月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TransactionAwareFileResourceProxy
        implements FileResource, InitializingBean {
    
    /** targetCacheManager */
    private FileResource targetFileResource;
    
    /** <默认构造函数> */
    public TransactionAwareFileResourceProxy() {
        super();
    }
    
    /** <默认构造函数> */
    public TransactionAwareFileResourceProxy(FileResource targetFileResource) {
        super();
        this.targetFileResource = targetFileResource;
        afterPropertiesSet();
    }
    
    /**
     * @param 对targetCacheManager进行赋值
     */
    public void setFileResource(FileResource targetFileResource) {
        this.targetFileResource = targetFileResource;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        if (this.targetFileResource == null) {
            throw new IllegalArgumentException(
                    "Property 'targetFileResource' is required.");
        }
    }
    
    /**
     * @return
     */
    @Override
    public String getRelativePath() {
        return this.targetFileResource.getRelativePath();
    }
    
    /**
     * @return
     */
    @Override
    public boolean exists() {
        return this.targetFileResource.exists();
    }
    
    /**
     * @param inputStream
     */
    @Override
    public void add(InputStream inputStream) {
        FileResource finalFR = this.targetFileResource;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void beforeCommit(boolean readOnly) {
                            finalFR.add(inputStream);
                        }
                    });
        } else {
            finalFR.add(inputStream);
        }
    }
    
    /**
     * 
     */
    @Override
    public void delete() {
        FileResource finalFR = this.targetFileResource;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void beforeCommit(boolean readOnly) {
                            finalFR.delete();
                        }
                    });
        } else {
            finalFR.delete();
        }
    }
    
    /**
     * @param inputStream
     */
    @Override
    public void save(InputStream inputStream) {
        FileResource finalFR = this.targetFileResource;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void beforeCommit(boolean readOnly) {
                            finalFR.save(inputStream);
                        }
                    });
        } else {
            finalFR.save(inputStream);
        }
    }
    
    /**
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return this.targetFileResource.getInputStream();
    }
}
