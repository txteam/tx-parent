/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.core.starter.component;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;
import com.tx.core.starter.mybatis.AbstractMybatisConfiguration;
import com.tx.core.starter.mybatis.ConfigurationCustomizer;
import com.tx.core.starter.mybatis.MybatisAutoConfiguration;
import com.tx.core.starter.mybatis.MybatisProperties;

/**
 * 基础数据持久层配置逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties(ComponentProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class ComponentSupportAutoConfiguration
        extends AbstractMybatisConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //
    }
    
    /** mybatis属性 */
    private MybatisProperties properties;
    
    /** 数据源 */
    private DataSource dataSource;
    
    /** <默认构造函数> */
    public ComponentSupportAutoConfiguration(MybatisProperties properties,
            ObjectProvider<DatabaseIdProvider> databaseIdProvider,
            ObjectProvider<Interceptor[]> interceptorsProvider,
            ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
            DataSource dataSource) {
        super(databaseIdProvider, interceptorsProvider,
                configurationCustomizersProvider);
        
        this.properties = (MybatisProperties) properties.clone();
        this.properties.setMapperLocations(new String[] {
                "classpath*:com/tx/component/basicdata/dao/impl/*SqlMap_BASICDATA.xml",
                "classpath*:com/tx/component/configuration/dao/impl/*SqlMap_BASICDATA.xml" });
        
        this.dataSource = dataSource;
    }
    
    /**
     * 构造sqlSessionFactory
     * <功能详细描述>
     * @return
     * @throws Exception [参数说明]
     * 
     * @return SqlSessionFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "tx.component.sqlSessionFactory")
    @ConditionalOnMissingBean(name = "tx.component.sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactory factory = MyBatisDaoSupportHelper
                .buildSqlSessionFactory(this.dataSource,
                        this.properties,
                        this.databaseIdProvider,
                        this.interceptors,
                        this.configurationCustomizers,
                        this.resourceLoader);
        return factory;
    }
    
    /**
     * 构造sqlSessionTemplate
     * <功能详细描述>
     * @return
     * @throws Exception [参数说明]
     * 
     * @return SqlSessionTemplate [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean(name = "tx.component.sqlSessionTemplate")
    @ConditionalOnMissingBean(name = "tx.component.sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        ExecutorType executorType = this.properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory(), executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory());
        }
    }
    
    /**
     * 注册myBatisDaoSupport类<br/>
     * <功能详细描述>
     * @param sqlSessionFactory
     * @return [参数说明]
     * 
     * @return MyBatisDaoSupport [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Bean("tx.component.myBatisDaoSupport")
    @ConditionalOnMissingBean(name = "tx.component.myBatisDaoSupport")
    public MyBatisDaoSupport myBatisDaoSupport() throws Exception {
        MyBatisDaoSupport myBatisDaoSupport = new MyBatisDaoSupport(
                sqlSessionTemplate());
        return myBatisDaoSupport;
    }
}
