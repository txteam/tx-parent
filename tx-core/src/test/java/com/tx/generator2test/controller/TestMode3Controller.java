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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.generator2test.model.TestMode3;
import com.tx.generator2test.service.TestMode3Service;
import com.tx.core.paged.model.PagedList;

import com.tx.generator2test.model.TestTypeEnum;

/**
 * 测试对象控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/testMode3")
public class TestMode3Controller {
    
    //测试对象业务层
    @Resource(name = "testMode3Service")
    private TestMode3Service testMode3Service;
    
    /**
     * 跳转到查询测试对象列表页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryList")
    public String toQueryList(ModelMap response) {
		response.put("types", TestTypeEnum.values());

        return "/generator2test/queryTestMode3List";
    }
    
    
    /**
     * 跳转到新增测试对象页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap response) {
    	response.put("testMode3", new TestMode3());
    	
		response.put("types", TestTypeEnum.values());

        return "/generator2test/addTestMode3";
    }
    
    /**
     * 跳转到编辑测试对象页面
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(
    		@RequestParam("id") Long id,
            ModelMap response) {
        TestMode3 testMode3 = this.testMode3Service.findById(id); 
        response.put("testMode3", testMode3);

		response.put("types", TestTypeEnum.values());
        
        return "/generator2test/updateTestMode3";
    }

    /**
     * 查询测试对象实例列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<TestMode3> queryList(
			@RequestParam(value="valid",required=false) Boolean valid,
    		@RequestParam MultiValueMap<String, String> request
    	) {
        Map<String,Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
    	
        List<TestMode3> resList = this.testMode3Service.queryList(
			valid,
			params         
        );
  
        return resList;
    }
    
    /**
     * 查询测试对象实例分页列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode3> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<TestMode3> queryPagedList(
			@RequestParam(value="valid",required=false) Boolean valid,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request
    	) {
		Map<String,Object> params = new HashMap<>();
		//params.put("",request.getFirst(""));

        PagedList<TestMode3> resPagedList = this.testMode3Service.queryPagedList(
			valid,
			params,
			pageIndex,
			pageSize
        );
        return resPagedList;
    }
    
    /**
     * 新增测试对象实例
     * <功能详细描述>
     * @param testMode3 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/add")
    public boolean add(TestMode3 testMode3) {
        this.testMode3Service.insert(testMode3);
        return true;
    }
    
    /**
     * 更新测试对象实例<br/>
     * <功能详细描述>
     * @param testMode3
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(TestMode3 testMode3) {
        boolean flag = this.testMode3Service.updateById(testMode3);
        return flag;
    }
    
    /**
     * 根据主键查询测试对象实例<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/findById")
    public TestMode3 findById(@RequestParam(value = "id") Long id) {
        TestMode3 testMode3 = this.testMode3Service.findById(id);
        return testMode3;
    }

	/**
     * 根据编码查询测试对象实例<br/> 
     * <功能详细描述>
     * @param code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/findByCode")
    public TestMode3 findByCode(@RequestParam(value = "code") String code) {
        TestMode3 testMode3 = this.testMode3Service.findByCode(code);
        return testMode3;
    }
    
    /**
     * 删除测试对象实例<br/> 
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/deleteById")
    public boolean deleteById(@RequestParam(value = "id") Long id) {
        boolean flag = this.testMode3Service.deleteById(id);
        return flag;
    }
    
    /**
     * 禁用测试对象实例
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/disableById")
    public boolean disableById(@RequestParam(value = "id") Long id) {
        boolean flag = this.testMode3Service.disableById(id);
        return flag;
    }
    
    /**
     * 启用测试对象实例<br/>
     * <功能详细描述>
     * @param id
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/enableById")
    public boolean enableById(@RequestParam(value = "id") Long id) {
        boolean flag = this.testMode3Service.enableById(id);
        return flag;
    }

	/**
     * 校验参数对应实例是否重复
	 * @param excludeId
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/check/{excludeId}")
    public Map<String, String> check(
            @PathVariable(value = "excludeId", required = false) Long excludeId,
            @RequestParam Map<String, String> params) {
        boolean flag = this.testMode3Service.exists(params, excludeId);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            resMap.put("ok", "");
        } else {
            resMap.put("error", "重复值");
        }
        return resMap;
    }
}