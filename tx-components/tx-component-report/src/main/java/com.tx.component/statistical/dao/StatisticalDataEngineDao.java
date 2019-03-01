//package com.tx.component.statistical.dao;
//
//import com.tx.core.mybatis.model.Order;
//import com.tx.core.paged.model.PagedList;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by SEELE on 2016/9/21.
// */
//public interface StatisticalDataEngineDao<T> {
//    List<T> queryList(String sql, String exeSqlStatement, Map<String, Object> params,
//                      Order... orders);
//
//    PagedList<T> queryPagedList(String sql, String exeSqlStatement, Map<String, Object> params,
//                                int pageSize, int pageIndex, Order... orders);
//
//    int count(String sql, Map<String, Object> params);
//}
