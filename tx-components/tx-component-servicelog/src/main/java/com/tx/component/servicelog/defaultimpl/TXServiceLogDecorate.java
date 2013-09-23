/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.component.servicelog.defaultimpl;

import com.tx.component.servicelog.context.ServiceLoggerSessionContext;
import com.tx.component.servicelog.exception.UnsupportServiceLoggerTypeException;
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
public class TXServiceLogDecorate implements ServiceLogDecorate {
    
    /**
     * @param srcObj
     * @return
     */
    @Override
    public Object decorate(Object srcObj) {
        AssertUtils.notNull(srcObj, "srcObj is null");
        
        if (srcObj instanceof TXServiceLog) {
            TXServiceLog other = (TXServiceLog) srcObj;
            Object res = decorateServiceLog(other);
            return res;
        } else {
            throw new UnsupportServiceLoggerTypeException(
                    "srcObject:{} not support.", new Object[] { srcObj });
        }
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
    private Object decorateServiceLog(TXServiceLog logInstance) {
        ServiceLoggerSessionContext context = ServiceLoggerSessionContext.getContext();
        
        logInstance.setClientIpAddress((String) context.getAttribute("clientIpAddress"));
        logInstance.setOperatorId((String) context.getAttribute("operatorId"));
        logInstance.setOrganizationId((String) context.getAttribute("organizationId"));
        logInstance.setVcid((String) context.getAttribute("vcidId"));
        return logInstance;
    }
    
}
