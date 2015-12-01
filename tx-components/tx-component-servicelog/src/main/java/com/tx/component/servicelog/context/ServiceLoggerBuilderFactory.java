/*
 * 描述： <描述>
 * 修改人： rain
 * 修改时间： 2015年11月24日
 * 项目： tx-component-servicelog
 */
package com.tx.component.servicelog.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.tx.component.servicelog.defaultimpl.TXServiceLoggerBuilder;
import com.tx.component.servicelog.exception.UnsupportServiceLoggerTypeException;

/**
 * 日志建造者工厂
 * 
 * @author rain
 * @version [版本号, 2015年11月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ServiceLoggerBuilderFactory extends ServiceLoggerConfigurator {
    
    // 这里暂时是注入的. 可以改成扫描
    /** 业务日志实例建造者 */
    protected List<ServiceLoggerBuilder> serviceLoggerBuilders;
    
    @SuppressWarnings("deprecation")
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (serviceLoggerBuilders == null) {
            serviceLoggerBuilders = new ArrayList<ServiceLoggerBuilder>();
        }
        
        // 以下代码兼容以前的配置文件
        {
            boolean isExistBuilder = false;
            for (ServiceLoggerBuilder serviceLoggerBuilder : serviceLoggerBuilders) {
                if (serviceLoggerBuilder instanceof TXServiceLoggerBuilder) {
                    isExistBuilder = true;
                    ((TXServiceLoggerBuilder) serviceLoggerBuilder).initBuilder();
                }
            }
            if (!isExistBuilder && serviceLoggerBuilder != null && serviceLoggerBuilder instanceof TXServiceLoggerBuilder) {
                TXServiceLoggerBuilder builder = (TXServiceLoggerBuilder) serviceLoggerBuilder;
                builder.setDataSourceType(dataSourceType);
                builder.setDataSource(dataSource);
                builder.initBuilder();
                serviceLoggerBuilders.add(serviceLoggerBuilder);
            }
        }
        
        if (CollectionUtils.isEmpty(serviceLoggerBuilders)) {
            logger.warn("扫描不到任何一个日志建造者工厂!!");
        }
        for (ServiceLoggerBuilder serviceLoggerBuilder : serviceLoggerBuilders) {
            logger.info("扫描到日志建造者工厂 : {}[{}]", serviceLoggerBuilder.getClass().getName(), serviceLoggerBuilder.order());
        }
    }
    
    /**
     * 
     * 根据传入的日志对象类型构建一个日志对象建造者
     *
     * @param logObjectType 日志对象类型
     *            
     * @return ServiceLoggerBuilder [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @version [版本号, 2015年11月24日]
     * @author rain
     */
    protected ServiceLoggerBuilder buildServiceLoggerBuilder(Class<?> logObjectType) {
        if (CollectionUtils.isNotEmpty(serviceLoggerBuilders)) {
            for (ServiceLoggerBuilder serviceLoggerBuilder : serviceLoggerBuilders) {
                boolean support = serviceLoggerBuilder.isSupport(logObjectType);
                if (support) {
                    return serviceLoggerBuilder;
                }
            }
        }
        throw new UnsupportServiceLoggerTypeException("unknow serviceLoggerBuilder!");
    }
    
    /** 业务日志实例建造者 */
    public void setServiceLoggerBuilders(List<ServiceLoggerBuilder> serviceLoggerBuilders) {
        Collections.sort(serviceLoggerBuilders, new Comparator<ServiceLoggerBuilder>() {
            @Override
            public int compare(ServiceLoggerBuilder s1, ServiceLoggerBuilder s2) {
                int o1 = s1.order(), o2 = s2.order();
                return o1 == o2 ? 0 : (o1 < o2 ? -1 : 1);
            }
        });
        this.serviceLoggerBuilders = serviceLoggerBuilders;
    }
}
