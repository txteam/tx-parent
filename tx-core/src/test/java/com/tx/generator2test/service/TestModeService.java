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

import com.tx.generator2test.dao.TestModeDao;
import com.tx.generator2test.model.TestMode;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TestMode的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testModeService")
public class TestModeService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TestModeService.class);
    
    @Resource(name = "testModeDao")
    private TestModeDao testModeDao;
    
    /**
     * 将testMode插入数据库中保存
     * 1、如果testMode 为空时抛出参数为空异常
     * 2、如果testMode 中部分必要参数为非法值时抛出参数不合法异常
     * 
     * @param testMode [参数说明]
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void insert(TestMode testMode) {
        //验证参数是否合法
        AssertUtils.notNull(testMode, "testMode is null.");
        AssertUtils.notEmpty(testMode.getCode(), "testMode.code is empty.");
        AssertUtils.notEmpty(testMode.getName(), "testMode.name is empty.");
        
        //FIXME:为添加的数据需要填入默认值的字段填入默认值
        testMode.setLastUpdateDate(new Date());
        testMode.setValid(true);
        testMode.setCreateDate(new Date());
        
        //调用数据持久层对实体进行持久化操作
        this.testModeDao.insert(testMode);
    }
    
    /**
     * 根据id删除TestMode实例
     * 1、如果入参数为空，则抛出异常
     * 2、执行删除后，将返回数据库中被影响的条数 > 0，则返回true
     *
     * @param id
     * @return boolean 删除的条数>0则为true [返回类型说明]
     * @exception throws 
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestMode condition = new TestMode();
        condition.setId(id);
        
        int resInt = this.testModeDao.delete(condition);
        boolean flag = resInt > 0;
        return flag;
    }
    
    /**
     * 根据id查询TestMode实体
     * 1、当id为empty时抛出异常
     *
     * @param id
     * @return TestMode [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode findById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestMode condition = new TestMode();
        condition.setId(id);
        
        TestMode res = this.testModeDao.find(condition);
        return res;
    }
    
    /**
     * 根据code查询TestMode实体
     * 1、当code为empty时抛出异常
     *
     * @param code
     * @return TestMode [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
     */
    public TestMode findByCode(String code) {
        AssertUtils.notEmpty(code, "code is empty.");
        
        TestMode condition = new TestMode();
        condition.setCode(code);
        
        TestMode res = this.testModeDao.find(condition);
        return res;
    }
    
    /**
     * 查询TestMode实体列表
     * <功能详细描述>
     * @param valid
     * @param params      
     * @return [参数说明]
     * 
     * @return List<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public List<TestMode> queryList(Boolean valid, Map<String, Object> params) {
        //判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestMode> resList = this.testModeDao.queryList(params);
        
        return resList;
    }
    
    /**
     * 分页查询TestMode实体列表
     * <功能详细描述>
     * @param valid
     * @param params    
     * @param pageIndex 当前页index从1开始计算
     * @param pageSize 每页显示行数
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public PagedList<TestMode> queryPagedList(Boolean valid,
            Map<String, Object> params, int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        params = params == null ? new HashMap<String, Object>() : params;
        params.put("valid", valid);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestMode> resPagedList = this.testModeDao
                .queryPagedList(params, pageIndex, pageSize);
        
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
    public boolean exists(Map<String, String> key2valueMap, String excludeId) {
        AssertUtils.notEmpty(key2valueMap, "key2valueMap is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(key2valueMap);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testModeDao.count(params);
        
        return res > 0;
    }
    
    /**
     * 根据id更新对象
     * <功能详细描述>
     * @param testMode
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(TestMode testMode) {
        //验证参数是否合法，必填字段是否填写
        AssertUtils.notNull(testMode, "testMode is null.");
        AssertUtils.notEmpty(testMode.getId(), "testMode.id is empty.");
        AssertUtils.notEmpty(testMode.getName(), "testMode.name is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", testMode.getId());
        
        //FIXME:需要更新的字段
        updateRowMap.put("lastUpdateOperatorId",
                testMode.getLastUpdateOperatorId());
        updateRowMap.put("name", testMode.getName());
        updateRowMap.put("testInt", testMode.getTestInt());
        updateRowMap.put("testLong", testMode.getTestLong());
        updateRowMap.put("testBigDecimal", testMode.getTestBigDecimal());
        updateRowMap.put("type", testMode.getType());
        updateRowMap.put("valid", testMode.isValid());
        updateRowMap.put("createOperatorId", testMode.getCreateOperatorId());
        updateRowMap.put("expiryDate", testMode.getExpiryDate());
        updateRowMap.put("modifyAble", testMode.isModifyAble());
        updateRowMap.put("parentId", testMode.getParentId());
        updateRowMap.put("remark", testMode.getRemark());
        updateRowMap.put("nested1", testMode.getNested1());
        updateRowMap.put("nested2", testMode.getNested2());
        updateRowMap.put("success", testMode.getSuccess());
        updateRowMap.put("effictiveDate", testMode.getEffictiveDate());
        updateRowMap.put("attributes", testMode.getAttributes());
        updateRowMap.put("description", testMode.getDescription());
        updateRowMap.put("lastUpdateDate", new Date());
        
        int updateRowCount = this.testModeDao.update(updateRowMap);
        //如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
    
    /**
     * 根据id禁用TestMode<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public boolean disableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", false);
        
        this.testModeDao.update(params);
        
        return true;
    }
    
    /**
      * 根据id启用TestMode<br/>
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean enableById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("valid", true);
        
        this.testModeDao.update(params);
        
        return true;
    }
}
