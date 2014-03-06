/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.dao.TestBasicData2Dao;
import com.tx.component.basicdata.model.TestBasicData2;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TestBasicData2的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testBasicData2Service")
public class TestBasicData2Service {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TestBasicData2Service.class);
    
    @Resource(name = "testBasicData2Dao")
    private TestBasicData2Dao testBasicData2Dao;
    
    /**
      * 将testBasicData2实例插入数据库中保存
      * 1、如果testBasicData2为空时抛出参数为空异常
      * 2、如果testBasicData2中部分必要参数为非法值时抛出参数不合法异常
     * <功能详细描述>
     * @param testBasicData2 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void insertTestBasicData2(TestBasicData2 testBasicData2) {
        //验证参数是否合法
        AssertUtils.notNull(testBasicData2, "testBasicData2 is null.");
        AssertUtils.notEmpty(testBasicData2.getCode(),
                "testBasicData2.code is empty.");
        
        //TODO:为添加的数据需要填入默认值的字段填入默认值
        
        //调用数据持久层对实体进行持久化操作
        this.testBasicData2Dao.insertTestBasicData2(testBasicData2);
    }
    
    /**
     * 根据code删除testBasicData2实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数
     * @param code
     * @return 返回删除的数据条数，<br/>
     * 有些业务场景，如果已经被别人删除同样也可以认为是成功的
     * 这里讲通用生成的业务层代码定义为返回影响的条数
     * @return int [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public boolean deleteByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestBasicData2 condition = new TestBasicData2();
        condition.setCode(code);
        int resInt = this.testBasicData2Dao.deleteTestBasicData2(condition);
        return resInt > 0;
    }
    
    /**
      * 根据Code查询TestBasicData2实体
      * 1、当code为empty时抛出异常
      * <功能详细描述>
      * @param code
      * @return [参数说明]
      * 
      * @return TestBasicData2 [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public TestBasicData2 findTestBasicData2ByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestBasicData2 condition = new TestBasicData2();
        condition.setCode(code);
        
        TestBasicData2 res = this.testBasicData2Dao.findTestBasicData2(condition);
        return res;
    }
    
    /**
      * 查询TestBasicData2实体列表
      *     不包含无效的实体
      * <功能详细描述>
      * @param code
      * @param name
      *       
      * @return [参数说明]
      * 
      * @return List<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
      */
    public List<TestBasicData2> queryTestBasicData2List(String code, String name) {
        //判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("name", name);
        params.put("valid", true);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestBasicData2> resList = this.testBasicData2Dao.queryTestBasicData2List(params);
        
        return resList;
    }
    
    /**
     * 查询TestBasicData2实体列表,
     *		如果实体集合中不包含appointCode对应的实体将根据该id查询到对应的实体然后加入到集合中（appointCode被禁用的情况）
     *     appointCode对应的实体如果不存在，系统将抛出异常（appointCode被删除的情况）
     * <功能详细描述>
     * @param code
     * @param name
     *       
     * @return [参数说明]
     * 
     * @return List<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestBasicData2> queryTestBasicData2ListIncludeAppointCode(
            String code, String name, String appointCode) {
        //判断条件合法性
        AssertUtils.notEmpty(appointCode, "appointCode is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("name", name);
        params.put("valid", true);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestBasicData2> resList = this.testBasicData2Dao.queryTestBasicData2List(params);
        
        Set<String> codeSet = new HashSet<String>();
        for (TestBasicData2 temp : resList) {
            codeSet.add(temp.getCode());
        }
        if (!codeSet.contains(appointCode)) {
            TestBasicData2 findRes = findTestBasicData2ByCode(appointCode);
            resList.add(0, findRes);
        }
        
        return resList;
    }
    
    /**
      * 查询包含已经停用的TestBasicData2实体列表
      * <功能详细描述>
      * @param code
      * @param name
      *       
      * @return [参数说明]
      * 
      * @return List<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<TestBasicData2> queryTestBasicData2ListIncludeInvalid(
            String code, String name) {
        //判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("name", name);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestBasicData2> resList = this.testBasicData2Dao.queryTestBasicData2List(params);
        
        return resList;
    }
    
    /**
     * 分页查询TestBasicData2实体列表
      *     不包含无效的实体
      * @param code
      * @param name
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<TestBasicData2> queryTestBasicData2PagedList(String code,
            String name, int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("name", name);
        params.put("valid", true);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestBasicData2> resPagedList = this.testBasicData2Dao.queryTestBasicData2PagedList(params,
                pageIndex,
                pageSize);
        
        return resPagedList;
    }
    
    /**
     * 分页查询TestBasicData2实体列表
     *     包含无效的的实体
      * @param code
      * @param name
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<TestBasicData2> queryTestBasicData2PagedListIncludeInvalid(
            String code, String name, int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("name", name);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestBasicData2> resPagedList = this.testBasicData2Dao.queryTestBasicData2PagedList(params,
                pageIndex,
                pageSize);
        
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
    public boolean isExist(Map<String, String> key2valueMap, String excludeCode) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeCode", excludeCode);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testBasicData2Dao.countTestBasicData2(params);
        
        return res > 0;
    }
    
    /**
      * 根据code更新对象
      * <功能详细描述>
      * @param testBasicData2
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateByCode(TestBasicData2 testBasicData2) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(testBasicData2, "testBasicData2 is null.");
        AssertUtils.notEmpty(testBasicData2.getCode(),
                "testBasicData2.code is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("code", testBasicData2.getCode());
        
        //TODO:需要更新的字段
        updateRowMap.put("valid", testBasicData2.isValid());
        updateRowMap.put("remark", testBasicData2.getRemark());
        updateRowMap.put("name", testBasicData2.getName());
        updateRowMap.put("type", testBasicData2.getType());
        updateRowMap.put("createDate", testBasicData2.getCreateDate());
        updateRowMap.put("lastUpdateDate", testBasicData2.getLastUpdateDate());
        int updateRowCount = this.testBasicData2Dao.updateTestBasicData2(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
     * 根据code禁用TestBasicData2<br/>
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public boolean disableByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("valid", false);
        
        this.testBasicData2Dao.updateTestBasicData2(params);
        
        return true;
    }
    
    /**
      * 根据code启用TestBasicData2<br/>
      * <功能详细描述>
      * @param postId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("valid", true);
        
        this.testBasicData2Dao.updateTestBasicData2(params);
        
        return true;
    }
}
