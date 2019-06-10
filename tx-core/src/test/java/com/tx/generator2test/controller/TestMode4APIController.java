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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.generator2test.model.TestMode4;
import com.tx.generator2test.service.TestMode4Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 测试对象API控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "测试对象API")
@RequestMapping("/api/testMode4")
public class TestMode4APIController {
    
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
    @ApiOperation(value = "新增测试对象")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean insert(@RequestBody TestMode4 testMode4) {
        this.testMode4Service.insert(testMode4);
        return true;
    }
    
    /**
     * 删除测试对象<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "删除测试对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
    public boolean deleteById(
    		@PathVariable(value = "id",required=true) Long id) {
        boolean flag = this.testMode4Service.deleteById(id);
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
    @ApiOperation(value = "修改测试对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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
    @ApiOperation(value = "根据主键查询测试对象")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
    @ApiOperation(value = "根据编码查询测试对象")
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public TestMode4 findByCode(
            @PathVariable(value = "code", required = true) String code) {
        TestMode4 res = this.testMode4Service.findByCode(code);
        
        return res;
    }

    /**
     * 查询测试对象实例列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
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
     * @param valid
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<TestMode4> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象分页列表")
    @RequestMapping(value = "/pagedlist/{pageSize}/{pageNumber}", method = RequestMethod.GET)
    public PagedList<TestMode4> queryPagedList(
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize,
            @RequestBody Querier querier
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
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象数量")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
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
    @ApiOperation(value = "查询测试对象是否存在")
    @RequestMapping(value = "/exists/{excludeId}", method = RequestMethod.GET)
    public boolean exists(
            @PathVariable(value = "excludeId", required = false) Long excludeId,
            @RequestBody Querier querier) {
        boolean flag = this.testMode4Service.exists(querier, excludeId);
        
        return flag;
    }
    

}