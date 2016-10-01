/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.template.dao.TemplateTableDao;
import com.tx.component.template.model.TemplateTable;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TemplateTable的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("templateTableService")
public class TemplateTableService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TemplateTableService.class);
    
    @SuppressWarnings("unused")
    //@Resource(name = "serviceLogger")
    private Logger serviceLogger;
    
    @Resource(name = "templateTableDao")
    private TemplateTableDao templateTableDao;
    
    /**
      * 将templateTable实例插入数据库中保存
      * 1、如果templateTable为空时抛出参数为空异常
      * 2、如果templateTable中部分必要参数为非法值时抛出参数不合法异常
     * <功能详细描述>
     * @param district [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void insertTemplateTable(TemplateTable templateTable) {
        //TODO:验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(templateTable, "templateTable is null.");
        AssertUtils.notEmpty(templateTable.getId(), "templateTable.id is empty.");
        
        this.templateTableDao.insertTemplateTable(templateTable);
    }
      
     /**
      * 根据id删除templateTable实例
      * 1、如果入参数为空，则抛出异常
      * 2、执行删除后，将返回数据库中被影响的条数
      * @param id
      * @return 返回删除的数据条数，<br/>
      * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
      * 这里讲通用生成的业务层代码定义为返回影响的条数
      * @return int [返回类型说明]
      * @exception throws 
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public int deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TemplateTable condition = new TemplateTable();
        condition.setId(id);
        return this.templateTableDao.deleteTemplateTable(condition);
    }
    
    /**
      * 根据Id查询TemplateTable实体
      * 1、当id为empty时抛出异常
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return TemplateTable [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public TemplateTable findTemplateTableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TemplateTable condition = new TemplateTable();
        condition.setId(id);
        return this.templateTableDao.findTemplateTable(condition);
    }
    
    /**
      * 根据TemplateTable实体列表
      * TODO:补充说明
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<TemplateTable> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<TemplateTable> queryTemplateTableList(/*TODO:自己定义条件*/) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TemplateTable> resList = this.templateTableDao.queryTemplateTableList(params);
        
        return resList;
    }
    
    /**
     * 分页查询TemplateTable实体列表
     * TODO:补充说明
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TemplateTable> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<TemplateTable> queryTemplateTablePagedList(/*TODO:自己定义条件*/int pageIndex,
            int pageSize) {
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TemplateTable> resPagedList = this.templateTableDao.queryTemplateTablePagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
      * 查询templateTable列表总条数
      * TODO:补充说明
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public int countTemplateTable(/*TODO:自己定义条件*/){
        //TODO:判断条件合法性
        
        //TODO:生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        
        //TODO:根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.templateTableDao.countTemplateTable(params);
        
        return res;
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param templateTable
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(TemplateTable templateTable) {
        //TODO:验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(templateTable, "templateTable is null.");
        AssertUtils.notEmpty(templateTable.getId(), "templateTable.id is empty.");
        
        
        //TODO:生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", templateTable.getId());
        
        //TODO:需要更新的字段
		updateRowMap.put("createOperatorId", templateTable.getCreateOperatorId());	
		//updateRowMap.put("tableStatus", templateTable.getTableStatus());	
		updateRowMap.put("remark", templateTable.getRemark());	
		updateRowMap.put("tableName", templateTable.getTableName());	
		updateRowMap.put("name", templateTable.getName());	
		//type:java.lang.String
		updateRowMap.put("tableType", templateTable.getTableType());
		updateRowMap.put("systemId", templateTable.getSystemId());	
		updateRowMap.put("createDate", templateTable.getCreateDate());	
		updateRowMap.put("lastUpdateDate", templateTable.getLastUpdateDate());	
        
        int updateRowCount = this.templateTableDao.updateTemplateTable(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
