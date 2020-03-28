///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2020年3月18日
// * <修改描述:>
// */
//package com.tx.component.file.service.impl;
//
//import java.io.InputStream;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.tx.component.file.catalog.FileCatalog;
//import com.tx.component.file.catalog.FileCatalogComposite;
//import com.tx.component.file.model.FileDefinition;
//import com.tx.component.file.resource.FileResource;
//import com.tx.component.file.resource.FolderResource;
//import com.tx.component.file.service.FileResourceService;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 文件资源业务层<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2020年3月18日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//public class FileResourceServiceImpl
//        implements InitializingBean, FileResourceService {
//    
//    /** 文件目录 */
//    private FileCatalogComposite fileCatalogComposite;
//    
//    /** <默认构造函数> */
//    public FileResourceServiceImpl() {
//        super();
//    }
//    
//    /** <默认构造函数> */
//    public FileResourceServiceImpl(FileCatalogComposite fileCatalogComposite) {
//        super();
//        this.fileCatalogComposite = fileCatalogComposite;
//        afterPropertiesSet();
//    }
//    
//    /**
//     * 
//     */
//    @Override
//    public void afterPropertiesSet() {
//        AssertUtils.notNull(this.fileCatalogComposite,
//                "fileCatalogComposite is empty.");
//    }
//    
//    private FileCatalog getFileCatalog(String catalog){
//        AssertUtils.notEmpty(catalog, "catalog is empty.");
//        
//        FileCatalog catalog = this.fileCatalogComposite.
//        return null;
//    }
//
//    /**
//     * @param catalog
//     * @param relativePath
//     * @return
//     */
//    @Override
//    public FileResource getFileResource(String catalog, String relativePath) {
//        AssertUtils.notEmpty(catalog, "catalog is empty.");
//        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
//        
//        FileDefinition fd = new FileDefinition(catalog, relativePath);
//        FileResource fr = this.fileCatalogComposite.getFileResource(fd);
//        return fr;
//    }
//
//    /**
//     * @param catalog
//     * @param relativeFolderPath
//     * @return
//     */
//    @Override
//    public FolderResource getFolderResource(String catalog,
//            String relativeFolderPath) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    
//    
//}
