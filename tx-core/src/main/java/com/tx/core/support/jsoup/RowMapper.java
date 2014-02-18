/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-11-20
 * <修改描述:>
 */
package com.tx.core.support.jsoup;


import org.jsoup.nodes.Element;

import com.tx.core.support.jsoup.exception.JsoupParseException;

/**
 * 元数据映射接口<br/>
 * <功能详细描述>
 * 
 * @author  wanxin
 * @version  [版本号, 2013-11-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface RowMapper<T> {
    
    /**
      * element与对象实例的映射处理类
      * <功能详细描述>
      * @param element
      * @param rowNum
      * @return
      * @throws SQLException [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    T mapRow(Element element, int rowNum) throws JsoupParseException;
}
