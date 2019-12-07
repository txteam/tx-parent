/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年12月5日
 * <修改描述:>
 */
package com.tx.component.config;

import com.tx.component.configuration.annotation.ConfigEntity;
import com.tx.component.plugin.loginplugin.LoginPluginConfig;

/**
 * QQ登陆插件配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年12月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConfigEntity(catalog = "plugin.login", code = "plugin.login.qq", name = "QQ登陆插件配置")
public class QQLoginPluginConfig extends LoginPluginConfig {
    
    private String methodName;
    
    private String test1;
    
    private String test2;
    
    /**
     * @return
     */
    @Override
    public boolean validate() {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * @return 返回 methodName
     */
    public String getMethodName() {
        return methodName;
    }
    
    /**
     * @param 对methodName进行赋值
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    /**
     * @return 返回 test1
     */
    public String getTest1() {
        return test1;
    }
    
    /**
     * @param 对test1进行赋值
     */
    public void setTest1(String test1) {
        this.test1 = test1;
    }
    
    /**
     * @return 返回 test2
     */
    public String getTest2() {
        return test2;
    }
    
    /**
     * @param 对test2进行赋值
     */
    public void setTest2(String test2) {
        this.test2 = test2;
    }
}
