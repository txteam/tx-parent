/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年7月15日
 * <修改描述:>
 */
package com.tx.core.lang.enums;

/**
 * 基础枚举类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年7月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Deprecated
public interface BaseSimpleEnum {
    
    /**
      * Key值
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getKey();
    
    /**
      * 名称<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getName();
}
