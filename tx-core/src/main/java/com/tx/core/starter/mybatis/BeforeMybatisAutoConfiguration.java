/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.tx.core.mybatis.interceptor.PagedDialectStatementHandlerInterceptor;
import com.tx.core.starter.util.CoreUtilAutoConfiguration;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * Mybatis插件、自定义配置等的加载位置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ MybatisAutoConfiguration.class, SqlSessionFactory.class,
        SqlSessionFactoryBean.class, })
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({CoreUtilAutoConfiguration.class,DataSourceAutoConfiguration.class})
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class BeforeMybatisAutoConfiguration {
    
    @Bean("pagedDialectStatementHandlerInterceptor")
    public PagedDialectStatementHandlerInterceptor pagedDialectStatementHandlerInterceptor() {
        PagedDialectStatementHandlerInterceptor interceptor = new PagedDialectStatementHandlerInterceptor();
        interceptor.setDataSourceType(DataSourceTypeEnum.MYSQL);
        return interceptor;
    }
}
