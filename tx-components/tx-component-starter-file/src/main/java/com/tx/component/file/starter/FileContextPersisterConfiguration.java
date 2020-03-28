/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月13日
 * <修改描述:>
 */
package com.tx.component.file.starter;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.tx.component.file.dao.FileDefinitionDao;
import com.tx.component.file.dao.impl.FileDefinitionDaoImpl;
import com.tx.component.file.service.FileDefinitionService;
import com.tx.component.file.service.impl.MybatisFileDefinitionServiceImpl;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.starter.component.ComponentConstants;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class FileContextPersisterConfiguration {
    
    /** <默认构造函数> */
    public FileContextPersisterConfiguration() {
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
    @ConditionalOnProperty(prefix = ComponentConstants.PERSISTER_PROPERTIES_PREFIX, value = "type", havingValue = "mybatis")
    @ConditionalOnBean(name = { "tx.component.myBatisDaoSupport" })
    public static class MybatisFileContextPersisterConfiguration
            implements InitializingBean {
        
        /** mybatis属性 */
        @Resource(name = "tx.component.myBatisDaoSupport")
        private MyBatisDaoSupport myBatisDaoSupport;
        
        /** 事务管理器 */
        @SuppressWarnings("unused")
        private TransactionTemplate transactionTemplate;
        
        /** application.name */
        @Value(value = "${spring.application.name}")
        private String applicationName;
        
        /** 属性文件 */
        private FileContextProperties properties;
        
        /** 容器所属模块：当该值为空时，使用spring.application.name的内容 */
        private String module;
        
        /** <默认构造函数> */
        public MybatisFileContextPersisterConfiguration(
                PlatformTransactionManager transactionManager,
                FileContextProperties properties) {
            this.transactionTemplate = new TransactionTemplate(
                    transactionManager);
            this.properties = properties;
        }
        
        /**
         * @throws Exception
         */
        @Override
        public void afterPropertiesSet() throws Exception {
            AssertUtils.notNull(this.myBatisDaoSupport,
                    "myBatisDaoSupport is null.");
            
            //初始化包名
            if (!StringUtils.isBlank(this.applicationName)) {
                this.module = this.applicationName;
            }
            if (!StringUtils.isEmpty(this.properties.getModule())) {
                this.module = this.properties.getModule();
            }
            AssertUtils.notEmpty(this.module, "module is empty.");
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
        @Bean("file.fileDefinitionDao")
        public FileDefinitionDao fileDefinitionDao() throws Exception {
            FileDefinitionDao dao = new FileDefinitionDaoImpl(
                    this.myBatisDaoSupport);
            return dao;
        }
        
        /**
         * 数据字典<br/>
         * <功能详细描述>
         * @return
         * @throws Exception [参数说明]
         * 
         * @return DataDictService [返回类型说明]
         * @exception throws [异常类型] [异常说明]
         * @see [类、类#方法、类#成员]
         */
        @Bean("file.fileDefinitionService")
        public FileDefinitionService fileDefinitionService(
                FileDefinitionDao fileDefinitionDao) throws Exception {
            FileDefinitionService service = new MybatisFileDefinitionServiceImpl(
                    fileDefinitionDao, this.module);
            return service;
        }
    }
}
