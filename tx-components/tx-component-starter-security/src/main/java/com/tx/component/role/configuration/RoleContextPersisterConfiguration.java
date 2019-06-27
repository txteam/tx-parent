/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月23日
 * <修改描述:>
 */
package com.tx.component.role.configuration;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.role.RoleConstants;
import com.tx.component.role.dao.RoleItemDao;
import com.tx.component.role.dao.RoleRefItemDao;
import com.tx.component.role.dao.RoleTypeItemDao;
import com.tx.component.role.dao.impl.RoleItemDaoMybatisImpl;
import com.tx.component.role.dao.impl.RoleRefItemDaoMybatisImpl;
import com.tx.component.role.dao.impl.RoleTypeItemDaoMybatisImpl;
import com.tx.component.role.service.RoleItemService;
import com.tx.component.role.service.RoleRefItemService;
import com.tx.component.role.service.RoleTypeItemService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.starter.component.ComponentConstants;

/**
 * 角色容器自动配置项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class RoleContextPersisterConfiguration {
    
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
    public static class MybatisRolePersisterConfiguration
            implements InitializingBean {
        
        /** mybatis属性 */
        @Resource(name = "tx.component.myBatisDaoSupport")
        private MyBatisDaoSupport myBatisDaoSupport;
        
        /** 事务管理器 */
        @SuppressWarnings("unused")
        private TransactionTemplate transactionTemplate;
        
        /** <默认构造函数> */
        public MybatisRolePersisterConfiguration(
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
         * 角色类型项持久层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return RoleTypeItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(RoleConstants.BEAN_NAME_ROLE_TYPE_ITEM_DAO)
        public RoleTypeItemDao roleTypeItemDao() {
            RoleTypeItemDaoMybatisImpl dao = new RoleTypeItemDaoMybatisImpl();
            dao.setMyBatisDaoSupport(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 角色项持久层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return RoleItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(RoleConstants.BEAN_NAME_ROLE_ITEM_DAO)
        public RoleItemDao roleItemDao() {
            RoleItemDaoMybatisImpl dao = new RoleItemDaoMybatisImpl();
            dao.setMyBatisDaoSupport(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 角色引用项持久层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return RoleRefItemDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(RoleConstants.BEAN_NAME_ROLE_REF_ITEM_DAO)
        public RoleRefItemDao roleRefItemDao() {
            RoleRefItemDaoMybatisImpl dao = new RoleRefItemDaoMybatisImpl();
            dao.setMyBatisDaoSupport(this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 角色类型项业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return RoleTypeItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(RoleConstants.BEAN_NAME_ROLE_TYPE_ITEM_SERVICE)
        public RoleTypeItemService roleTypeItemService() {
            RoleTypeItemService service = new RoleTypeItemService(
                    roleTypeItemDao());
            return service;
        }
        
        /**
         * 角色项业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return RoleItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(RoleConstants.BEAN_NAME_ROLE_ITEM_SERVICE)
        public RoleItemService roleItemService() {
            RoleItemService service = new RoleItemService(roleItemDao());
            return service;
        }
        
        /**
         * 角色引用项业务层<br/>
         * <功能详细描述>
         * @return [参数说明]
         * 
         * @return RoleRefItemService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean(RoleConstants.BEAN_NAME_ROLE_REF_ITEM_SERVICE)
        public RoleRefItemService roleRefItemService() {
            RoleRefItemService service = new RoleRefItemService(
                    roleRefItemDao());
            return service;
        }
    }
}
