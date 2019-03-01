//package com.tx.component.statistical.dao.impl;
//
//import com.tx.component.statistical.dao.StatisticalDataEngineDao;
//import com.tx.core.mybatis.model.Order;
//import com.tx.core.mybatis.support.MyBatisDaoSupport;
//import com.tx.core.paged.model.PagedList;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by SEELE on 2016/9/21.
// */
//@Service("statisticalDataEngineDao")
//public class StatisticalDataEngineDaoImpl<T>
//        implements StatisticalDataEngineDao<T> {
//    @Resource
//    private MyBatisDaoSupport myBatisDaoSupport;
//
//    @Override
//    public List<T> queryList(String sql, String exeSqlStatement,
//            Map<String, Object> params, Order... orders) {
//
//        if (params == null) {
//            params = new HashMap<>();
//        }
//        HashMap<String, Object> requestPparam = new HashMap<>();
//        requestPparam.put("sql", sql);
//        requestPparam.putAll(params);
//        List list = new ArrayList();
//        if(orders!=null) {
//            for (Order order : orders) {
//                list.add(order);
//            }
//        }
//        return myBatisDaoSupport.queryList(exeSqlStatement,
//                requestPparam,
//                list);
//
//    }
//
//    @Override
//    public PagedList<T> queryPagedList(String sql, String exeSqlStatement,
//            Map<String, Object> params, int pageSize, int pageIndex,
//            Order... orders) {
//        if (params == null) {
//            params = new HashMap<>();
//        }
//
//        HashMap<String, Object> requestPparam = new HashMap<>();
//        ;
//        requestPparam.put("sql", sql);
//        requestPparam.putAll(params);
//        List list = new ArrayList();
//        if(orders!=null) {
//            for (Order order : orders) {
//                list.add(order);
//            }
//        }
//        return myBatisDaoSupport.queryPagedList(exeSqlStatement,
//                requestPparam,
//                pageIndex,
//                pageSize,
//                list);
//    }
//
//    @Override
//    public int count(String sql, Map<String, Object> params) {
//
//            if (params == null) {
//                params = new HashMap<>();
//            }
//            HashMap<String, Object> requestPparam = new HashMap<>();
//            requestPparam.put("sql", sql);
//            requestPparam.putAll(params);
//
//            return myBatisDaoSupport.count("statisticalDataEngine.exeSqlCount",
//                    requestPparam);
//
//    }
//
//}
