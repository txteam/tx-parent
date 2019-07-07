/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月23日
 * <修改描述:>
 */
package com.tx.component.auth.configuration;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.auth.AuthConstants;
import com.tx.component.auth.dao.AuthItemDao;
import com.tx.component.auth.dao.AuthRefItemDao;
import com.tx.component.auth.dao.AuthTypeItemDao;
import com.tx.component.auth.dao.impl.AuthItemDaoMybatisImpl;
import com.tx.component.auth.dao.impl.AuthRefItemDaoMybatisImpl;
import com.tx.component.auth.dao.impl.AuthTypeItemDaoMybatisImpl;
import com.tx.component.auth.service.AuthItemService;
import com.tx.component.auth.service.AuthRefItemService;
import com.tx.component.auth.service.AuthTypeItemService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.starter.component.ComponentConstants;

/**
 * 权限容器自动配置项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class AuthContextPersisterConfiguration {
    
    /**
     * mybatis持久层逻辑实现<br/>
     * <功能详细描述>
     * 
     * @author  Administrator
     * @version  [版本号, 2019年5月2日]
     * @see  [相关类/方法]
     * @since  [产品/模块版本]
     */
    @Configuration
    @ConditionalOnProperty(prefix = ComponentConstants.PERSISTER_PROPERTIES_PREFIX, value = "type", havingValue = "mybatis")
    @ConditionalOnBean(name = { "tx.component.myBatisDaoSupport" })
    public static class MybatisAuthContextPersisterConfiguration
            implements InitializingBean {
        
        /** mybatis属性 */
        @Resource(name = "tx.component.myBatisDaoSupport")
        private MyBatisDaoSupport myBatisDaoSupport;
        
        /** 事务管理器 */
        @SuppressWarnings("unused")
        private TransactionTemplate transactionTemplate;
        
        /** <默认构造函数> */
        public MybatisAuthContextPersisterConfiguration(
                PlatformTransactionManager transactionManager) {
            this.transactionTemplate = new TransactionTemplate(
                    transactionManager);
        }
        
        /**
         * @throws Exception
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            AssertUtils.notNull(this.myBatisDaoSupport,
                    "myBatisDaoSupport is null.");
        }
        
        /**
         * 权限类型项持久层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return AuthTypeItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(name = AuthConstants.BEAN_NAME_AUTH_TYPE_ITEM_DAO)
        public AuthTypeItemDao authTypeItemDao() {
            AuthTypeItemDaoMybatisImpl dao = new AuthTypeItemDaoMybatisImpl();
            dao.setMyBatisDaoSupport(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 权限项持久层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return AuthItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(AuthConstants.BEAN_NAME_AUTH_ITEM_DAO)
        public AuthItemDao authItemDao() {
            AuthItemDaoMybatisImpl dao = new AuthItemDaoMybatisImpl();
            dao.setMyBatisDaoSupport(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 权限引用项持久层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return AuthRefItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(AuthConstants.BEAN_NAME_AUTH_REF_ITEM_DAO)
        public AuthRefItemDao authRefItemDao() {
            AuthRefItemDaoMybatisImpl dao = new AuthRefItemDaoMybatisImpl();
            dao.setMyBatisDaoSupport(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 权限类型项业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return AuthTypeItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(AuthConstants.BEAN_NAME_AUTH_TYPE_ITEM_SERVICE)
        public AuthTypeItemService authTypeItemService(
                AuthTypeItemDao authTypeItemDao) {
            AuthTypeItemService service = new AuthTypeItemService(
                    authTypeItemDao);
            return service;
        }
        
        /**
         * 权限项业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return AuthItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(AuthConstants.BEAN_NAME_AUTH_ITEM_SERVICE)
        public AuthItemService authItemService(AuthItemDao authItemDao) {
            AuthItemService service = new AuthItemService(authItemDao);
            return service;
        }
        
        /**
         * 权限引用项业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return AuthRefItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(AuthConstants.BEAN_NAME_AUTH_REF_ITEM_SERVICE)
        public AuthRefItemService authRefItemService(
                AuthRefItemDao authRefItemDao) {
            AuthRefItemService service = new AuthRefItemService(authRefItemDao);
            return service;
        }
    }
}
