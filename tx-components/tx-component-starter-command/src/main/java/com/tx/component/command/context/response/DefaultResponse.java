package com.tx.component.command.context.response;

import java.util.HashMap;
import java.util.Map;

import com.tx.component.command.context.CommandResponse;
import com.tx.core.TxConstants;

/**
  * 交易默认返回值对象<br/>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年11月30日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class DefaultResponse implements CommandResponse {
    
    private final Map<String, Object> attributes = new HashMap<String, Object>(
            TxConstants.INITIAL_MAP_SIZE);
    
    /**
     * @param key
     * @return
     */
    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
    
    /**
     * @param key
     * @param value
     */
    @Override
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }
}
