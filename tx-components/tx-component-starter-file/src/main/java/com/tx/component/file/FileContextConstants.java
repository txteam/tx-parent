/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月13日
 * <修改描述:>
 */
package com.tx.component.file;

/**
 * 文件容器常量<br/>
 *    暂只支持oss存储
 *    以及本地存储
 *    如果需要:
 *     未来可以增加七牛云，FastDFS的能力支撑
 *     
 * 设计实现过程中考虑记录：
 *    放弃在文件容器中提供缓存逻辑，首先fileDefintion对象是高频使用的，也可能经常新增内容
 *    其次，如果需要缓存的场景，让对应的对象存储url即可实现
 *    再然后真需要缓存可以在对应模块进行实现即可
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface FileContextConstants {
    
    /** 文件容器配置 */
    String PROPERTIES_PREFIX = "tx.component.file";
    
    /** 默认的模块 */
    String DEFAULT_CATALOG = "default";
    
    ///** 默认的模块 */
    //String VIEWHANDLER_THUMBNAIL = "thumbnail";
    //
    ///** 默认的模块 */
    //String VIEWHANDLER_DEFAULT = "default";
}
