/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年1月8日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support.handler;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.core.Ordered;

import com.tx.component.servicelogger.support.LogArgumentHandler;
import com.tx.component.servicelogger.util.ServiceLoggerUtils;

/**
 * 实现创建时间注入逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SessionAttributesLogArgHandler implements LogArgumentHandler {
    
    /**
     * @param bw
     * @param pd
     * @param td
     * @return
     */
    @Override
    public boolean support(BeanWrapper bw) {
        Map<String, Object> attMap = ServiceLoggerUtils.getAttributes();
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                continue;
            }
            if (attMap.containsKey(pd.getName()) && pd.getPropertyType()
                    .isInstance(attMap.get(pd.getName()))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param bw
     * @param pd
     * @param td
     */
    @Override
    public void handle(BeanWrapper bw) {
        Map<String, Object> attMap = ServiceLoggerUtils.getAttributes();
        for (PropertyDescriptor pd : bw.getPropertyDescriptors()) {
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                continue;
            }
            if (attMap.containsKey(pd.getName()) && pd.getPropertyType()
                    .isInstance(attMap.get(pd.getName()))) {
                if (bw.getPropertyValue(pd.getName()) == null) {
                    //原值为空时才写入值
                    bw.setPropertyValue(pd.getName(), attMap.get(pd.getName()));
                }
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
