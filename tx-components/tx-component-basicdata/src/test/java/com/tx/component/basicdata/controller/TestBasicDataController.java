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
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.component.auth.annotation.CheckOperateAuth;
import com.tx.component.basicdata.model.TestBasicData;
import com.tx.component.basicdata.service.TestBasicDataService;

/**
 * TestBasicData显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//TODO:指定自动生成的权限上级权限,以及对应的权限名称
@CheckOperateAuth(key = "testBasicData_manage", name = "testBasicData管理")
@Controller("testBasicDataController")
@RequestMapping("/testBasicData")
public class TestBasicDataController {
    
    @Resource(name = "testBasicDataService")
    private TestBasicDataService testBasicDataService;
    
    /**
      * 跳转到查询TestBasicData列表页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryTestBasicDataList")
    public String toQueryTestBasicDataList() {
        return "/basicdata/queryTestBasicDataList";
    }
    
    /**
     * 跳转到查询TestBasicData分页列表页面<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toQueryTestBasicDataPagedList")
    public String toQueryTestBasicDataPagedList() {
        return "/basicdata/queryTestBasicDataPagedList";
    }
    
    /**
      * 跳转到添加TestBasicData页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAddTestBasicData")
    public String toAddTestBasicData(ModelMap response) {
        response.put("testBasicData", new TestBasicData());
        
        return "/basicdata/addTestBasicData";
    }
    
    /**
     * 跳转到更新TestBasicData页面
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toUpdateTestBasicData")
    public String toUpdateTestBasicData(
            @RequestParam("testBasicDataId") String testBasicDataId,
            ModelMap modelMap) {
        TestBasicData resTestBasicData = this.testBasicDataService.findTestBasicDataById(testBasicDataId);
        modelMap.put("testBasicData", resTestBasicData);
        
        return "/basicdata/updateTestBasicData";
    }
    
    /**
      * 判断TestBasicData:name是否已经被使用
      * <功能详细描述>
      * @param uniqueGetterName
      * @param code
      * @param excludeTestBasicDataId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/testBasicDataCodeIsExist")
    public Map<String, String> validateNameIsExist(
            @RequestParam("name") String name,
            @RequestParam(value = "id", required = false) String excludeTestBasicDataId) {
        Map<String, String> key2valueMap = new HashMap<String, String>();
        key2valueMap.put("name", name);
        
        boolean flag = this.testBasicDataService.isExist(key2valueMap,
                excludeTestBasicDataId);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            //TODO:修改验证重复成功提示信息
            resMap.put("ok", "可用的testBasicData name");
        } else {
            //TODO:修改验证重复失败提示信息
            resMap.put("error", "已经存在的testBasicData name");
        }
        return resMap;
    }
    
    /**
      * 判断TestBasicData:name,code是否已经被使用
      * <功能详细描述>
      * @param uniqueGetterName
      * @param uniqueGetterName
      * @param code
      * @param excludeTestBasicDataId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/testBasicDataCodeIsExist")
    public Map<String, String> validateNameAndCodeIsExist(
            @RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam(value = "id", required = false) String excludeTestBasicDataId) {
        Map<String, String> key2valueMap = new HashMap<String, String>();
        key2valueMap.put("name", name);
        key2valueMap.put("code", code);
        
        boolean flag = this.testBasicDataService.isExist(key2valueMap,
                excludeTestBasicDataId);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            //TODO:修改验证重复成功提示信息
            resMap.put("ok", "可用的testBasicData name,code");
        } else {
            //TODO:修改验证重复失败提示信息
            resMap.put("error", "已经存在的testBasicData name,code");
        }
        return resMap;
    }
    
    /**
      * 查询TestBasicData列表<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return List<TestBasicData> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryTestBasicDataListIncludeInvalid")
    public List<TestBasicData> queryTestBasicDataListIncludeInvalid(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "maxCreateDate", required = false) Date maxCreateDate,
            @RequestParam(value = "minCreateDate", required = false) Date minCreateDate,
            @RequestParam(value = "name", required = false) String name) {
        List<TestBasicData> resList = this.testBasicDataService.queryTestBasicDataListIncludeInvalid(code,
                maxCreateDate,
                minCreateDate,
                name);
        return resList;
    }
    
    /**
     * 查询TestBasicData列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryTestBasicDataListIncludeInvalid")
    public List<TestBasicData> queryTestBasicDataListIncludeAppointId(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "maxCreateDate", required = false) Date maxCreateDate,
            @RequestParam(value = "minCreateDate", required = false) Date minCreateDate,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "appointId") String appointId) {
        List<TestBasicData> resList = this.testBasicDataService.queryTestBasicDataListIncludeAppointId(code,
                maxCreateDate,
                minCreateDate,
                name,
                appointId);
        return resList;
    }
    
    /**
     * 查询TestBasicData列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<TestBasicData> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryTestBasicDataList")
    public List<TestBasicData> queryTestBasicDataList(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "maxCreateDate", required = false) Date maxCreateDate,
            @RequestParam(value = "minCreateDate", required = false) Date minCreateDate,
            @RequestParam(value = "name", required = false) String name) {
        List<TestBasicData> resList = this.testBasicDataService.queryTestBasicDataList(code,
                maxCreateDate,
                minCreateDate,
                name);
        return resList;
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
    @CheckOperateAuth(key = "add_testBasicData", name = "增加TestBasicData")
    @RequestMapping("/addTestBasicData")
    @ResponseBody
    public boolean addTestBasicData(TestBasicData testBasicData) {
        this.testBasicDataService.insertTestBasicData(testBasicData);
        return true;
    }
    
    /**
      * 更新组织<br/>
      *<功能详细描述>
      * @param testBasicData
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改删编辑权限名称
    @CheckOperateAuth(key = "update_testBasicData", name = "编辑TestBasicData")
    @RequestMapping("/updateTestBasicData")
    @ResponseBody
    public boolean updateTestBasicData(TestBasicData testBasicData) {
        this.testBasicDataService.updateById(testBasicData);
        
        return true;
    }
    
    /**
      * 删除指定TestBasicData<br/> 
      *<功能详细描述>
      * @param testBasicDataId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改删除权限名称
    @CheckOperateAuth(key = "delete_testBasicData", name = "删除TestBasicData")
    @ResponseBody
    @RequestMapping("/deleteTestBasicDataById")
    public boolean deleteTestBasicDataById(
            @RequestParam(value = "testBasicDataId") String testBasicDataId) {
        boolean resFlag = this.testBasicDataService.deleteById(testBasicDataId);
        return resFlag;
    }
    
    /**
      * 禁用TestBasicData
      * @param testBasicDataId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改禁用权限名称
    @CheckOperateAuth(key = "disable_testBasicData", name = "禁用TestBasicData")
    @ResponseBody
    @RequestMapping("/disableTestBasicDataById")
    public boolean disableTestBasicDataById(
            @RequestParam(value = "testBasicDataId") String testBasicDataId) {
        boolean resFlag = this.testBasicDataService.disableById(testBasicDataId);
        return resFlag;
    }
    
    /**
      * 启用TestBasicData<br/>
      *<功能详细描述>
      * @param testBasicDataId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //TODO:修改启用权限名称
    @CheckOperateAuth(key = "enable_testBasicData", name = "启用TestBasicData")
    @ResponseBody
    @RequestMapping("/enableTestBasicDataById")
    public boolean enableTestBasicDataById(
            @RequestParam(value = "testBasicDataId") String testBasicDataId) {
        boolean resFlag = this.testBasicDataService.enableById(testBasicDataId);
        return resFlag;
    }
    
}
