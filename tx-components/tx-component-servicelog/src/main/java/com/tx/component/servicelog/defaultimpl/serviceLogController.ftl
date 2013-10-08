/*
 * 描          述:  业务日志ServiceLog:${entitySimpleName}
 * 修  改   人:  
 * 修改时间:  
 * <修改描述:>
 */
package ${modulePackageName}.servicelog.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${modulePackageName}.servicelog.${entitySimpleName};
import com.tx.component.servicelog.context.ServiceLoggerContext;
import com.tx.core.paged.model.PagedList;

/**
 * LoginLog查询显示逻辑
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-10-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller("loginLogController")
@RequestMapping("/servicelog/${moduleSimpleName}/${entitySimpleName}")
public class ${entitySimpleName}Controller {
    
    /**
      * 跳转到查询登录日志页面
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/toQuery${entitySimpleName}")
    public String toQuery${entitySimpleName}() {
        return "/mainframe/query${entitySimpleName}";
    }
    
    /**
      * 分页查询登录日志<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return PagedList<LoginLog> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    @ResponseBody
    @RequestMapping("/query${entitySimpleName}PagedList")
    public PagedList<LoginLog> query${entitySimpleName}PagedList(Date minCreateDate,
            Date maxCreateDate, int pageIndex, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("minCreateDate", minCreateDate);
        params.put("maxCreateDate", maxCreateDate);
        
        PagedList<${entitySimpleName}> resPagedList = ServiceLoggerContext.getLogger(${entitySimpleName}.class)
                .queryPagedList(params, pageIndex, pageSize);
                
        return resPagedList;
    }
}
