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

import com.tx.generator2test.model.TestMode2;
import com.tx.generator2test.service.TestMode2Service;
import com.tx.core.paged.model.PagedList;

import com.tx.generator2test.model.TestTypeEnum;

/**
 * 测试对象2控制层<br/>
 * 
 * @author []
 * @version [版本号]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/testMode2")
public class TestMode2Controller {
    
    //测试对象2业务层
    @Resource(name = "testMode2Service")
    private TestMode2Service testMode2Service;
    
    /**
     * 跳转到查询测试对象2列表页面<br/>
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
        
        return "/generator2test/queryTestMode2List";
    }
    
    /**
     * 跳转到新增测试对象2页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap response) {
        response.put("testMode2", new TestMode2());
        
        response.put("types", TestTypeEnum.values());
        
        return "/generator2test/addTestMode2";
    }
    
    /**
     * 跳转到编辑测试对象2页面
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam("testMode2Code") String testMode2Code,
            ModelMap response) {
        TestMode2 testMode2 = this.testMode2Service.findByCode(testMode2Code);
        response.put("testMode2", testMode2);
        
        response.put("types", TestTypeEnum.values());
        
        return "/generator2test/updateTestMode2";
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
    @ResponseBody
    @RequestMapping("/queryList")
    public List<TestMode2> queryList(
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
        
        List<TestMode2> resList = this.testMode2Service.queryList(params);
        
        return resList;
    }
    
    /**
     * 查询测试对象2实例分页列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestMode2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<TestMode2> queryPagedList(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        //params.put("",request.getFirst(""));
        
        PagedList<TestMode2> resPagedList = this.testMode2Service
                .queryPagedList(params, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * 新增测试对象2实例
     * <功能详细描述>
     * @param testMode2 [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/add")
    public boolean add(TestMode2 testMode2) {
        this.testMode2Service.insert(testMode2);
        return true;
    }
    
    /**
     * 更新测试对象2实例<br/>
     * <功能详细描述>
     * @param testMode2
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(TestMode2 testMode2) {
        boolean flag = this.testMode2Service.updateByCode(testMode2);
        return flag;
    }
    
    /**
     * 删除测试对象2实例<br/> 
     * <功能详细描述>
     * @param testMode2Code
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/deleteByCode")
    public boolean deleteByCode(
            @RequestParam(value = "testMode2Code") String testMode2Code) {
        boolean flag = this.testMode2Service.deleteByCode(testMode2Code);
        return flag;
    }
    
    /**
     * 校验参数对应实例是否重复
     * @param excludeCode
     * @param params
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/check/{excludeCode}")
    public Map<String, String> check(
            @PathVariable(value = "excludeCode", required = false) String excludeCode,
            @RequestParam Map<String, String> params) {
        boolean flag = this.testMode2Service.exists(params, excludeCode);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            resMap.put("ok", "");
        } else {
            resMap.put("error", "重复值");
        }
        return resMap;
    }
}