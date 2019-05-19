///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2016年10月2日
// * <修改描述:>
// */
//package com.tx.component.statistical.mybatismapping;
//
//import com.tx.core.exceptions.util.AssertUtils;
//
///**
// * 模板引擎容器配置器<br/>
// * <功能详细描述>
// *
// * @author Administrator
// * @version [版本号, 2016年10月2日]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//public class StatisticalReportEngineContext
//        extends StatisticalReportEngineBuilder implements  StatisticalMapperEngine {
//
//    protected static StatisticalMapperEngine statisticalMapperEngine;
//
//    public static StatisticalMapperEngine getStatisticalMapperEngine() {
//        if (statisticalMapperEngine != null) {
//            return statisticalMapperEngine;
//        }
//        synchronized (StatisticalMapperEngine.class) {
//            statisticalMapperEngine = (StatisticalMapperEngine) applicationContext.getBean(beanName,
//                    StatisticalMapperEngine.class);
//        }
//        AssertUtils.notNull(statisticalMapperEngine,
//                "statisticalMapperEngine is null.");
//        return statisticalMapperEngine;
//    }
//}
