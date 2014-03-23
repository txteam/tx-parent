/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2014-3-18
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml.model;

import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;


 /**
  * byte类型参数
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2014-3-18]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamConverter(ByteParamConverter.class)
@XStreamAlias("byte")
public class ByteParam {
    
    /** byte参数key */
    @XStreamAsAttribute
    private String key;
    
    private Resource value;

    /**
     * @return 返回 key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param 对key进行赋值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return 返回 value
     */
    public Resource getValue() {
        return value;
    }

    /**
     * @param 对value进行赋值
     */
    public void setValue(Resource value) {
        this.value = value;
    }
}
