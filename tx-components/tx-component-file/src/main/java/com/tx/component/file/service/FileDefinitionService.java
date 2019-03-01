/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.file.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.model.FileDefinition;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * FileDefinition的业务层<br/>
 * <功能详细描述>
 *
 * @author Rain.he
 * @version [版本号, 2015年3月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileDefinitionService {

    /** 日志 */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(FileDefinitionService.class);

    /** 文件定义Dao层 */
    @Resource
    private FileDefinitionDao fileDefinitionDao;

    /**
     * 将fileDefinition实例插入数据库中保存<br/>
     * <功能详细描述>
     *
     * @param fileDefinition 文件定义
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getFilename(),
                "fileDefinition.filename is null.");
        AssertUtils.notEmpty(fileDefinition.getRelativePath(),
                "fileDefinition.filename is null.");

        Date now = new Date();
        fileDefinition.setCreateDate(now);
        fileDefinition.setLastUpdateDate(now);
        if (StringUtils.isEmpty(fileDefinition.getFilenameExtension())) {
            fileDefinition.setFilenameExtension(StringUtils.getFilenameExtension(fileDefinition.getFilename()));
        }

        //插入数据
        this.fileDefinitionDao.insertFileDefinition(fileDefinition);
    }

    /**
     * 将对应的记录移除到历史表<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void moveToHisById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        //根据id查询文件定义
        FileDefinition fileDefinition = findById(fileDefinitionId);

        //插入历史表
        Date now = new Date();
        fileDefinition.setDeleteDate(now);
        this.fileDefinitionDao.insertFileDefinitionToHis(fileDefinition);

        //删除当前表中的数据
        deleteById(fileDefinitionId);
    }

    /**
     * 将对应的记录移除到历史表<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return void
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void moveToHis(FileDefinition fileDefinition) {
        AssertUtils.notNull(fileDefinition, "fileDefinition is null.");
        AssertUtils.notEmpty(fileDefinition.getId(),
                "fileDefinition.id is empty.");

        //插入历史表
        Date now = new Date();
        fileDefinition.setDeleteDate(now);
        this.fileDefinitionDao.insertFileDefinitionToHis(fileDefinition);

        //删除当前表中的数据
        deleteById(fileDefinition.getId());
    }

    /**
     * 根据文件定义id删除fileDefinition实例<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return int 返回删除的数据条数
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");

        FileDefinition condition = new FileDefinition();
        condition.setId(fileDefinitionId);
        return this.fileDefinitionDao.deleteFileDefinition(condition);
    }

    /**
     * 根据Id查询FileDefinition实体<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return FileDefinition 文件定义
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinition findById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "id is empty.");

        FileDefinition condition = new FileDefinition();
        condition.setId(fileDefinitionId);

        return this.fileDefinitionDao.findFileDefinition(condition);
    }

    /**
     * 根据Id查询FileDefinition实体<br/>
     *
     * @param fileDefinitionId 文件定义id
     * @return FileDefinition 文件定义
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinition findByRelativePath(String module, String relativePath) {
        AssertUtils.notEmpty(relativePath, "relativePath is empty.");

        FileDefinition condition = new FileDefinition();
        condition.setModule(module);
        condition.setRelativePath(relativePath);

        return this.fileDefinitionDao.findFileDefinition(condition);
    }

    /**
     * 根据所属模块以及所在目录查询文件定义<br/>
     * <功能详细描述>
     *
     * @param module
     * @param relativeFolder
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<FileDefinition> queryList(String module, String relativeFolder,
                                          String[] filenameExtensions, Map<String, Object> params) {
        AssertUtils.notEmpty(module, "module is empty.");

        params = params == null ? new HashMap<String, Object>() : params;
        params.put("module", module);
        params.put("relativeFolder", relativeFolder);
        params.put("filenameExtensions",
                filterFilenameExtensions(filenameExtensions));

        List<FileDefinition> resList = this.fileDefinitionDao.queryFileDefinition(params);
        return resList;
    }

    /**
     * 根据所属模块以及所在目录查询文件定义<br/>
     * <功能详细描述>
     *
     * @param module
     * @param relativeFolder
     * @param params
     * @return List<FileDefinition> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<FileDefinition> queryPagedList(String module,
                                                   String relativeFolder, String[] filenameExtensions,
                                                   Map<String, Object> params, int pageIndex, int pageSize) {
        AssertUtils.notEmpty(module, "module is empty.");

        params = params == null ? new HashMap<String, Object>() : params;
        params.put("module", module);
        params.put("relativeFolder", relativeFolder);
        params.put("filenameExtensions",
                filterFilenameExtensions(filenameExtensions));

        PageHelper.startPage(pageIndex, pageSize);

        List<FileDefinition> resPagedList = this.fileDefinitionDao.queryFileDefinition(params);

        PageInfo<FileDefinition> pageInfo = new PageInfo<>(resPagedList);
        return pageInfo;
    }

    /**
     * 过滤处理文件扩展名<br/>
     * <功能详细描述>
     *
     * @param filenameExtensions
     * @return Set<String> [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private Set<String> filterFilenameExtensions(String[] filenameExtensions) {
        Set<String> res = new HashSet<>();
        if (ArrayUtils.isEmpty(filenameExtensions)) {
            return res;
        }
        for (String fileExt : filenameExtensions) {
            if (fileExt.lastIndexOf(".") >= 0) {
                fileExt = fileExt.substring(fileExt.lastIndexOf('.') + 1);
            }
            if (!StringUtils.isEmpty(fileExt)) {
                res.add(fileExt);
            }
        }
        return res;
    }

    /**
     * 更新<br/>
     *
     * @param fileDefinition 文件定义
     * @return int 更新条数
     * @throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
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
        //updateRowMap.put("deleteDate", fileDefinition.getDeleteDate());
        //updateRowMap.put("viewUrl", fileDefinition.getViewUrl());
        updateRowMap.put("relativePath", fileDefinition.getRelativePath());
        updateRowMap.put("filename", fileDefinition.getFilename());
        updateRowMap.put("filenameExtension",
                fileDefinition.getFilenameExtension());

        updateRowMap.put("lastUpdateDate", new Date());

        return this.fileDefinitionDao.updateFileDefinition(updateRowMap);
    }
}
