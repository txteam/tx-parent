/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-12-18
 * <修改描述:>
 */
package com.tx.core.dbscript.impl;

import com.tx.core.dbscript.TableDefinition;
import com.tx.core.dbscript.TableDefinitionFilter;

/**
 * 默认的表定义过滤器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-12-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DefaultTableDefinitionFilter implements TableDefinitionFilter {
    
    /**
     * @param tableDefinition
     * @return
     */
    @Override
    public boolean accept(TableDefinition tableDefinition) {
        return true;
    }
}
