/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.generator2test.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.generator2test.facade.TestMode4Facade;
import com.tx.generator2test.model.TestMode4;
import com.tx.generator2test.service.TestMode4Service;

import io.swagger.annotations.Api;

/**
 * 测试对象API控制层[TestMode4APIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "测试对象API")
@RequestMapping("/api/testMode4")
public class TestMode4APIController implements TestMode4Facade {
    
    //测试对象业务层
    @Resource(name = "testMode4Service")
    private TestMode4Service testMode4Service;
    
    /**
     * 新增测试对象<br/>
     * <功能详细描述>
     * @param testMode4 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public TestMode4 insert(@RequestBody TestMode4 testMode4) {
        this.testMode4Service.insert(testMode4);
        return testMode4;
    }
    
    /**
     * 根据id删除测试对象<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean deleteById(
    		@PathVariable(value = "id",required=true) Long id) {
        boolean flag = this.testMode4Service.deleteById(id);
        return flag;
    }
	
	/**
     * 根据code删除测试对象<br/> 
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean deleteByCode(
    		@PathVariable(value = "code",required=true) String code){
        boolean flag = this.testMode4Service.deleteByCode(code);
        return flag;    
    }
    
    /**
     * 更新测试对象<br/>
     * <功能详细描述>
     * @param testMode4
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(@PathVariable(value = "id",required=true) Long id,
    		@RequestBody TestMode4 testMode4) {
        boolean flag = this.testMode4Service.updateById(id,testMode4);
        return flag;
    }
    

    /**
     * 根据主键查询测试对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TestMode4 [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public TestMode4 findById(
            @PathVariable(value = "id", required = true) Long id) {
        TestMode4 res = this.testMode4Service.findById(id);
        
        return res;
    }

    /**
     * 根据编码查询测试对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TestMode4 [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public TestMode4 findByCode(
            @PathVariable(value = "code", required = true) String code) {
        TestMode4 res = this.testMode4Service.findByCode(code);
        
        return res;
    }

    /**
     * 查询测试对象实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<TestMode4> queryList(
    		@RequestBody Querier querier
    	) {
        List<TestMode4> resList = this.testMode4Service.queryList(
			querier         
        );
  
        return resList;
    }
    
    /**
     * 查询测试对象分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<TestMode4> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<TestMode4> resPagedList = this.testMode4Service.queryPagedList(
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
	/**
     * 查询测试对象数量<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public int count(
            @RequestBody Querier querier) {
        int count = this.testMode4Service.count(
        	querier);
        
        return count;
    }

	/**
     * 查询测试对象是否存在<br/>
	 * @param excludeId
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean exists(@RequestBody Querier querier,
            @RequestParam(value = "excludeId", required = false) Long excludeId) {
        boolean flag = this.testMode4Service.exists(querier, excludeId);
        
        return flag;
    }

	/**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<TestMode4> queryChildrenByParentId(@PathVariable(value = "parentId", required = true) Long parentId,
            Querier querier){
        List<TestMode4> resList = this.testMode4Service.queryChildrenByParentId(parentId,
			querier         
        );
  
        return resList;
    }

	/**
     * 根据条件查询基础数据分页列表<br/>
     * <功能详细描述>
     * @param parentId
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<T> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<TestMode4> queryDescendantsByParentId(@PathVariable(value = "parentId", required = true) Long parentId,
            Querier querier){
        List<TestMode4> resList = this.testMode4Service.queryDescendantsByParentId(parentId,
			querier         
        );
  
        return resList;
    }
}