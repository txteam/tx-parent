package com.tx.component.statistical.controller;

import com.tx.component.statistical.context.StatisticalContext;
import com.tx.component.statistical.mapping.ReportStatement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("")
    public String index(ModelMap modelMap) {

        StatisticalContext statisticalContext = StatisticalContext.getContext();
        Map<String, ReportStatement> reportStatementMap = ReportStatement.getReportStatementMap();
        modelMap.put("reports", reportStatementMap.values());
        return "index";
    }
}
