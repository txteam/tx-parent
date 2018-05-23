///*
// * 描          述:  业务日志ServiceLog:TaskExecuteLog
// * 修  改   人:  
// * 修改时间:  
// * <修改描述:>
// */
//package com.tx.component.task.controller;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.collections.MapUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.tx.component.task.context.TaskContext;
//import com.tx.component.task.model.TaskExecuteLog;
//import com.tx.component.task.service.TaskExecuteLogService;
//import com.tx.core.paged.model.PagedList;
//
///**
// * TaskExecuteLog查询显示逻辑
// * <功能详细描述>
// * 
// * @author  brady
// * @version  [版本号, 2013-10-8]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Controller("taskExecuteLogController")
//@RequestMapping("/taskExecuteLog")
//public class TaskExecuteLogController {
//    
//    /**
//      * 跳转到查询TaskExecuteLog日志页面
//      *<功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @RequestMapping("/toQueryTaskExecuteLogPagedList")
//    public String toQueryTaskExecuteLogPagedList(
//            @RequestParam(value = "parentCode", required = false) String parentCode, ModelMap response) {
//        response.put("parentCode", parentCode);
//        return "/task/queryTaskExecuteLogPagedList";
//    }
//    
//    /**
//      * 分页查询TaskExecuteLog日志<br/>
//      * <功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return PagedList<LoginLog> [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @ResponseBody
//    @RequestMapping("/queryTaskExecuteLogPagedList")
//    public PagedList<TaskExecuteLog> queryTaskExecuteLogPagedList(
//            @RequestParam(value = "parentCode", required = false) String parentCode,
//            @RequestParam Map<String, String> request,
//            @RequestParam(value = "minCreateDate", required = false) Date minCreateDate,
//            @RequestParam(value = "maxCreateDate", required = false) Date maxCreateDate,
//            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageIndex,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
//        Map<String, Object> params = new HashMap<>();
//        if (!MapUtils.isEmpty(params)) {
//            for (Entry<String, String> entryTemp : request.entrySet()) {
//                params.put(entryTemp.getKey(), entryTemp.getValue());
//            }
//        }
//        params.put("parentCode", parentCode);
//        
//        TaskExecuteLogService logService = TaskContext.getContext().getTaskExecuteLogService();
//        PagedList<TaskExecuteLog> resPagedList = logService.queryPagedList(params, pageIndex, pageSize);
//        return resPagedList;
//    }
//}
