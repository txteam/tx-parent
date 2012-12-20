/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${dao.basePackage}.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import ${dao.basePackage}.${dao.simpleEntityTypeName}Dao;
import ${dao.entityTypeName};
import com.tx.core.mybatis.model.Order;
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * ${dao.simpleEntityTypeName}持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("${dao.lowerCaseEntityTypeName}Dao")
public class ${dao.simpleEntityTypeName}DaoImpl implements ${dao.simpleEntityTypeName}Dao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insert${dao.simpleEntityTypeName}(${dao.simpleEntityTypeName} condition) {
        this.myBatisDaoSupport.insertUseUUID("${dao.lowerCaseEntityTypeName}.insert${dao.simpleEntityTypeName}", condition, "id");
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete${dao.simpleEntityTypeName}(${dao.simpleEntityTypeName} condition) {
        return this.myBatisDaoSupport.delete("${dao.lowerCaseEntityTypeName}.delete${dao.simpleEntityTypeName}", condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public ${dao.simpleEntityTypeName} find${dao.simpleEntityTypeName}(${dao.simpleEntityTypeName} condition) {
        return this.myBatisDaoSupport.<${dao.simpleEntityTypeName}> find("${dao.lowerCaseEntityTypeName}.find${dao.simpleEntityTypeName}", condition);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<${dao.simpleEntityTypeName}> query${dao.simpleEntityTypeName}List(Map<String, Object> params) {
        return this.myBatisDaoSupport.<${dao.simpleEntityTypeName}> queryList("${dao.lowerCaseEntityTypeName}.query${dao.simpleEntityTypeName}",
                params);
    }
    
    /**
     * @param params
     * @param orderList
     * @return
     */
    @Override
    public List<${dao.simpleEntityTypeName}> query${dao.simpleEntityTypeName}List(Map<String, Object> params,
            List<Order> orderList) {
        return this.myBatisDaoSupport.<${dao.simpleEntityTypeName}> queryList("${dao.lowerCaseEntityTypeName}.query${dao.simpleEntityTypeName}",
                params,
                orderList);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count${dao.simpleEntityTypeName}(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("${dao.lowerCaseEntityTypeName}.query${dao.simpleEntityTypeName}Count",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<${dao.simpleEntityTypeName}> query${dao.simpleEntityTypeName}PagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<${dao.simpleEntityTypeName}> queryPagedList("${dao.lowerCaseEntityTypeName}.query${dao.simpleEntityTypeName}",
                params,
                pageIndex,
                pageSize);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param orderList
     * @return
     */
    @Override
    public PagedList<${dao.simpleEntityTypeName}> query${dao.simpleEntityTypeName}PagedList(Map<String, Object> params,
            int pageIndex, int pageSize, List<Order> orderList) {
        return this.myBatisDaoSupport.<${dao.simpleEntityTypeName}> queryPagedList("${dao.lowerCaseEntityTypeName}.query${dao.simpleEntityTypeName}",
                params,
                pageIndex,
                pageSize,
                orderList);
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update${dao.simpleEntityTypeName}(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("${dao.lowerCaseEntityTypeName}.update${dao.simpleEntityTypeName}", updateRowMap);
    }
}
