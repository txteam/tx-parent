/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月10日
 * <修改描述:>
 */
package com.tx.core.dbscript.initializer;

import com.tx.core.TxConstants;
import com.tx.core.ddlutil.executor.TableDDLExecutor;

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
    
    /** <默认构造函数> */
    public AbstractTableInitializer() {
        super();
    }
    
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
    public String initialize(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        
        sb.append(COMMENT_PREFIX)
                .append("----------tables----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(tables(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------sequences----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(sequences(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------packages----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(packages(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------functions----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(functions(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------procedures----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(procedures(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------triggers----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(triggers(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------views----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(views(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------initdatas----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(initdatas(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        sb.append(COMMENT_PREFIX)
                .append("----------jobs----------")
                .append(COMMENT_SUFFIX)
                .append(LINE_SEPARATOR);
        sb.append(jobs(tableDDLExecutor, tableAutoInitialize));
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * 
     */
    @Override
    public String tables(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String sequences(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String packages(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String functions(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String procedures(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String triggers(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String views(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String initdatas(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
    
    /**
     * 
     */
    @Override
    public String jobs(TableDDLExecutor tableDDLExecutor,
            boolean tableAutoInitialize) {
        return "";
    }
}
