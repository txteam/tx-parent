/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.file.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.model.FileDefinition;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;

/**
 * FileDefinition的业务层<br/>
 * <功能详细描述>
 *
 * @author Rain.he
 * @version [版本号, 2015年3月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileDefinitionServiceImpl
        implements InitializingBean, FileDefinitionService {
    
    /** 日志 */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory
            .getLogger(FileDefinitionServiceImpl.class);
    
    /** 文件定义Dao层 */
    private FileDefinitionDao fileDefinitionDao;
    
    /** <默认构造函数> */
    public FileDefinitionServiceImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public FileDefinitionServiceImpl(FileDefinitionDao fileDefinitionDao) {
        super();
        this.fileDefinitionDao = fileDefinitionDao;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }
    
    /**
     * @param fileDefinition
     */
    @Override
    public FileDefinition save(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getCatalog(),
                "fileDefinition.catalog is empty.");
        AssertUtils.notEmpty(fileDefinition.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        FileDefinition dbFileDeinition = null;
        //查询实例
        if (StringUtils.isEmpty(fileDefinition.getId())) {
            dbFileDeinition = findByRelativePath(fileDefinition.getCatalog(),
                    fileDefinition.getRelativePath());
        } else {
            dbFileDeinition = findById(fileDefinition.getId());
        }
        //如果实例为空
        if (dbFileDeinition == null) {
            fileDefinition.setId(null);
            dbFileDeinition = fileDefinition;
            //插入
            insert(dbFileDeinition);
        } else {
            //更新
            updateById(dbFileDeinition);
        }
        return dbFileDeinition;
    }
    
    /**
     * @param fileDefinition
     */
    @Override
    @Transactional
    public void insert(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getCatalog(),
                "fileDefinition.catalog is empty.");
        AssertUtils.notEmpty(fileDefinition.getRelativePath(),
                "fileDefinition.relativePath is empty.");
        
        fileDefinition.setFilename(
                StringUtils.getFilename(fileDefinition.getRelativePath()));
        String filenameExtension = fileDefinition.getRelativePath();
        filenameExtension = filenameExtension != null
                ? filenameExtension.toLowerCase() : null;
        fileDefinition.setFilenameExtension(filenameExtension);
        
        Date now = new Date();
        fileDefinition.setCreateDate(now);
        fileDefinition.setLastUpdateDate(now);
        
        //插入数据
        this.fileDefinitionDao.insert(fileDefinition);
    }
    
    /**
     * @param fileDefinitionId
     * @return
     */
    @Override
    @Transactional
    public boolean moveToHisById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        //根据id查询文件定义
        FileDefinition fileDefinition = findById(fileDefinitionId);
        if (fileDefinition == null) {
            return false;
        }
        //插入历史表(可当做回收站使用)
        Date now = new Date();
        fileDefinition.setDeleted(true);
        fileDefinition.setDeleteDate(now);
        this.fileDefinitionDao.insertToHis(fileDefinition);
        
        //删除当前表中的数据
        boolean flag = deleteById(fileDefinitionId) == 1;
        return flag;
    }
    
    /**
     * @param fileDefinition
     * @return
     */
    @Override
    @Transactional
    public boolean moveToHis(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getId(),
                "fileDefinition.id is empty.");
        
        //插入历史表
        Date now = new Date();
        fileDefinition.setDeleteDate(now);
        fileDefinition.setDeleted(true);
        this.fileDefinitionDao.insertToHis(fileDefinition);
        //删除当前表中的数据
        boolean flag = deleteById(fileDefinition.getId()) == 1;
        return flag;
    }
    
    /**
     * @param fileDefinitionId
     * @return
     */
    @Override
    @Transactional
    public int deleteById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        FileDefinition condition = new FileDefinition();
        condition.setId(fileDefinitionId);
        return this.fileDefinitionDao.delete(condition);
    }
    
    /**
     * @param catalog
     * @param relativePath
     * @return
     */
    @Override
    @Transactional
    public boolean deleteByRelativePath(String catalog, String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        FileDefinition condition = new FileDefinition();
        condition.setRelativePath(relativePath);
        condition.setCatalog(catalog);
        boolean flag = this.fileDefinitionDao.delete(condition) == 1;
        return flag;
    }
    
    /**
     * @param fileDefinitionId
     * @return
     */
    @Override
    public FileDefinition findById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "id is empty.");
        
        FileDefinition condition = new FileDefinition();
        condition.setId(fileDefinitionId);
        return this.fileDefinitionDao.find(condition);
    }
    
    /**
     * @param catalog
     * @param relativePath
     * @return
     */
    @Override
    public FileDefinition findByRelativePath(String catalog,
            String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        FileDefinition condition = new FileDefinition();
        condition.setCatalog(catalog);
        condition.setRelativePath(relativePath);
        
        FileDefinition fd = this.fileDefinitionDao.find(condition);
        return fd;
    }
    
    /**
     * @param catalog
     * @param relativeFolder
     * @param filenameExtensions
     * @param params
     * @return
     */
    @Override
    public List<FileDefinition> queryList(String catalog, String relativeFolder,
            String[] filenameExtensions, Map<String, Object> params) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("catalog", catalog);
        params.put("relativeFolder", relativeFolder);
        if (!ArrayUtils.isEmpty(filenameExtensions)) {
            params.put("filenameExtensions",
                    Arrays.asList(filenameExtensions)
                            .stream()
                            .map(fe -> fe.toLowerCase())
                            .collect(Collectors.toList()));
        }
        List<FileDefinition> resList = this.fileDefinitionDao.queryList(params);
        return resList;
    }
    
    /**
     * @param catalog
     * @param relativeFolder
     * @param filenameExtensions
     * @param querier
     * @return
     */
    @Override
    public List<FileDefinition> queryList(String catalog, String relativeFolder,
            String[] filenameExtensions, Querier querier) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        querier = querier == null ? new Querier() : querier;
        querier.getParams().put("catalog", catalog);
        querier.getParams().put("relativeFolder", relativeFolder);
        if (!ArrayUtils.isEmpty(filenameExtensions)) {
            querier.getParams().put("filenameExtensions",
                    Arrays.asList(filenameExtensions)
                            .stream()
                            .map(fe -> fe.toLowerCase())
                            .collect(Collectors.toList()));
        }
        List<FileDefinition> resList = this.fileDefinitionDao
                .queryList(querier);
        return resList;
    }
    
    /**
     * @param catalog
     * @param relativeFolder
     * @param filenameExtensions
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<FileDefinition> queryPagedList(String catalog,
            String relativeFolder, String[] filenameExtensions,
            Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("catalog", catalog);
        params.put("relativeFolder", relativeFolder);
        if (!ArrayUtils.isEmpty(filenameExtensions)) {
            params.put("filenameExtensions",
                    Arrays.asList(filenameExtensions)
                            .stream()
                            .map(fe -> fe.toLowerCase())
                            .collect(Collectors.toList()));
        }
        PagedList<FileDefinition> resPagedList = this.fileDefinitionDao
                .queryPagedList(params, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * @param catalog
     * @param relativeFolder
     * @param filenameExtensions
     * @param querier
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<FileDefinition> queryPagedList(String catalog,
            String relativeFolder, String[] filenameExtensions, Querier querier,
            int pageIndex, int pageSize) {
        AssertUtils.notEmpty(catalog, "catalog is empty.");
        
        querier = querier == null ? new Querier() : querier;
        querier.getParams().put("catalog", catalog);
        querier.getParams().put("relativeFolder", relativeFolder);
        if (!ArrayUtils.isEmpty(filenameExtensions)) {
            querier.getParams().put("filenameExtensions",
                    Arrays.asList(filenameExtensions)
                            .stream()
                            .map(fe -> fe.toLowerCase())
                            .collect(Collectors.toList()));
        }
        PagedList<FileDefinition> resPagedList = this.fileDefinitionDao
                .queryPagedList(querier, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * @param fileDefinition
     * @return
     */
    @Override
    @Transactional
    public int updateById(FileDefinition fileDefinition) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getId(),
                "fileDefinition.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", fileDefinition.getId());
        
        //需要更新的字段
        updateRowMap.put("relativePath", fileDefinition.getRelativePath());
        updateRowMap.put("filename",
                StringUtils.getFilename(fileDefinition.getRelativePath()));
        updateRowMap.put("filenameExtension",
                StringUtils
                        .getFilenameExtension(
                                fileDefinition.getFilenameExtension())
                        .toLowerCase());
        updateRowMap.put("lastUpdateDate", new Date());
        
        return this.fileDefinitionDao.update(updateRowMap);
    }
}
