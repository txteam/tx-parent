/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年6月3日
 * <修改描述:>
 */
package com.tx.core.querier.model;

import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.querier.QuerierConstants;
import com.tx.core.util.JPAParseUtils.JPAColumnInfo;

/**
 * 查询条件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryOrderItem {
    
    /** 搜索属性 */
    private final String column;
    
    /** 排序方向 */
    private final String direction;
    
    /** <默认构造函数> */
    public QueryOrderItem(JPAColumnInfo column, OrderDirectionEnum direction) {
        super();
        AssertUtils.notNull(column, "column is null.");
        AssertUtils.notEmpty(column.getColumnName(),
                "column.columnName is empty.");
        
        this.column = column.getColumnName();
        this.direction = direction == null
                ? QuerierConstants.DEFAULT_DIRECTION.toString()
                : direction.toString();
    }
    
    /**
     * @return 返回 column
     */
    public String getColumn() {
        return column;
    }
    
    /**
     * @return 返回 direction
     */
    public String getDirection() {
        return direction;
    }
}
