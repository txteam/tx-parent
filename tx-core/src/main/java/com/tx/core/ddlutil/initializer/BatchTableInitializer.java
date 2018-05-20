/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年5月10日
 * <修改描述:>
 */
package com.tx.core.ddlutil.initializer;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.OrderComparator;

import com.tx.core.TxConstants;

/**
 * 抽象表初始化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BatchTableInitializer extends AbstractTableInitializer {
    
    /** 初始化器列表 */
    private List<TableInitializer> initializerList;
    
    /** <默认构造函数> */
    public BatchTableInitializer() {
        super();
    }
    
    /** <默认构造函数> */
    public BatchTableInitializer(List<TableInitializer> initializerList) {
        super();
        this.initializerList = initializerList;
    }
    
    /**
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String tables(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.tables(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String sequences(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.sequences(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String packages(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.packages(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String functions(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.functions(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String procedures(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.procedures(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String triggers(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.triggers(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String views(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.views(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String initdatas(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.initdatas(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @param tableAutoInitialize
     * @return
     */
    @Override
    public String jobs(boolean tableAutoInitialize) {
        if (CollectionUtils.isEmpty(initializerList)) {
            return LINE_SEPARATOR;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (TableInitializer initializer : initializerList) {
            sb.append(initializer.jobs(tableAutoInitialize));
        }
        sb.append(LINE_SEPARATOR);
        
        return sb.toString();
    }
    
    /**
     * @return 返回 initializerList
     */
    public List<TableInitializer> getInitializerList() {
        return initializerList;
    }
    
    /**
     * @param 对initializerList进行赋值
     */
    public void setInitializerList(List<TableInitializer> initializerList) {
        this.initializerList = initializerList;
    }
}
