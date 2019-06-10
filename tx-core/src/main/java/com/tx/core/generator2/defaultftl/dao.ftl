/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${dao.basePackage}.dao;

import com.tx.core.mybatis.dao.MybatisBaseDao;
import ${dao.entityTypeName};

/**
 * ${dao.entityTypeSimpleName}持久层
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ${dao.entityTypeSimpleName}Dao extends MybatisBaseDao<${dao.entityTypeSimpleName}, ${dao.pkColumn.propertyType.getSimpleName()}>{
}
