/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.file.dao.impl;

import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.model.FileDefinition;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.dao.impl.MybatisBaseDaoImpl;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * FileDefinition持久层
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileDefinitionDaoImpl
        extends MybatisBaseDaoImpl<FileDefinition, String>
        implements FileDefinitionDao {
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public FileDefinitionDaoImpl() {
        super();
    }
    
    /** <默认构造函数> */
    public FileDefinitionDaoImpl(MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
    
    /* 覆父类中afterPropertiesSet方法，不进行sqlMap自动注入 */
    @Override
    public void afterPropertiesSet() {
        AssertUtils.notNull(getMyBatisDaoSupport(),
                "myBatisDaoSupport is null.");
        AssertUtils.notNull(this.entityType, "entityType is null.");
        AssertUtils.notNull(this.pkPropertyType, "pkPropertyType is null.");
    }
    
    /**
     * @param fileDefinition
     */
    @Override
    public void insertToHis(FileDefinition fileDefinition) {
        this.myBatisDaoSupport.insert("fileDefinition.insertToHis",
                fileDefinition);
    }
    
    /**
     * @return 返回 myBatisDaoSupport
     */
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return myBatisDaoSupport;
    }
    
    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
}
