//package com.tx.component.statistical.service.impl;
//
//import com.tx.component.statistical.context.DataSourceRegister;
//import com.tx.component.statistical.dao.StatisticalDataEngineDao;
//import com.tx.component.statistical.mapping.DataSourceMapping;
//import com.tx.component.statistical.mapping.ReportStatement;
//import com.tx.component.statistical.mapping.ViewItem;
//import com.tx.component.statistical.service.StatisticalDataEngineService;
//import com.tx.core.mybatis.model.Order;
//import com.tx.core.paged.model.PagedList;
//import com.tx.core.util.StringUtils;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.ibatis.scripting.xmltags.DynamicContext;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by SEELE on 2016/9/21.
// */
//@Service("defaultStatisticalEngineService")
//public class DefaultStatisticalEngineServiceImpl<T>
//        implements StatisticalDataEngineService<T> {
//    @Resource(name = "statisticalDataEngineDao")
//    private StatisticalDataEngineDao<T> statisticalDataEngineDao;
//
//    @Resource
//    private DataSourceRegister dataSourceRegister;
//
//    @Override
//    public List<T> queryList(String datasource, String reportCode,
//            Map<String, Object> params) {
//        return queryList(datasource, reportCode, params, null);
//    }
//
//    @Override
//    public List<T> queryList(String datasource, String reportCode,
//            final Map<String, Object> params, Order... orders) {
//        String sql = generatorSql(reportCode, datasource, params);
//        return statisticalDataEngineDao.queryList(sql,
//                exeSqlStatement(),
//                params,
//                orders);
//    }
//
//    @Override
//    public List<T> queryList(String datasourceKey, Map<String, Object> params) {
//        String sql = generatorSql(datasourceKey, params);
//        return statisticalDataEngineDao.queryList(sql, exeSqlStatement(), params);
//    }
//
//    @Override
//    public PagedList<T> queryPagedList(String datasource, String reportCode,
//            Map<String, Object> params, int pagesize, int pageIndex) {
//        return queryPagedList(datasource,
//                reportCode,
//                params,
//                pagesize,
//                pageIndex,
//                null);
//    }
//
//    @Override
//    public PagedList<T> queryPagedList(String datasource, String reportCode,
//            final Map<String, Object> params, int pageSize, int pageIndex,
//            Order... orders) {
//
//        String sql = generatorSql(reportCode, datasource, params);
//        return statisticalDataEngineDao.queryPagedList(sql,
//                exeSqlStatement(),
//                params,
//                pageSize,
//                pageIndex,
//                orders);
//    }
//
//    @Override
//    public int count(String datasource, String reportCode,
//            final Map<String, Object> params) {
//
//        String sql = generatorSql(reportCode, datasource, params);
//        return statisticalDataEngineDao.count(sql, params);
//    }
//
//    /**
//     * 统计数据
//     * @param reportStatement
//     * @param params
//     * @return
//     */
//    @Override
//    public T statisticalData(ReportStatement reportStatement, Map params) {
//        String sql = generatorSql( reportStatement.getCode(),reportStatement.getViewMap().getDatasourceId(),
//                params);
//        StringBuffer statisticalSql = new StringBuffer();
//
//        statisticalSql.append("select ");
//
//        boolean isContainStatisticalColumn = false;
//        List<ViewItem> viewItems = reportStatement.getViewMap().getItems();
//        for (ViewItem viewItem : viewItems) {
//            String statisticalType = viewItem.getStatisticalType();
//            if (StringUtils.isEmpty(statisticalType)) {
//                continue;
//            }
//            statisticalSql.append(statisticalType + "(")
//                    .append(viewItem.getColumn())
//                    .append(") as ")
//                    .append(viewItem.getColumn());
//            statisticalSql.append(" ,");
//            isContainStatisticalColumn = true;
//        }
//
//        if(isContainStatisticalColumn == false){
//            return null;
//        }
//        statisticalSql = new StringBuffer(
//                statisticalSql.substring(0, statisticalSql.length()-1));
//
//        statisticalSql.append(" from (").append(sql).append(") as t");
//
//        List<T> list = statisticalDataEngineDao.queryList(statisticalSql.toString(),
//                exeSqlStatement(),
//                params);
//        if (CollectionUtils.isNotEmpty(list)) {
//            return list.get(0);
//        }
//        return null;
//    }
//    @Override
//    public T statisticalData(ReportStatement reportStatement, Map params, int pageSize, int pageNumber) {
//        String sql = generatorSql( reportStatement.getCode(),reportStatement.getViewMap().getDatasourceId(),
//                params);
//        StringBuffer statisticalSql = new StringBuffer();
//        int limitStart = (pageSize)*(pageNumber-1);
//        int limitSize = pageSize;
//
//        statisticalSql.append("select ");
//
//        List<ViewItem> viewItems = reportStatement.getViewMap().getItems();
//        boolean isContainStatisticalColumn = false;
//        for (ViewItem viewItem : viewItems) {
//            String statisticalType = viewItem.getStatisticalType();
//            if (StringUtils.isEmpty(statisticalType)) {
//                continue;
//            }
//            statisticalSql.append(statisticalType + "(")
//                    .append(viewItem.getColumn())
//                    .append(") as ")
//                    .append(viewItem.getColumn());
//            statisticalSql.append(" ,");
//            isContainStatisticalColumn = true;
//        }
//        if(isContainStatisticalColumn == false){
//            return null;
//        }
//        statisticalSql = new StringBuffer(
//                statisticalSql.substring(0, statisticalSql.length()-1));
//
//        statisticalSql.append(" from (")
//                .append(sql)
//                .append(" limit ")
//                .append(limitStart)
//                .append(",")
//                .append(limitSize)
//                .append(") as t");
//
//        List<T> list = statisticalDataEngineDao.queryList(statisticalSql.toString(),
//                exeSqlStatement(),
//                params);
//        if (CollectionUtils.isNotEmpty(list)) {
//            return list.get(0);
//        }
//        return null;
//    }
//
//    protected String generatorSql(String reportCode, String datasource,
//            Map<String, Object> params) {
//        DataSourceMapping dataSourceMap = dataSourceRegister
//                .getDatasourceMap(reportCode, datasource);
//
//        DynamicContext dynamicContext = new DynamicContext(
//                dataSourceMap.getConfiguration(), params);
//
//        dataSourceMap.getSqlNode().apply(dynamicContext);
//
//        return dynamicContext.getSql();
//    }
//
//    protected String generatorSql(String datasourceKey,
//            Map<String, Object> params) {
//        DataSourceMapping dataSourceMap = dataSourceRegister
//                .getDatasourceMap(datasourceKey);
//
//        DynamicContext dynamicContext = new DynamicContext(
//                dataSourceMap.getConfiguration(), params);
//
//        dataSourceMap.getSqlNode().apply(dynamicContext);
//
//        return dynamicContext.getSql();
//    }
//
//    protected String exeSqlStatement() {
//        return "statisticalDataEngine.exeSql";
//    }
//
//}
