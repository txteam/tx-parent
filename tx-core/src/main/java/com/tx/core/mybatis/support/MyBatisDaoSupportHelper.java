/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年11月20日
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.tx.core.mybatis.interceptor.PagedDialectStatementHandlerInterceptor;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 辅助构建MybatisDaoSupport实例<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年11月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MyBatisDaoSupportHelper {
    
    private static ResourceLoader defaultResourceLoader = new DefaultResourceLoader();
    
    private static PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    
    /**
     * 构建MybatisDaoSupport
     * <功能详细描述>
     * @param configLocation
     * @param mapperLocations
     * @param dataSourceType
     * @param dataSource
     * @return
     * @throws Exception [参数说明]
     * 
     * @return MyBatisDaoSupport [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static MyBatisDaoSupport buildMyBatisDaoSupport(
            String configLocation, DataSourceTypeEnum dataSourceType,
            DataSource dataSource) throws Exception {
        PersistenceExceptionTranslator exceptionTranslator = buildDefaultExceptionTranslator(dataSource);
        
        MyBatisDaoSupport myBatisDaoSupport = doBuildMyBatisDaoSupport(configLocation,
                null,
                dataSourceType,
                dataSource,
                exceptionTranslator);
        
        return myBatisDaoSupport;
    }
    
    /**
      * 构建MybatisDaoSupport
      * <功能详细描述>
      * @param configLocation
      * @param mapperLocations
      * @param dataSourceType
      * @param dataSource
      * @return
      * @throws Exception [参数说明]
      * 
      * @return MyBatisDaoSupport [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static MyBatisDaoSupport buildMyBatisDaoSupport(
            String configLocation, String[] mapperLocations,
            DataSourceTypeEnum dataSourceType, DataSource dataSource)
            throws Exception {
        PersistenceExceptionTranslator exceptionTranslator = buildDefaultExceptionTranslator(dataSource);
        
        MyBatisDaoSupport myBatisDaoSupport = doBuildMyBatisDaoSupport(configLocation,
                mapperLocations,
                dataSourceType,
                dataSource,
                exceptionTranslator);
        
        return myBatisDaoSupport;
    }
    
    /**
      * 构建MybatisDaoSupport实例<br/>
      * <功能详细描述>
      * @param isSingler
      * @param dataSourceType
      * @param dataSource
      * @return [参数说明]
      * 
      * @return MyBatisDaoSupport [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static MyBatisDaoSupport doBuildMyBatisDaoSupport(
            String configLocation, String[] mapperLocations,
            DataSourceTypeEnum dataSourceType, DataSource dataSource,
            PersistenceExceptionTranslator exceptionTranslator)
            throws Exception {
        SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory(configLocation,
                mapperLocations,
                dataSourceType,
                dataSource);
        
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(
                sqlSessionFactory, ExecutorType.SIMPLE, exceptionTranslator);
        MyBatisDaoSupport myBatisDaoSupport = new MyBatisDaoSupport();
        myBatisDaoSupport.setSqlSessionTemplate(sqlSessionTemplate);
        myBatisDaoSupport.afterPropertiesSet();
        
        return myBatisDaoSupport;
    }
    
    /** 
     * 构建sqlSessionFactoryBean
     * <功能详细描述>
     * @param configLocation
     * @param dataSourceType
     * @param dataSource
     * @return
     * @throws Exception [参数说明]
     * 
     * @return SqlSessionFactory [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static SqlSessionFactory buildSqlSessionFactory(
            String configLocation, String[] mapperLocations,
            DataSourceTypeEnum dataSourceType, DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setFailFast(true);
        sqlSessionFactoryBean.setTypeHandlersPackage("com.tx.core.mybatis.handler");
        sqlSessionFactoryBean.setPlugins(new Interceptor[] { buildPagedDialectStatementHandlerInterceptor(dataSourceType) });
        
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(defaultResourceLoader.getResource(configLocation));
        //if(applicationContext)
        Set<Resource> mapperLocationResourcesSet = new HashSet<>();
        if (mapperLocations != null) {
            for (String mapperLocationTemp : mapperLocations) {
                Resource[] resourcesTemp = pathMatchingResourcePatternResolver.getResources(mapperLocationTemp);
                mapperLocationResourcesSet.addAll(new HashSet<>(
                        Arrays.asList(resourcesTemp)));
            }
            sqlSessionFactoryBean.setMapperLocations(mapperLocationResourcesSet.toArray(new Resource[] {}));
        }
        //sqlSessionFactoryBean.setTypeHandlersPackage("com.tx.core.mybatis.handler");
        //PagedDialectStatementHandlerInterceptor pagedInterceptor = new PagedDialectStatementHandlerInterceptor();
        //pagedInterceptor.setDataSourceType(dataSourceType);
        //sqlSessionFactoryBean.setPlugins(new Interceptor[] { pagedInterceptor });
        
        sqlSessionFactoryBean.afterPropertiesSet();
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) sqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }
    
    /**
      * 构建分页容器支撑拦截器<br/>
      * <功能详细描述>
      * @param dataSourceType
      * @return [参数说明]
      * 
      * @return Interceptor [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static Interceptor buildPagedDialectStatementHandlerInterceptor(
            DataSourceTypeEnum dataSourceType) {
        PagedDialectStatementHandlerInterceptor resInterceptor = new PagedDialectStatementHandlerInterceptor();
        resInterceptor.setDataSourceType(dataSourceType);
        return resInterceptor;
    }
    
    /**
     * 构建Mybatis操作句柄的异常翻译体<br/>
     * <功能详细描述>
     * @param dataSource
     * @return [参数说明]
     * 
     * @return PersistenceExceptionTranslator [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static PersistenceExceptionTranslator buildDefaultExceptionTranslator(
            DataSource dataSource) {
        PersistenceExceptionTranslator defaultExceptionTranslator = new MyBatisExceptionTranslator(
                dataSource, false);
        return defaultExceptionTranslator;
    }
}
