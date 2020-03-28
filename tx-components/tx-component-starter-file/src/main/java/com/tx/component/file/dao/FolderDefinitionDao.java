/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.file.dao;

import com.tx.component.file.model.FolderDefinition;
import com.tx.core.mybatis.dao.MybatisBaseDao;

/**
 * FolderDefinition持久层
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface FolderDefinitionDao
        extends MybatisBaseDao<FolderDefinition, String> {
    
    /**
     * 插入到历史表<br/>
     * <功能详细描述>
     * @param fileDefinition [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void insertToHis(FolderDefinition folderDefinition);
}
