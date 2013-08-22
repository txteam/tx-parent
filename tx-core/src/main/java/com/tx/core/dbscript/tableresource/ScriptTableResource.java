/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-8-19
 * <修改描述:>
 */
package com.tx.core.dbscript.tableresource;

import java.io.IOException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.exceptions.util.ExceptionWrapperUtils;

/**
 * TableResource基础类<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class ScriptTableResource extends BaseTableResource implements
        ResourceLoaderAware {
    
    private ResourceLoader resourceLoader;
    
    /** <默认构造函数> */
    public ScriptTableResource(DataSource dataSource) {
        super(dataSource);
    }

    /** <默认构造函数> */
    public ScriptTableResource(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected abstract String getCreateTableSqlScriptFilePath(
            DataSourceTypeEnum dataSourceType, String tableName);
    
    protected abstract String getInitDataSqlScriptFilePath(
            DataSourceTypeEnum dataSourceType, String tableName);
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * @param dataSourceType
     * @param tableName
     * @param params
     * @return
     */
    @Override
    protected String doGetCreateTableSqlScript(
            DataSourceTypeEnum dataSourceType, String tableName,
            Map<String, String> params) {
        String location = getCreateTableSqlScriptFilePath(dataSourceType,
                tableName);
        Resource resource = this.resourceLoader.getResource(location);
        AssertUtils.isExist(resource,
                "createTableSqlScript is null or not exist.tableName:{},filePath:{}",
                new Object[] { tableName, location });
        try {
            String script = IOUtils.toString(resource.getInputStream());
            return script;
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "读取创建脚本异常. tableName:{},filePath:{}",
                    new Object[] { tableName, location });
        }
    }
    
    /**
     * @param dataSourceType
     * @param tableName
     * @param params
     * @return
     */
    @Override
    protected String doGetInitDataSqlScript(DataSourceTypeEnum dataSourceType,
            String tableName, Map<String, String> params) {
        String location = getInitDataSqlScriptFilePath(dataSourceType,
                tableName);
        Resource resource = this.resourceLoader.getResource(location);
        AssertUtils.isExist(resource,
                "initTableSqlScript is null or not exist.tableName:{},filePath:{}",
                new Object[] { tableName, location });
        try {
            String script = IOUtils.toString(resource.getInputStream());
            return script;
        } catch (IOException e) {
            throw ExceptionWrapperUtils.wrapperIOException(e,
                    "读取初始化脚本异常. tableName:{},filePath:{}",
                    new Object[] { tableName, location });
        }
    }
}
