/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.mqmsg8583.context;

import com.tx.core.model.ParameterizedTypeReference;

/**
 * 消息项转换器子项<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AbstractMsgItemTransfer<T> extends ParameterizedTypeReference<T>
        implements MsgItemTransfer<T> {
    
    protected Msg8583TransferConfigurator configurator;
    
    /**
     * @param configurator
     */
    @Override
    public void setConfigurator(Msg8583TransferConfigurator configurator) {
        this.configurator = configurator;
    }
    
    /**
     * @param sb
     * @param parameter
     * @param start
     * @param length
     */
    @Override
    public void marshal(StringBuffer sb, T parameter, int start, int length) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param sb
     * @param start
     * @param length
     * @return
     */
    @Override
    public T unmarshal(StringBuffer sb, int start, int length) {
        // TODO Auto-generated method stub
        return null;
    }
}
