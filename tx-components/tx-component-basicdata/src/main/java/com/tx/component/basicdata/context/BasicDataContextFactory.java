/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月4日
 * <修改描述:>
 */
package com.tx.component.basicdata.context;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.tx.component.basicdata.dao.DataDictDao;
import com.tx.component.basicdata.dao.impl.DataDictDaoImpl;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.dbscript.model.DataSourceTypeEnum;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.mybatis.support.MyBatisDaoSupportHelper;

/**
 * 基础数据容器工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration
public class BasicDataContextFactory extends BasicDataContext implements
        FactoryBean<BasicDataContext> {
    
    @Bean(name = "basicdata.myBatisDaoSupport")
    public MyBatisDaoSupport basicdata_myBatisDaoSupport() throws Exception {
        MyBatisDaoSupport support = MyBatisDaoSupportHelper.buildMyBatisDaoSupport(this.mybatisConfigLocation,
                this.mybatisMapperLocations,
                DataSourceTypeEnum.MYSQL,
                this.dataSource);
        return support;
    }
    
    @Bean(name = "dataDictDao")
    public DataDictDao dataDictDao() {
        DataDictDao dao = new DataDictDaoImpl();
        return dao;
    }
    
    @Bean(name = "dataDictService")
    public DataDictService dataDictService() {
        DataDictService dao = new DataDictService();
        return dao;
    }
    
    /**
      * 基础数据业务层工厂
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataServiceFactory [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Bean(name = "basicDataServiceFactory")
    public BasicDataServiceFactory basicDataServiceFactory() {
        BasicDataServiceFactory serviceFactory = new BasicDataServiceFactory();
        return serviceFactory;
    }
    
    /**
     * @return
     * @throws Exception
     */
    @Override
    public BasicDataContext getObject() throws Exception {
        return getContext();
    }
    
    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return BasicDataContext.class;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
