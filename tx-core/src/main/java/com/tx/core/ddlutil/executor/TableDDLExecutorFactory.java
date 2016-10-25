/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月20日
 * <修改描述:>
 */
package com.tx.core.ddlutil.executor;

import org.springframework.beans.factory.FactoryBean;

/**
 * 表DDL处理器工厂类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TableDDLExecutorFactory implements FactoryBean<TableDDLExecutor> {

    /**
     * @return
     * @throws Exception
     */
    @Override
    public TableDDLExecutor getObject() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return TableDDLExecutor.class;
    }

    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
