/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${dao.basePackage}.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import ${dao.basePackage}.dao.${dao.entityTypeSimpleName}Dao;
import ${dao.entityTypeName};
import com.tx.core.mybatis.support.MyBatisDaoSupport;
import com.tx.core.paged.model.PagedList;

/**
 * ${dao.entityTypeSimpleName}持久层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, 2012-12-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("${dao.entityTypeSimpleName?uncap_first}Dao")
public class ${dao.entityTypeSimpleName}DaoImpl implements ${dao.entityTypeSimpleName}Dao {
    
    @Resource(name = "myBatisDaoSupport")
    private MyBatisDaoSupport myBatisDaoSupport;
    
    /**
     * @param condition
     */
    @Override
    public void insert(${dao.entityTypeSimpleName} ${dao.entityTypeSimpleName?uncap_first}) {
        this.myBatisDaoSupport.insertUseUUID("${dao.entityTypeSimpleName?uncap_first}.insert", ${dao.entityTypeSimpleName?uncap_first}, "id");
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchInsert(List<${dao.entityTypeSimpleName}> ${dao.entityTypeSimpleName?uncap_first}){
        this.myBatisDaoSupport.batchInsertUseUUID("${dao.entityTypeSimpleName?uncap_first}.insert", ${dao.entityTypeSimpleName?uncap_first}, "id",true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int delete(${dao.entityTypeSimpleName} ${dao.entityTypeSimpleName?uncap_first}) {
        return this.myBatisDaoSupport.delete("${dao.entityTypeSimpleName?uncap_first}.delete", ${dao.entityTypeSimpleName?uncap_first});
    }
    
    /**
     * @param updateRowMap
     * @return
     */
    @Override
    public int update(Map<String, Object> updateRowMap) {
        return this.myBatisDaoSupport.update("${dao.entityTypeSimpleName?uncap_first}.update", updateRowMap);
    }
    
    /**
     * @param condition
     */
    @Override
    public void batchUpdate(List<Map<String,Object>> updateRowMapList){
        this.myBatisDaoSupport.batchUpdate("${dao.entityTypeSimpleName?uncap_first}.update", updateRowMapList,true);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public ${dao.entityTypeSimpleName} find(${dao.entityTypeSimpleName} ${dao.entityTypeSimpleName?uncap_first}) {
        return this.myBatisDaoSupport.<${dao.entityTypeSimpleName}> find("${dao.entityTypeSimpleName?uncap_first}.find", ${dao.entityTypeSimpleName?uncap_first});
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public List<${dao.entityTypeSimpleName}> queryList(Map<String, Object> params) {
        return this.myBatisDaoSupport.<${dao.entityTypeSimpleName}> queryList("${dao.entityTypeSimpleName?uncap_first}.query",
                params);
    }
    
    /**
     * @param params
     * @return
     */
    @Override
    public int count(Map<String, Object> params) {
        return this.myBatisDaoSupport.<Integer> find("${dao.entityTypeSimpleName?uncap_first}.queryCount",
                params);
    }
    
    /**
     * @param params
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PagedList<${dao.entityTypeSimpleName}> queryPagedList(Map<String, Object> params,
            int pageIndex, int pageSize) {
        return this.myBatisDaoSupport.<${dao.entityTypeSimpleName}> queryPagedList("${dao.entityTypeSimpleName?uncap_first}.query",
                params,
                pageIndex,
                pageSize);
    }
}
