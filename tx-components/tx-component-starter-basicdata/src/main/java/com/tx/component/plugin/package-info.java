/**
 * 插件容器设计期间，考虑过多实例的问题，但后来还是依然坚持不实现一个类实现多赢多个instance的实践
 * 一般需要多实例的场景经过反复推敲,其实完全可以通过插件实例的代码中去实现兼容多个配置的情形。
 * 例如一个项目中，需要不同的虚中心，或是租户，使用自己账号配置的微信appid进行登录
 * 这个时候可以实现插件x,或完全不用插件的逻辑，进行实现。
 * 
 * 插件容器的从设计之初，仅仅服务于插件配置
 *    只是对单例插件，一插件一配置的这种插件提供配置支持而已，并未提供额外更多的功能.
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2019年12月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
package com.tx.component.plugin;