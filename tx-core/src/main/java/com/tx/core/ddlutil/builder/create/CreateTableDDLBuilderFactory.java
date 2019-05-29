package com.tx.core.ddlutil.builder.create;

import com.tx.core.ddlutil.dialect.Dialect4DDL;
import com.tx.core.ddlutil.model.TableDef;

/**
  * 创建DDLBuilder工厂<br/>
  * <功能详细描述>
  * 
  * @author  Administrator
  * @version  [版本号, 2016年11月6日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface CreateTableDDLBuilderFactory {
    
    /**
      * 获取默认的DDLDialect.用于创建实例期间传入默认的DDLDialect不能为空<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return DDLDialect [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public Dialect4DDL getDefaultDDLDialect();
    
    /**
     * 创建建设表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract CreateTableDDLBuilder newInstance(String tableName);
    
    /**
     * 创建建设表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract CreateTableDDLBuilder newInstance(TableDef tableDef);
    
    /**
     * 创建建设表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract CreateTableDDLBuilder newInstance(String tableName,
            Dialect4DDL ddlDialect);
    
    /**
     * 创建建设表构建器实例<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public abstract CreateTableDDLBuilder newInstance(TableDef tableDef,
            Dialect4DDL ddlDialect);
}