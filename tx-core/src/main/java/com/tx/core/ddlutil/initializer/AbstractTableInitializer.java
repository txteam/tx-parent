/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月10日
 * <修改描述:>
 */
package com.tx.core.ddlutil.initializer;

/**
 * 抽象表初始化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class AbstractTableInitializer implements TableInitializer {
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * 初始化表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void initialize() {
        tables();
        sequences();
        packages();
        functions();
        procedures();
        triggers();
        views();
        initdatas();
        jobs();
    }
    
    /**
     * 
     */
    @Override
    public void tables() {
    }
    
    /**
     * 
     */
    @Override
    public void sequences() {
    }
    
    /**
     * 
     */
    @Override
    public void packages() {
    }
    
    /**
     * 
     */
    @Override
    public void functions() {
    }
    
    /**
     * 
     */
    @Override
    public void procedures() {
    }
    
    /**
     * 
     */
    @Override
    public void triggers() {
    }
    
    /**
     * 
     */
    @Override
    public void views() {
    }
    
    /**
     * 
     */
    @Override
    public void initdatas() {
    }
    
    /**
     * 
     */
    @Override
    public void jobs() {
    }
}
