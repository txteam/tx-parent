package com.tx.component.statistical.component.reportRenderComponent.impl;

import com.tx.component.statistical.component.reportRenderComponent.ReportTypeEnum;
import com.tx.component.statistical.mapping.ReportStatement;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/20.
 */
@Component("pagedGridReportRenderHandler")
public class PagedGridReportRenderHandler
        extends AbstractReportRenderHandler<Map<String, Object>> {

    public ReportTypeEnum supportType() {
        return ReportTypeEnum.PAGED_LIST_GRID;
    }

    public Object queryPageData(Map params,
            ReportStatement reportStatement) {
        int pageSize = params.get("pageSize") == null ? 20
                : Integer.parseInt((String) params.get("pageSize"));
        int pageIndex = params.get("pageNumber") == null ? 1
                : Integer.parseInt((String) params.get("pageNumber"));
        
        return super.queryPagedList(params,
                reportStatement,
                pageSize,
                pageIndex);
    }
    
}
