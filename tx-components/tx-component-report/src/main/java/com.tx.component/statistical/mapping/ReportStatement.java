package com.tx.component.statistical.mapping;

import com.tx.component.statistical.context.StatisticalResourceRegister;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2016/9/20.
 */
public class ReportStatement extends BaseAttrEntry {
    private static final Map<String, ReportStatement> reportStatementMap = new HashMap<>();
    private static final Map<String, SqlMapperItem> sqlMapperItemMap = new HashedMap();

    /**
     * 唯一
     */
    private String code;

    /**
     * 类型 - 大类型，表格，图形
     */
    private String type;

    /**
     * 子类型 - 饼图还是线性图还是地图等
     */
    private String subType;

    /**
     * 名称
     */
    private String name;

    /**
     * 页面脚本
     */
    private String script;

    /**
     * 查询条件
     */
    private ConditionMap conditionMap = new ConditionMap();

    /**
     * 展示列表
     */
    private ViewMap viewMap = new ViewMap();

    /**
     * 查询语句
     */
    private List<SqlMapperItem> sqlMapperItems = new ArrayList<>();

    /**
     * 报表来源
     */
    private Resource reportResource;

    /**
     * 解析此报表时，所用的解析器
     */
    private StatisticalResourceRegister statisticalResourceRegister;

    public static Map<String, ReportStatement> getReportStatementMap() {
        return reportStatementMap;
    }

    public static ReportStatement getReportStatement(String code) {
        return reportStatementMap.get(code);
    }


    public StatisticalResourceRegister getStatisticalResourceRegister() {
        return statisticalResourceRegister;
    }

    public void setStatisticalResourceRegister(
            StatisticalResourceRegister statisticalResourceRegister) {
        this.statisticalResourceRegister = statisticalResourceRegister;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public ConditionMap getConditionMap() {
        return conditionMap;
    }

    public void setConditionMap(ConditionMap conditionMap) {
        this.conditionMap = conditionMap;
    }

    public ViewMap getViewMap() {
        return viewMap;
    }

    public void setViewMap(ViewMap viewMap) {
        this.viewMap = viewMap;
    }

//    public DataSourceMap getDataSourceMap() {
//        return dataSourceMap;
//    }
//
//    public void setDataSourceMap(DataSourceMap dataSourceMap) {
//        this.dataSourceMap = dataSourceMap;
//    }

    public Resource getReportResource() {
        return reportResource;
    }

    public void setReportResource(Resource reportResource) {
        this.reportResource = reportResource;
    }


    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }


    public static Map<String, SqlMapperItem> getSqlMapperItemMap() {
        return sqlMapperItemMap;
    }

    public List<SqlMapperItem> getSqlMapperItems() {
        return sqlMapperItems;
    }

    public void setSqlMapperItems(List<SqlMapperItem> sqlMapperItems) {
        this.sqlMapperItems = sqlMapperItems;
    }
}
