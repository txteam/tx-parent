/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${dao.basePackage}.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tx.core.mybatis.dao.impl.MybatisBaseDaoImpl;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import ${dao.basePackage}.dao.${dao.entityTypeSimpleName}Dao;
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
@Component("${dao.entityTypeSimpleName?uncap_first}Dao")
public class ${dao.entityTypeSimpleName}DaoImpl 
		extends MybatisBaseDaoImpl<${dao.entityTypeSimpleName}, ${dao.pkColumn.propertyType.getSimpleName()}>
		implements ${dao.entityTypeSimpleName}Dao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @return 返回 myBatisDaoSupport
     */
    public MyBatisDaoSupport getMyBatisDaoSupport() {
        return myBatisDaoSupport;
    }

    /**
     * @param 对myBatisDaoSupport进行赋值
     */
    public void setMyBatisDaoSupport(MyBatisDaoSupport myBatisDaoSupport) {
        this.myBatisDaoSupport = myBatisDaoSupport;
    }
}
