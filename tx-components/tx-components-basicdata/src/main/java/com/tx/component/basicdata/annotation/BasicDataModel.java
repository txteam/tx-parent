/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;


 /**
  * 基础数据模型<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public @interface BasicDataModel {
    
    /**
     * 是否进行数据缓存<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public boolean isCache() default true;
    
    /**
      * 是否在启动时就进行缓存<br/>
      *     如果isCache为false，该配置则无效
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public boolean isCacheOnStartup() default false;
    
    public String querySql();
    
    public String insertSql();
    
    public String deleteSql();
    
    public String updateSql();
}
