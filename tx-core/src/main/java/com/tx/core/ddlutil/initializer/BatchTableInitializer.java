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

/**
 * 抽象表初始化器<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年5月10日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BatchTableInitializer {
    
    /** 初始化器列表 */
    protected List<TableInitializer> initializerList;
    
    /**
     * 初始化表<br/>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void initialize() {
        if (CollectionUtils.isEmpty(initializerList)) {
            return;
        }
        Collections.sort(initializerList, OrderComparator.INSTANCE);
        
        for (TableInitializer initializer : initializerList) {
            initializer.tables();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.sequences();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.packages();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.functions();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.procedures();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.triggers();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.views();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.initdatas();
        }
        
        for (TableInitializer initializer : initializerList) {
            initializer.jobs();
        }
    }
}
