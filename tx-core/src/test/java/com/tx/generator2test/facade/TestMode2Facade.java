/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.generator2test.facade;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tx.core.paged.model.PagedList;
import com.tx.core.querier.model.Querier;
import com.tx.generator2test.model.TestMode2;

import io.swagger.annotations.ApiOperation;

/**
 * 测试对象2接口门面层[TestMode2Facade]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface TestMode2Facade {
    
    /**
     * 新增测试对象2<br/>
     * <功能详细描述>
     * @param testMode2 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "新增测试对象2")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public TestMode2 insert(@RequestBody TestMode2 testMode2);
    
    /**
     * 根据code删除测试对象2<br/> 
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "根据编码删除测试对象2")
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE) 
    public boolean deleteByCode(
    		@PathVariable(value = "code",required=true) String code);

    /**
     * 更新测试对象2<br/>
     * <功能详细描述>
     * @param testMode2
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "修改测试对象2")
    @RequestMapping(value = "/{code}", method = RequestMethod.PUT)
    public boolean updateByCode(@PathVariable(value = "code",required=true) String code,
    		@RequestBody TestMode2 testMode2);

    /**
     * 根据主键查询测试对象2<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TestMode2 [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "根据主键查询测试对象2")
    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public TestMode2 findByCode(
            @PathVariable(value = "code", required = true) String code);
    

    /**
     * 查询测试对象2实例列表<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return List<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TestMode2> queryList(
    		@RequestBody Querier querier
    	);
    
    /**
     * 查询测试对象2分页列表<br/>
     * <功能详细描述>
     * @param pageIndex
     * @param pageSize
     * @param querier
     * @return [参数说明]
     * 
     * @return PagedList<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2分页列表")
    @RequestMapping(value = "/pagedlist/{pageSize}/{pageNumber}", method = RequestMethod.GET)
    public PagedList<TestMode2> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	);
    
	/**
     * 查询测试对象2数量<br/>
     * <功能详细描述>
     * @param querier
     * @return [参数说明]
     * 
     * @return int [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2数量")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public int count(
            @RequestBody Querier querier);

	/**
     * 查询测试对象2是否存在<br/>
	 * @param excludeCode
     * @param querier
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2是否存在")
    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public boolean exists(
    		@RequestBody Querier querier,
            @RequestParam(value = "excludeCode", required = false) String excludeCode
            );
}