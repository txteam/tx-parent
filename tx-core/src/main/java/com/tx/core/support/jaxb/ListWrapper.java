/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月25日
 * <修改描述:>
 */
package com.tx.core.support.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;


 /**
  * List集合的包装类，用以辅助解析List对象<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年9月25日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper<T> {
    
    @XmlAnyElement(lax=true)
    private List<T> items;
    
    /** <默认构造函数> */
    public ListWrapper() {
        items = new ArrayList<T>();
    }
 
    /** <默认构造函数> */
    public ListWrapper(List<T> items) {
        this.items = items;
    }

    /**
     * @return 返回 items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * @param 对items进行赋值
     */
    public void setItems(List<T> items) {
        this.items = items;
    }
}
