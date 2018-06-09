/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.core.mybatis.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体持久层注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EntityDaoRegistry {
    
    private final Map<Class<?>, EntityDao<?>> knownMappers = new HashMap<Class<?>, EntityDao<?>>();
}
