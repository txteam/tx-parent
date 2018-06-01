/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月1日
 * <修改描述:>
 */

/**
 * 写入session的信息一般分为两种<br/>
 *    一种： 登录成功后即写入，登出后销毁
 *    第二种： 临时写入（该种在实际使用场景中已经比较少了）
 *    
 * 第一种：如果在首次写入本地session时，向远端session的redis实现同时写入一份session即可
 *    当session被使用时，并且上次同步时间已经超过远端session失效时间时，自动更新远端的session失效时间即可
 *    如果修改成功，则修改本地session的同步时间记录
 *    当一台新的服务被客户访问时，该机器上不存在客户的session时，即可通过token从redis中获取session
 * 第二种：实时的通过redis的session实现即可
 *    
 * 在此类实现中远端的session可以直接考虑使用spring-session即可
 *    还需要考虑jwt的实现
 *    同时考虑spring security.oauth2.的使用。 
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
package com.tx.component.session;