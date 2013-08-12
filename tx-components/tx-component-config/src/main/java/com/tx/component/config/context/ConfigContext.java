/*
 * 描          述:  <描述>
 * 修  改   人:  wanxin
 * 修改时间:  2013-8-8
 * <修改描述:>
 */
package com.tx.component.config.context;

/**
 * 配置容器<br/>
 * test2
 * test2
 * 
 * @author  wanxin
 * @version  [版本号, 2013-8-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConfigContext {
    
    /** 是否处于开发模式  开发模式中 getValue 将优先获取 developValue */
    private boolean isDevelop = false;
    
    /** 配置是否可重复 */
    private boolean repeatAble = false;
    
    private static ConfigContext context;
    
    private ConfigContext() {
        super();
    }
    
    public static ConfigContext getContext() {
        synchronized (ConfigContext.class) {
            if (context == null) {
                context = new ConfigContext();
            }
        }
        return context;
    }
    
    /**
     * @return 返回 isDevelop
     */
    public boolean isDevelop() {
        return isDevelop;
    }
    
    /**
     * @param 对isDevelop进行赋值
     */
    public void setDevelop(boolean isDevelop) {
        this.isDevelop = isDevelop;
    }
    
    /**
     * @return 返回 repeatAble
     */
    public boolean isRepeatAble() {
        return repeatAble;
    }
    
    /**
     * @param 对repeatAble进行赋值
     */
    public void setRepeatAble(boolean repeatAble) {
        this.repeatAble = repeatAble;
    }
}
