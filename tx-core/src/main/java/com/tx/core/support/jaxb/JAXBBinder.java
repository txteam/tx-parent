/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月25日
 * <修改描述:>
 */
package com.tx.core.support.jaxb;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * JAXBBinder对象<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JAXBBinder {
    
    private Marshaller marshaller;
    
    private Unmarshaller unmarshaller;
    
    public String toXML(Object obj) {
        return null;
    }
    
    public <T> T fromXML(String xml, Class<T> type) {
        return null;
    }
}
