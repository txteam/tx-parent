/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.component.plugin.starter;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.plugin.dao.PluginInstanceDao;
import com.tx.component.plugin.dao.impl.PluginInstanceDaoImpl;
import com.tx.component.plugin.service.PluginInstanceService;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.starter.component.ComponentConstants;

/**
 * 基础数据持久层配置逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class PluginPersisterConfiguration {
    
    /** <默认构造函数> */
    public PluginPersisterConfiguration() {
        super();
    }
    
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
    public static class MybatisConfigContextPersisterConfiguration
            implements InitializingBean {
        
        /** mybatis属性 */
        @Resource(name = "tx.component.myBatisDaoSupport")
        private MyBatisDaoSupport myBatisDaoSupport;
        
        /** 事务管理器 */
        @SuppressWarnings("unused")
        private TransactionTemplate transactionTemplate;
        
        /** <默认构造函数> */
        public MybatisConfigContextPersisterConfiguration(
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
         * 数据字典实例<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("plugin.pluginInstanceDao")
        @ConditionalOnMissingBean(name = "plugin.pluginInstanceDao")
        public PluginInstanceDao pluginInstanceDao() throws Exception {
            PluginInstanceDao dao = new PluginInstanceDaoImpl(
                    this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 数据字典实例<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictDao [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("plugin.pluginInstanceService")
        @ConditionalOnMissingBean(name = "plugin.pluginInstanceService")
        public PluginInstanceService pluginInstanceService(
                PluginInstanceDao pluginInstanceDao) throws Exception {
            PluginInstanceService service = new PluginInstanceService(
                    pluginInstanceDao);
            return service;
        }
    }
}
