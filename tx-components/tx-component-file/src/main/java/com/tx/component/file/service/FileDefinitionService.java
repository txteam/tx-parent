/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.file.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.model.FileDefinition;
import com.tx.core.exceptions.util.AssertUtils;

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
    @Resource(name = "fileContext.fileDefinitionDao")
    private FileDefinitionDao fileDefinitionDao;
    
    /**
     * 将fileDefinition实例插入数据库中保存<br/>
     * <功能详细描述>
     * 
     * @param fileDefinition 文件定义
     * 
     * @return void
     * @exception [异常类型] [异常说明]
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
        
        this.fileDefinitionDao.insert(fileDefinition);
    }
    
    /**
     * 将对应的记录移除到历史表<br/>
     * 
     * @param fileDefinitionId 文件定义id
     * 
     * @return void
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void moveToHisById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        FileDefinition fileDefinition = findById(fileDefinitionId);
        
        Date now = new Date();
        fileDefinition.setDeleteDate(now);
        
        this.fileDefinitionDao.insertToHis(fileDefinition);
        deleteById(fileDefinitionId);
    }
    
    /**
     * 根据文件定义id删除fileDefinition实例<br/>
     * 
     * @param fileDefinitionId 文件定义id
     * 
     * @return int 返回删除的数据条数
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "fileDefinitionId is empty.");
        
        FileDefinition condition = new FileDefinition();
        condition.setId(fileDefinitionId);
        return this.fileDefinitionDao.delete(condition);
    }
    
    /**
     * 根据Id查询FileDefinition实体<br/>
     * 
     * @param fileDefinitionId 文件定义id
     * 
     * @return FileDefinition 文件定义
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public FileDefinition findById(String fileDefinitionId) {
        AssertUtils.notEmpty(fileDefinitionId, "id is empty.");
        
        FileDefinition condition = new FileDefinition();
        condition.setId(fileDefinitionId);
        
        return this.fileDefinitionDao.find(condition);
    }
    
    /**
     * 更新<br/>
     * 
     * @param fileDefinition 文件定义
     * 
     * @return int 更新条数
     * @exception [异常类型] [异常说明]
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
        updateRowMap.put("deleteDate", fileDefinition.getDeleteDate());
        updateRowMap.put("relativePath", fileDefinition.getRelativePath());
        updateRowMap.put("filenameExtension",
                fileDefinition.getFilenameExtension());
        updateRowMap.put("filename", fileDefinition.getFilename());
        updateRowMap.put("lastUpdateDate", fileDefinition.getLastUpdateDate());
        
        return this.fileDefinitionDao.update(updateRowMap);
    }
}
