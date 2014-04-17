/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年4月17日
 * <修改描述:>
 */
package com.tx.component.rule.loader.xml.model;

import java.io.File;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年4月17日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XStreamConverter(FileParamConverter.class)
@XStreamAlias("file")
public class FileParam {
    
    /** byte参数key */
    @XStreamAsAttribute
    private String key;
    
    /** 对应文件路径:classpath */
    private File value;

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
    public File getValue() {
        return value;
    }

    /**
     * @param 对value进行赋值
     */
    public void setValue(File value) {
        this.value = value;
    }
}
