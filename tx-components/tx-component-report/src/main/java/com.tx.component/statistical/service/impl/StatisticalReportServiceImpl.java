package com.tx.component.statistical.service.impl;

import com.github.pagehelper.PageInfo;
import com.tx.component.statistical.dao.StatisticalReportDao;
import com.tx.component.statistical.mapping.ReportStatement;
import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.service.StatisticalReportService;
import com.tx.core.mybatis.model.Order;
import com.tx.core.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2016/9/21.
 */

public class StatisticalReportServiceImpl
        implements StatisticalReportService {

    @Resource(name = "StatisticalReport.statisticalReportDao")
    private StatisticalReportDao statisticalReportDao;


    /**
     * 查询数据列表
     *
     * @param sqlMapId
     * @param reportCode
     * @param params     @return
     */
    @Override
    public List queryList(String sqlMapId, String reportCode, Map<String, Object> params) {
        String statementId = reportCode + "." + sqlMapId;
        return statisticalReportDao.queryList(statementId, params, null);
    }

    @Override
    public List queryList(String sqlMapId, String reportCode, Map<String, Object> params, Order... orders) {
        String statementId = reportCode + "." + sqlMapId;
        return statisticalReportDao.queryList(statementId, params, orders);
    }

    @Override
    public List queryList(String statementId, Map<String, Object> params) {
        return statisticalReportDao.queryList(statementId, params);
    }

    /**
     * 查询分页数据
     *  @param sqlMapId
     * @param reportCode
     * @param params
     * @param pageSize
     * @param pageIndex  @return
     */
    @Override
    public PageInfo queryPagedList(String sqlMapId, String reportCode, Map<String, Object> params, int pageSize, int pageIndex) {
        String statementId = reportCode + "." + sqlMapId;
        return statisticalReportDao.queryPagedList(statementId, params, pageSize, pageIndex);
    }

    @Override
    public PageInfo queryPagedList(String sqlMapId, String reportCode, Map<String, Object> params, int pageSize, int pageIndex, Order... orders) {
        String statementId = reportCode + "." + sqlMapId;
        return statisticalReportDao.queryPagedList(statementId, params, pageSize, pageIndex, orders);

    }

    @Override
    public int count(String sqlMapId, String reportCode, Map<String, Object> params) {
        String statementId = reportCode + "." + sqlMapId + "Count";
        return statisticalReportDao.count(statementId, params);

    }

    @Override
    public Object statisticalData(ReportStatement reportStatement, Map params) {
        String statementId = reportStatement.getCode() + "." + reportStatement.getViewMap().getSqlMapperId() + "Statistical";
        params.put("statisticalColumn", generatorStatisticalColumn(reportStatement));

        List list = statisticalReportDao.queryList(statementId, params, null);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Object statisticalData(ReportStatement reportStatement, final  Map params, int pageSize, int pageNumber) {
        Map<String,Object> newParams = new HashMap<>();
//        BeanUtils.copyProperties(params,newParams);

        newParams.putAll(params);
        String statementId = reportStatement.getCode() + "." + reportStatement.getViewMap().getSqlMapperId() + "Statistical";
        newParams.put("statisticalColumn", generatorStatisticalColumn(reportStatement));
        int limitStart = (pageSize) * (pageNumber - 1);
        int limitSize = pageSize;

        newParams.put("limitStart", limitStart);
        newParams.put("limitSize", limitSize);

        List list = statisticalReportDao.queryList(statementId, newParams, null);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    private String generatorStatisticalColumn(ReportStatement reportStatement) {
        StringBuffer statisticalSql = new StringBuffer();
        statisticalSql.append("count(1) as cnt , ");
        for (ViewItem viewItem : reportStatement.getViewMap().getItems()) {
            String statisticalType = viewItem.getStatisticalType();
            if (StringUtils.isEmpty(statisticalType)) {
                continue;
            }
            statisticalSql.append(statisticalType + "(")
                    .append(viewItem.getColumn())
                    .append(") as ")
                    .append(viewItem.getColumn());
            statisticalSql.append(" ,");
        }
        statisticalSql = new StringBuffer(
                statisticalSql.substring(0, statisticalSql.length() - 1));
        return statisticalSql.toString();
    }
}
