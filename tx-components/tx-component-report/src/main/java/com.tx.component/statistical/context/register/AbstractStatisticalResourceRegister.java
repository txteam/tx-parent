/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月6日
 * <修改描述:>
 */
package com.tx.component.statistical.context.register;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.tx.component.statistical.context.StatisticalResourceRegister;
import com.tx.component.statistical.mapping.ReportStatement;
import com.tx.component.statistical.mapping.SqlMapperItem;
import com.tx.component.statistical.mybatismapping.StatisticalMapperAssistantRepository;
import com.tx.core.exceptions.util.AssertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

/**
 * 顶层统计资源注册器<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2015年11月6日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class AbstractStatisticalResourceRegister
        implements StatisticalResourceRegister {

    private static Map<String, ReportStatement> reportStatementMap = ReportStatement.getReportStatementMap();
    private static Map<String, SqlMapperItem> sqlMapperItemMap = ReportStatement.getSqlMapperItemMap();

    protected static Map<String, Class> typeMap = new HashedMap();

    static {
        typeMap.put("map", Map.class);
        typeMap.put("string", String.class);

        typeMap.put("boolean", Boolean.class);
        typeMap.put("byte", Byte.class);
        typeMap.put("char", Char.class);
        typeMap.put("int", Integer.class);
        typeMap.put("short", Short.class);
        typeMap.put("float", Float.class);
        typeMap.put("double", Double.class);

    }


    @javax.annotation.Resource(name = "StatisticalReport.StatisticalMapperAssistantRepository")
    private StatisticalMapperAssistantRepository statisticalMapperAssistantRepository;

    @Override
    public ReportStatement registerResource(Resource resource) {
        ReportStatement reportStatement = parseResource(resource);

        //将解析器以及资源写入报表中
        reportStatement.setReportResource(resource);
        reportStatement.setStatisticalResourceRegister(this);

        AssertUtils.notEmpty(reportStatement, "报表解析失败");
        String reportCode = reportStatement.getCode();
        AssertUtils.isTrue(!reportStatementMap.containsKey(reportCode),"已经存在相同的报表【{}】",
                new Object[]{reportCode});
        reportStatementMap.put(reportCode, reportStatement);

        List<SqlMapperItem> srSqlItems = reportStatement.getSqlMapperItems();
        registerSqlMapper(srSqlItems, true);

        return reportStatement;

    }

    @Override
    public void registerResource(Resource[] resources) {
        for (Resource resource : resources) {
            registerResource(resource);
        }
    }

    /**
     * 重新加载报表
     *
     * @param reportStatement
     */
    @Override
    public void reloadReport(ReportStatement reportStatement) {
        AssertUtils.notEmpty(reportStatement, "报表不存在，不能重新加载！");
        Resource resource = reportStatement.getReportResource();
        String reportCode = reportStatement.getCode();
        reportStatement = reportStatement.getStatisticalResourceRegister().parseResource(resource);
        AssertUtils.notEmpty(reportStatement, "报表解析失败");
        //将解析器以及资源写入报表中
        reportStatement.setStatisticalResourceRegister(this);
        reportStatement.setReportResource(resource);

        reportStatementMap.put(reportCode, reportStatement);

        List<SqlMapperItem> srSqlItems = reportStatement.getSqlMapperItems();
        registerSqlMapper(srSqlItems, true);
    }

    /**
     * 重新注册sql脚本
     *
     * @param sqlMapperItemList
     * @param isOverride        是否覆盖
     */
    private void registerSqlMapper(List<SqlMapperItem> sqlMapperItemList, boolean isOverride) {
        if (CollectionUtils.isEmpty(sqlMapperItemList)) {
            return;
        }

        //验证是否存在该脚本
        if (!isOverride) {
            for (SqlMapperItem sqlMapperItem : sqlMapperItemList) {
                String id = sqlMapperItem.getFullId();
                if (sqlMapperItemMap.containsKey(id)) {
                    AssertUtils.isTrue(false, "已经存在该脚本");
                } else {
                    sqlMapperItemMap.put(id, sqlMapperItem);
                }
            }
        }
        statisticalMapperAssistantRepository.assistant(sqlMapperItemList);
    }

}
