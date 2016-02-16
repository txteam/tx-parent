/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月15日
 * <修改描述:>
 */
package com.tx.component.communication.template;

import java.util.Map;
import java.util.Set;

/**
 * 消息模板<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MessageTemplate {
    
    /**
      * 获取模板Key集合<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String[] templateKeys();
    
    /**
      * 获取模板Key集合<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return Set<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Set<String> getTemplateKeySet();
    
    /**
      * 判断模板是否包含指定Key值<br/>
      * <功能详细描述>
      * @param templateKey
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean containsKey(String templateKey);
    
    /**
     * 根据内容获取
     * <功能详细描述>
     * @param content
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public String getTemplateKeyByContent(String content);
    
    /**
      * 获取转换后的消息内容<br/>
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getTransferedContent(String templateKey,
            Map<String, Object> params);
    
}
