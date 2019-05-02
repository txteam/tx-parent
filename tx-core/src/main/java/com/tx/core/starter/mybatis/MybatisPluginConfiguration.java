/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.core.mybatis.interceptor.PagedDialectStatementHandlerInterceptor;
import com.tx.core.starter.persister.PersisterProperties;
import com.tx.core.util.dialect.DialectUtils;

/**
 * mybatisSupport自动配置类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class MybatisPluginConfiguration {
    
    /** 日志记录句柄 */
    protected static final Logger logger = LoggerFactory
            .getLogger(MybatisPluginConfiguration.class);
    
    /** 持久层配置属性 */
    private final PersisterProperties properties;
    
    /** 持久层配置 */
    public MybatisPluginConfiguration(PersisterProperties properties) {
        this.properties = properties;
    }
    
    /**
     * 写入分页插件<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return PagedDialectStatementHandlerInterceptor [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ConditionalOnMissingBean({ PagedDialectStatementHandlerInterceptor.class })
    @Bean("pagedDialectStatementHandlerInterceptor")
    public PagedDialectStatementHandlerInterceptor pagedDialectStatementHandlerInterceptor() {
        PagedDialectStatementHandlerInterceptor interceptor = new PagedDialectStatementHandlerInterceptor();
        Dialect dialect = DialectUtils
                .getDialect(properties.getDatabasePlatform());
        interceptor.setDialect(dialect);
        return interceptor;
    }
}
