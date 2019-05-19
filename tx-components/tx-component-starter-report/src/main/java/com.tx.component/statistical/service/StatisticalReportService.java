package com.tx.component.statistical.service;

import com.github.pagehelper.PageInfo;
import com.tx.component.statistical.mapping.ReportStatement;
import com.tx.core.mybatis.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2016/9/21.
 */
public interface StatisticalReportService {
    /**
     * 查询数据列表
     *
     * @param sqlMapId
     * @param params
     * @return
     */
    List queryList(String sqlMapId, String reportCode,
                   Map<String, Object> params);

    List queryList(String sqlMapId, String reportCode,
                   Map<String, Object> params, Order... orders);

    List queryList(String fullId,
                   Map<String, Object> params);

    /**
     * 查询分页数据
     *
     * @param sqlMapId
     * @param params
     * @param pagesize
     * @param pageIndex
     * @return
     */
    PageInfo queryPagedList(String sqlMapId, String reportCode,
                            Map<String, Object> params, int pagesize, int pageIndex);


    PageInfo queryPagedList(String sqlMapId, String reportCode,
                            Map<String, Object> params, int pageSize, int pageIndex,
                            Order... orders);

    int count(String sqlMapId, String reportCode, Map<String, Object> params);

    Object statisticalData(ReportStatement reportStatement, Map params);

    Object statisticalData(ReportStatement reportStatement, Map params, int pageSize, int pageNumber);
}
