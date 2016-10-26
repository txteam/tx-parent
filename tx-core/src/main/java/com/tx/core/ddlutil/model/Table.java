package com.tx.core.ddlutil.model;

import java.util.List;

/**
  * 获取表定义<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年10月24日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface Table {
    
    /**
     * @return 返回 name
     */
    public abstract String getTableName();
    
    /**
     * @return 返回 comment
     */
    public abstract String getComment();
    
    /**
     * @return 返回 columns
     */
    public abstract List<? extends TableColumn> getColumns();
    
    /**
     * @return 返回 indexes
     */
    public abstract List<? extends TableIndex> getIndexes();
    
}