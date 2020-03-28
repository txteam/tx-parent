/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月13日
 * <修改描述:>
 */
package com.tx.component.file.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.OrderComparator;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.model.FileDefinitionDetail;
import com.tx.component.file.model.FileResourceDetail;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FolderResource;
import com.tx.component.file.resource.TransactionAwareFileResourceProxy;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.MessageUtils;

/**
 * 文件目录组合<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FileCatalogComposite implements InitializingBean {
    
    /** 文件目录实现 */
    private List<FileCatalog> catalogs;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notEmpty(catalogs, "catalogs is empty.");
        Collections.sort(this.catalogs, OrderComparator.INSTANCE);
    }
    
    /**
     * 根据文件定义获取文件资源<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileResource getFileResource(String catalog, String relativePath) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FileCatalog c = doGetFileCatalog(catalog);
        FileResource resource = c.getFileResource(relativePath);
        return resource;
    }
    
    /**
     * 根据文件定义获取文件资源<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FolderResource getFolderResource(String catalog,
            String relativePath) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FileCatalog c = doGetFileCatalog(catalog);
        FolderResource resource = c.getFolderResource(relativePath);
        return resource;
    }
    
    /**
     * 根据文件定义获取文件资源<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getViewUrl(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getCatalog(),
                "fileDefinition.catalog is empty.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileCatalog c = doGetFileCatalog(fd.getCatalog());
        String viewUrl = c.getViewUrl(fd);
        return viewUrl;
    }
    
    /**
     * 根据文件定义获取文件资源<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getViewUrlByRelativePath(String catalog,
            String relativePath) {
        AssertUtils.notEmpty(catalog, "catalog.catalog is empty.");
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FileCatalog c = doGetFileCatalog(catalog);
        String viewUrl = c.getViewUrlByRelativePath(relativePath);
        return viewUrl;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionDetail setupFileDefinitionDetail(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileDefinitionDetail fdd = doSetupFileDefinitionDetail(fd);
        return fdd;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param fdList
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<FileDefinitionDetail> setupFileDefinitionDetail(
            List<FileDefinition> fdList) {
        if (fdList == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(fdList)) {
            return new ArrayList<FileDefinitionDetail>();
        }
        
        List<FileDefinitionDetail> resList = fdList.stream()
                .map(fd -> doSetupFileDefinitionDetail(fd))
                .collect(Collectors.toList());
        return resList;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param fdPagedList
     * @return [参数说明]
     * 
     * @return PagedList<FileDefinitionDetail> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<FileDefinitionDetail> setupFileDefinitionDetail(
            PagedList<FileDefinition> fdPagedList) {
        if (fdPagedList == null) {
            return null;
        }
        PagedList<FileDefinitionDetail> resPagedList = new PagedList<>();
        resPagedList.setCount(fdPagedList.getCount());
        resPagedList.setPageIndex(fdPagedList.getPageIndex());
        resPagedList.setPageSize(fdPagedList.getPageSize());
        resPagedList.setQueryPageSize(fdPagedList.getQueryPageSize());
        if (CollectionUtils.isEmpty(fdPagedList.getList())) {
            return resPagedList;
        }
        
        resPagedList.setList(fdPagedList.getList()
                .stream()
                .map(fd -> doSetupFileDefinitionDetail(fd))
                .collect(Collectors.toList()));
        return resPagedList;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileDefinitionDetail doSetupFileDefinitionDetail(
            FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getCatalog(),
                "fileDefinition.catalog is empty.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileDefinitionDetail fdd = new FileDefinitionDetail(fd);
        FileCatalog c = doGetFileCatalog(fd.getCatalog());
        fdd.setCatalog(c.getCatalog());
        fdd.setViewUrl(c.getViewUrl(fd));
        fdd.setPermission(c.getPermission());
        FileResource fr = new TransactionAwareFileResourceProxy(
                c.getFileResource(fd.getRelativePath()));
        fdd.setResource(fr);
        return fdd;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileResourceDetail setupFileResourceDetail(String catalog,
            FileResource fr) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        AssertUtils.notNull(fr, "fr is null.");
        AssertUtils.notEmpty(fr.getRelativePath(),
                "fileResource.relativePath is empty.");
        
        FileResourceDetail frd = doSetupFileResourceDetail(catalog, fr);
        return frd;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param fdList
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<FileResourceDetail> setupFileResourceDetail(String catalog,
            List<FileResource> frList) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        if (frList == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(frList)) {
            return new ArrayList<FileResourceDetail>();
        }
        
        List<FileResourceDetail> resList = frList.stream()
                .map(fr -> doSetupFileResourceDetail(catalog, fr))
                .collect(Collectors.toList());
        return resList;
    }
    
    /**
     * 装载文件定义<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileResourceDetail doSetupFileResourceDetail(String catalog,
            FileResource fileResource) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        AssertUtils.notNull(fileResource, "fileResource is null.");
        AssertUtils.notEmpty(fileResource.getRelativePath(),
                "fileResource.relativePath is empty.");
        
        FileResourceDetail frd = new FileResourceDetail();
        FileCatalog c = doGetFileCatalog(catalog);
        frd.setCatalog(c.getCatalog());
        frd.setPermission(c.getPermission());
        frd.setViewUrl(
                c.getViewUrlByRelativePath(fileResource.getRelativePath()));
        FileResource fr = new TransactionAwareFileResourceProxy(
                c.getFileResource(fileResource.getRelativePath()));
        frd.setResource(fr);
        return frd;
    }
    
    /** 
     * 根据文件定义获取对应的文件目录实现<br/>
     * <功能详细描述>
     * @param catalog
     * @return [参数说明]
     * 
     * @return FileCatalog [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileCatalog doGetFileCatalog(String catalog) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        FileCatalog fileCatalog = null;
        for (FileCatalog catalogTemp : this.catalogs) {
            if (StringUtils.equalsIgnoreCase(catalog,
                    catalogTemp.getCatalog())) {
                continue;
            }
            fileCatalog = catalogTemp;
            break;
        }
        AssertUtils.notNull(fileCatalog,
                MessageUtils.format("matched catalog is not exist.catalog:{}",
                        new Object[] { fileCatalog.getCatalog() }));
        return fileCatalog;
    }
}
