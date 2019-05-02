/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 实体持久层注册器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapperEntityDaoFactory<T>
        implements FactoryBean<MapperEntityDao<T>>, InitializingBean {
    
    private Logger logger = LoggerFactory.getLogger(MapperEntityDaoFactory.class);
    
    private Class<T> beanType;
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    protected SqlSessionFactory sqlSessionFactory;
    
    protected Configuration configuration;
    
    private EntityMapperBuilderAssistant assistant;
    
    private MapperEntityDao<T> entityDao;
    
    /** <默认构造函数> */
    public MapperEntityDaoFactory(Class<T> beanType,
            MyBatisDaoSupport myBatisDaoSupport) {
        super();
        this.beanType = beanType;
        this.myBatisDaoSupport = myBatisDaoSupport;
        
        this.sqlSessionFactory = this.myBatisDaoSupport.getSqlSessionFactory();
        this.configuration = this.myBatisDaoSupport.getSqlSessionFactory()
                .getConfiguration();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        logger.info("始构建实体自动持久层，开始.beanType:{}", this.beanType.getName());
        
        //构建SqlMap
        this.assistant = new EntityMapperBuilderAssistant(this.configuration,
                beanType);
        this.assistant.registe();
        logger.info("构建实体自动持久层：sqlmap:{}",
                this.assistant.getCurrentNamespace());
        
        //构建Dao
        this.entityDao = new DefaultMapperEntityDaoImpl<>(this.beanType,
                this.myBatisDaoSupport, this.assistant);
        
        logger.info("构建实体自动持久层：完成.beanType:{}", this.beanType.getName());
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return MapperEntityDao.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public MapperEntityDao<T> getObject() throws Exception {
        return this.entityDao;
    }
}
