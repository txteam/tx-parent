/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.generator2test.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.generator2test.dao.TestMode2Dao;
import com.tx.generator2test.model.TestMode2;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TestMode2的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testMode2Service")
public class TestMode2Service {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TestMode2Service.class);
    
    @Resource(name = "testMode2Dao")
    private TestMode2Dao testMode2Dao;
    
    /**
     * 将testMode2插入数据库中保存
     * 1、如果testMode2 为空时抛出参数为空异常
     * 2、如果testMode2 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param testMode2 [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(TestMode2 testMode2) {
        //验证参数是否合法
        AssertUtils.notNull(testMode2, "testMode2 is null.");
           
        //FIXME:为添加的数据需要填入默认值的字段填入默认值
		testMode2.setLastUpdateDate(new Date());
		testMode2.setCreateDate(new Date());
        
        //调用数据持久层对实体进行持久化操作
        this.testMode2Dao.insert(testMode2);
    }
    
    /**
     * 根据code删除TestMode2实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param code
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestMode2 condition = new TestMode2();
        condition.setCode(code);
        
        int resInt = this.testMode2Dao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据code查询TestMode2实体
     * 1、当code为empty时抛出异常
     *
     * @param code
     * @return TestMode2 [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode2 findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestMode2 condition = new TestMode2();
        condition.setCode(code);
        
        TestMode2 res = this.testMode2Dao.find(condition);
        return res;
    }
    
    
    /**
     * 查询TestMode2实体列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode2> queryList(
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode2> resList = this.testMode2Dao.queryList(params);
        
        return resList;
    }
    
	/**
     * 分页查询TestMode2实体列表
     * <功能详细描述>
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<TestMode2> queryPagedList(
		Map<String,Object> params,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestMode2> resPagedList = this.testMode2Dao.queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 判断是否已经存在<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Map<String,String> key2valueMap, String excludeCode) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeCode", excludeCode);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode2Dao.count(params);
        
        return res > 0;
    }
    
    /**
     * 根据code更新对象
     * <功能详细描述>
     * @param testMode2
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateByCode(TestMode2 testMode2) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(testMode2, "testMode2 is null.");
        AssertUtils.notEmpty(testMode2.getCode(), "testMode2.code is empty.");

        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("code", testMode2.getCode());
        
        //FIXME:需要更新的字段
		updateRowMap.put("lastUpdateOperatorId", testMode2.getLastUpdateOperatorId());
		updateRowMap.put("name", testMode2.getName());
		updateRowMap.put("testInt", testMode2.getTestInt());
		updateRowMap.put("testLong", testMode2.getTestLong());
		updateRowMap.put("type", testMode2.getType());
		updateRowMap.put("createOperatorId", testMode2.getCreateOperatorId());
		updateRowMap.put("remark", testMode2.getRemark());
		updateRowMap.put("nested1", testMode2.getNested1());
		updateRowMap.put("nested2", testMode2.getNested2());
		updateRowMap.put("attributes", testMode2.getAttributes());
		updateRowMap.put("description", testMode2.getDescription());
		updateRowMap.put("lastUpdateDate", new Date());

        int updateRowCount = this.testMode2Dao.update(updateRowMap); 
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
