/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2020年3月19日
 * <修改描述:>
 */
package com.tx.component.tts.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tx.component.plugin.context.Plugin;

/**
 * 发送短信插件<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2020年3月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class SendTTSPlugin<CONFIG extends SendTTSPluginConfig>
        extends Plugin<CONFIG> {
    
    /** 日志记录器 */
    protected Logger logger = LoggerFactory.getLogger(SendTTSPlugin.class);
}
