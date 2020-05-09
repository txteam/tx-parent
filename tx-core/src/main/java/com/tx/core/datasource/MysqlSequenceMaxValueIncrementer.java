/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2020年5月9日
 * <修改描述:>
 */
package com.tx.core.datasource;

import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;

/**
 * MysqlSequence的实现<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2020年5月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MysqlSequenceMaxValueIncrementer
        extends AbstractSequenceMaxValueIncrementer {
    
    /**
     * 
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }
    
    /**
     * @return
     */
    @Override
    protected String getSequenceQuery() {
        return "select nextval(" + getIncrementerName() + ")";
    }
    
}
