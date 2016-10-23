/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月24日
 * <修改描述:>
 */
package com.tx.core.model;

/**
 * 因Spring中Ordered接口，中getOrder涉及字段一般为数据库中关键字所以提供该类去支撑此类业务逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface OrderedSupport {
    
    /**
     * 用Integer常量中的最小值作为最高优先级<br/>
     * @see java.lang.Integer#MIN_VALUE
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
    
    /**
     * 用Integer常量中的最大值作为最低优先级<br/>
     * @see java.lang.Integer#MAX_VALUE
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;
    
    /**
      * 获取对象的排序值索引<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    int getOrderPriority();
}
