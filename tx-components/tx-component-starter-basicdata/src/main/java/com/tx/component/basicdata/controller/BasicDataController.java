/*
 * 描       述:  <描述>
 * 修  改 人:  
 * 修改时间:
 * <修改描述:>
 */
package com.tx.component.basicdata.controller;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tx.component.basicdata.context.BasicDataContext;
import com.tx.component.basicdata.context.BasicDataService;
import com.tx.component.basicdata.model.BasicData;
import com.tx.component.basicdata.model.BasicDataType;
import com.tx.component.basicdata.model.DataDict;
import com.tx.component.basicdata.service.BasicDataTypeService;
import com.tx.component.basicdata.service.DataDictService;
import com.tx.core.TxConstants;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.paged.model.PagedList;
import com.tx.core.util.ObjectUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * DataDict显示层逻辑<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Api(value = "/basicData", tags = "基础数据API")
@RequestMapping("/basicData")
public class BasicDataController
        implements InitializingBean, ResourceLoaderAware {
    
    /** 基础数据类型业务层 */
    @Resource(name = "basicdata.basicDataTypeService")
    private BasicDataTypeService basicDataTypeService;
    
    /** 数据字典业务层 */
    @Resource(name = "basicdata.dataDictService")
    private DataDictService dataDictService;
    
    /** 资源加载器 */
    private ResourceLoader resourceLoader;
    
    /** 编码到类型的映射 */
    private Map<String, BasicDataType> code2typeMap;
    
    /** 方法到编码到页面的映射关联 */
    private Map<BDPageTypeEnum, Map<String, Map<String, String>>> viewtype2package2code2page = new HashMap<>();
    
    /**
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<BasicDataType> bdtList = this.basicDataTypeService.queryList(null,
                true,
                null);
        for (BasicDataType bdt : bdtList) {
            this.code2typeMap.put(bdt.getCode(), bdt);
        }
    }
    
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
    @RequestMapping("/toBasicDataMainframe")
    public String toDataDictMainframe(ModelMap response) {
        //加载数据类型
        return "/basicdata/basicDataMainframe";
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
    @RequestMapping("/toQuery")
    public String toQuery(
            @RequestParam(value = "basicDataTypeCode", required = false) String basicDataTypeCode,
            ModelMap response) {
        BDPageTypeEnum pageType = BDPageTypeEnum.QueryPagedList;
        String pageName = pageType.getDefaultPage();
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            return "/basicdata/queryDataDictPagedList";
        }
        
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<?> type = bdType.getType();
        response.put("basicDataTypeCode", basicDataTypeCode);
        response.put("basicDataType", bdType);
        AssertUtils.notNull(type,
                "type(code={}) is not exist.",
                basicDataTypeCode);
        
        //跳转到查询页
        switch (bdType.getViewType()) {
            case PAGEDLIST:
                pageName = getPageName(bdType, BDPageTypeEnum.QueryPagedList);
                break;
            case TREE:
                pageName = getPageName(bdType, BDPageTypeEnum.QueryTree);
                break;
            case LIST:
            default:
                pageName = getPageName(bdType, BDPageTypeEnum.QueryList);
                break;
        }
        return pageName;
    }
    
    /**
     * 跳转到添加DataDict页面<br/>
     * <功能详细描述>
     * @return [参数说明]
     *
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toAdd")
    public String toAdd(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam Map<String, String> requestMap, ModelMap response) {
        BDPageTypeEnum bdPageType = BDPageTypeEnum.AddBasicData;
        String pageName = bdPageType.defaultPage;
        
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            AssertUtils.isTrue(false,
                    "basicDataTypeCode is error.{}",
                    basicDataTypeCode);
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<?> type = bdType.getType();
        response.put("basicDataTypeCode", basicDataTypeCode);
        response.put("basicDataType", bdType);
        AssertUtils.notNull(type,
                "type(code={}) is not exist.",
                basicDataTypeCode);
        
        BasicData bd = (BasicData) ObjectUtils.newInstance(type);
        response.put("basicData", bd);
        
        pageName = getPageName(bdType, BDPageTypeEnum.AddBasicData);
        return pageName;
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
    @RequestMapping("/toUpdate")
    public String toUpdate(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam("id") String id,
            @RequestParam MultiValueMap<String, String> requestMap,
            ModelMap response) {
        BDPageTypeEnum bdPageType = BDPageTypeEnum.UpdateBasicData;
        String pageName = bdPageType.defaultPage;
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            DataDict resDataDict = this.dataDictService.findById(id);
            AssertUtils.notNull(resDataDict, "resDataDict is null.id:{}", id);
            
            basicDataTypeCode = resDataDict.getBasicDataTypeCode();
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        response.put("basicDataTypeCode", basicDataTypeCode);
        response.put("basicDataType", bdType);
        AssertUtils.notNull(type,
                "type(code={}) is not exist.",
                basicDataTypeCode);
        
        BasicDataService<?> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        BasicData bd = service.findById(id);
        response.put("basicData", bd);
        pageName = getPageName(bdType, bdPageType);
        return pageName;
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
    @ApiOperation(value = "判断编码是否存在", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "basicDataTypeCode", value = "基础数据类型编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "需排除的基础数据id", required = false, dataType = "String") })
    @ResponseBody
    @RequestMapping("/validateCodeIsExist")
    public Map<String, String> validateCodeIsExist(
            @RequestParam(value = "basicDataTypeCode", required = false) String basicDataTypeCode,
            @RequestParam("code") String code,
            @RequestParam(value = "id", required = false) String excludeDataDictId,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, String> key2valueMap = new HashMap<String, String>();
        key2valueMap.put("code", code);
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            boolean flag = this.dataDictService.isExist(basicDataTypeCode,
                    key2valueMap,
                    excludeDataDictId);
            Map<String, String> resMap = new HashMap<String, String>();
            if (!flag) {
                resMap.put("ok", "可用的编码.");
            } else {
                resMap.put("error", "已经存在的编码.");
            }
            return resMap;
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        AssertUtils.notNull(type,
                "type is null.basicDataTypeCode:{}",
                basicDataTypeCode);
        
        BasicDataService<?> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        boolean flag = service.isExist(key2valueMap, excludeDataDictId);
        Map<String, String> resMap = new HashMap<String, String>();
        if (!flag) {
            resMap.put("ok", "可用的编码.");
        } else {
            resMap.put("error", "已经存在的编码.");
        }
        return resMap;
    }
    
    /**
     * 查询DataDict列表<br/>
     * <功能详细描述>
     * @return [参数说明]
     *
     * @return List<DataDict> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/queryList")
    public List<? extends BasicData> queryList(
            @RequestParam(value = "basicDataTypeCode", required = false) String basicDataTypeCode,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestParam(value = "modifyAble", required = false) Boolean modifyAble,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        params.put("modifyAble", modifyAble);
        params.put("code", request.getFirst("code"));
        params.put("name", request.getFirst("name"));
        params.put("remark", request.getFirst("remark"));
        
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            List<DataDict> resList = this.dataDictService
                    .queryList(basicDataTypeCode, valid, params);
            return resList;
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        AssertUtils.notNull(type,
                "type is null.basicDataTypeCode:{}",
                basicDataTypeCode);
        
        BasicDataService<?> service = BasicDataContext.getContext()
                .getBasicDataService(type);
        List<? extends BasicData> resList = service.queryList(valid, params);
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
    public PagedList<? extends BasicData> queryPagedList(
            @RequestParam(value = "basicDataTypeCode", required = false) String basicDataTypeCode,
            @RequestParam(value = "valid", required = false) Boolean valid,
            @RequestParam(value = "modifyAble", required = false) Boolean modifyAble,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam MultiValueMap<String, String> request) {
        Map<String, Object> params = new HashMap<>();
        params.put("modifyAble", modifyAble);
        params.put("code", request.getFirst("code"));
        params.put("name", request.getFirst("name"));
        params.put("remark", request.getFirst("remark"));
        
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            PagedList<DataDict> resPagedList = this.dataDictService
                    .queryPagedList(basicDataTypeCode,
                            valid,
                            params,
                            pageIndex,
                            pageSize);
            return resPagedList;
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        AssertUtils.notNull(type,
                "type is null.basicDataTypeCode:{}",
                basicDataTypeCode);
        
        BasicDataService<? extends BasicData> service = BasicDataContext
                .getContext().getBasicDataService(type);
        PagedList<? extends BasicData> resPagedList = service
                .queryPagedList(valid, params, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * 添加组织结构页面
     * <功能详细描述>
     * @param organization [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/add")
    @ResponseBody
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean add(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam MultiValueMap<String, String> requestMap) {
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            return false;
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        if (type == null) {
            return false;
        }
        
        BasicData obj = ObjectUtils.newInstance(type);
        obj.setModifyAble(true);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            //如果属性存在并且可以写入则进行写入
            if (bw.isWritableProperty(propertyName)
                    && requestMap.containsKey(propertyName)) {
                bw.setPropertyValue(propertyName,
                        requestMap.getFirst(propertyName));
            }
        }
        BasicDataService service = BasicDataContext.getContext()
                .getBasicDataService(type);
        service.insert(obj);
        
        return true;
    }
    
    /**
     * 更新组织<br/>
     * <功能详细描述>
     * @param dataDict
     * @return [参数说明]
     *
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/update")
    @ResponseBody
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean update(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam MultiValueMap<String, String> requestMap) {
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            return false;
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        if (type == null) {
            return false;
        }
        
        BasicData obj = ObjectUtils.newInstance(type);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            String propertyName = pd.getName();
            //如果属性存在并且可以写入则进行写入
            if (bw.isWritableProperty(propertyName)
                    && requestMap.containsKey(propertyName)) {
                bw.setPropertyValue(propertyName,
                        requestMap.getFirst(propertyName));
            }
        }
        BasicDataService service = BasicDataContext.getContext()
                .getBasicDataService(type);
        service.updateById(obj);
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
    @ResponseBody
    @RequestMapping("/deleteById")
    public boolean deleteById(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam(value = "id") String id) {
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            DataDict dd = this.dataDictService.findEntityById(id);
            if (dd == null) {
                return false;
            }
            basicDataTypeCode = dd.getBasicDataTypeCode();
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        AssertUtils.notNull(type,
                "type is null.basicDataTypeCode:{}",
                basicDataTypeCode);
        
        BasicDataService<? extends BasicData> service = BasicDataContext
                .getContext().getBasicDataService(type);
        boolean resFlag = service.deleteById(id);
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
    @ResponseBody
    @RequestMapping("/disableById")
    public boolean disableById(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam(value = "id") String id) {
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            DataDict dd = this.dataDictService.findEntityById(id);
            if (dd == null) {
                return false;
            }
            basicDataTypeCode = dd.getBasicDataTypeCode();
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        AssertUtils.notNull(type,
                "type is null.basicDataTypeCode:{}",
                basicDataTypeCode);
        
        BasicDataService<? extends BasicData> service = BasicDataContext
                .getContext().getBasicDataService(type);
        boolean resFlag = service.disableById(id);
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
    @ResponseBody
    @RequestMapping("/enableById")
    public boolean enableById(
            @RequestParam(value = "basicDataTypeCode") String basicDataTypeCode,
            @RequestParam(value = "id") String id) {
        if (StringUtils.isEmpty(basicDataTypeCode)
                || !this.code2typeMap.containsKey(basicDataTypeCode)) {
            DataDict dd = this.dataDictService.findEntityById(id);
            if (dd == null) {
                return false;
            }
            basicDataTypeCode = dd.getBasicDataTypeCode();
        }
        
        //基础数据类型
        BasicDataType bdType = this.code2typeMap.get(basicDataTypeCode);
        Class<? extends BasicData> type = bdType.getType();
        AssertUtils.notNull(type,
                "type is null.basicDataTypeCode:{}",
                basicDataTypeCode);
        
        BasicDataService<? extends BasicData> service = BasicDataContext
                .getContext().getBasicDataService(type);
        boolean resFlag = service.enableById(id);
        return resFlag;
    }
    
    /**
      * 基础数据页类型<br/>
      * <功能详细描述>
      *
      * @author  Administrator
      * @version  [版本号, 2016年10月10日]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    protected static enum BDPageTypeEnum {
        //增加基础数据
        AddBasicData("add", "/basicdata/addBasicData"),
        
        //更新基础数据
        UpdateBasicData("update", "/basicdata/updateBasicData"),
        
        //查询树列表
        QueryTree("queryTree", "/basicdata/queryBasicDataTree"),
        
        //查询列表
        //如果类型为空的时候，跳转到基础数据列表《分页》
        QueryList("queryList", "/basicdata/queryBasicDataList"),
        
        //查询分页列表
        //如果类型为空的时候，跳转到基础数据列表《分页》
        QueryPagedList("queryPagedList", "/basicdata/queryBasicDataPagedList");
        
        /** 默认页面 */
        private final String pageName;
        
        private final String defaultPage;
        
        /** <默认构造函数> */
        private BDPageTypeEnum(String pageName, String defaultPage) {
            this.pageName = pageName;
            this.defaultPage = defaultPage;
        }
        
        public String getPageName() {
            return pageName;
        }
        
        public String getDefaultPage() {
            return defaultPage;
        }
    }
    
    /**
     * 获取页面名称<br/>
     * <功能详细描述>
     * @param pageType
     * @param basicDataTypeCode
     * @return [参数说明]
     *
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private String getPageName(BasicDataType baisBasicDataType,
            BDPageTypeEnum viewtype) {
        AssertUtils.notNull(baisBasicDataType, "baisBasicDataType is null.");
        AssertUtils.notNull(viewtype, "pageType is null.");
        
        String module = baisBasicDataType.getModule().toLowerCase();
        String code = baisBasicDataType.getCode().toLowerCase();
        AssertUtils.notEmpty(module, "module is empty.");
        AssertUtils.notEmpty(code, "code is empty.");
        
        String page = "";
        if (!viewtype2package2code2page.containsKey(viewtype)) {
            viewtype2package2code2page.put(viewtype,
                    new HashMap<String, Map<String, String>>());
        }
        
        if (!viewtype2package2code2page.get(viewtype).containsKey(module)) {
            viewtype2package2code2page.get(viewtype).put(module,
                    new HashMap<String, String>());
        }
        if (viewtype2package2code2page.get(viewtype)
                .get(module)
                .containsKey(code)) {
            page = viewtype2package2code2page.get(viewtype)
                    .get(module)
                    .get(code);
            return page;
        }
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        sb.append("/").append(module).append("/").append(code).append("/");
        sb.append(viewtype.getPageName());
        page = sb.toString();
        if (!isExistOfJspPage(page)) {
            //如果页面不存在获取其对应的默认页面
            page = viewtype.getDefaultPage();
        }
        viewtype2package2code2page.get(viewtype).get(module).put(code, page);
        return page;
    }
    
    /**
     * 判断Jsp页面是否存在
     * <功能详细描述>
     * @param page
     * @return [参数说明]
     *
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private boolean isExistOfJspPage(String page) {
        StringBuilder jspPageSB = new StringBuilder("/WEB-INF/view");
        jspPageSB.append(page).append(".jsp");
        org.springframework.core.io.Resource jspPageResource = resourceLoader
                .getResource(jspPageSB.toString());
        if (jspPageResource.exists()) {
            return true;
        } else {
            return false;
        }
    }
}