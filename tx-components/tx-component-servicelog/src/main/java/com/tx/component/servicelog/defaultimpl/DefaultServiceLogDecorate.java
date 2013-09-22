/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import javax.servlet.http.HttpServletRequest;

import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.logger.ServiceLogDecorate;
import com.tx.core.exceptions.util.AssertUtils;

/**
 * 业务日志加工器默认实现
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultServiceLogDecorate implements ServiceLogDecorate {
    
    /**
     * @param srcObj
     * @return
     */
    @Override
    public Object decorate(Object srcObj) {
        AssertUtils.notNull(srcObj, "srcObj is null");
        
        if (srcObj instanceof ServiceLog) {
            ServiceLog other = (ServiceLog) srcObj;
            
        }
        
        return null;
    }
    
    /**
      * 装饰业务日志实例<br/>
      *<功能详细描述>
      * @param logInstance
      * @return [参数说明]
      * 
      * @return Object [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private Object decorateServiceLog(ServiceLog logInstance) {
        logInstance.setClientIpAddress(getClientIpAddress());
        
        //logInstance.setOperatorId(operatorId);
        //logInstance.setOrganizationId(organizationId);
        //logInstance.setVcid(vcid);
        return logInstance;
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
