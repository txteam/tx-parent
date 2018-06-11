/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.starter.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@ConditionalOnClass({ MybatisAutoConfiguration.class, SqlSessionFactory.class,
        SqlSessionFactoryBean.class })
@ConditionalOnBean({ SqlSessionFactory.class, SqlSessionTemplate.class,
        MybatisAutoConfiguration.class })
@AutoConfigureAfter({ CoreUtilAutoConfiguration.class,
        MybatisAutoConfiguration.class })
public class MybatisSupportAutoConfiguration {
    
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
    @Bean
    public MyBatisDaoSupport myBatisDaoSupport(
            SqlSessionTemplate sqlSessionTemplate) {
        MyBatisDaoSupport support = new MyBatisDaoSupport(sqlSessionTemplate);
        return support;
    }
}
