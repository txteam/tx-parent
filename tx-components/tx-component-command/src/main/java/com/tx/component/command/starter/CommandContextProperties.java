/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月1日
 * <修改描述:>
 */
package com.tx.component.command.starter;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
  * 命令容器配置<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2018年5月1日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@ConfigurationProperties(prefix = "command")
public class CommandContextProperties {
    
    private DataSource datasource;

    /**
     * @return 返回 datasource
     */
    public DataSource getDatasource() {
        return datasource;
    }

    /**
     * @param 对datasource进行赋值
     */
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }
}
