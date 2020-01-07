/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年1月8日
 * <修改描述:>
 */
package com.tx.component.servicelogger.support.handler;

import java.beans.PropertyDescriptor;
import java.util.Date;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.core.Ordered;

import com.tx.component.servicelogger.support.LogArgumentHandler;

/**
 * 实现创建时间注入逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年1月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CreateDateLogArgHandler implements LogArgumentHandler {
    
    private static final String CREATE_DATE_FIELD = "createDate";
    
    /**
     * @param bw
     * @param pd
     * @param td
     * @return
     */
    @Override
    public boolean support(BeanWrapper bw) {
        try {
            PropertyDescriptor pd = bw.getPropertyDescriptor(CREATE_DATE_FIELD);
            if (pd.getReadMethod() == null || pd.getWriteMethod() == null) {
                return false;
            }
            if (bw.getPropertyValue(CREATE_DATE_FIELD) == null) {
                return true;
            }
        } catch (InvalidPropertyException e) {
            //do nothing
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
        bw.setPropertyValue(CREATE_DATE_FIELD, new Date());
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }
}
