/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年9月21日
 * <修改描述:>
 */
package com.tx.component.file.context;


 /**
  * 文件模块<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年9月21日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface FileModule {
    
    /**
      * 文件归属模块<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String module();
    
    /**
      * 文件存储基础路径<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String path();
    
    /**
      * 前端系统访问路径<br/>
      *     如果不填写，将自动设置为module的路径
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String viewPath();
    
    /**
      * 非必填：可填写：file|..等。也支持自动扩展入的Driver实现<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    String driver();
}
