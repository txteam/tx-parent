/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月6日
 * <修改描述:>
 */
package com.tx.core.jdbc.model;


 /**
  * 查询条件类型<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月6日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public enum QueryConditionTypeEnum {
    EQUAL,
    GREATER,
    GREATER_OR_EQUAL,
    LESS,
    LESS_OR_EQUAL,
    LIKE,
    LIKE_AFTER,
    LIKE_BEFORE,
    UNEQUAL;
}
