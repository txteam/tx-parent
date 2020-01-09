/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.core.mybatis.mapper;

import java.io.Serializable;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.mybatis.dao.impl.MybatisBaseDaoImpl;
import com.tx.core.mybatis.support.MyBatisDaoSupport;

/**
 * 默认的实体自动持久层<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultMapperEntityDaoImpl<T, ID extends Serializable>
        extends MybatisBaseDaoImpl<T, ID> implements MapperEntityDao<T, ID> {
    
    /** bean类型 */
    private Class<T> beanType;
    
    /** mybatis句柄 */
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /** <默认构造函数> */
    public DefaultMapperEntityDaoImpl(Class<T> beanType,
            Class<ID> pkPropertyType, MyBatisDaoSupport myBatisDaoSupport) {
        super(beanType, pkPropertyType);
        AssertUtils.notNull(myBatisDaoSupport, "myBatisDaoSupport is null.");
        
        this.beanType = beanType;
        this.myBatisDaoSupport = myBatisDaoSupport;
        super.afterPropertiesSet();
    }
    
    /**
     * @return
     */
    @Override
    public Class<T> getEntityType() {
        return this.beanType;
    }

    /**
     * @return
     */
    @Override
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return this.myBatisDaoSupport;
    }
}
