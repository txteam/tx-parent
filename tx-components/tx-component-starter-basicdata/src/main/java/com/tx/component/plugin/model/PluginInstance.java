/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月4日
 * <修改描述:>
 */
package com.tx.component.plugin.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 插件实例<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PluginInstance implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -5855434622179526250L;
    
    /** 插件实例唯一键 */
    private String id;
    
    /** 归属模块：这个值涉及到是否从其他系统获取插件的配置值，外部系统不支持启动初始化，需在启动前通过其他手段在被调用系统中进行初始化。 */
    private String module;
    
    /** 插件实例唯一名 */
    private String name;
    
    /** 插件版本 */
    private String version;
    
    /** 插件作者 */
    private String author;
    
    /** 插件是否已经进行安装 */
    private boolean installed = false;
    
    /** 是否已启用： 安装完成后需要完成配置后才能够启用，启动期间需要检查配置是否合理，如果为启用的 */
    private boolean valid;
    
    /** 是否支持多个 */
    private boolean multiple;
    
    /** 插件配置前置参数 */
    private String prefix;
    
    /** 最后更新时间 */
    private Date lastUpdateDate;
    
    /** 创建时间 */
    private Date createDate;
    
}
