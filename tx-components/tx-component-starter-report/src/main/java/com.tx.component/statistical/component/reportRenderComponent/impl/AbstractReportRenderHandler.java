package com.tx.component.statistical.component.reportRenderComponent.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.tx.component.statistical.component.conditionCompent.ConditionComponent;
import com.tx.component.statistical.component.reportRenderComponent.ReportTypeEnum;
import com.tx.component.statistical.component.viewComponent.ViewComponent;
import com.tx.component.statistical.context.StatisticalContext;
import com.tx.component.statistical.mapping.BaseAttr;
import com.tx.component.statistical.mapping.ConditionItem;
import com.tx.component.statistical.mapping.ReportStatement;
import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.model.StatisticalPagedList;
import com.tx.component.statistical.service.StatisticalReportService;
import com.tx.component.statistical.type.ConditionTypeEnum;
import com.tx.component.statistical.utils.NumberUtils;
import com.tx.core.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by SEELE on 2016/9/20.
 */
public abstract class AbstractReportRenderHandler<T> {
//    @Resource(name = "defaultStatisticalEngineService")
//    protected StatisticalDataEngineService<T> statisticalDataEngineService;

    @Resource(name = "StatisticalReport.StatisticalReportService")
    protected StatisticalReportService statisticalReportService;


    public String renderReportPage() {
//        return "/statistical/" + supportType().name().toLowerCase();
        return  supportType().name().toLowerCase();
    }

    protected abstract ReportTypeEnum supportType();

    public boolean isSupport(String type) {
        return supportType().getCode().equalsIgnoreCase(type);
    }

    /**
     * 渲染页面的路径
     *
     * @return
     */

