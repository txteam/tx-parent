/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.EntityDaoRegistrar;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.starter.util.CoreUtilAutoConfiguration;

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
@EnableConfigurationProperties(MybatisAutoConfigurationProperties.class)
@ConditionalOnClass({ MybatisAutoConfiguration.class, SqlSessionFactory.class,
        SqlSessionFactoryBean.class })
@ConditionalOnBean(value = { SqlSessionFactory.class, SqlSessionTemplate.class,
        MybatisAutoConfiguration.class })
@AutoConfigureAfter({ CoreUtilAutoConfiguration.class,
        MybatisAutoConfiguration.class })
public class MybatisSupportAutoConfiguration
        implements InitializingBean, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    /** 属性文件 */
    private MybatisAutoConfigurationProperties properties;
    
    /** sqlSessionTemplate句柄 */
    private SqlSessionTemplate sqlSessionTemplate;
    
    /** mybatis句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public MybatisSupportAutoConfiguration(
            MybatisAutoConfigurationProperties properties) {
        super();
        this.properties = properties;
    }
    
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
        if (StringUtils.isEmpty(properties.getSqlSessionTemplateRef())
                && this.applicationContext
                        .getBeansOfType(SqlSessionTemplate.class).size() == 1) {
            this.sqlSessionTemplate = this.applicationContext
                    .getBean(SqlSessionTemplate.class);
        } else {
            AssertUtils.isTrue(
                    this.applicationContext.containsBean(
                            properties.getSqlSessionTemplateRef()),
                    "sqlSessionTemplate:{} is not exist.",
                    properties.getSqlSessionTemplateRef());
            this.sqlSessionTemplate = this.applicationContext.getBean(
                    properties.getSqlSessionTemplateRef(),
                    SqlSessionTemplate.class);
        }
        
        this.myBatisDaoSupport = new MyBatisDaoSupport(sqlSessionTemplate);
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
    @ConditionalOnMissingBean
    @Bean("myBatisDaoSupport")
    public MyBatisDaoSupport myBatisDaoSupport(
            SqlSessionTemplate sqlSessionTemplate) {
        return this.myBatisDaoSupport;
    }
    
    /**
     * 实体自动持久化注册机<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return EntityDaoRegistrar [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ConditionalOnMissingBean
    @Bean("entityDaoRegistrar")
    public EntityDaoRegistrar entityDaoRegistrar() {
        EntityDaoRegistrar bean = new EntityDaoRegistrar(
                properties.getBasePackages(), this.myBatisDaoSupport);
        return bean;
    }
}
