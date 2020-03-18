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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.OrderComparator;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.model.FileDefinitionDetail;
import com.tx.component.file.resource.FileResource;
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
    public String getViewUrl(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileCatalog catalog = doGetFileCatalog(fd);
        String viewUrl = catalog.getViewUrl(fd);
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
    public FileResource getFileResource(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileCatalog catalog = doGetFileCatalog(fd);
        FileResource resource = catalog.getFileResource(fd);
        return resource;
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
    public FileDefinitionDetail setup(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileDefinitionDetail fdd = doSetup(fd);
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
    public List<FileDefinitionDetail> setup(List<FileDefinition> fdList) {
        if (fdList == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(fdList)) {
            return new ArrayList<FileDefinitionDetail>();
        }
        
        List<FileDefinitionDetail> resList = fdList.stream()
                .map(fd -> doSetup(fd))
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
    public PagedList<FileDefinitionDetail> setup(
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
                .map(fd -> doSetup(fd))
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
    private FileDefinitionDetail doSetup(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileDefinitionDetail fdd = new FileDefinitionDetail(fd);
        FileCatalog catalog = doGetFileCatalog(fd);
        
        fdd.setCatalog(catalog.getCatalog());
        fdd.setViewUrl(catalog.getViewUrl(fd));
        fdd.setPermission(catalog.getPermission());
        FileResource fr = new TransactionAwareFileResourceProxy(
                catalog.getFileResource(fd));
        fdd.setResource(fr);
        return fdd;
    }
    
    /** 
     * 根据文件定义获取对应的文件目录实现<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return FileCatalog [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private FileCatalog doGetFileCatalog(FileDefinition fd) {
        FileCatalog catalog = null;
        for (FileCatalog c : this.catalogs) {
            if (!c.match(fd)) {
                continue;
            }
            catalog = c;
            break;
        }
        AssertUtils.notNull(catalog,
                MessageUtils.format("matched catalog is not exist.fd:{}",
                        new Object[] { fd }));
        return catalog;
    }
}
