/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.file.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.component.file.model.FileDefinition;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * FileDefinition的业务层<br/>
 * <功能详细描述>
 * 
 * @author Rain.he
 * @version [版本号, 2015年3月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileDefinitionPersistService implements InitializingBean {
    
    /** 缓存名 */
    private static final String cacheName = "file_definition_cache";
    
    /** 日志 */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(FileDefinitionPersistService.class);
    
    /** 文件定义Dao层 */
    @Resource(name = "fileContext.fileDefinitionService")
    private FileDefinitionService fileDefinitionService;
    
    @Resource(name = "fileContext.cacheManager")
    private CacheManager cacheManager;
    
    /** 缓存 */
    private Cache fileDefinitionCache;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.fileDefinitionCache = this.cacheManager.getCache(FileDefinitionPersistService.cacheName);
    }
    
    /**
     * 返回文件定义对象<br/>
     * 
     * @param fileId 文件定义id
     * 
     * @return FileDefinition 文件定义
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinition findById(final String fileId) {
        AssertUtils.notEmpty(fileId, "fileId is empty.");
        
        FileDefinition fileDefinition = null;
        ValueWrapper vw = this.fileDefinitionCache.get(fileId);
        if (vw != null) {
            //如果缓存里有存储
            Object fdObject = vw.get();
            if (fdObject != null && fdObject instanceof FileDefinition) {
                fileDefinition = (FileDefinition) fdObject;
                return fileDefinition;
            } else {
                fileDefinition = this.fileDefinitionService.findById(fileId);
            }
        }else{
            fileDefinition = this.fileDefinitionService.findById(fileId);
        }
        
        final FileDefinition finalFD = fileDefinition;
        final Cache finalCache = this.fileDefinitionCache;
        if (finalCache instanceof TransactionAwareCacheDecorator) {
            if(finalFD != null){
                finalCache.put(fileId, finalFD);
            }
        } else {
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    public void afterCommit() {
                        if(finalFD != null){
                            finalCache.put(fileId, finalFD);
                        }
                    }
                });
            } else {
                if(finalFD != null){
                    finalCache.put(fileId, finalFD);
                }
            }
        }
        
        return fileDefinition;
    }
    
    /**
     * 删除对应的文件，并将对应文件的相关记录移除到历史表中<br/>
     * 
     * @param fileId 文件定义id
     * 
     * @return void
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void evict(final String fileId) {
        AssertUtils.notEmpty(fileId, "fileId is empty.");
        
        final Cache finalCache = this.fileDefinitionCache;
        if (finalCache instanceof TransactionAwareCacheDecorator) {
            finalCache.evict(fileId);
        } else {
            if (TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    public void afterCommit() {
                        finalCache.evict(fileId);
                    }
                });
            } else {
                finalCache.evict(fileId);
            }
        }
    }
}
