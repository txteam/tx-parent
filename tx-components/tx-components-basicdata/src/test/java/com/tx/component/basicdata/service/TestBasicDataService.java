/*
 * 描          述:  <描述>
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package com.tx.component.basicdata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tx.component.basicdata.dao.TestBasicDataDao;
import com.tx.component.basicdata.model.TestBasicData;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;

/**
 * TestBasicData的业务层
 * <功能详细描述>
 * 
 * @author  
 * @version  [版本号, ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("testBasicDataService")
public class TestBasicDataService {
    
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(TestBasicDataService.class);
    
    @Resource(name = "testBasicDataDao")
    private TestBasicDataDao testBasicDataDao;
    
    /**
      * 将testBasicData实例插入数据库中保存
      * 1、如果testBasicData为空时抛出参数为空异常
      * 2、如果testBasicData中部分必要参数为非法值时抛出参数不合法异常
     * <功能详细描述>
     * @param district [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws
     * @see [类、类#方法、类#成员]
    */
    @Transactional
    public void insertTestBasicData(TestBasicData testBasicData) {
        //验证参数是否合法
        AssertUtils.notNull(testBasicData, "testBasicData is null.");
        AssertUtils.notEmpty(testBasicData.getId(),
                "testBasicData.id is empty.");
        
        //为添加的数据需要填入默认值的字段填入默认值
        
        //调用数据持久层对实体进行持久化操作
        this.testBasicDataDao.insertTestBasicData(testBasicData);
    }
    
    /**
     * 根据id删除testBasicData实例
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
    public boolean deleteById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestBasicData condition = new TestBasicData();
        condition.setId(id);
        int resInt = this.testBasicDataDao.deleteTestBasicData(condition);
        return resInt > 0;
    }
    
    /**
      * 根据Id查询TestBasicData实体
      * 1、当id为empty时抛出异常
      * <功能详细描述>
      * @param id
      * @return [参数说明]
      * 
      * @return TestBasicData [返回类型说明]
      * @exception throws 可能存在数据库访问异常DataAccessException
      * @see [类、类#方法、类#成员]
     */
    public TestBasicData findTestBasicDataById(String id) {
        AssertUtils.notEmpty(id, "id is empty.");
        
        TestBasicData condition = new TestBasicData();
        condition.setId(id);
        
        TestBasicData res = this.testBasicDataDao.findTestBasicData(condition);
        return res;
    }
    
    /**
      * 根据TestBasicData实体列表
      * 
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return List<TestBasicData> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public List<TestBasicData> queryTestBasicDataList(boolean valid,
            String code, String name) {
        //判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("valid", valid);
        params.put("code", code);
        params.put("name", name);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        List<TestBasicData> resList = this.testBasicDataDao.queryTestBasicDataList(params);
        
        return resList;
    }
    
    /**
     * 分页查询TestBasicData实体列表
     * TODO:补充说明
     * 
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public PagedList<TestBasicData> queryTestBasicDataPagedList(boolean valid,
            String code, String name, int pageIndex, int pageSize) {
        //T判断条件合法性
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("valid", valid);
        params.put("code", code);
        params.put("name", name);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        PagedList<TestBasicData> resPagedList = this.testBasicDataDao.queryTestBasicDataPagedList(params,
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
    public boolean isExist(String code, String excludeId) {
        AssertUtils.notEmpty(code, "code is empty");
        
        //生成查询条件
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        params.put("excludeId", excludeId);
        
        //根据实际情况，填入排序字段等条件，根据是否需要排序，选择调用dao内方法
        int res = this.testBasicDataDao.countTestBasicData(params);
        
        return res > 0;
    }
    
    /**
      * 根据id更新对象
      * <功能详细描述>
      * @param testBasicData
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @Transactional
    public boolean updateById(TestBasicData testBasicData) {
        //验证参数是否合法，必填字段是否填写，
        AssertUtils.notNull(testBasicData, "testBasicData is null.");
        AssertUtils.notEmpty(testBasicData.getId(),
                "testBasicData.id is empty.");
        
        //生成需要更新字段的hashMap
        Map<String, Object> updateRowMap = new HashMap<String, Object>();
        updateRowMap.put("id", testBasicData.getId());
        
        //TODO:需要更新的字段
        updateRowMap.put("valid", testBasicData.isValid());
        updateRowMap.put("remark", testBasicData.getRemark());
        updateRowMap.put("name", testBasicData.getName());
        updateRowMap.put("code", testBasicData.getCode());
        updateRowMap.put("type", testBasicData.getType());
        updateRowMap.put("createDate", testBasicData.getCreateDate());
        updateRowMap.put("lastUpdateDate", testBasicData.getLastUpdateDate());
        int updateRowCount = this.testBasicDataDao.updateTestBasicData(updateRowMap);
        
        //TODO:如果需要大于1时，抛出异常并回滚，需要在这里修改
        return updateRowCount >= 1;
    }
}
