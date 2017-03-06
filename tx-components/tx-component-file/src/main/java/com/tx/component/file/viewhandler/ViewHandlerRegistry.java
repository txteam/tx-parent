/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月14日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 视图处理器注册表<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月14日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ViewHandlerRegistry implements InitializingBean,
        ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    /** 默认的视图处理 */
    @Resource(name = "defaultViewHandler")
    private ViewHandler defaultViewHandler;
    
    /** 视图处理器映射 */
    private final Map<String, ViewHandler> viewHandlerMap = new HashMap<String, ViewHandler>();
    
    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<ViewHandler> handlers = applicationContext.getBeansOfType(ViewHandler.class)
                .values();
        for (ViewHandler vh : handlers) {
            viewHandlerMap.put(vh.name(), vh);
        }
    }
    
    /**
      * 获取对应的视图处理器<br/>
      * <功能详细描述>
      * @param viewHandlerName
      * @return [参数说明]
      * 
      * @return ViewHandler [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public ViewHandler getViewHandler(String viewHandlerName) {
        ViewHandler viewHandler = defaultViewHandler;
        if (StringUtils.isEmpty(viewHandlerName)) {
            return viewHandler;
        }
        
        if (viewHandlerMap.containsKey(viewHandlerName)) {
            viewHandler = viewHandlerMap.get(viewHandlerName);
        }
        return viewHandler;
    }
}
