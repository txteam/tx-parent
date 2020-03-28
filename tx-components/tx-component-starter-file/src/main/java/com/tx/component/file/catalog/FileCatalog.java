/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月11日
 * <修改描述:>
 */
package com.tx.component.file.catalog;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.resource.FileResource;
import com.tx.component.file.resource.FileResourceLoader;
import com.tx.component.file.resource.FolderResource;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 文件分类(文件虚拟目录virtual folder..)
 * 
 * @author  Administrator
 * @version  [版本号, 2020年2月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface FileCatalog extends PriorityOrdered {
    
    /**
     * 文件目录定义<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default boolean match(String catalog) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        if (StringUtils.equalsIgnoreCase(getCatalog(), catalog)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 生成存储路径<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String generateRelativePath(FileDefinition fd) {
        return null;
    }
    
    /**
     * 获取文件外部访问路径<br/>
     * <功能详细描述>
     * @param fd
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getViewUrl(FileDefinition fd) {
        if (!FileCatalogPermissionEnum.PUBLIC_READ.equals(getPermission())
                || !FileCatalogPermissionEnum.PUBLIC_READ_WRITE
                        .equals(getPermission())) {
            //没有公共读权限的文件，其ViewUrl为空字符串
            return "";
        }
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        return getViewUrlByRelativePath(fd.getRelativePath());
    }
    
    /**
     * 获取文件外部访问路径<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getViewUrlById(String id) {
        if (!FileCatalogPermissionEnum.PUBLIC_READ.equals(getPermission())
                || !FileCatalogPermissionEnum.PUBLIC_READ_WRITE
                        .equals(getPermission())) {
            //没有公共读权限的文件，其ViewUrl为空字符串
            return "";
        }
        AssertUtils.notEmpty(id, "id is empty.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("/resource/fileid/").append(id);
        return sb.toString();
    }
    
    /**
     * 获取文件外部访问路径<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default String getViewUrlByRelativePath(String relativePath) {
        if (!FileCatalogPermissionEnum.PUBLIC_READ.equals(getPermission())
                || !FileCatalogPermissionEnum.PUBLIC_READ_WRITE
                        .equals(getPermission())) {
            //没有公共读权限的文件，其ViewUrl为空字符串
            return "";
        }
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("/resource/file/")
                .append(getCatalog())
                .append("/")
                .append(relativePath);
        return sb.toString();
    }
    
    /**
     * 文件所属目录<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    String getCatalog();
    
    /**
     * 目录的资源加载器<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return ResourceLoader [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileResourceLoader getFileResourceLoader();
    
    /**
     * 获取文件资源<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return Resource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default FileResource getFileResource(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FileResource fileResource = getFileResourceLoader()
                .getFile(relativePath);
        return fileResource;
    }
    
    /**
     * 获取文件夹资源<br/>
     * <功能详细描述>
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FolderResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default FolderResource getFolderResource(String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        
        FolderResource folderResource = getFileResourceLoader()
                .getFolder(relativePath);
        return folderResource;
    }
    
    /**
     * 目录访问权限<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return FileCatalogPermissionEnum [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default FileCatalogPermissionEnum getPermission() {
        return FileCatalogPermissionEnum.PRIVATE;
    }
    
    /**
     * 默认优先级<br/>
     * @return
     */
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
