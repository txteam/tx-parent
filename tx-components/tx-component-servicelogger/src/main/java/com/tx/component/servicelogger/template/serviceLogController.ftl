/*
 * 描述: 业务日志ServiceLog:${jpaMetaClass.entitySimpleName}
 * 修改人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${jpaMetaClass.modulePackageName}.servicelog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${jpaMetaClass.modulePackageName}.servicelog.${jpaMetaClass.entitySimpleName};
import com.tx.component.servicelog.context.ServiceLoggerContext;
import com.tx.core.paged.model.PagedList;

/**
 * ${jpaMetaClass.entitySimpleName}查询显示逻辑
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller("${StringUtils.uncapitalize('${jpaMetaClass.entitySimpleName}')}Controller")
@RequestMapping("/servicelog/${jpaMetaClass.modulePackageSimpleName}/${StringUtils.uncapitalize('${jpaMetaClass.entitySimpleName}')}")
public class ${jpaMetaClass.entitySimpleName}Controller {
    
    /**
     * 跳转到查询${jpaMetaClass.entitySimpleName}日志页面
     * <功能详细描述>
     *
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQuery${jpaMetaClass.entitySimpleName}PagedList")
    public String toQuery${jpaMetaClass.entitySimpleName}PagedList() {
        return "/mainframe/query${jpaMetaClass.entitySimpleName}PagedList";
    }
    
    /**
     * 分页查询${jpaMetaClass.entitySimpleName}日志<br/>
     * <功能详细描述>
     *
     * @return [参数说明]
     * 
     * @return PagedList<LoginLog> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/query${jpaMetaClass.entitySimpleName}PagedList")
    public PagedList<${jpaMetaClass.entitySimpleName}> query${jpaMetaClass.entitySimpleName}PagedList(
            @RequestParam MultiValueMap<String, String> requestParameters,
            @RequestParam(value = "minCreateDate", required = false) Date minCreateDate,
            @RequestParam(value = "maxCreateDate", required = false) Date maxCreateDate,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("minCreateDate", minCreateDate);
        params.put("maxCreateDate", maxCreateDate);
        
        PagedList<${jpaMetaClass.entitySimpleName}> resPagedList = ServiceLoggerContext.getLogger(${jpaMetaClass.entitySimpleName}.class)
                .queryPagedList(params, pageIndex, pageSize);
        return resPagedList;
    }
    
    /**
     * 处理返回数据<br/>
     * <功能详细描述>
     * 
     * @param resPagedList
     * @return [参数说明]
     * 
     * @return PagedList<LoginLog> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    protected PagedList<${jpaMetaClass.entitySimpleName}> filter(PagedList<${jpaMetaClass.entitySimpleName}> resPagedList) {
        return resPagedList;
    }
}
