/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.alter;

import com.tx.core.ddlutil.dialect.DDLDialect;

/**
 * 修改表DDL构建器工厂<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface AlterTableDDLBuilderFactory {
    
    /**
      * 获取默认的DDL方言类<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return DDLDialect [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    DDLDialect getDefaultDDLDialect();
}
