/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年11月6日
 * <修改描述:>
 */
package com.tx.core.ddlutil.builder.create.impl;

import java.util.HashMap;
import java.util.Map;

import com.tx.core.ddlutil.builder.create.CreateTableDDLBuilderFactory;
import com.tx.core.ddlutil.dialect.Dialect4DDL;
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
public class CreateTableDDLBuilderFactoryRegistry {
    
    /** 类型和Factory的映射 */
    protected static Map<DataSourceTypeEnum, CreateTableDDLBuilderFactory> type2factoryMap = new HashMap<>();
    
    static {
        Dialect4DDL mysqlDialect = MysqlDDLDialect.INSTANCE;
        CreateTableDDLBuilderFactoryRegistry.registeFactory(
                DataSourceTypeEnum.MYSQL,
                new MysqlCreateTableDDLBuilder(mysqlDialect));
        //        CreateTableDDLBuilderFactoryRegistry.registeFactory(DataSourceTypeEnum.MySQL5InnoDBDialect,
        //                new MysqlCreateTableDDLBuilder(mysqlDialect));
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
            CreateTableDDLBuilderFactory factory) {
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
    public static CreateTableDDLBuilderFactory getFactory(
            DataSourceTypeEnum dataSourceType) {
        AssertUtils.notNull(dataSourceType, "dataSourceType is null.");
        
        CreateTableDDLBuilderFactory builder = type2factoryMap
                .get(dataSourceType);
        AssertUtils.notNull(builder,
                "builder is not exist.dataSourceType:{}",
                new Object[] { dataSourceType });
        
        return builder;
    }
}
