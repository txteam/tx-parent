///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2014年6月20日
// * <修改描述:>
// */
//package com.tx.component.task.controller;
//
//import java.util.HashMap;
//import java.util.List;
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
//import com.tx.component.task.model.TaskDef;
//import com.tx.component.task.model.TaskDetail;
//import com.tx.component.task.model.TaskResultEnum;
//import com.tx.component.task.model.TaskStatus;
//import com.tx.component.task.model.TaskStatusEnum;
//import com.tx.component.task.service.TaskDetailService;
//import com.tx.component.task.service.TaskStatusService;
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 任务容器控制器<br/>
// * <功能详细描述>
// * 
// * @author  Administrator
// * @version  [版本号, 2014年6月20日]
// * @see  [相关类/方法]
// * @since  [产品/模块版本]
// */
//@Controller("taskDetailController")
//@RequestMapping("/taskDetail")
//public class TaskDetailController {
//    
//    /**
//      * 跳转到查询任务定义列表页面<br/>
//      * <功能详细描述>
//      * @return [参数说明]
//      * 
//      * @return String [返回类型说明]
//      * @exception throws [异常类型] [异常说明]
//      * @see [类、类#方法、类#成员]
//     */
//    @RequestMapping("/toQueryTaskDetailList")
//    public String toQueryTaskDetailList(@RequestParam(value = "parentCode", required = false) String parentCode,
//            ModelMap response) {
//        response.put("parentCode", parentCode);
//        return "/task/queryTaskDetailList";
//    }
//    
//    /**
//     * 查询任务定义<br/>
//     * <功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return List<TaskDefinition> [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//     */
//    @ResponseBody
//    @RequestMapping("/queryTaskDetailList")
//    public List<TaskDetail> queryTaskDetailList(@RequestParam(value = "parentCode", required = false) String parentCode,
//            @RequestParam Map<String, String> request) {
//        Map<String, Object> params = new HashMap<>();
//        if (!MapUtils.isEmpty(params)) {
//            for (Entry<String, String> entryTemp : request.entrySet()) {
//                params.put(entryTemp.getKey(), entryTemp.getValue());
//            }
//        }
//        params.put("parentCode", parentCode);
//        List<String> taskIds = TaskContext.getContext().getTaskContextRegistry().getTaskIds();
//        params.put("taskIds", taskIds);
//        
//        TaskDetailService taskDetailService = TaskContext.getContext().getTaskDetailService();
//        List<TaskDetail> taskDetailList = taskDetailService.queryList(params);
//        
//        return taskDetailList;
//    }
//    
//    /**
//     * 是否正在执行<br/>
//     * <功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @ResponseBody
//    @RequestMapping("/isExecuting")
//    public boolean isExecuting(@RequestParam("taskId") String taskId) {
//        TaskDef taskDef = TaskContext.getContext().getTaskContextRegistry().getTaskDefById(taskId);
//        AssertUtils.notNull(taskDef, "taskDef:{} is null.", taskId);
//        AssertUtils.isTrue(taskDef.isExecutable(), "task:{} is not executable.", taskId);
//        
//        TaskStatusService taskStatusService = TaskContext.getContext().getTaskStatusService();
//        TaskStatus taskStatus = taskStatusService.findByTaskId(taskId);
//        AssertUtils.notNull(taskStatus, "taskDef:{} is null.", taskId);
//        
//        if (TaskStatusEnum.WAIT_EXECUTE.equals(taskStatus.getStatus())) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//    
//    /**
//     * 是否正在执行<br/>
//     * <功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @ResponseBody
//    @RequestMapping("/execute")
//    public boolean execute(@RequestParam("taskId") String taskId) {
//        TaskDef taskDef = TaskContext.getContext().getTaskContextRegistry().getTaskDefById(taskId);
//        if (taskDef == null) {
//            return false;
//        }
//        if (!taskDef.isExecutable()) {
//            return false;
//        }
//        if (!isExecuting(taskId)) {
//            return false;
//        }
//        TaskContext.getContext().getTaskContextRegistry().executeByTaskId(taskId);
//        return true;
//    }
//    
//    /**
//     * 是否正在执行<br/>
//     * <功能详细描述>
//     * @return [参数说明]
//     * 
//     * @return boolean [返回类型说明]
//     * @exception throws [异常类型] [异常说明]
//     * @see [类、类#方法、类#成员]
//    */
//    @ResponseBody
//    @RequestMapping("/isSuccess")
//    public boolean isSuccess(@RequestParam("taskId") String taskId) {
//        TaskDef taskDef = TaskContext.getContext().getTaskContextRegistry().getTaskDefById(taskId);
//        if (taskDef == null) {
//            return false;
//        }
//        if (!taskDef.isExecutable()) {
//            return false;
//        }
//        
//        TaskStatusService taskStatusService = TaskContext.getContext().getTaskStatusService();
//        TaskStatus taskStatus = taskStatusService.findByTaskId(taskId);
//        AssertUtils.notNull(taskStatus, "taskDef:{} is null.", taskId);
//        
//        if (TaskStatusEnum.EXECUTING.equals(taskStatus.getStatus())) {
//            return false;
//        } else {
//            if (taskStatus.getResult() == null || TaskResultEnum.SUCCESS.equals(taskStatus.getResult())) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//}
