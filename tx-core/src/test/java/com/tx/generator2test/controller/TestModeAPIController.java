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
import com.tx.generator2test.facade.TestModeFacade;
import com.tx.generator2test.model.TestMode;
import com.tx.generator2test.service.TestModeService;

import io.swagger.annotations.Api;

/**
 * 测试对象API控制层[TestModeAPIController]<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@RestController
@Api(tags = "测试对象API")
@RequestMapping("/api/testMode")
public class TestModeAPIController implements TestModeFacade {
    
    //测试对象业务层
    @Resource(name = "testModeService")
    private TestModeService testModeService;
    
    /**
     * 新增测试对象<br/>
     * <功能详细描述>
     * @param testMode [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public TestMode insert(@RequestBody TestMode testMode) {
        this.testModeService.insert(testMode);
        return testMode;
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
    		@PathVariable(value = "id",required=true) String id) {
        boolean flag = this.testModeService.deleteById(id);
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
        boolean flag = this.testModeService.deleteByCode(code);
        return flag;    
    }
    
    /**
     * 更新测试对象<br/>
     * <功能详细描述>
     * @param testMode
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean updateById(@PathVariable(value = "id",required=true) String id,
    		@RequestBody TestMode testMode) {
        boolean flag = this.testModeService.updateById(id,testMode);
        return flag;
    }
    
    /**
     * 禁用测试对象<br/>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
	@Override
    public boolean disableById(
    		@PathVariable(value = "id", required = true) String id) {
        boolean flag = this.testModeService.disableById(id);
        return flag;
    }
    
    /**
     * 启用测试对象<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public boolean enableById(
    		@PathVariable(value = "id", required = true) String id) {
        boolean flag = this.testModeService.enableById(id);
        return flag;
    }

    /**
     * 根据主键查询测试对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TestMode [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public TestMode findById(
            @PathVariable(value = "id", required = true) String id) {
        TestMode res = this.testModeService.findById(id);
        
        return res;
    }

    /**
     * 根据编码查询测试对象<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return TestMode [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public TestMode findByCode(
            @PathVariable(value = "code", required = true) String code) {
        TestMode res = this.testModeService.findByCode(code);
        
        return res;
    }

    /**
     * 查询测试对象实例列表<br/>
     * <功能详细描述>
     * @param valid
     * @param querier
     * @return [参数说明]
     * 
     * @return List<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public List<TestMode> queryList(
			@RequestParam(value = "valid", required = false) Boolean valid,
    		@RequestBody Querier querier
    	) {
        List<TestMode> resList = this.testModeService.queryList(
			valid,
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
     * @return PagedList<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @Override
    public PagedList<TestMode> queryPagedList(
			@RequestParam(value = "valid", required = false) Boolean valid,
			@RequestBody Querier querier,
			@PathVariable(value = "pageNumber", required = true) int pageIndex,
            @PathVariable(value = "pageSize", required = true) int pageSize
    	) {
        PagedList<TestMode> resPagedList = this.testModeService.queryPagedList(
			valid,
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
    @Override
    public int count(
			@RequestParam(value = "valid", required = false) Boolean valid,
            @RequestBody Querier querier) {
        int count = this.testModeService.count(
			valid,
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
            @RequestParam(value = "excludeId", required = false) String excludeId) {
        boolean flag = this.testModeService.exists(querier, excludeId);
        
        return flag;
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
    @Override
    public List<TestMode> queryChildrenByParentId(@PathVariable(value = "parentId", required = true) String parentId,
			@RequestParam(value = "valid", required = false) Boolean valid,
            Querier querier){
        List<TestMode> resList = this.testModeService.queryChildrenByParentId(parentId,
			valid,
			querier         
        );
  
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
    @Override
    public List<TestMode> queryDescendantsByParentId(@PathVariable(value = "parentId", required = true) String parentId,
			@RequestParam(value = "valid", required = false) Boolean valid,
            Querier querier){
        List<TestMode> resList = this.testModeService.queryDescendantsByParentId(parentId,
			valid,
			querier         
        );
  
        return resList;
    }
}