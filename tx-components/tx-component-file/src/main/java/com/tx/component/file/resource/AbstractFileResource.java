/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月15日
 * <修改描述:>
 */
package com.tx.component.file.resource;

import java.io.InputStream;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tx.component.file.model.FileDefinition;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 抽象文件定义资源<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractFileResource implements FileResource {
    
    /** 文件定义 */
    protected FileDefinition fileDefinition;
    
    /** <默认构造函数> */
    public AbstractFileResource(FileDefinition fileDefinition) {
        super();
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        this.fileDefinition = fileDefinition;
    }
    
    /**
     * @return
     */
    @Override
    public String getViewUrl() {
        return null;
    }
    
    /**
     * @return
     */
    @Override
    public final String getFilename() {
        return this.fileDefinition.getFilename();
    }
    
    /**
     * @return 返回 fileDefinition
     */
    public final FileDefinition getFileDefinition() {
        return fileDefinition;
    }
    
    /**
     * @param inputStream
     */
    @Override
    public final void save(final InputStream inputStream) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                /**
                 * @param readOnly
                 */
                @Override
                public void beforeCommit(boolean readOnly) {
                    doSave(inputStream);
                }
            });
        } else {
            doSave(inputStream);
        }
    }
    
    /**
     * 保存InputStream<br/>
     * <功能详细描述>
     * @param inputStream [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void doSave(InputStream inputStream);
    
    /**
     * @param inputStream
     */
    @Override
    public final void add(final InputStream inputStream) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                /**
                 * @param readOnly
                 */
                @Override
                public void beforeCommit(boolean readOnly) {
                    doAdd(inputStream);
                }
            });
        } else {
            doAdd(inputStream);
        }
    }
    
    /**
     * 新增文件资源<br/>
     * <功能详细描述>
     * @param inputStream [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void doAdd(InputStream inputStream);
    
    /**
     * 
     */
    @Override
    public final void delete() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                /**
                 * @param readOnly
                 */
                @Override
                public void beforeCommit(boolean readOnly) {
                    doDelete();
                }
            });
        } else {
            doDelete();
        }
    }
    
    /**
     * 新增文件资源<br/>
     * <功能详细描述>
     * @param inputStream [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void doDelete();
}
