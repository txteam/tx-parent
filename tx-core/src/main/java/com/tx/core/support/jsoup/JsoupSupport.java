/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-11-20
 * <修改描述:>
 */
package com.tx.core.support.jsoup;

import java.util.List;

import org.jsoup.nodes.Element;

/**
 * jsoup解析接口<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-11-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface JsoupSupport {
    
    /**
      * 将元素节点解析为单个对象实例<br/>
      *<功能详细描述>
      * @param element
      * @param rowMapper
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> T evaluateForObject(Element element, RowMapper<T> rowMapper);
    
    /**
      * 将元素节点解析为一组对象实例集合<br/>
      *<功能详细描述>
     * @param <T>
      * @param element
      * @param rowMapper
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public <T> List<T> evaluateForList(List<Element> elements, RowMapper<T> rowMapper);
}
