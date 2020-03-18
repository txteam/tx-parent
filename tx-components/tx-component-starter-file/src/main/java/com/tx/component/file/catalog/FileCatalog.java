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
     * 默认优先级<br/>
     * @return
     */
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
    
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
    default boolean match(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getCatalog(),
                "fileDefinition.catalog is empty.");
        if (StringUtils.equalsIgnoreCase(getCatalog(), fd.getCatalog())) {
            return true;
        } else {
            return false;
        }
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
        return "";
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
     * @param fd
     * @return [参数说明]
     * 
     * @return Resource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default FileResource getFileResource(FileDefinition fd) {
        AssertUtils.notNull(fd, "fileDefinition is null.");
        AssertUtils.notEmpty(fd.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileResource fileResource = getFileResourceLoader().getResource(fd);
        return fileResource;
    }
}
