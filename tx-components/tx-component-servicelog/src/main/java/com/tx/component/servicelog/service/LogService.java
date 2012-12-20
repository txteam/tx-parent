package com.tx.component.servicelog.service;
//package com.boda.components.servicelog.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.boda.domain.system.HandleLog;
//import com.boda.ios.log.dao.LoggerDao;
//import com.boda.ios.log.exception.LoggerException;
//import com.boda.ios.log.model.LogLoggingOn;
//import com.core.service.CRUDSupport;
//import com.core.util.DateUtil;
//import com.core.util.StringUtil;
//
//@Component("loggerService")
//public class LogService {
//  private Logger logger = LoggerFactory.getLogger(LogService.class);
//
//  @Resource(name = "loggerDao")
//  private LoggerDao loggerDao;
//
//  /**
//   * 查询业务日志
//   * 
//   * @author liujun
//   * @param handle传入查询的参数
//   * @param page页数
//   * @param limit每页显示多少条
//   * */
//  public CRUDSupport<HandleLog> getHandleLog(HandleLog handle, Integer page,
//          Integer limit) {
//      List<Criterion> criterions = new ArrayList<Criterion>();
//
//      if (!StringUtil.isEmpty(handle.getMenuId())) {
//          criterions.add(org.hibernate.criterion.Restrictions.eq("menuId",
//                  handle.getMenuId()));
//      }
//
//      if (!StringUtil.isEmpty(handle.getEmpId())) {
//          criterions.add(org.hibernate.criterion.Restrictions.eq("empId",
//                  handle.getEmpId()));
//      }
//
//      if (!StringUtil.isEmpty(handle.getAutId())) {
//          criterions.add(org.hibernate.criterion.Restrictions.eq("autId",
//                  handle.getAutId()));
//      }
//
//      if (!StringUtil.isEmpty(handle.getContext())) {
//          criterions.add(org.hibernate.criterion.Restrictions.like("context",
//                  handle.getContext()));
//      }
//
//      if (!StringUtil.isEmpty(handle.getStartTime())) {
//          criterions.add(org.hibernate.criterion.Restrictions.lt("startTime",
//                  handle.getStartTime()));
//      }
//
//      if (!StringUtil.isEmpty(handle.getEndTime())) {
//          criterions.add(org.hibernate.criterion.Restrictions.gt("endTime",
//                  handle.getEndTime()));
//      }
//
//      List<Order> orders = new ArrayList<Order>();
//      orders.add(Order.desc("date"));
//      return loggerDao.queryHandleLog(criterions, orders, page, limit);
//  }
//
//  /**
//   * PRO 刘军 注释捏?
//   * <功能简述> <功能详细描述>
//   * 
//   * @param handleLog
//   *            [参数说明]
//   * 
//   * @return void [返回类型说明]
//   * @exception throws [异常类型] [异常说明]
//   * @see [类、类#方法、类#成员]
//   */
//  @Transactional
//  public void insertHandleLog(HandleLog handleLog) {
//      try {
//          loggerDao.insertHandleLog(handleLog);
//      } catch (Exception e) {
//          logger.error(e.toString(), e);
//          throw new LoggerException(e);
//      }
//  }
//
//  public CRUDSupport<LogLoggingOn> queryLogLoggingOn(String empId,
//          String startTime, String endTime, int page, int limit)
//          throws Exception {
//      List<Criterion> criterions = new ArrayList<Criterion>();
//
//      if (!StringUtil.isEmpty(empId)) {
//          criterions.add(Restrictions.eq("employeeid", empId));
//      }
//
//      if (!StringUtil.isEmpty(startTime)) {
//          criterions.add(Restrictions.ge("logintime",
//                  DateUtil.strToDate(startTime, "yyyy-MM-dd")));
//      }
//
//      if (!StringUtil.isEmpty(endTime)) {
//          criterions.add(Restrictions.le("logintime",
//                  DateUtil.strToDate(endTime, "yyyy-MM-dd")));
//      }
//
//      List<Order> orders = new ArrayList<Order>();
//      CRUDSupport<LogLoggingOn> support = loggerDao.queryLogLoggingOn(
//              criterions, orders, page, limit);
//      return support;
//  }
//}
