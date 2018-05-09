///*
// * 描       述:  <描述>
// * 修  改 人:  
// * 修改时间:
// * <修改描述:>
// */
//package com.tx.component.basicdata.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.tx.component.auth.annotation.CheckOperateAuth;
//import com.tx.component.basicdata.model.BasicDataType;
//import com.tx.component.basicdata.model.BasicDataViewTypeEnum;
//import com.tx.component.basicdata.service.BasicDataTypeService;
//
///**
// * BasicDataType显示层逻辑<br/>
// * <功能详细描述>
// * 
// * @author  PengQingyang
// * @version  [版本号, 2013-8-27]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@CheckOperateAuth(key = "basicDataType_manage_menuauth", name = "basicDataType管理")
//@Controller("basicDataTypeController")
//@RequestMapping("/basicDataType")
//public class BasicDataTypeController {
//    
//    @Resource(name = "basicdata.basicDataTypeService")
//    private BasicDataTypeService basicDataTypeService;
//    
//    /**
//      * 跳转到查询BasicDataType列表页面<br/>
//      *<功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @RequestMapping("/toQueryBasicDataTypeList")
//    public String toQueryBasicDataTypeList(ModelMap response) {
//        return "/basicdata/queryBasicDataTypeList";
//    }
//    
//    /**
//     * 跳转到添加BasicDataType页面<br/>
//     * <功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return String [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @RequestMapping("/toAddBasicDataType")
//    public String toAddBasicDataType(ModelMap response) {
//        response.put("basicDataType", new BasicDataType());
//        
//        return "/basicdata/addBasicDataType";
//    }
//    
//    /**
//     * 跳转到编辑BasicDataType页面
//     *<功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return String [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @RequestMapping("/toUpdateBasicDataType")
//    public String toUpdateBasicDataType(
//            @RequestParam("basicDataTypeId") String basicDataTypeId,
//            ModelMap response) {
//        BasicDataType resBasicDataType = this.basicDataTypeService.findById(basicDataTypeId);
//        response.put("basicDataType", resBasicDataType);
//        
//        return "/basicdata/updateBasicDataType";
//    }
//    
//    /**
//     * 判断BasicDataType:
//     *  type
//     *
//     * 是否已经被使用
//     * @param uniqueGetterName
//     * @param excludeBasicDataTypeId
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @ResponseBody
//    @RequestMapping("/validateTypeIsExist")
//    public Map<String, String> validateTypeIsExist(
//            @RequestParam("type") String type,
//            @RequestParam(value = "id", required = false) String excludeBasicDataTypeId,
//            @RequestParam MultiValueMap<String, String> request) {
//        
//        Map<String, String> key2valueMap = new HashMap<String, String>();
//        key2valueMap.put("type", type);
//        
//        boolean flag = this.basicDataTypeService.isExist(key2valueMap,
//                excludeBasicDataTypeId);
//        
//        Map<String, String> resMap = new HashMap<String, String>();
//        if (!flag) {
//            resMap.put("ok", "可用的基础数据类型");
//        } else {
//            resMap.put("error", "已经存在的基础数据类型");
//        }
//        return resMap;
//    }
//    
//    /**
//     * 查询BasicDataType列表<br/>
//     *<功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return List<BasicDataType> [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @ResponseBody
//    @RequestMapping("/queryList")
//    public List<BasicDataType> queryList(
//            @RequestParam(value = "valid", required = false) Boolean valid,
//            @RequestParam(value = "common", required = false) Boolean common,
//            @RequestParam(value = "modifyAble", required = false) Boolean modifyAble,
//            @RequestParam(value = "viewType", required = false) BasicDataViewTypeEnum viewType,
//            @RequestParam(value = "type", required = false) Class<?> type,
//            @RequestParam MultiValueMap<String, String> request) {
//        Map<String, Object> params = new HashMap<>();
//        
//        params.put("module", request.getFirst("module"));
//        params.put("common", common);
//        params.put("tableName", request.getFirst("tableName"));
//        params.put("name", request.getFirst("name"));
//        params.put("modifyAble", modifyAble);
//        params.put("code", request.getFirst("code"));
//        params.put("viewType", viewType);
//        params.put("type", type);
//        
//        List<BasicDataType> resList = this.basicDataTypeService.queryList(valid,
//                params);
//        
//        return resList;
//    }
//    
//    /**
//     * 添加组织结构页面
//     *<功能详细描述>
//     * @param organization [参数说明]
//     * 
//     * @return void [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @CheckOperateAuth(key = "add_basicDataType_oauth", name = "增加基础数据类型")
//    @RequestMapping("/add")
//    @ResponseBody
//    public boolean add(BasicDataType basicDataType) {
//        this.basicDataTypeService.insert(basicDataType);
//        return true;
//    }
//    
//    /**
//      * 更新组织<br/>
//      *<功能详细描述>
//      * @param basicDataType
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @CheckOperateAuth(key = "update_basicDataType_oauth", name = "编辑基础数据类型")
//    @RequestMapping("/update")
//    @ResponseBody
//    public boolean update(BasicDataType basicDataType) {
//        this.basicDataTypeService.updateById(basicDataType);
//        
//        return true;
//    }
//    
//    /**
//      * 删除指定BasicDataType<br/> 
//      *<功能详细描述>
//      * @param basicDataTypeId
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @CheckOperateAuth(key = "delete_basicDataType_oauth", name = "删除基础数据类型")
//    @ResponseBody
//    @RequestMapping("/deleteById")
//    public boolean deleteById(
//            @RequestParam(value = "basicDataTypeId") String basicDataTypeId) {
//        boolean resFlag = this.basicDataTypeService.deleteById(basicDataTypeId);
//        return resFlag;
//    }
//    
//    /**
//      * 禁用BasicDataType
//      * @param basicDataTypeId
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @CheckOperateAuth(key = "disable_basicDataType_oauth", name = "禁用基础数据类型")
//    @ResponseBody
//    @RequestMapping("/disableById")
//    public boolean disableById(
//            @RequestParam(value = "basicDataTypeId") String basicDataTypeId) {
//        boolean resFlag = this.basicDataTypeService.disableById(basicDataTypeId);
//        return resFlag;
//    }
//    
//    /**
//      * 启用BasicDataType<br/>
//      *<功能详细描述>
//      * @param basicDataTypeId
//      * @return [参数说明]
//      * 
//      * @return boolean [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @CheckOperateAuth(key = "enable_basicDataType_oauth", name = "启用基础数据类型")
//    @ResponseBody
//    @RequestMapping("/enableById")
//    public boolean enableById(
//            @RequestParam(value = "basicDataTypeId") String basicDataTypeId) {
//        boolean resFlag = this.basicDataTypeService.enableById(basicDataTypeId);
//        return resFlag;
//    }
//}