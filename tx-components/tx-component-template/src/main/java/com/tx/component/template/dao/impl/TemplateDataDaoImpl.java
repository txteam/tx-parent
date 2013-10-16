/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-14
 * <修改描述:>
 */
package com.tx.component.template.dao.impl;

import java.util.Map;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.component.template.dao.TemplateDataDao;
import com.tx.component.template.model.TemplateTable;
import com.tx.component.template.model.TemplateTablePersisterHelper;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TemplateDataDaoImpl implements TemplateDataDao {
    
    private DataSource dataSource;
    
    private String systemId;
    
    private DataSourceTypeEnum dataSourceType;
    
    private JdbcTemplate jdbcTemplate;

    /** <默认构造函数> */
    public TemplateDataDaoImpl(DataSource dataSource, String systemId,
            DataSourceTypeEnum dataSourceType) {
        super();
        
        AssertUtils.notNull(dataSource,"dataSource is null");
        
        this.dataSource = dataSource;
        this.systemId = systemId;
        this.dataSourceType = dataSourceType;
        
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
    
    /**
     * @param templateTable
     * @param rowMap
     * @return
     */
    @Override
    public String insert(TemplateTable templateTable, Map<String, Object> rowMap) {
        TemplateTablePersisterHelper helper = TemplateTablePersisterHelper.newInstance(templateTable);
        //this.jdbcTemplate.update(helper.getInsertSql(), pss)
        
        return null;
    }
    
}
