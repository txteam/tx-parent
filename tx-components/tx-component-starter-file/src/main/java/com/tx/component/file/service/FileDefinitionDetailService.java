/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月19日
 * <修改描述:>
 */
package com.tx.component.file.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.model.FileDefinitionDetail;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * 文件定义详情业务层<br/>
 *    既有数据也同时进行文件资源的处理
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface FileDefinitionDetailService {
    
    /**
     * 保存文件定义<br/>
     * <功能详细描述>
     * @param catalog
     * @param input
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default FileDefinitionDetail save(String catalog, InputStream input) {
        FileDefinitionDetail fdd = save(catalog, null, input);
        return fdd;
    }
    
    /**
     * 保存文件<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @param input
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    default FileDefinitionDetail save(String catalog, String relativePath,
            InputStream input) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        AssertUtils.notNull(input, "input is null.");
        
        FileDefinition fd = new FileDefinition(catalog, relativePath);
        FileDefinitionDetail fdd = save(catalog, null, input);
        return fdd;
    }
    
    /**
     * 保存文件定义<br/>
     * <功能详细描述>
     * @param fd
     * @param input
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileDefinitionDetail save(FileDefinition fd, InputStream input);
    
    default FileDefinitionDetail add(String catalog, InputStream input) {
        FileDefinitionDetail fdd = add(catalog, null, input);
        return fdd;
    }
    
    public FileDefinitionDetail add(String catalog, String relativePath,
            InputStream input);
    
    public FileDefinitionDetail add(FileDefinition fd, InputStream input);
    
    public boolean deleteById(String id);
    
    public boolean deleteById(String id, boolean recycleAble);
    
    public boolean deleteByByRelativePath(String catalog, String relativePath);
    
    boolean deleteByByRelativePath(String catalog, String relativePath,
            boolean recycleAble);
    
    public FileDefinitionDetail findById(String fileId);
    
    /**
     * 
     * <功能详细描述>
     * @param catalog
     * @param relativePath
     * @return [参数说明]
     * 
     * @return FileDefinitionDetail [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinitionDetail findByRelativePath(String catalog,
            String relativePath);
    
    /**
     * 根据条件查询文件定义<br/>
     * <功能详细描述>
     * @param catalog
     * @param folderId 可以为空
     * @param filenameExtensions 可以为空
     * @param querier
     * @return [参数说明]
     * 
     * @return List<FileDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<FileDefinitionDetail> queryList(String catalog, String folderId,
            String[] filenameExtensions, Querier querier);
    
    /**
     * 根据所属模块以及所在目录查询文件定义<br/>
     * <功能详细描述>
     *
     * @param catalog 不为空
     * @param folderId 可以为空
     * @param filenameExtensions 可以为空
     * @param querier
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    PagedList<FileDefinitionDetail> queryPagedList(String catalog,
            String folderId, String[] filenameExtensions, Querier querier,
            int pageIndex, int pageSize);
}
