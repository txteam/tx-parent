/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月13日
 * <修改描述:>
 */
package com.tx.component.file.catalog.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.tx.component.file.catalog.FileCatalog;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.component.file.resource.impl.VFSFileResourceLoader;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 问题文件目录<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class VFSFileCatalog implements FileCatalog, InitializingBean {
    
    /** 目录 */
    private String catalog;
    
    /** 路径 */
    private String path;
    
    /** 文件资源加载器 */
    private FileResourceLoader fileResourceLoader;
    
    /** <默认构造函数> */
    public VFSFileCatalog() {
        super();
    }
    
    /** <默认构造函数> */
    public VFSFileCatalog(String catalog, String path) {
        super();
        this.catalog = catalog;
        this.path = path;
        
        afterPropertiesSet();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notEmpty(this.catalog, "catalog is empty.");
        AssertUtils.notEmpty(this.path, "path is empty.");
        
        this.fileResourceLoader = new VFSFileResourceLoader(this.path);
    }
    
    /**
     * @return
     */
    @Override
    public String getCatalog() {
        return this.catalog;
    }
    
    /**
     * @return
     */
    @Override
    public FileResourceLoader getFileResourceLoader() {
        return this.fileResourceLoader;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
    
    /**
     * @param 对fileResourceLoader进行赋值
     */
    public void setFileResourceLoader(FileResourceLoader fileResourceLoader) {
        this.fileResourceLoader = fileResourceLoader;
    }
}