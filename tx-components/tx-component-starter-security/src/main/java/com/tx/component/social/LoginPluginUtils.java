/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月25日
 * <修改描述:>
 */
package com.tx.component.social;

import java.util.Map;

import org.apache.commons.lang3.EnumUtils;

import com.tx.component.social.model.SocialAccountTypeEnum;

/**
 * 登陆插件常用工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class LoginPluginUtils {
    
    /** 账户类型映射 */
    private final static Map<String, SocialAccountTypeEnum> SOCIAL_ACCOUNT_TYPE_MAP = EnumUtils
            .getEnumMap(SocialAccountTypeEnum.class);
    
    //    /**
    //     * 根据传入插件简称获取对应插件实例<br/>
    //     * <功能详细描述>
    //     * @param plugin
    //     * @return [参数说明]
    //     * 
    //     * @return LoginPlugin<?> [返回类型说明]
    //     * @exception throws [异常类型] [异常说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static LoginPlugin<?> getLoginPlugin(String plugin) {
    //        LoginPlugin<?> loginPlugin = null;
    //        switch (plugin) {
    //            case "WX":
    //                loginPlugin = PluginContext.getContext()
    //                        .getPlugin(WXLoginPlugin.class);
    //                break;
    //            //            case "QQ":
    //            //                loginPlugin = PluginContext.getContext()
    //            //                        .getPlugin(QQLoginPlugin.class);
    //            //                break;
    //            case "WB":
    //                loginPlugin = PluginContext.getContext()
    //                        .getPlugin(WBLoginPlugin.class);
    //                break;
    //            //            case "BD":
    //            //                loginPlugin = null;
    //            //                break;
    //            case "GH":
    //                loginPlugin = PluginContext.getContext()
    //                        .getPlugin(GHLoginPlugin.class);
    //                break;
    //        }
    //        return loginPlugin;
    //    }
}
