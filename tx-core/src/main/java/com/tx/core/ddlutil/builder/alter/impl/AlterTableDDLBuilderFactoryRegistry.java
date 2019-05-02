/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.alter.impl;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.ddlutil.builder.alter.AlterTableDDLBuilderFactory;
import com.tx.core.ddlutil.dialect.DDLDialect;
import com.tx.core.ddlutil.dialect.MysqlDDLDialect;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.util.dialect.DataSourceTypeEnum;

/**
 * 注册工厂实例<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年11月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AlterTableDDLBuilderFactoryRegistry {
    
    /** 类型和Factory的映射 */
    protected static Map<DataSourceTypeEnum, AlterTableDDLBuilderFactory> type2factoryMap = new HashMap<>();
    
    static {
        DDLDialect mysqlDialect = MysqlDDLDialect.INSTANCE;
        AlterTableDDLBuilderFactoryRegistry.registeFactory(DataSourceTypeEnum.MYSQL,
                new MysqlAlterTableDDLBuilder(mysqlDialect));
        AlterTableDDLBuilderFactoryRegistry.registeFactory(DataSourceTypeEnum.MYSQL,
                new MysqlAlterTableDDLBuilder(mysqlDialect));
    }
    
    /**
      * 注册工厂实例<br/>
      * <功能详细描述>
      * @param factory [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void registeFactory(DataSourceTypeEnum dataSourceType,
            AlterTableDDLBuilderFactory factory) {
        AssertUtils.notNull(factory, "factory is null.");
        AssertUtils.notNull(factory.getDefaultDDLDialect(),
                "factory.defaultDDLDialect is null.");
        AssertUtils.notNull(dataSourceType, "factory.dataSourceType is null.");
        
        AssertUtils.isTrue(!type2factoryMap.containsKey(dataSourceType),
                "duplicat dataSourceType:{}",
                new Object[] { dataSourceType });
        type2factoryMap.put(dataSourceType, factory);
    }
    
    /**
     * 根据数据库类型获取数据库DDL构建器实例<br/>
     * <功能详细描述>
     * @param dataSourceType
     * @return [参数说明]
     * 
     * @return CreateTableDDLBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static AlterTableDDLBuilderFactory getFactory(
            DataSourceTypeEnum dataSourceType) {
        AssertUtils.notNull(dataSourceType, "dataSourceType is null.");
        
        AlterTableDDLBuilderFactory builder = type2factoryMap.get(dataSourceType);
        AssertUtils.notNull(builder,
                "builder is not exist.dataSourceType:{}",
                new Object[] { dataSourceType });
        
        return builder;
    }
}
