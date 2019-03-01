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
<#--<c:set var="chartClass" value="col-lg-12"/>-->


<#if  (conditionItemPage?size>0) >
<#--<c:set var="chartClass" value="col-lg-9"/>-->
    <div class="col-lg-3">
        <form id="queryForm" class="form" method="post"
              action="${request.contextPath}/statisticalReport/viewChartReport/${type}/${reportSubType}.action">
            <#list conditionItemPage as temp >
                <div class="checkbox">
                    <label>${temp.key}</label>
                ${temp.value}
                </div>
            </#list>
            <button type="button" onclick="renderPage();" class="btn btn-default">提交</button>
        </form>
    </div>
</#if>
    <div class="col-lg-12">
        <div id="echart" style="width: 100%;height:650px;"></div>
    </div>
</div>

<link rel="stylesheet" href="${request.contextPath}/js/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css">
<script type="text/javascript" src="${request.contextPath}/js/jquery-1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/datePicker/WdatePicker.js" type="text/javascript"
        charset="utf-8"></script>

<script type="text/javascript" src="${request.contextPath}/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>


<#--
<script type="text/javascript" src="${request.contextPath}/js/echarts/echarts-all.js"></script>
--%>
<%--
<script type="text/javascript" src="${request.contextPath}/js/echarts/asset/js/esl/esl.js"></script>
-->
<script type="text/javascript" src="${request.contextPath}/js/echarts/echarts.js"></script>
<#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/dark.js"></script>-->
<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/shine.js"></script>
<#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/roma.js"></script>-->
<#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/macarons.js"></script>-->
<#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/infographic.js"></script>
<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/vintage.js"></script>-->

<script type="text/javascript" src="${request.contextPath}/js/echarts/EchartTools.js"></script>


<div id="toc"></div>
<script type="text/javascript">
    renderPage();

    function getParams() {
        var params = {};
    <#list conditionItems as item>
        params["${item.id}"] = $("#${item.id}").val();
    </#list>
        return params;
    }
    function renderPage() {
        $.post(
                '${request.contextPath}/statisticalReport/queryData/${reportCode}.action',

                getParams(),
                function (data) {
                    var echartData = new Array();
                    for (var o in data) {
                        var tempData = data[o];
                        var echartObj = {name: tempData.NAME, value: tempData.VALUE, group: tempData.GROUP};
                        echartData.push(echartObj);
                    }
                    var option = ECharts.ChartOptionTemplates.${reportSubType}(echartData, "${reportName}", true);
                    var container = $("#echart")[0];
                    opt = ECharts.ChartConfig(container, option);

                    ECharts.Charts.RenderChart(opt, "shine");

                    //初始化页面的值
                    loadParamValue();
                }
        )

    }


    function loadParamValue() {
        var els;
    <#if (paramMap?? && paramMap ?size>0)>
        <#list paramMap as tempParam>
            els = document.getElementsByName('${tempParam.key}');
            for (inx in els) {
                $(els[inx]).val("${tempParam.value}");
            }
        </#list>
    </#if>
    }

</script>


</body>
</html>