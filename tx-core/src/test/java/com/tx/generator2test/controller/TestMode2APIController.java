/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.generator2test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tx.core.paged.model.PagedList;
import com.tx.generator2test.model.TestMode2;
import com.tx.generator2test.service.TestMode2Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 测试对象2API控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "测试对象2API")
@RequestMapping("/api/TestMode2?uncap_first")
public class TestMode2APIController {
    
    //测试对象2业务层
    @Resource(name = "testMode2Service")
    private TestMode2Service testMode2Service;
    
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
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public boolean insert(@RequestBody TestMode2 testMode2) {
        this.testMode2Service.insert(testMode2);
        return true;
    }
    
    /**
     * 删除测试对象2<br/> 
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "删除测试对象2")
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE) 
    public boolean deleteByCode(
    	@PathVariable(value = "code",required=true) String code) {
        boolean flag = this.testMode2Service.deleteByCode(code);
        return flag;
    }
    
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
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public boolean update(@RequestBody TestMode2 testMode2) {
        boolean flag = this.testMode2Service.updateByCode(testMode2);
        return flag;
    }
    

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
    public TestMode2 findById(
            @PathVariable(value = "code", required = true) String code) {
        TestMode2 res = this.testMode2Service.findByCode(code);
        
        return res;
    }
    

	/**
     * 查询测试对象2实例列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2列表")
    @RequestMapping(value = "/list/{valid}", method = RequestMethod.GET)
    public List<TestMode2> queryList(
    		@RequestParam MultiValueMap<String, String> request
    	) {
        Map<String,Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
    	
        List<TestMode2> resList = this.testMode2Service.queryList(
			params         
        );
  
        return resList;
    }
    
    /**
     * 查询测试对象2分页列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2分页列表")
    @RequestMapping(value = "/pagedlist/{pageSize}/{pageNumber}/{valid}", method = RequestMethod.GET)
    public PagedList<TestMode2> queryPagedList(
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize,
            @RequestParam MultiValueMap<String, String> request
    	) {
		Map<String,Object> params = new HashMap<>();
		//params.put("",request.getFirst(""));

        PagedList<TestMode2> resPagedList = this.testMode2Service.queryPagedList(
			params,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }

	/**
     * 查询测试对象2是否存在<br/>
	 * @param excludeCode
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询测试对象2是否存在")
    @RequestMapping(value = "/exists/{excludeCode}", method = RequestMethod.GET)
    public boolean exists(
            @PathVariable(value = "excludeCode", required = false) String excludeCode,
            @RequestParam Map<String, String> params) {
        boolean flag = this.testMode2Service.exists(params, excludeCode);
        
        return flag;
    }
}