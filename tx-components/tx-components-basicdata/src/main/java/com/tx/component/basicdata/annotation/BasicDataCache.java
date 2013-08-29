/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-29
 * <修改描述:>
 */
package com.tx.component.basicdata.annotation;

import com.tx.component.basicdata.model.CacheType;


 /**
  * 基础数据缓存<br/>
  *     具有该注解的基础数据将被缓存<br/>
  *     在启动时是否就进行缓存<br/>
  *     如果是枚举型默认不被进行缓存<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-29]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public @interface BasicDataCache {
    
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
    public boolean isCacheOnStartup();
    
    /**
     * 缓存类型<br/>
     *<功能详细描述>
     * @return [参数说明]
     * 
     * @return CacheType [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
   public CacheType cacheType() default CacheType.LRU;
}
