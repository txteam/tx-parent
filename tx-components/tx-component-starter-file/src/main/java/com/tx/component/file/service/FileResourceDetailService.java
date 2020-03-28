package com.tx.component.file.service;

import java.io.InputStream;
import java.util.List;

import com.tx.component.file.model.FileResourceDetail;
import com.tx.component.file.resource.FolderResource;

/**
 * 文件资源业务层<br/>
 * 调用该业务层，不进行FileDefinition的持久化操作
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface FileResourceDetailService {
    
    /**
     * 查询文件资源<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileResourceDetail getFileResourceDetail(String catalog,
            String relativePath);
    
    /**
     * 判断文件资源是否存在<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean exists(String catalog, String relativePath);
    
    /**
     * 保存文件资源<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @param input [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileResourceDetail save(String catalog, String relativePath,
            InputStream input);
    
    /**
     * 新增文件资源<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @param input [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileResourceDetail add(String catalog, String relativePath,
            InputStream input);
    
    /**
     * 相对目录中的文件遍历<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FileResource [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FolderResource getFolderResource(String catalog, String relativePath);
    
    /**
     * 删除文件<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void delete(String catalog, String relativePath);
    
    /**
     * 保存文件资源<br/>
     * <功能详细描述>
     * @param folderResource
     * @param relativePath
     * @param input [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileResourceDetail save(FolderResource folderResource, String relativePath,
            InputStream input);
    
    /**
     * 新增文件资源<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @param input [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileResourceDetail add(FolderResource folderResource, String relativePath,
            InputStream input);
    
    /**
     * 删除文件<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void delete(FolderResource folderResource, String relativePath);
    
    /**
     * 判断文件资源是否存在<br/>
     * <功能详细描述>
     * @param folderResource
     * @param relativePath
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean exists(FolderResource folderResource, String relativePath);
    
    /**
     * 判断文件资源是否存在<br/>
     * <功能详细描述>
     * @param folderResource
     * @param relativePath
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<FileResourceDetail> list(FolderResource folderResource,
            String relativePath);
}