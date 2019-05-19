package com.tx.component.statistical.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tx.component.statistical.dao.StatisticalReportDao;
import com.tx.core.mybatis.model.Order;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by SEELE on 2016/9/21.
 */
public class StatisticalReportDaoImpl<T> implements StatisticalReportDao<T> {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public List<T> queryList(String statementId, Map<String, Object> params, Order... orders) {
        processOrderList(params, orders);

        return sqlSessionTemplate.<T>selectList(statementId, params);
    }


    public PageInfo<T> queryPagedList(String statementId, Map<String, Object> params, int pageSize, int pageIndex, Order... orders) {
        processOrderList(params, orders);
        PageHelper.startPage(pageIndex, pageSize);
        List<T> list = sqlSessionTemplate.<T>selectList(statementId, params);

        PageInfo<T> page = new PageInfo(list);
        return page;

    }

    @Override
    public int count(String statementId, Map<String, Object> params) {
        return sqlSessionTemplate.<Integer>selectOne(statementId,
                params);
    }

    private void processOrderList(Map<String, Object> params, Order... orders) {
        if (orders != null && orders.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Order orderTemp : orders) {
                sb.append(orderTemp.toSqlString()).append(",");
            }
            if (sb.length() > 0) {
                String orderSql = sb.substring(0, sb.length() - 1);

                params.put("orderSql", orderSql);
            }
        }
    }
}
