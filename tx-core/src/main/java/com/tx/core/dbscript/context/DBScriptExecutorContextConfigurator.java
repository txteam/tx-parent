/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-17
 * <修改描述:>
 */
package com.tx.core.dbscript.context;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.ComputerEnvironment;

/**
 * 数据库脚本自动执行容器<br/>
 * 
 * @author brady
 * @version [版本号, 2013-12-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DBScriptExecutorContextConfigurator implements InitializingBean {
    
    protected static final Logger logger = LoggerFactory.getLogger(DBScriptExecutorContext.class);
    
    /** 数据源类型 */
    protected DataSourceTypeEnum dataSourceType;
    
    /** 数据源 */
    protected DataSource dataSource;
    
    /** jdbcTemplate */
    protected JdbcTemplate jdbcTemplate;
    
    /** 容器是否关闭，如果关闭后，脚本将不会被执行，即使被显示调用脚本也不会执行 */
    private boolean enable = true;
    
    /** 在执行脚本过程中如果出现的错误是否继续执行 */
    private boolean continueOnError = true;
    
    /** 是否忽略脚本执行过程中错误的drop语句 */
    private boolean ignoreFailedDrops = true;
    
    /** 是否更新不存在容器表中的数据 */
    private boolean updateNotExistTableInContext = false;
    
    /** 脚本的编码 */
    private String sqlScriptEncoding = "UTF-8";
    
    /** 在enable == true 的情况，如果该值不为空，则当前机器ip必须含有该指定ip容器才会被执行 */
    private String enableIpAddress;
    
    /** 在enable == true 的情况，如果该值不为空，则当前机器的mac地址必须包含指定的mac地址才会被执行 */
    private String enableMacAddress;
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        AssertUtils.notNull(dataSourceType, "dataSourceType is null.");
        AssertUtils.notNull(dataSource == null, "dataSource is null.");
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        }
    }
    
    /**
     * @return 返回 enable
     */
    public boolean isEnable() {
        if (!enable) {
            return false;
        } else {
            if (!StringUtils.isEmpty(this.enableIpAddress)) {
                if (!ComputerEnvironment.getHostAddressSet()
                        .contains(this.enableIpAddress)) {
                    return false;
                }
            }
            if (!StringUtils.isEmpty(this.enableMacAddress)) {
                if (!ComputerEnvironment.getMacAddressSet()
                        .contains(this.enableMacAddress)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    /**
     * @param 对enable进行赋值
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    /**
     * @return 返回 dataSourceType
     */
    public DataSourceTypeEnum getDataSourceType() {
        return dataSourceType;
    }
    
    /**
     * @param 对dataSourceType进行赋值
     */
    public void setDataSourceType(DataSourceTypeEnum dataSourceType) {
        this.dataSourceType = dataSourceType;
    }
    
    /**
     * @return 返回 dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * @param 对dataSource进行赋值
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * @param 对enableIpAddress进行赋值
     */
    public void setEnableIpAddress(String enableIpAddress) {
        this.enableIpAddress = enableIpAddress;
    }
    
    /**
     * @param 对enableMacAddress进行赋值
     */
    public void setEnableMacAddress(String enableMacAddress) {
        this.enableMacAddress = enableMacAddress;
    }
    
    /**
     * @return 返回 continueOnError
     */
    public boolean isContinueOnError() {
        return continueOnError;
    }
    
    /**
     * @param 对continueOnError进行赋值
     */
    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }
    
    /**
     * @return 返回 ignoreFailedDrops
     */
    public boolean isIgnoreFailedDrops() {
        return ignoreFailedDrops;
    }
    
    /**
     * @param 对ignoreFailedDrops进行赋值
     */
    public void setIgnoreFailedDrops(boolean ignoreFailedDrops) {
        this.ignoreFailedDrops = ignoreFailedDrops;
    }
    
    /**
     * @return 返回 sqlScriptEncoding
     */
    public String getSqlScriptEncoding() {
        return sqlScriptEncoding;
    }
    
    /**
     * @param 对sqlScriptEncoding进行赋值
     */
    public void setSqlScriptEncoding(String sqlScriptEncoding) {
        this.sqlScriptEncoding = sqlScriptEncoding;
    }
    
    /**
     * @return 返回 jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    /**
     * @param 对jdbcTemplate进行赋值
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * @return 返回 updateNotExistTableInContext
     */
    public boolean isUpdateNotExistTableInContext() {
        return updateNotExistTableInContext;
    }
    
    /**
     * @param 对updateNotExistTableInContext进行赋值
     */
    public void setUpdateNotExistTableInContext(
            boolean updateNotExistTableInContext) {
        this.updateNotExistTableInContext = updateNotExistTableInContext;
    }
}
