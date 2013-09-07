/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-9-7
 * <修改描述:>
 */
package com.tx.component.basicdata.valuegenerator;


 /**
  * 属性值生成器<br/>
  *     服务于基础数据在实际使用过程中，部分字段值在添加时，不能由客户指定<br/>
  *     需要系统自动生成的情况<br/>
  * <功能详细描述>
  * 
  * @author  PengQingyang
  * @version  [版本号, 2013-9-7]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public interface ValueGenerator<T> {
    
    /**
      * 生成值<br/>
      *<功能详细描述>
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    T generate();
}
