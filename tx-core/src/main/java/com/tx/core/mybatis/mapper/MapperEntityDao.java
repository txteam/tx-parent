/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月3日
 * <修改描述:>
 */
package com.tx.core.mybatis.mapper;

import java.io.Serializable;

import com.tx.core.mybatis.dao.MybatisBaseDao;

/**
 * 实体持久层实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MapperEntityDao<T, ID extends Serializable> extends MybatisBaseDao<T, ID>{
    
    /**
     * 获取泛型类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Type [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Class<T> getEntityType();
}
