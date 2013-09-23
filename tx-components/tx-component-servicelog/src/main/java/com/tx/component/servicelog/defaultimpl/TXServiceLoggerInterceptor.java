/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-23
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.interceptor.BaseServiceLoggerInterceptor;

/**
 * 业务日志容器拦截器，负责向业务日志容器中写入初始化的一些业务日志信息<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TXServiceLoggerInterceptor extends BaseServiceLoggerInterceptor {
    
    /**
     * @return
     */
    @Override
    protected Map<String, Object> initAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();
        
        attributes.put("clientIpAddress", getClientIpAddress());
        attributes.put("vcid", getVcid());
        attributes.put("organizationId", getOrganizationId());
        attributes.put("operatorId", getOperatorId());
        return attributes;
    }
    
    /**
      * 获取操作员id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String getOperatorId() {
        ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
        
        if (context.getRequest() == null
                || context.getRequest().getSession(false) == null) {
            return "";
        } else {
            HttpSession session = context.getRequest().getSession(false);
            
            Object operatorIdObj = session.getAttribute("operatorId");
            if (operatorIdObj != null && operatorIdObj instanceof String) {
                String operatorId = (String) operatorIdObj;
                return operatorId;
            } else {
                return "";
            }
        }
    }
    
    /**
      * 获取当前组织id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String getOrganizationId() {
        ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
        
        if (context.getRequest() == null
                || context.getRequest().getSession(false) == null) {
            return "";
        } else {
            HttpSession session = context.getRequest().getSession(false);
            
            Object orgIdObj = session.getAttribute("organizationId");
            if (orgIdObj != null && orgIdObj instanceof String) {
                String organizationId = (String) orgIdObj;
                return organizationId;
            } else {
                return "";
            }
        }
    }
    
    /**
      * 获取当前虚中心id
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private String getVcid() {
        ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
        
        if (context.getRequest() == null
                || context.getRequest().getSession(false) == null) {
            return "";
        } else {
            HttpSession session = context.getRequest().getSession(false);
            
            Object vcidObj = session.getAttribute("vcid");
            if (vcidObj != null && vcidObj instanceof String) {
                String vcid = (String) vcidObj;
                return vcid;
            } else {
                return "";
            }
        }
    }
    
    /**
     * 获取调用客户端ip<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private String getClientIpAddress() {
        ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
        if (context.getRequest() == null) {
            return "";
        } else {
            HttpServletRequest request = context.getRequest();
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }
    }
    
}