    /**
     * 初始化页面参数
     *
     * @param modelMap
     * @throws ClassNotFoundException
     */
    public void initPageParams(ModelMap modelMap,
                               ReportStatement reportStatement) {
        try {
            setDefaultProperties(reportStatement);
            buildPageModel(reportStatement, modelMap);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    /**
     * 根据不同的渲染试图，设置适合自己的属性
     *
     * @param reportStatement
     */
    protected void setDefaultProperties(ReportStatement reportStatement) {

    }

    public void buildPageModel(ReportStatement reportStatement,
                               ModelMap modelMap) {
        modelMap.put("reportName", reportStatement.getName());
        modelMap.put("script", reportStatement.getScript());
        modelMap.put("conditionItems", reportStatement.getConditionMap().getItems());
        modelMap.put("conditionItemPage", buildCondition(reportStatement));
        modelMap.put("viewMapsItems", buildViewMap(reportStatement));

        modelMap.put("viewItemsJSON", JSONObject.toJSONString(buildViewItems(reportStatement)));
        modelMap.put("viewItems", buildViewItems(reportStatement));

        modelMap.put("viewItemsFrozen", buildViewItemsFrozenOrUnFrozen(reportStatement, true));
        modelMap.put("viewItemsUnFrozen", buildViewItemsFrozenOrUnFrozen(reportStatement, false));

        modelMap.put("conditionMap", reportStatement.getConditionMap());
        String firstColumn = null;
        for (ViewItem viewItem : reportStatement.getViewMap().getItems()) {
            if (viewItem.getShow() == null || viewItem.getShow() != false) {
                firstColumn = viewItem.getColumn();
                break;
            }
        }
        modelMap.put("firstColumn", firstColumn);
        modelMap.put("reportSubType", reportStatement.getSubType());
//        modelMap.put("reportSubType", reportStatement.getSubType()==null?null: reportStatement.getSubType().toLowerCase());

    }

    public Collection<BaseAttr> buildViewMap(ReportStatement reportStatement) {
        //加入一些默认的属性
        return reportStatement.getViewMap().getNodeAttrs().values();
    }

    /**
     * 构建展示列的属性
     *
     * @param reportStatement
     * @return
     */
    protected List<Collection<BaseAttr>> buildViewItems(
            ReportStatement reportStatement) {
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        Collection<ViewComponent> viewComponents = statisticalContext
                .getViewComponents();

        List<Collection<BaseAttr>> resultViewItemAttr = new ArrayList<>();
        List<ViewItem> viewItems = reportStatement.getViewMap().getItems();
        for (ViewItem viewItem : viewItems) {
            Collection<BaseAttr> viewItemAttrs = null;
            for (ViewComponent c : viewComponents) {
                if (c.isSupport(viewItem.getType())) {
                    viewItemAttrs = c.rebuildNodeAttr(viewItem).values();
                    break;
                }
            }
            if (CollectionUtils.isNotEmpty(viewItemAttrs)) {
                resultViewItemAttr.add(viewItemAttrs);
            }
        }
        return resultViewItemAttr;
    }

    protected List<Collection<BaseAttr>> buildViewItemsFrozenOrUnFrozen(
            ReportStatement reportStatement, boolean frozen) {
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        Collection<ViewComponent> viewComponents = statisticalContext
                .getViewComponents();

        List<Collection<BaseAttr>> resultViewItemAttr = new ArrayList<>();
        List<ViewItem> viewItems = reportStatement.getViewMap().getItems();
        for (ViewItem viewItem : viewItems) {
            if (NumberUtils.parseBoolean(viewItem.getFrozen()) != frozen) {
                continue;
            }
            Collection<BaseAttr> viewItemAttrs = null;
            for (ViewComponent c : viewComponents) {
                if (c.isSupport(viewItem.getType())) {
                    viewItemAttrs = c.rebuildNodeAttr(viewItem).values();
                    break;
                }
            }
            if (CollectionUtils.isNotEmpty(viewItemAttrs)) {
                resultViewItemAttr.add(viewItemAttrs);
            }
        }
        return resultViewItemAttr;
    }

    /**
     * 构建条件项标签
     *
     * @param reportStatement
     * @return
     */
    protected Map<String, String> buildCondition(
            ReportStatement reportStatement) {
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        Map<ConditionTypeEnum, ConditionComponent> cons = statisticalContext
                .getConditionComponentMap();

        List<ConditionItem> conditionItems = reportStatement.getConditionMap()
                .getItems();
        Map<String, String> tags = new LinkedHashMap<>();
        for (ConditionItem conditionItem : conditionItems) {
            String tag = null;
            for (ConditionComponent c : cons.values()) {
                if (c.isSupport(conditionItem.getType())) {
                    tag = c.generatorHtml(conditionItem);
                    if (StringUtils.isNotEmpty(tag)) {
                        break;
                    }
                }
            }
            if (StringUtils.isNotEmpty(tag)) {
                tags.put(conditionItem.getName(), tag);
            }
        }
        return tags;
    }

    public List<T> queryList(Map params, ReportStatement reportStatement) {
        return statisticalReportService.queryList(
                reportStatement.getViewMap().getSqlMapperId(),
                reportStatement.getCode(),
                params);
    }

    public StatisticalPagedList<T> queryPagedList(Map params, ReportStatement reportStatement,
                                                  int pageSize, int pageNumber) {

        if (params.containsKey("sort")) {
            String order = "asc";
            if (params.containsKey("order")) {
                order = (String) params.get("order");
            }

            params.put("orderSql", params.get("sort") + " " + order);
        }
        PageInfo pagedList = statisticalReportService.queryPagedList(reportStatement.getViewMap().getSqlMapperId(),
                reportStatement.getCode(), params, pageSize, pageNumber);

        StatisticalPagedList<T> statisticalPagedList = new StatisticalPagedList<T>(pagedList);
        statisticalPagedList.setCurrentPageStatisticalRecord(statisticalDataCurrentPage(params, reportStatement));
        statisticalPagedList.setTotalStatisticalRecord(statisticalData(params, reportStatement));

        return statisticalPagedList;
    }

    /**
     * 页面展示查询数据
     *
     * @param params
     * @param reportStatement
     * @return
     */
    public abstract Object queryPageData(Map params,
                                         ReportStatement reportStatement);

    public int countData(Map params, ReportStatement reportStatement) {
        return statisticalReportService.count(
                reportStatement.getViewMap().getSqlMapperId(),
                reportStatement.getCode(),
                params);
    }

    public T statisticalData(Map params, ReportStatement reportStatement) {
        return (T) statisticalReportService.statisticalData(reportStatement,
                params);
    }


    public T statisticalDataCurrentPage(Map params, ReportStatement reportStatement) {
        Integer pageSize = params.get("pageSize") == null ? null
                : Integer.parseInt((String) params.get("pageSize"));
        Integer pageNumber = params.get("pageNumber") == null ? null
                : Integer.parseInt((String) params.get("pageNumber"));

        if (pageSize == null && pageNumber == null) {
            return (T) statisticalReportService.statisticalData(reportStatement,
                    params);
        }
        pageSize = pageSize == null ? 20 : pageSize;
        pageNumber = pageNumber == null ? 20 : pageNumber;

        return (T) statisticalReportService.statisticalData(reportStatement,
                params, pageSize, pageNumber);

    }
}
