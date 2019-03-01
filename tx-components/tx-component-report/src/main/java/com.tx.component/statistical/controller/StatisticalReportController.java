package com.tx.component.statistical.controller;

import com.tx.component.statistical.component.reportRenderComponent.impl.AbstractReportRenderHandler;
import com.tx.component.statistical.context.StatisticalContext;
import com.tx.component.statistical.mapping.ReportStatement;
import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.model.StatisticalPagedList;
import com.tx.component.statistical.utils.EnumUtil;
import com.tx.core.exceptions.util.AssertUtils;
import com.tx.core.support.poi.excel.ExcelTypeEnum;
import com.tx.core.support.poi.excel.model.ExportExcelModel;
import com.tx.core.util.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import org.apache.tools.ant.Project;
//import org.apache.tools.ant.taskdefs.Zip;
//import org.apache.tools.ant.types.FileSet;

/**
 * Created by SEELE on 2016/6/27.
 */
@Controller
@RequestMapping("statisticalReport")
public class StatisticalReportController {
    protected Logger logger = LoggerFactory
            .getLogger(StatisticalReportController.class);


    @RequestMapping("reloadAllReport/{reportCode}")
    public String reloadAllReport(HttpServletRequest request,
                                  @PathVariable("reportCode") String reportCode, ModelMap response) {
        StatisticalContext statisticalContext = StatisticalContext.getContext();

        try {
            statisticalContext.reRegisterResource();
        } catch (Exception e) {
            logger.error("重载报表失败", e);
        }
        return toViewReport(request, reportCode, response);
    }


    @RequestMapping("reload/{reportCode}")
    public String toViewAndReloadReport(HttpServletRequest request,
                                        @PathVariable("reportCode") String reportCode, ModelMap response) {

        StatisticalContext statisticalContext = StatisticalContext.getContext();

        try {
            statisticalContext.reloadReport(reportCode);
        } catch (Exception e) {
            logger.error("重载报表失败", e);
        }
        return toViewReport(request, reportCode, response);
    }

    @RequestMapping("toView/{reportCode}")
    public String toViewReport(HttpServletRequest request,
                               @PathVariable("reportCode") String reportCode, ModelMap response) {
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportTypeHandler = statisticalContext
                .getReportRenderHandler(reportCode);

        response.put("conditionMap",
                reportStatement.getConditionMap().getItems());
        response.put("viewMap", reportStatement.getViewMap().getItems());
        response.put("paramMap", getParameterMap(request));
        response.put("reportCode", reportCode);
        abstractReportTypeHandler.initPageParams(response, reportStatement);

        return abstractReportTypeHandler.renderReportPage();
    }

