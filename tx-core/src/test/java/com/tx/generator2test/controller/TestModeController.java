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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.generator2test.model.TestMode;
import com.tx.generator2test.service.TestModeService;
import com.tx.core.paged.model.PagedList;

import com.tx.generator2test.model.TestTypeEnum;

/**
 * 测试对象[TestMode]Controller层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/testMode")
public class TestModeController {
    
    @Resource(name = "testModeService")
    private TestModeService testModeService;
    
    /**
     * 跳转到查询测试对象列表页面[TestMode]<br/>
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
        
        return "/generator2test/queryTestModeList";
    }
    
    /**
     * 跳转到新增测试对象[TestMode]页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap response) {
        response.put("testMode", new TestMode());
        
        response.put("types", TestTypeEnum.values());
        
        return "/generator2test/addTestMode";
    }
    
    /**
     * 跳转到编辑测试对象[TestMode]页面
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam("testModeId") String testModeId,
            ModelMap response) {
        TestMode testMode = this.testModeService.findById(testModeId);
        response.put("testMode", testMode);
        
        response.put("types", TestTypeEnum.values());
        
        return "/generator2test/updateTestMode";
    }
    
    /**
     * 查询TestMode列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<TestMode> queryList(
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
        
        List<TestMode> resList = this.testModeService.queryList(valid, params);
        
        return resList;
    }
    
    /**
     * 查询TestMode分页列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<TestMode> queryPagedList(
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
        
        PagedList<TestMode> resPagedList = this.testModeService
                .queryPagedList(valid, params, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * 新增TestMode
     * <功能详细描述>
     * @param organization [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/add")
    public boolean add(TestMode testMode) {
        this.testModeService.insert(testMode);
        return true;
    }
    
    /**
     * 更新TestMode<br/>
     * <功能详细描述>
     * @param testMode
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(TestMode testMode) {
        boolean flag = this.testModeService.updateById(testMode);
        return flag;
    }
    
    /**
     * 删除TestMode<br/> 
     * <功能详细描述>
     * @param testModeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/deleteById")
    public boolean deleteById(
            @RequestParam(value = "testModeId") String testModeId) {
        boolean flag = this.testModeService.deleteById(testModeId);
        return flag;
    }
    
    /**
     * 禁用TestMode
     * @param testModeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/disableById")
    public boolean disableById(
            @RequestParam(value = "testModeId") String testModeId) {
        boolean flag = this.testModeService.disableById(testModeId);
        return flag;
    }
    
    /**
     * 启用TestMode<br/>
     * <功能详细描述>
     * @param testModeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/enableById")
    public boolean enableById(
            @RequestParam(value = "testModeId") String testModeId) {
        boolean flag = this.testModeService.enableById(testModeId);
        return flag;
    }
}