/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-10-10
 * <修改描述:>
 */
package com.tx.component.basicdata.testmodel;

import java.lang.reflect.Modifier;


 /**
  * <功能简述>
  * <功能详细描述>
  * 
  * @author  brady
  * @version  [版本号, 2013-10-10]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class Main {
    public static void main(String[] args) {
        System.out.println(Modifier.isInterface(AuthItem.class.getModifiers()));
        
        System.out.println(Modifier.isPublic(AuthItem.class.getModifiers()));
        
        System.out.println(Modifier.isPrivate(AuthItem.class.getModifiers()));
        
        System.out.println(Modifier.isAbstract(AuthItem.class.getModifiers()));
        
        System.out.println(Modifier.isAbstract(AuthItemImpl.class.getModifiers()));
    }
}
