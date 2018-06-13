/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2012-12-1
 * <修改描述:>
 */
package com.tx.component.auth.springmvc;


 /**
  * <无权限时的处理器>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-12-1]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface NoDataRowAuthProcessor {
    
    /**
     * 显示为空
     */
    String TYPE_SHOWEMPTY = "showEmpty";
    
    /**
     * 遮罩部分信息
     */
    String TYPE_MASKINFO = "maskInfo";
    
    /**
      * 权限处理器名
      * 具有列注解时发现无对应权限
      * 其对应的处理名
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String type();
    
    /**
      * 处理字符串
      * 
      * @param waitProcesseStr
      * @param parameters
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String process(String waitProcesseStr,String... parameters);
}
