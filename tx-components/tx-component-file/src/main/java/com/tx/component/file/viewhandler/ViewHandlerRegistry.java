/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月14日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.file.viewhandler.impl.DefaultViewHandler;

/**
 * 视图处理器注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ViewHandlerRegistry implements InitializingBean{
    
    /** 默认的视图处理 */
    private final ViewHandler defaultViewHandler = new DefaultViewHandler();
    
    /** 视图处理器映射 */
    private final Map<String, ViewHandler> viewHandlerMap = new HashMap<String, ViewHandler>();

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        
    }
}
