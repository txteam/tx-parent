/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.mybatis.mapper;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.util.JPAParseUtils;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * 实体持久层注册器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("rawtypes")
public class MapperEntityDaoFactory
        implements FactoryBean<MapperEntityDao>, InitializingBean {
    
    protected Logger logger = LoggerFactory
            .getLogger(MapperEntityDaoFactory.class);
    
    private Class<?> beanType;
    
    private MyBatisDaoSupport myBatisDaoSupport;
    
    protected SqlSessionFactory sqlSessionFactory;
    
    protected Configuration configuration;
    
    private DefaultMapperEntityDaoImpl entityDao;
    
    /** <默认构造函数> */
    public MapperEntityDaoFactory(Class<?> beanType,
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
    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() {
        //logger.info("始构建实体自动持久层，开始.beanType:{}", this.beanType.getName());
        //构建Dao
        JPAColumnInfo pk = JPAParseUtils.parsePKTableColumn(beanType);
        this.entityDao = new DefaultMapperEntityDaoImpl(this.beanType,
                pk.getPropertyType(), this.myBatisDaoSupport);
        //logger.info(" --- 构建实体自动持久层.namespace:{}",this.entityDao.getAssistant().getNamespace());
        //logger.info("构建实体自动持久层：完成.beanType:{}", this.beanType.getName());
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
     */
    @Override
    public Class<?> getObjectType() {
        return MapperEntityDao.class;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public MapperEntityDao getObject() throws Exception {
        return this.entityDao;
    }
}
