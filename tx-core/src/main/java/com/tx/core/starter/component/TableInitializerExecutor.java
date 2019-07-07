/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2019年4月30日
 * <修改描述:>
 */
package com.tx.core.starter.component;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.CollectionUtils;

import com.tx.core.dbscript.initializer.TableInitializer;
import com.tx.core.ddlutil.executor.TableDDLExecutor;
import com.tx.core.util.ClassScanUtils;

/**
 * 基础数据持久层配置逻辑<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2019年4月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@ConditionalOnBean(TableDDLExecutor.class)
@ConditionalOnProperty(prefix = ComponentConstants.PERSISTER_PROPERTIES_PREFIX, value = "table-auto-initialize", havingValue = "true")
public class TableInitializerExecutor implements InitializingBean {
    
    /** 表是否自动初始化 */
    private boolean tableAutoInitialize = false;
    
    /** 表ddl自动执行器 */
    private TableDDLExecutor tableDDLExecutor;
    
    /** 基础数据容器初始化构造函数 */
    public TableInitializerExecutor(ComponentProperties properties,
            TableDDLExecutor tableDDLExecutor) {
        this.tableDDLExecutor = tableDDLExecutor;
        this.tableAutoInitialize = properties.getPersister() == null ? false
                : properties.getPersister().isTableAutoInitialize();
    }
    
    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<? extends TableInitializer>> initializers = ClassScanUtils
                .scanByParentClass(TableInitializer.class, "com.tx");
        if (CollectionUtils.isEmpty(initializers) || !this.tableAutoInitialize
                || this.tableDDLExecutor == null) {
            return;
        }
        
        for (Class<? extends TableInitializer> initializerClazz : initializers) {
            Constructor<? extends TableInitializer> c = ConstructorUtils
                    .getAccessibleConstructor(initializerClazz);
            if (c == null) {
                continue;
            }
            TableInitializer initializer = c.newInstance();
          
            //初始化表
            initializer.initialize(this.tableDDLExecutor, this.tableAutoInitialize);
        }
    }
    
}
