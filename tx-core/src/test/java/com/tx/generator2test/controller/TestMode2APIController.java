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
import com.tx.generator2test.facade.TestMode2Facade;
import com.tx.generator2test.model.TestMode2;
import com.tx.generator2test.service.TestMode2Service;

import io.swagger.annotations.Api;

/**
 * 测试对象2API控制层[TestMode2APIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "测试对象2API")
@RequestMapping("/api/testMode2")
public class TestMode2APIController implements TestMode2Facade {
    
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
    @Override
    public TestMode2 insert(@RequestBody TestMode2 testMode2) {
        this.testMode2Service.insert(testMode2);
        return testMode2;
    }
    
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
    @Override
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
    @Override
    public boolean updateByCode(@PathVariable(value = "code",required=true) String code,
    		@RequestBody TestMode2 testMode2) {
        boolean flag = this.testMode2Service.updateByCode(code,testMode2);
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
    @Override
    public TestMode2 findByCode(
            @PathVariable(value = "code", required = true) String code) {
        TestMode2 res = this.testMode2Service.findByCode(code);
        
        return res;
    }

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
    @Override
    public List<TestMode2> queryList(
    		@RequestBody Querier querier
    	) {
        List<TestMode2> resList = this.testMode2Service.queryList(
			querier         
        );
  
        return resList;
    }
    
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
    @Override
    public PagedList<TestMode2> queryPagedList(
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<TestMode2> resPagedList = this.testMode2Service.queryPagedList(
			querier,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
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
    @Override
    public int count(
            @RequestBody Querier querier) {
        int count = this.testMode2Service.count(
        	querier);
        
        return count;
    }

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
    @Override
    public boolean exists(@RequestBody Querier querier,
            @RequestParam(value = "excludeCode", required = false) String excludeCode) {
        boolean flag = this.testMode2Service.exists(querier, excludeCode);
        
        return flag;
    }
}