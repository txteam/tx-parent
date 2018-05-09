///*
// * 描          述:  <描述>
// * 修  改   人:  Administrator
// * 修改时间:  2015年11月6日
// * <修改描述:>
// */
//package com.tx.component.statistical.context;
//
//import com.tx.component.statistical.mapping.DataSourceMapping;
//import com.tx.core.exceptions.util.AssertUtils;
//import com.tx.core.util.StringUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 数据源注册器<br/>
// * <功能详细描述>
// *
// * @author Administrator
// * @version [版本号, 2015年11月6日]
// * @see [相关类/方法]
// * @since [产品/模块版本]
// */
//@Component("dataSourceRegister")
//public class DataSourceRegister {
//
//    private static Map<String, DataSourceMapping> dataSourceMapHashMap = Collections
//            .synchronizedMap(new HashMap<String, DataSourceMapping>());
//
//    public static final boolean registerDataSource(
//            DataSourceMapping dataSourceMap) {
//        String key = dataSourceMap.getNamespace() + "." + dataSourceMap.getId();
//        AssertUtils.isTrue(!dataSourceMapHashMap.containsKey(key),
//                "已经存在datasource【" + key + "】");
//        dataSourceMapHashMap.put(key, dataSourceMap);
//        return true;
//    }
//
//    /**
//     * 重新注册
//     *
//     * @param dataSourceMap
//     * @return
//     */
//    public static final boolean reRegisterDataSource(
//            DataSourceMapping dataSourceMap) {
//        String key = dataSourceMap.getNamespace() + "." + dataSourceMap.getId();
//        dataSourceMapHashMap.put(key, dataSourceMap);
//        return true;
//    }
//
//    public static final boolean registerDataSource(
//            List<DataSourceMapping> dataSourceMaps) {
//        registerDataSource(dataSourceMaps, null);
//        return true;
//
//    }
//
//    public static DataSourceMapping getDatasourceMap(String key) {
//        return dataSourceMapHashMap.get(key);
//    }
//
//    public static DataSourceMapping getDatasourceMap(String reportCode, String datasource) {
//        return getDatasourceMap(reportCode + "." + datasource);
//    }
//
//    public static void registerDataSource(
//            List<DataSourceMapping> dataSourceMappingList, String namespace) {
//        for (DataSourceMapping dataSourceMapping : dataSourceMappingList) {
//            if (StringUtils.isNotEmpty(namespace)) {
//                dataSourceMapping.setNamespace(namespace);
//            }
//            registerDataSource(dataSourceMapping);
//        }
//    }
//
//    /**
//     * 重新注册资源
//     * @param dataSourceMappingList
//     * @param namespace
//     */
//    public static void reRegisterDataSource(List<DataSourceMapping> dataSourceMappingList, String namespace) {
//        for (DataSourceMapping dataSourceMapping : dataSourceMappingList) {
//            if (StringUtils.isNotEmpty(namespace)) {
//                dataSourceMapping.setNamespace(namespace);
//            }
//            reRegisterDataSource(dataSourceMapping);
//        }
//
//    }
//
//}
