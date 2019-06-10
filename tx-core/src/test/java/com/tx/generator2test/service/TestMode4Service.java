/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.generator2test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.generator2test.dao.TestMode4Dao;
import com.tx.generator2test.model.TestMode4;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Filter;
import com.tx.core.querier.model.Querier;
import com.tx.core.querier.model.QuerierBuilder;

/**
 * 测试对象的业务层[TestMode4Service]
 * <功能详细描述>
 * 
 * @author  
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component("testMode4Service")
public class TestMode4Service {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TestMode4Service.class);
    
    @Resource(name = "testMode4Dao")
    private TestMode4Dao testMode4Dao;
    
    /**
     * 新增测试对象实例<br/>
     * 将testMode4插入数据库中保存
     * 1、如果testMode4 为空时抛出参数为空异常
     * 2、如果testMode4 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param testMode4 [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(TestMode4 testMode4) {
        //验证参数是否合法
        AssertUtils.notNull(testMode4, "testMode4 is null.");
		AssertUtils.notEmpty(testMode4.getCode(), "testMode4.code is empty.");
		AssertUtils.notEmpty(testMode4.getName(), "testMode4.name is empty.");
           
        //FIXME:为添加的数据需要填入默认值的字段填入默认值
		testMode4.setLastUpdateDate(new Date());
		testMode4.setCreateDate(new Date());
        
        //调用数据持久层对实例进行持久化操作
        this.testMode4Dao.insert(testMode4);
    }
    
    /**
     * 根据id删除测试对象实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param id
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(Long id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestMode4 condition = new TestMode4();
        condition.setId(id);
        
        int resInt = this.testMode4Dao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据id查询测试对象实例
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TestMode4 [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode4 findById(Long id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestMode4 condition = new TestMode4();
        condition.setId(id);
        
        TestMode4 res = this.testMode4Dao.find(condition);
        return res;
    }
    
    /**
     * 根据code查询测试对象实例
     * 1、当code为empty时抛出异常
     *
     * @param code
     * @return TestMode4 [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode4 findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestMode4 condition = new TestMode4();
        condition.setCode(code);
        
        TestMode4 res = this.testMode4Dao.find(condition);
        return res;
    }
    
    /**
     * 查询测试对象实例列表
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode4> queryList(
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode4> resList = this.testMode4Dao.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询测试对象实例列表
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode4> queryList(
		Querier querier   
    	) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode4> resList = this.testMode4Dao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 分页查询测试对象实例列表
     * <功能详细描述>
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<TestMode4> queryPagedList(
		Map<String,Object> params,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestMode4> resPagedList = this.testMode4Dao.queryPagedList(params, pageIndex, pageSize);
        
        return resPagedList;
    }
    
	/**
     * 分页查询测试对象实例列表
     * <功能详细描述>
     * @param querier    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<TestMode4> queryPagedList(
		Querier querier,
    	int pageIndex,
        int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
 
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestMode4> resPagedList = this.testMode4Dao.queryPagedList(querier, pageIndex, pageSize);
        
        return resPagedList;
    }
    
    /**
     * 查询测试对象实例数量<br/>
     * <功能详细描述>
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(
		Map<String,Object> params   
    	) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode4Dao.count(params);
        
        return res;
    }
    
    /**
     * 查询测试对象实例数量<br/>
     * <功能详细描述>
     * @param querier      
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public int count(
		Querier querier   
    	) {
        //判断条件合法性
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode4Dao.count(querier);
        
        return res;
    }
    
    /**
     * 判断测试对象实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Map<String,String> key2valueMap, Long excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode4Dao.count(params);
        
        return res > 0;
    }
    
    /**
     * 判断测试对象实例是否已经存在<br/>
     * <功能详细描述>
     * @param key2valueMap
     * @param excludeId
     * @return
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public boolean exists(Querier querier, Long excludeId) {
        AssertUtils.notNull(querier, "querier is null.");
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testMode4Dao.count(querier,excludeId);
        
        return res > 0;
    }
    
    /**
     * 根据id更新测试对象实例<br/>
     * <功能详细描述>
     * @param testMode4
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(Long id,TestMode4 testMode4) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(testMode4, "testMode4 is null.");
        AssertUtils.notEmpty(id, "id is empty.");
		AssertUtils.notEmpty(testMode4.getName(), "testMode4.name is empty.");

        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        //FIXME:需要更新的字段
		updateRowMap.put("lastUpdateOperatorId", testMode4.getLastUpdateOperatorId());
		updateRowMap.put("name", testMode4.getName());
		updateRowMap.put("testInt", testMode4.getTestInt());
		updateRowMap.put("testLong", testMode4.getTestLong());
		updateRowMap.put("testBigDecimal", testMode4.getTestBigDecimal());
		updateRowMap.put("type", testMode4.getType());
		updateRowMap.put("expiryDate", testMode4.getExpiryDate());
		updateRowMap.put("modifyAble", testMode4.isModifyAble());
		updateRowMap.put("parentId", testMode4.getParentId());
		updateRowMap.put("remark", testMode4.getRemark());
		updateRowMap.put("nested1", testMode4.getNested1());
		updateRowMap.put("nested2", testMode4.getNested2());
		updateRowMap.put("success", testMode4.getSuccess());
		updateRowMap.put("effictiveDate", testMode4.getEffictiveDate());
		updateRowMap.put("attributes", testMode4.getAttributes());
		updateRowMap.put("description", testMode4.getDescription());
		updateRowMap.put("lastUpdateDate", new Date());

        boolean flag = this.testMode4Dao.update(id,updateRowMap); 
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }
    
    /**
     * 根据id更新测试对象实例<br/>
     * <功能详细描述>
     * @param testMode4
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(TestMode4 testMode4) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(testMode4, "testMode4 is null.");
        AssertUtils.notEmpty(testMode4.getId(), "testMode4.id is empty.");
		AssertUtils.notEmpty(testMode4.getName(), "testMode4.name is empty.");

        boolean flag = updateById(testMode4.getId(),testMode4); 
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return flag;
    }

    /**
     * 根据parentId查询测试对象子级实例列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode4> queryChildrenByParentId(Long parentId,
			Map<String,Object> params) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("parentId", parentId);

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode4> resList = this.testMode4Dao.queryList(params);
        
        return resList;
    }
    
    /**
     * 根据parentId查询测试对象子级实例列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode4> queryChildrenByParentId(Long parentId,
			Querier querier) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
		querier.getFilters().add(Filter.eq("parentId", parentId));

        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode4> resList = this.testMode4Dao.queryList(querier);
        
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param params
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode4> queryDescendantsByParentId(Long parentId,
            Map<String, Object> params) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        Set<Long> ids = new HashSet<>();
        Set<Long> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<TestMode4> resList = doNestedQueryList(ids, parentIds, params);
        return resList;
    }
    
    /**
     * 查询嵌套列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param params
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<TestMode4> doNestedQueryList(
    		Set<Long> ids,Set<Long> parentIds,Map<String, Object> params) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<TestMode4>();
        }
        
        //ids避免数据出错时导致无限循环
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.putAll(params);
        queryParams.put("parentIds", parentIds);
        List<TestMode4> resList = queryList( params);
        
        Set<Long> newParentIds = new HashSet<>();
        for (TestMode4 bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryList( ids, newParentIds, params));
        return resList;
    }
    
    /**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode4> queryDescendantsByParentId(Long parentId,
            Querier querier) {
        //判断条件合法性
        AssertUtils.notEmpty(parentId,"parentId is empty.");
        
        //生成查询条件
        querier = querier == null ? QuerierBuilder.newInstance().querier()
                : querier;
        Set<Long> ids = new HashSet<>();
        Set<Long> parentIds = new HashSet<>();
        parentIds.add(parentId);
        
        List<TestMode4> resList = doNestedQueryList(ids, parentIds, querier);
        return resList;
    }
    
    /**
     * 查询嵌套列表<br/>
     * <功能详细描述>
     * @param ids
     * @param parentIds
     * @param querier
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private List<TestMode4> doNestedQueryList(
    		Set<Long> ids,Set<Long> parentIds,Querier querier) {
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<TestMode4>();
        }
        
        //ids避免数据出错时导致无限循环
        Querier querierClone = (Querier)querier.clone();
        querierClone.getFilters().add(Filter.in("parentId", parentIds));
        List<TestMode4> resList = queryList(querierClone);
        
        Set<Long> newParentIds = new HashSet<>();
        for (TestMode4 bdTemp : resList) {
            if (!ids.contains(bdTemp.getId())) {
                newParentIds.add(bdTemp.getId());
            }
            ids.add(bdTemp.getId());
        }
        //嵌套查询下一层级
        resList.addAll(doNestedQueryList( ids, newParentIds, querier));
        return resList;
    }
}
