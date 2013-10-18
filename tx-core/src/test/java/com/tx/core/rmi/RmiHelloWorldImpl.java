/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-16
 * <修改描述:>
 */
package com.tx.core.rmi;


 /**
  * 创建rmi服务
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-16]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class RmiHelloWorldImpl implements HelloWorld{
    
    private String name;
    
    public void helloWorld(){
        System.out.println(this.name + " say:helloworld.");
    }

    /** <默认构造函数> */
    public RmiHelloWorldImpl(String name) {
        super();
        this.name = name + (int)(Math.random()*100);
    }
}
