/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.basicdata.controller;

import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataType;
import com.tx.component.basicdata.service.BasicDataTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * BasicDataType显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Api(value = "/basicDataType", tags = "基础数据类型API")
@RequestMapping("/basicDataType")
public class BasicDataTypeController {
    
    /** 基础数据类型业务层 */
    private BasicDataTypeService basicDataTypeService;
    
    /** <默认构造函数> */
    public BasicDataTypeController() {
        super();
    }
    
    /** <默认构造函数> */
    public BasicDataTypeController(BasicDataTypeService basicDataTypeService) {
        super();
        this.basicDataTypeService = basicDataTypeService;
    }
    
    /**
    * 跳转到查询BasicDataType列表页面<br/>
    * <功能详细描述>
    * @return [参数说明]
    * 
    * @return String [返回类型说明]
    * @exception throws [异常类型] [异常说明]
    * @see [类、类#方法、类#成员]
    */
    @RequestMapping("/toQueryBasicDataTypeList")
    public String toQueryBasicDataTypeList(ModelMap response) {
        return "/basicdata/queryBasicDataTypeList";
    }
    
    /**
     * 查询BasicDataType列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return List<BasicDataType> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "归属模块", required = false, dataType = "String"),
            @ApiImplicitParam(name = "common", value = "是否为通用类型", required = false, dataType = "Boolean"),
            @ApiImplicitParam(name = "code", value = "基础数据类型编码", required = false, dataType = "String") })
    @ResponseBody
    @RequestMapping(value = "/queryList", method = { RequestMethod.GET })
    public List<BasicDataType> queryList(
            @RequestParam(value = "module", required = false) String module,
            @RequestParam(value = "common", required = false) Boolean common,
            @RequestParam(value = "code", required = false) String code) {
        
        List<BasicDataType> resList = this.basicDataTypeService
                .queryList(module, common, code);
        return resList;
    }
    
    /**
     * 查询BasicDataType<br/>
     * <功能详细描述>
     * @param basicDataTypeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "查询实例", notes = "")
    @ApiImplicitParam(name = "basicDataTypeCode", value = "基础数据类型编码", required = true, dataType = "String")
    @ResponseBody
    @RequestMapping(value = "/findByCode", method = RequestMethod.GET)
    public BasicDataType findByCode(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode) {
        BasicDataType type = this.basicDataTypeService
                .findByCode(basicDataTypeCode);
        return type;
    }
    
    /**
     * 启用BasicDataType<br/>
     * <功能详细描述>
     * @param basicDataTypeId
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ApiOperation(value = "根据基础数据类型查询基础数据类型实例", notes = "")
    @ApiImplicitParam(name = "basicDataType", value = "基础数据类型", required = true, dataType = "String", paramType = "query")
    @ResponseBody()
    @RequestMapping(value = "/findByType", method = RequestMethod.GET)
    public BasicDataType findByType(
            @RequestParam(value = "basicDataType") Class<? extends BasicData> basicDataType) {
        BasicDataType type = this.basicDataTypeService
                .findByType(basicDataType);
        return type;
    }
    //    /**
    //     * 查询BasicDataType列表<br/>
    //     * <功能详细描述>
    //     * @return [参数说明]
    //     * 
    //     * @return List<BasicDataType> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @ApiOperation(value = "查询基础数据类型列表", notes = "")
    //    @ApiImplicitParams({
    //            @ApiImplicitParam(name = "common", value = "是否为通用类型", required = false, dataType = "Boolean"),
    //            @ApiImplicitParam(name = "viewType", value = "视图类型", required = false, dataType = "BasicDataViewTypeEnum") })
    //    @ResponseBody
    //    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    //    public List<BasicDataType> queryList(
    //            //@RequestParam(value = "valid", required = false) Boolean valid,
    //            //@RequestParam(value = "modifyAble", required = false) Boolean modifyAble,
    //            @RequestParam(value = "common", required = false) Boolean common,
    //            @RequestParam(value = "viewType", required = false) BasicDataViewTypeEnum viewType,
    //            @RequestParam(value = "type", required = false) Class<?> type,
    //            @RequestParam MultiValueMap<String, String> request) {
    //        Map<String, Object> params = new HashMap<>();
    //        
    //        params.put("common", common);
    //        params.put("module", request.getFirst("module"));
    //        params.put("tableName", request.getFirst("tableName"));
    //        params.put("name", request.getFirst("name"));
    //        
    //        //params.put("modifyAble", modifyAble);
    //        params.put("code", request.getFirst("code"));
    //        params.put("viewType", viewType);
    //        params.put("type", type);
    //        
    //        List<BasicDataType> resList = this.basicDataTypeService.queryList(valid,
    //                params);
    //        
    //        return resList;
    //    }
    //    /**
    //     * 启用BasicDataType<br/>
    //     * <功能详细描述>
    //     * @param basicDataTypeId
    //     * @return [参数说明]
    //     * 
    //     * @return boolean [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    @ApiOperation(value = "根据基础数据唯一键查询基础数据类型实例", notes = "")
    //    @ApiImplicitParam(name = "basicDataTypeId", value = "基础数据类型唯一键", required = true, dataType = "String")
    //    @ResponseBody
    //    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    //    public BasicDataType findById(
    //            @RequestParam(value = "basicDataTypeId") String basicDataTypeId) {
    //        BasicDataType type = this.basicDataTypeService
    //                .findById(basicDataTypeId);
    //        return type;
    //    }
}