/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年6月20日
 * <修改描述:>
 */
package com.tx.core.jdbc.tableview;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.jdbc.tableview.annotation.TableView;
import com.tx.core.reflection.JpaMetaClass;


 /**
  * 表视图定义对象<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年6月20日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TableViewDescriptor<T> {
    
    private Class<T> type;
    
    private String tableViewName;
    
    private JpaMetaClass<T> jpaMetaClass;

    /** <默认构造函数> */
    public TableViewDescriptor(Class<T> type) {
        super();
        AssertUtils.notNull(type,"type is null.");
        AssertUtils.isTrue(type.isAnnotationPresent(TableView.class),"tableViewAnno is not present.");
        TableView tableViewAnno = type.getAnnotation(TableView.class);
        this.type = type;
        this.tableViewName = tableViewAnno.tableViewName();
    }
    
    
    
    
    
}
