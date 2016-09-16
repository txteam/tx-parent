/*
5 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月6日
 * <修改描述:>
 */
package com.tx.core.support.mqmsg8583.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息8583类型项转换器注册机<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年9月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Msg8583ItemTransferRegistry {
    
    @SuppressWarnings("unused")
    private static final Map<Class<?>, Class<?>> reversePrimitiveMap = new HashMap<Class<?>, Class<?>>() {
        {
            put(Byte.class, byte.class);
            put(Short.class, short.class);
            put(Integer.class, int.class);
            put(Long.class, long.class);
            put(Float.class, float.class);
            put(Double.class, double.class);
            put(Boolean.class, boolean.class);
            put(Character.class, char.class);
        }
    };
    
    private final Map<Class<?>, Msg8583ItemTransfer<?>> TYPE_2_TRANSFER_MAP = new HashMap<Class<?>, Msg8583ItemTransfer<?>>();
    
    //private final Msg8583ItemTransfer<Object> UNKNOWN_TYPE_HANDLER = new UnknownTypeHandler(this);
    //private final Map<Class<?>, m<?>> ALL_TYPE_HANDLERS_MAP = new HashMap<Class<?>, TypeHandler<?>>();
    
    private <T> void register(Class<T> javaType, Msg8583ItemTransfer<?> transfer) {
        if (javaType != null) {
            //Msg8583ItemTransfer<?> transfer = TYPE_2_TRANSFER_MAP.get(javaType);
            //map.put(jdbcType, handler);
            
            if (reversePrimitiveMap.containsKey(javaType)) {
                //register(reversePrimitiveMap.get(javaType), jdbcType, handler);
            }
        }
        //ALL_TYPE_HANDLERS_MAP.put(handler.getClass(), handler);
    }
}
