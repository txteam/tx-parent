/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月9日
 * <修改描述:>
 */
package com.tx.core.ddlutil.initializer;

import org.springframework.core.PriorityOrdered;

import com.tx.core.ddlutil.executor.TableDDLExecutor;

/**
 * 表初始化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface TableInitializer extends PriorityOrdered {
    
    String COMMENT_PREFIX = "-- ";
    
    String COMMENT_SUFFIX = " ";
    
    String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    
    /**
     * 初始化表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String initialize(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 表初始化器table<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String tables(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 初始化sequences<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String sequences(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 初始化包package<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String packages(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 表初始化器function<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String functions(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 初始化存储过程<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String procedures(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 初始化触发器<br/>
     *<功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String triggers(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 初始化视图逻辑层<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String views(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 表初始化器<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String initdatas(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
    /**
     * 初始化任务<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String jobs(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize);
    
}
