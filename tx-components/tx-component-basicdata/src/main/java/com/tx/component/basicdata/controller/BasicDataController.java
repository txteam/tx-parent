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
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.paged.model.PagedList;

/**
 * DataDict显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@CheckOperateAuth(key = "dataDict_manage_menuauth", name = "dataDict管理")
@Controller("dataDictController")
@RequestMapping("/dataDict")
public class BasicDataController {
    
    @Resource(name = "dataDictService")
    private DataDictService dataDictService;
    
    /**
      * 跳转到数据字典管理页面<br/>
      * <功能详细描述>
      * @param response
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toDataDictMainframe")
    public String toDataDictMainframe(ModelMap response) {
        
        return "/basicdata/dataDictMainframe";
    }
    
    /**
      * 跳转到查询DataDict列表页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQueryDataDict")
    public String toQueryDataDictList(ModelMap response) {
        boolean isPagedList = false;
        
        if (isPagedList) {
            
        }
        return "/basicdata/queryDataDictList";
    }
    
    /**
      * 跳转到添加DataDict页面<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAddDataDict")
    public String toAddDataDict(ModelMap response) {
        response.put("dataDict", new DataDict());
        
        return "/basicdata/addDataDict";
    }
    
    /**
     * 跳转到编辑DataDict页面
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toUpdateDataDict")
    public String toUpdateDataDict(
            @RequestParam("dataDictId") String dataDictId, ModelMap response) {
        DataDict resDataDict = this.dataDictService.findById(dataDictId);
        response.put("dataDict", resDataDict);
        
        return "/basicdata/updateDataDict";
    }
    
    /**
     * 判断DataDict:
     *  code
     *  basicDataTypeCode
     *
     * 是否已经被使用
     * @param uniqueGetterName
     * @param uniqueGetterName
     * @param excludeDataDictId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/validateCodeAndBasicDataTypeCodeIsExist")
    public Map<String, String> validateCodeAndBasicDataTypeCodeIsExist(
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam("code") String code,
            @RequestParam("basicDataTypeCode") String basicDataTypeCode,
            @RequestParam(value = "id", required = false) String excludeDataDictId) {
        
        Map<String, String> key2valueMap = new HashMap<String, String>();
        key2valueMap.put("code", code);
        key2valueMap.put("basicDataTypeCode", basicDataTypeCode);
        
        boolean flag = this.dataDictService.isExist(key2valueMap,
                excludeDataDictId);
        
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            //FIXME:修改验证重复成功提示信息
            resMap.put("ok", "可用的dataDict code,basicDataTypeCode");
        } else {
            //FIXME:修改验证重复失败提示信息
            resMap.put("error", "已经存在的dataDict code,basicDataTypeCode");
        }
        return resMap;
    }
    
    /**
     * 查询DataDict列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<DataDict> queryList(
            @RequestParam(value = "basicDataTypeCode", required = false) String basicDataTypeCode,
            @RequestParam(value = "valid", required = false) Boolean valid,
            
            @RequestParam(value = "modifyAble", required = false) Boolean modifyAble,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        
        params.put("modifyAble", modifyAble);
        params.put("code", request.getFirst("code"));
        params.put("remark", request.getFirst("remark"));
        params.put("name", request.getFirst("name"));
        
        List<DataDict> resList = this.dataDictService.queryList(basicDataTypeCode,
                valid,
                params);
        
        return resList;
    }
    
    /**
     * 查询DataDict分页列表<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    @ResponseBody
    @RequestMapping("/queryPagedList")
    public PagedList<DataDict> queryPagedList(
            @RequestParam(value = "basicDataTypeCode", required = false) String basicDataTypeCode,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestParam(value = "modifyAble", required = false) Boolean modifyAble,
            @RequestParam MultiValueMap<String, String> request,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Map<String, Object> params = new HashMap<>();
        
        params.put("modifyAble", modifyAble);
        params.put("code", request.getFirst("code"));
        params.put("remark", request.getFirst("remark"));
        params.put("name", request.getFirst("name"));
        
        PagedList<DataDict> resPagedList = this.dataDictService.queryPagedList(basicDataTypeCode,
                valid,
                params,
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
    //FIXME:修改删增加权限名称
    @CheckOperateAuth(key = "add_dataDict_oauth", name = "增加DataDict")
    @RequestMapping("/add")
    @ResponseBody
    public boolean add(DataDict dataDict) {
        this.dataDictService.insert(dataDict);
        return true;
    }
    
    /**
      * 更新组织<br/>
      *<功能详细描述>
      * @param dataDict
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改删编辑权限名称
    @CheckOperateAuth(key = "update_dataDict_oauth", name = "编辑DataDict")
    @RequestMapping("/update")
    @ResponseBody
    public boolean update(DataDict dataDict) {
        this.dataDictService.updateById(dataDict);
        
        return true;
    }
    
    /**
      * 删除指定DataDict<br/> 
      *<功能详细描述>
      * @param dataDictId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改删除权限名称
    @CheckOperateAuth(key = "delete_dataDict_oauth", name = "删除DataDict")
    @ResponseBody
    @RequestMapping("/deleteById")
    public boolean deleteById(
            @RequestParam(value = "dataDictId") String dataDictId) {
        boolean resFlag = this.dataDictService.deleteById(dataDictId);
        return resFlag;
    }
    
    /**
      * 禁用DataDict
      * @param dataDictId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改禁用权限名称
    @CheckOperateAuth(key = "disable_dataDict_oauth", name = "禁用DataDict")
    @ResponseBody
    @RequestMapping("/disableById")
    public boolean disableById(
            @RequestParam(value = "dataDictId") String dataDictId) {
        boolean resFlag = this.dataDictService.disableById(dataDictId);
        return resFlag;
    }
    
    /**
      * 启用DataDict<br/>
      *<功能详细描述>
      * @param dataDictId
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    //FIXME:修改启用权限名称
    @CheckOperateAuth(key = "enable_dataDict_oauth", name = "启用DataDict")
    @ResponseBody
    @RequestMapping("/enableById")
    public boolean enableById(
            @RequestParam(value = "dataDictId") String dataDictId) {
        boolean resFlag = this.dataDictService.enableById(dataDictId);
        return resFlag;
    }
}