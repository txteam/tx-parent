/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年3月22日
 * <修改描述:>
 */
package com.tx.core;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2014年3月22日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class TypeReflectionTest<T> {
    
    /** <默认构造函数> */
    public TypeReflectionTest() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        TypeReflectionTest<Object> t  = new TypeReflectionTest<Object>();
        System.out.println(t.getClass().getGenericSuperclass());
    }
}
