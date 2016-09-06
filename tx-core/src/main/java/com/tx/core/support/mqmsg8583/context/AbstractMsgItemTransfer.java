/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.mqmsg8583.context;

import org.apache.commons.lang3.StringUtils;

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
public abstract class AbstractMsgItemTransfer<T> extends
        ParameterizedTypeReference<T> implements Msg8583ItemTransfer<T> {
    
    /** 消息项转换配置器 */
    protected Msg8583TransferConfigurator configurator;
    
    /**
     * @param configurator
     */
    @Override
    public final void setConfigurator(Msg8583TransferConfigurator configurator) {
        this.configurator = configurator;
    }
    
    /**
     * @param sb
     * @param parameter
     * @param start
     * @param length
     */
    @Override
    public final void marshal(StringBuffer sb, T parameter, int offset,
            int length) {
        String parameterTarget = doMarshal(parameter, length);
        sb.insert(offset, parameterTarget);
    }
    
    /**
      * 将指定类型转换为字符串<br/>
      * <功能详细描述>
      * @param parameter
      * @param length
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract String doMarshal(T parameter, int length);
    
    /**
     * @param sb
     * @param start
     * @param length
     * @return
     */
    @Override
    public T unmarshal(StringBuffer sb, int start, int length) {
        String parameter = null;
        if (sb.length() >= start && sb.length() >= start + length) {
            parameter = sb.substring(start, start + length);
        }else if(sb.length() >= start){
            parameter = sb.substring(start, sb.length());
        }else{
            parameter = null;
        }
        T res = doUnmarshal(parameter);
        return res;
    }
    
    /**
      * 将字符串解析为指定的对象<br/>
      * <功能详细描述>
      * @param parameter
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected abstract T doUnmarshal(String parameter);
}