    @ResponseBody
    @RequestMapping("queryData/{reportCode}")
    public Object queryData(HttpServletRequest request,
                            @PathVariable("reportCode") String reportCode,
                            ModelMap response) {
        Map params = getParameterMap(request);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportRenderHandler = statisticalContext
                .getReportRenderHandler(reportCode);
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        Object object = abstractReportRenderHandler.queryPageData(params,
                reportStatement);
        return object;
    }

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    private Map<String, Object> getParameterMap(HttpServletRequest request) {
        AssertUtils.notNull(request, "request is null.");
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> result = new HashMap<>();
        result.putAll(params);
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
//            if (entry.getValue() instanceof String[]) {
            String[] strs = entry.getValue();
            if (strs.length == 1) {
                result.put(entry.getKey(), strs[0]);
            }
//            }
        }
        return result;
    }

    /**
     * 数据统计
     *
     * @param request
     * @param reportCode
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("viewStatistical/{reportCode}")
    public Object viewStatistical(HttpServletRequest request,
                                  @PathVariable("reportCode") String reportCode, ModelMap response) {
        Map params = getParameterMap(request);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportRenderHandler = statisticalContext
                .getReportRenderHandler(reportCode);
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        return abstractReportRenderHandler.statisticalData(params,
                reportStatement);
    }

    //需要实现分页
    @ResponseBody
    @RequestMapping("toExportReportFile/{reportCode}")
    public Map<String, String> exportReport(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @PathVariable("reportCode") String reportCode) {

        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportRenderHandler = statisticalContext
                .getReportRenderHandler(reportCode);
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        Map params = getParameterMap(request);
        int cnt = abstractReportRenderHandler.countData(params,
                reportStatement);

//        Collection viewNodeMap = abstractReportRenderHandler
//                .buildViewMap(reportStatement);

        String filePathRoot = request.getSession()
                .getServletContext()
                .getRealPath("/");
        String fileRelativePath = "export/statisticalReport/xls/" + reportCode
                + System.currentTimeMillis() + "/";
        String fileName = null;
        int xlsSize = 20000;


        for (int i = 1; i <= Math.ceil(cnt / (float) xlsSize); i++) {
            StatisticalPagedList<Map<String, Object>> pagedList = abstractReportRenderHandler
                    .queryPagedList(params, reportStatement, xlsSize, i);
            List list = pagedList.getList();
//            list.add();
            StatisticalReportExcel excel = new StatisticalReportExcel(
                    list,
                    reportStatement.getViewMap().getItems());
            excel.setSheet1Name("统计数据_" + i);

            Map<String, Object> total = pagedList.getTotalStatisticalRecord();
            excel.buildExcel();
            excel.appendRow("统计数据_" + i, total);

            fileName = fileRelativePath + reportCode + "["
                    + ((i - 1) * xlsSize + 1) + "-" + ((i) * xlsSize) + "].xls";

            File file = new File(filePathRoot + fileName);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                excel.getWorkbook().write(fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                logger.error("文件生成失败", e);
            }
        }
        Map<String, String> res = new HashMap<>();
        if (cnt > xlsSize) {
            String zipFileName = "export/statisticalReport/zip/" + reportCode
                    + System.currentTimeMillis() + ".zip";

            File zipFile = new File(filePathRoot + zipFileName);
            File dirFile = new File(filePathRoot + fileRelativePath);

//            Zip zip = new Zip();
//            Project prj = new Project();
//            zip.setProject(prj);
//            zip.setDestFile(zipFile);
//            FileSet fileSet = new FileSet();
//            fileSet.setProject(prj);
//            fileSet.setDir(dirFile);
//            zip.addFileset(fileSet);
//            zip.execute();
//            dirFile.delete();
//
//            res.put("fileName", zipFileName);

        } else {
            res.put("fileName", fileName);
        }
        return res;
    }

    @RequestMapping("downloadReportFile")
    public void downloadReportFile(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "fileName", required = false) String fileName) {
        response.reset(); // 非常重要
        String filePath = request.getSession()
                .getServletContext()
                .getRealPath("/");
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            // 失败
        }

        response.setContentType("application/vnd.ms-excel");
        String codedFileName = null;
        try {
            codedFileName = "" + file.getName();
            // response.setContentType("application/vnd.ms-excel");
            codedFileName = new String(codedFileName.getBytes(), "ISO8859-1");
            response.setHeader("content-disposition",
                    "attachment;filename=" + codedFileName);

            BufferedInputStream br = new BufferedInputStream(
                    new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            OutputStream out = response.getOutputStream();
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
            br.close();
            out.close();

            // 清理已经下载的文件
            file.delete();

        } catch (Exception e) {
            logger.error("文件生成失败", e);

        }
    }

    private static class StatisticalReportExcel
            extends ExportExcelModel<Map<String, Object>> {
        List<ViewItem> viewItems;

        public StatisticalReportExcel(List<Map<String, Object>> dataList,
                                      List<ViewItem> fieldNodes) {
            super(dataList);
            this.viewItems = fieldNodes;
        }

        public StatisticalReportExcel(ExcelTypeEnum excelType,
                                      List<Map<String, Object>> dataList, List<ViewItem> fieldNodes) {
            super(excelType, dataList);
            this.viewItems = fieldNodes;

        }

        @Override
        protected void doWriteSheet1Row(int arg0, Row row,
                                        Map<String, Object> por) {

            doWriteSheet1Row(arg0, row, por, false);

        }

        private void doWriteSheet1Row(int arg0, Row row,
                                      Map<String, Object> por, boolean footer) {
            int column = 0;

            boolean first = true;
            for (ViewItem viewItem : viewItems) {
                Boolean show = viewItem.getShow();
                Object value = por.get(viewItem.getColumn());
                if (show == null || show != false) {
                    if (footer && first) {
                        value = "统  计";
                    }
                    first = false;

                    if (value != null) {
                        String enumClass = viewItem.getEnumClass();
                        if (StringUtils.isNotEmpty(viewItem.getEnumClass())) {
                            try {
                                Map<String, Enum<?>> enums = EnumUtil
                                        .parseEnum(enumClass);
                                Enum enumObj = enums.get(value);
                                value = EnumUtil.getFieldValue(enumObj,
                                        viewItem.getRefValue());

                            } catch (ClassNotFoundException e) {

                            }
                        }
                    }
                    row.createCell(column++).setCellValue(
                            value == null ? "" : value.toString());
                }

            }

        }


        @Override
        protected int writeSheet1Header(Sheet sheet) {
            Row row = sheet.createRow(0);
            int column = 0;
            for (ViewItem viewItem : viewItems) {
                Boolean showpage = viewItem.getShow();
                if (showpage == null || showpage != false) {
                    Cell cell = row.createCell(column++);
                    cell.setCellValue(viewItem.getName() == null
                            ? viewItem.getColumn() : viewItem.getName());
                }
            }
            return 1;
        }

        public void appendRow(String sheetName, Map<String, Object> por) {
            Sheet sheet = getWorkbook().getSheet(sheetName);
            ;
            Row row = sheet.createRow(sheet.getLastRowNum() + 2);


            doWriteSheet1Row(1, row, por, true);
            for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
                Cell cell = it.next();
                CellStyle cellStyle = getWorkbook().createCellStyle();
                cellStyle.setFillBackgroundColor((short) 12);
                cell.setCellStyle(cellStyle);
            }
        }

        /**
         * 颜色
         * @return
         *//*
           private CellStyle getHeaderCellStyle() {
           CellStyle cellStyle = getWorkbook().createCellStyle();
           cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
           cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
           return cellStyle;
           }
           
           private CellStyle getMoneyCellStyle() {
           CellStyle cellStyle = getWorkbook().createCellStyle();
           //  cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
           Font font = getWorkbook().createFont();
           font.setFontHeightInPoints((short) 24); //字体大小
           font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
           font.setColor(HSSFColor.ORANGE.index);
           cellStyle.setFont(font);
           return cellStyle;
           }
           
           }
           
           @RequestMapping("viewChartReport/{reportCode}")
           public String viewChartReport(HttpServletRequest request, ModelMap response,
           @PathVariable("reportCode") String reportCode) {
           
           return viewChartReport(request,response,reportCode,null);
           
           }
           
           @RequestMapping("viewChartReport/{reportCode}/{chartType}")
           public String viewChartReport(HttpServletRequest request, ModelMap response,
                                @PathVariable("reportCode") String reportCode,@PathVariable("chartType") String chartType) {
           Map<String, Object> map = getParameterMap(request);
           StatisticalContext statisticalContext = StatisticalContext.getContext();
           List<ChartInfo> result = statisticalContext.queryChartList(map, reportCode);
           
           StatisticalReportConfig reportConfig = statisticalContext
              .getReportConfig(reportCode);
           
           response.put("resultData", result);
           response.put("paramMap", getParameterMap(request));
           response.put("reportCode", reportCode);
           response.put("reportName", reportConfig.getName());
           if (reportConfig.getQueriers() != null) {
           response.put("queriers",
                  reportConfig.getQueriers().getHtmlTagNodes());
           }
           
           Set<String> supportChartType = new HashSet<>();
           supportChartType.add("pie");
           supportChartType.add("bars");
           supportChartType.add("lines");
           supportChartType.add("map");
           
           if(StringUtils.isEmpty(chartType) || !supportChartType.contains(chartType.toLowerCase())) {
           response.put("showChartType", reportConfig.getType().toLowerCase());
           }else{
           response.put("showChartType", chartType.toLowerCase());
           }
           return "/statistical/chartReportView";
           
           }
           
           @ResponseBody
           @RequestMapping("ajax/getResult/{reportCode}/{id}")
           public Map<String, Object> ajaxGetResult(HttpServletRequest request,
           ModelMap response, @PathVariable("reportCode") String reportCode,
           @PathVariable("id") String id) {
           Map<String, Object> map = getParameterMap(request);
           StatisticalContext statisticalContext = StatisticalContext.getContext();
           Map<String, Object> result = statisticalContext.findResultForMap(reportCode,
              map,
              id);
           
           return result;
           
           }*/


    }
}
