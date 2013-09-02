/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-14
 * <修改描述:>
 */
package com.tx.component.basicdata.context.impl;


 /**
  * 基础数据容器<br/>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-8-14]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class BasicDataContext {
    
    /** 基础数据容器单例 */
    private static BasicDataContext context = new BasicDataContext();

    /** <默认构造函数> */
    private BasicDataContext() {
        super();
    }
    
    /**
      * 包内可见的实例化方法<br/> 
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return BasicDataContext [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    static BasicDataContext newInstance(){
        return context;
    }
    
    
}
