//package com.tx.component.statistical.service;
//
//import com.tx.component.statistical.mapping.ReportStatement;
//import com.tx.core.mybatis.model.Order;
//import com.tx.core.paged.model.PagedList;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by SEELE on 2016/9/21.
// */
//public interface StatisticalDataEngineService<T> {
//    /**
//     * 查询数据列表
//     * @param datasource
//     * @param params
//     * @return
//     */
//    List<T> queryList(String datasource,String reportCode,
//            Map<String, Object> params);
//
//    List<T> queryList(String datasource, String reportCode,
//                      Map<String, Object> params, Order... orders);
//
//    List<T> queryList(String datasourceKey,
//                      Map<String, Object> params);
//
//    /**
//     * 查询分页数据
//     * @param datasource
//     * @param params
//     * @param pagesize
//     * @param pageIndex
//     * @return
//     */
//    PagedList<T> queryPagedList(String datasource,String reportCode,
//            Map<String, Object> params, int pagesize, int pageIndex);
//
//
//    PagedList<T> queryPagedList(String datasource, String reportCode,
//                                Map<String, Object> params, int pageSize, int pageIndex,
//                                Order... orders);
//
//    int count(String datasource,String reportCode,Map<String,Object> params);
//
//    T statisticalData(ReportStatement reportStatement, Map params);
//
//    T statisticalData(ReportStatement reportStatement, Map params, int pageSize, int pageNumber);
//}
