/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月2日
 * <修改描述:>
 */
package com.tx.component.command.context;

/**
 * 定义交易记录类型<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TradingRecordType {
    
    /**
      * 获取交易记录类型关键字Key
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getKey();
    
    /**
      * 获取交易记录类型名称Name
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public String getName();
}
