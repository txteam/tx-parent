package com.tx.component.file.service;

import java.util.List;
import java.util.Map;

import com.tx.component.file.model.FileDefinition;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * 文件定义业务层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface FileDefinitionService {
    
    /**
     * 文件定义<br/>
     * <功能详细描述>
     * @param fileDefinition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileDefinition save(FileDefinition fileDefinition);
    
    /**
     * 将fileDefinition实例插入数据库中保存<br/>
     * <功能详细描述>
     *
     * @param fileDefinition 文件定义
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    void insert(FileDefinition fileDefinition);
    
    /**
     * 将对应的记录移除到历史表<br/>
     * @param fileDefinitionId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean moveToHisById(String fileDefinitionId);
    
    /**
     * 将对应的记录移除到历史表<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean moveToHis(FileDefinition fileDefinition);
    
    /**
     * 根据文件定义id删除fileDefinition实例<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return int 返回删除的数据条数
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    int deleteById(String fileDefinitionId);
    
    /**
     * 根据相对路径删除文件<br/>
     * <功能详细描述>
     * @param relativePath
     * @param catalog
     * @param module
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    boolean deleteByRelativePath(String catalog, String relativePath);
    
    /**
     * 根据文件定义ID查询文件定义实体<br/>
     * <功能详细描述>
     * @param fileDefinitionId
     * @return [参数说明]
     * 
     * @return FileDefinition [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileDefinition findById(String fileDefinitionId);
    
    /**
     * 根据相对路径查询文件定义<br/>
     * <功能详细描述>
     * @param relativePath
     * @param catalog
     * @param module
     * @return [参数说明]
     * 
     * @return FileDefinition [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    FileDefinition findByRelativePath(String catalog, String relativePath);
    
    /**
     * 根据条件查询文件定义<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativeFolder
     * @param filenameExtensions
     * @param params
     * @return [参数说明]
     * 
     * @return List<FileDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<FileDefinition> queryList(String catalog, String relativeFolder,
            String[] filenameExtensions, Map<String, Object> params);
    
    /**
     * 根据条件查询文件定义<br/>
     * <功能详细描述>
     * @param catalog
     * @param relativeFolder
     * @param filenameExtensions
     * @param querier
     * @return [参数说明]
     * 
     * @return List<FileDefinition> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    List<FileDefinition> queryList(String catalog, String relativeFolder,
            String[] filenameExtensions, Querier querier);
    
    /**
     * 根据所属模块以及所在目录查询文件定义<br/>
     * <功能详细描述>
     *
     * @param catalog
     * @param relativeFolder
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    PagedList<FileDefinition> queryPagedList(String catalog,
            String relativeFolder, String[] filenameExtensions,
            Map<String, Object> params, int pageIndex, int pageSize);
    
    /**
     * 根据所属模块以及所在目录查询文件定义<br/>
     * <功能详细描述>
     *
     * @param catalog
     * @param relativeFolder
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    PagedList<FileDefinition> queryPagedList(String catalog,
            String relativeFolder, String[] filenameExtensions, Querier querier,
            int pageIndex, int pageSize);
    
    /**
     * 更新<br/>
     *
     * @param fileDefinition 文件定义
     * @return int 更新条数
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    int updateById(FileDefinition fileDefinition);
    
}