/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年1月22日
 * <修改描述:>
 */
package com.tx.component.file.viewhandler;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

import com.tx.component.file.context.FileContext;

/**
 * 抽象视图处理器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年1月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractViewHandler implements ViewHandler,InitializingBean {
    
    /** 文件容器 */
    @Resource(name = "fileContext")
    protected FileContext fileContext;
    
    /**
     * @throws Exception
     */
    @Override
    public final void afterPropertiesSet() throws Exception {
        init();
    }
    
    /**
      * 初始化视图处理器<br/>
      * <功能详细描述> [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    protected void init(){
    }
}
