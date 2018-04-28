/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年2月3日
 * <修改描述:>
 */
package com.tx.component.command.context;

/**
 * 获取辅助类实例<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年2月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface Helper<H extends Helper<H>> {
    
    /**
      * 获取辅助类实例<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return H [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public H getHelper();
    
    /**
      * 是否匹配条件支撑<br/> 
      * <功能详细描述>
      * @param params
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean supports(Object... params);
}
