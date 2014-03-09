/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.basicdata.controller;

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

import com.tx.component.auth.annotation.CheckOperateAuth;
import com.tx.component.basicdata.model.TestBasicData2;
import com.tx.component.basicdata.service.TestBasicData2Service;
import com.tx.core.paged.model.PagedList;

/**
 * TestBasicData2显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//TODO:指定自动生成的权限上级权限,以及对应的权限名称
@CheckOperateAuth(key = "testBasicData2_manage", name = "testBasicData2管理")
@Controller("testBasicData2Controller")
@RequestMapping("/testBasicData2")
public class TestBasicData2Controller {
    
    @Resource(name = "testBasicData2Service")
    private TestBasicData2Service testBasicData2Service;
    
    /**
      * 跳转到查询TestBasicData2列表页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryTestBasicData2List")
    public String toQueryTestBasicData2List() {
        return "/basicdata/queryTestBasicData2List";
    }
    
    /**
     * 跳转到查询TestBasicData2分页列表页面<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toQueryTestBasicData2PagedList")
    public String toQueryTestBasicData2PagedList() {
        return "/basicdata/queryTestBasicData2PagedList";
    }
    
    /**
      * 跳转到添加TestBasicData2页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAddTestBasicData2")
    public String toAddTestBasicData2(ModelMap response) {
        response.put("testBasicData2", new TestBasicData2());
        
        return "/basicdata/addTestBasicData2";
    }
    
    /**
     * 跳转到编辑TestBasicData2页面
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toUpdateTestBasicData2")
    public String toUpdateTestBasicData2(
            @RequestParam("testBasicData2Code") String testBasicData2Code,
            ModelMap modelMap) {
        TestBasicData2 resTestBasicData2 = this.testBasicData2Service.findTestBasicData2ByCode(testBasicData2Code);
        modelMap.put("testBasicData2", resTestBasicData2);
        
        return "/basicdata/updateTestBasicData2";
    }
    
    /**
      * 判断TestBasicData2:name是否已经被使用
      * <功能详细描述>
      * @param uniqueGetterName
      * @param code
      * @param excludeTestBasicData2Code
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/validateNameIsExist")
    public Map<String, String> validateNameIsExist(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam("name") String name,
            @RequestParam(value = "code", required = false) String excludeTestBasicData2Code) {
        Map<String, String> key2valueMap = new HashMap<String, String>();
        key2valueMap.put("name", name);
        
        boolean flag = this.testBasicData2Service.isExist(key2valueMap,
                excludeTestBasicData2Code);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            //TODO:修改验证重复成功提示信息
            resMap.put("ok", "可用的testBasicData2 name");
        } else {
            //TODO:修改验证重复失败提示信息
            resMap.put("error", "已经存在的testBasicData2 name");
        }
        return resMap;
    }
    
    /**
      * 查询TestBasicData2列表<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<TestBasicData2> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryTestBasicData2ListIncludeInvalid")
    public List<TestBasicData2> queryTestBasicData2ListIncludeInvalid(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name) {
        List<TestBasicData2> resList = this.testBasicData2Service.queryTestBasicData2ListIncludeInvalid(code,
                name);
        return resList;
    }
    
    /**
     * 查询TestBasicData2列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryTestBasicData2ListIncludeInvalid")
    public List<TestBasicData2> queryTestBasicData2ListIncludeAppointCode(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "appointCode") String appointCode) {
        List<TestBasicData2> resList = this.testBasicData2Service.queryTestBasicData2ListIncludeAppointCode(code,
                name,
                appointCode);
        return resList;
    }
    
    /**
     * 查询TestBasicData2列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryTestBasicData2List")
    public List<TestBasicData2> queryTestBasicData2List(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name) {
        List<TestBasicData2> resList = this.testBasicData2Service.queryTestBasicData2List(code,
                name);
        return resList;
    }
    
    /**
     * 查询TestBasicData2分页列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryTestBasicData2PagedList")
    public PagedList<TestBasicData2> queryTestBasicData2PagedList(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PagedList<TestBasicData2> resPagedList = this.testBasicData2Service.queryTestBasicData2PagedList(code,
                name,
                pageIndex,
                pageSize);
        return resPagedList;
    }
    
    /**
     * 查询TestBasicData2分页列表(包含无效的实体)<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return PagedList<TestBasicData2> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryTestBasicData2PagedListIncludeInvalid")
    public PagedList<TestBasicData2> queryTestBasicData2PagedListIncludeInvalid(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PagedList<TestBasicData2> resPagedList = this.testBasicData2Service.queryTestBasicData2PagedListIncludeInvalid(code,
                name,
                pageIndex,
                pageSize);
        return resPagedList;
    }
    
    /**
     * 添加组织结构页面
     *<功能详细描述>
     * @param organization [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    //TODO:修改删增加权限名称
    @CheckOperateAuth(key = "add_testBasicData2", name = "增加TestBasicData2")
    @RequestMapping("/addTestBasicData2")
    @ResponseBody
    public boolean addTestBasicData2(TestBasicData2 testBasicData2) {
        this.testBasicData2Service.insertTestBasicData2(testBasicData2);
        return true;
    }
    
    /**
      * 更新组织<br/>
      *<功能详细描述>
      * @param testBasicData2
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改删编辑权限名称
    @CheckOperateAuth(key = "update_testBasicData2", name = "编辑TestBasicData2")
    @RequestMapping("/updateTestBasicData2")
    @ResponseBody
    public boolean updateTestBasicData2(TestBasicData2 testBasicData2) {
        this.testBasicData2Service.updateByCode(testBasicData2);
        
        return true;
    }
    
    /**
      * 删除指定TestBasicData2<br/> 
      *<功能详细描述>
      * @param testBasicData2Code
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改删除权限名称
    @CheckOperateAuth(key = "delete_testBasicData2", name = "删除TestBasicData2")
    @ResponseBody
    @RequestMapping("/deleteTestBasicData2ByCode")
    public boolean deleteTestBasicData2ByCode(
            @RequestParam(value = "testBasicData2Code") String testBasicData2Code) {
        boolean resFlag = this.testBasicData2Service.deleteByCode(testBasicData2Code);
        return resFlag;
    }
    
    /**
      * 禁用TestBasicData2
      * @param testBasicData2Code
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改禁用权限名称
    @CheckOperateAuth(key = "disable_testBasicData2", name = "禁用TestBasicData2")
    @ResponseBody
    @RequestMapping("/disableTestBasicData2ByCode")
    public boolean disableTestBasicData2ByCode(
            @RequestParam(value = "testBasicData2Code") String testBasicData2Code) {
        boolean resFlag = this.testBasicData2Service.disableByCode(testBasicData2Code);
        return resFlag;
    }
    
    /**
      * 启用TestBasicData2<br/>
      *<功能详细描述>
      * @param testBasicData2Code
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改启用权限名称
    @CheckOperateAuth(key = "enable_testBasicData2", name = "启用TestBasicData2")
    @ResponseBody
    @RequestMapping("/enableTestBasicData2ByCode")
    public boolean enableTestBasicData2ByCode(
            @RequestParam(value = "testBasicData2Code") String testBasicData2Code) {
        boolean resFlag = this.testBasicData2Service.enableByCode(testBasicData2Code);
        return resFlag;
    }
    
}
