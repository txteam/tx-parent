<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${reportName}</title>
    <style>
        .table-condition td {
            width: 25%;
        }

        .table-condition th {
            width: 7%;
        }

        .table-condition .startInput {
            width: 40%;
        }

        .table-condition .endInput {
            width: 40%;
        }
    </style>


</head>
<body>

<div class="">
    <c:set var="chartClass" value="col-lg-12"/>

    <c:if test="${fn:length(conditionItemPage)>0}">
        <c:set var="chartClass" value="col-lg-9"/>
        <div class="col-lg-3">
            <form id="queryForm" class="form" method="post"
                  action="${contextPath}/statisticalReport/viewChartReport/${type}/${reportSubType}.action">
                <c:forEach items="${conditionItemPage}" var="temp" varStatus="cols">
                    <div class="checkbox">
                        <label>${temp.key}</label>
                            ${temp.value}
                    </div>
                </c:forEach>


                <button type="button" onclick="renderPage();" class="btn btn-default">提交</button>
            </form>
        </div>
    </c:if>
    <div class="${chartClass}">
        <div id="echart" style="width: 100%;height:650px;"></div>
    </div>
</div>

<link rel="stylesheet" href="${contextPath}/js/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css">
<script type="text/javascript" src="${contextPath}/js/jquery-1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/datePicker/WdatePicker.js" type="text/javascript"
        charset="utf-8"></script>

<script type="text/javascript" src="${contextPath}/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>


<%--<script type="text/javascript" src="${contextPath}/js/echarts/echarts-all.js"></script>--%>
<%--<script type="text/javascript" src="${contextPath}/js/echarts/asset/js/esl/esl.js"></script>--%>
<script type="text/javascript" src="${contextPath}/js/echarts/echarts.js"></script>
<script type="text/javascript" src="${contextPath}/js/echarts/theme/dark.js"></script>
<script type="text/javascript" src="${contextPath}/js/echarts/theme/shine.js"></script>
<script type="text/javascript" src="${contextPath}/js/echarts/theme/roma.js"></script>
<script type="text/javascript" src="${contextPath}/js/echarts/theme/macarons.js"></script>
<script type="text/javascript" src="${contextPath}/js/echarts/theme/infographic.js"></script>
<script type="text/javascript" src="${contextPath}/js/echarts/theme/vintage.js/echarts/vintage.js"></script>

<script type="text/javascript" src="${contextPath}/js/echarts/EchartTools.js"></script>


<div id="toc"></div>
<script type="text/javascript">
    renderPage();

    function getParams() {
        var params = {};
        <c:forEach items="${conditionItems}" var="item">
        params["${item.id}"] = $("#${item.id}").val();
        </c:forEach>
        return params;
    }
    function renderPage() {
        $.post(
            '${contextPath}/statisticalReport/queryData/${reportCode}.action',

            getParams(),
            function (data) {
                var echartData = new Array();
                for (var o in data) {
                    var tempData = data[o];
                    var echartObj = {name: tempData.NAME, value: tempData.VALUE, group: tempData.GROUP};
                    echartData.push(echartObj);
                }
                var option = ECharts.ChartOptionTemplates.${reportSubType}(echartData, "${reportName}",true);
                var container = $("#echart")[0];
                opt = ECharts.ChartConfig(container, option);

                ECharts.Charts.RenderChart(opt,"shine");

                //初始化页面的值
                loadParamValue();
            }
        )

    }


    function loadParamValue() {
        var els;
        <c:forEach  items="${paramMap}" var="tempParam">
        els = document.getElementsByName('${tempParam.key}');
        for (inx in els) {
            $(els[inx]).val("${tempParam.value}");
        }
        </c:forEach>

    }

</script>


</body>
</html>