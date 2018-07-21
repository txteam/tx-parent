<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${reportName}</title>
    <link rel="stylesheet" href="${request.contextPath}/js/jquery-ui/css/no-theme/jquery-ui-1.10.4.custom.css"
          type="text/css">
    <link rel="stylesheet" id="easyuiTheme"
          href="${request.contextPath}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css">
    <link rel="stylesheet" href="${request.contextPath}/js/jquery-easyui-extension/icons.css" type="text/css">
    <link rel="stylesheet" href="${request.contextPath}/style/extEasyUIIcon.css" type="text/css">
<#--<link rel="stylesheet" href="${request.contextPath}/js/validator-0.8.1/jquery.validator.css" type="text/css">-->
<#--<link rel="stylesheet" href="${request.contextPath}/js/components/fileupload/jquery.fileupload-ui.css">-->
    <link rel="stylesheet" href="${request.contextPath}/css/commons.css">


    <!-- jquery -->
    <script type="text/javascript" src="${request.contextPath}/js/jquery.min.js" type="text/javascript"
            charset="utf-8"></script>
    <!-- jqueryui -->
    <script type="text/javascript" src="${request.contextPath}/js/jquery-ui/js/jquery-ui-1.10.4.custom.js"
            charset="utf-8"></script>
    <!-- easyui -->
    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-1.3.5/plugins/jquery.layout.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-extension/jquery.jdirk.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/js/jquery-easyui-extension/jeasyui.extensions.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/js/jquery-easyui-extension/jeasyui.extensions.menu.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/js/jquery-easyui-extension/jeasyui.extensions.datagrid.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/js/extEasyUI.js" charset="utf-8"></script>

    <!-- other -->
    <script type="text/javascript" src="${request.contextPath}/js/datePicker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript" src="${request.contextPath}/js/commons.js" charset="utf-8"></script>

    <style>
        /*.table-condition td {*/
        /*width: 25%;*/
        /*}*/

        .table-condition th {
            width: 7%;
        }

        .table-condition .startInput {
            width: 40%;
        }

        .table-condition .endInput {
            width: 40%;
        }

        /*table td input, table td select{
            width: 96%;
        }*/
    </style>
    <script>
        $(document).ready(function () {

//
//            function number(cellValue,row,index){
//                return "<span style='text-align:right'>"+cellValue+" &nbsp;&nbsp;</span>"
//            }
        });
    </script>
    <script type="text/javascript">
        var grid = null;
        var _contextPath = "${request.contextPath}";

        //页脚扩展-支持背景色修改
        var myview = $.extend({}, $.fn.datagrid.defaults.view, {
            renderFooter: function (target, container, frozen) {
                var opts = $.data(target, 'datagrid').options;
                var rows = $.data(target, 'datagrid').footer || [];
                var fields = $(target).datagrid('getColumnFields', frozen);
                var table = ['<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>'];

                for (var i = 0; i < rows.length; i++) {
                    var styleValue = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';
                    var style = styleValue ? 'style="' + styleValue + '"' : '';
                    table.push('<tr class="datagrid-row" datagrid-row-index="' + i + '"' + style + '>');
                    table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
                    table.push('</tr>');
                }
                table.push('</tbody></table>');
                $(container).html(table.join(''));
            }
        });

        $(document).ready(function () {
                grid = $('#grid').datagrid({
            url: '${request.contextPath}/statisticalReport/queryData/${reportCode}.action',
            view: myview,
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            checkOnSelect: false,
            selectOnCheck: false,
            nowrap: false,
            striped: true,
            singleSelect: true,
            pagination: true,
            showFooter: true,
            pageSize: 15,
            pageList: [15, 50, 100, 500, 1000, 2000],
            rownumbers: true,
            loadFilter: function (data) {
                var res = {total: 0, rows: [], footer: []};
                if (data != null && data.list != null) {
                    res['total'] = data.count;
                    res['rows'] = data.list;
                    if (data.currentPageStatisticalRecord != null) {
                        data["${firstColumn}"] = "<br/><b>总&nbsp;&nbsp;&nbsp;&nbsp;计</b>"
                        var currentPageStatisticalRecord = data.currentPageStatisticalRecord;
                        var totalStatisticalRecord = data.totalStatisticalRecord;
                        currentPageStatisticalRecord["${firstColumn}"] = "<br/><b>本页统计</b>";
                        totalStatisticalRecord["${firstColumn}"] = "<br/><b>总&nbsp;&nbsp;&nbsp;&nbsp;计</b>";

                        currentPageStatisticalRecord.grid_footer = true;
                        totalStatisticalRecord.grid_footer = true;

                        res['footer'] = [currentPageStatisticalRecord, totalStatisticalRecord];
                    }

                }
                return res;
            },
            rowStyler: function (index, row) {
                if (typeof row.grid_footer != 'undefined') {
                    return 'background-color:#EFEFEF;color:black;font-weight:bolder;';
                }

            },
        frozenColumns: [[
        <#list viewItemsFrozen as item >
            {
            <#list item as attr >
            ${attr.key}:<#if attr.obj>${attr.value} <#else >"${attr.value}" </#if>,
            <#--${attr.key}:<#if boolean_format(attr.obj)>${attr.value}<#else >"${attr.value}"</#if>-->
            </#list>
        }
            <#if item_has_next>,</#if>
        </#list>
            ]],
                columns: [[
        <#list viewItemsUnFrozen as item >
            {
            <#list item as attr >
            ${attr.key}:<#if attr.obj>${attr.value} <#else >"${attr.value}" </#if>,
            <#--${attr.key}:<#if boolean_format(attr.obj)>${attr.value}<#else >"${attr.value}"</#if>-->
            </#list>
        }
            <#if item_has_next>,</#if>
        </#list>
            ]],
            toolbar: '#toolbar',
                    onLoadSuccess
            :
            function () {
                $(this).datagrid('unselectAll');
                $(this).datagrid('tooltip');

            }
        })
            ;

        });

        function fmoney(s, n) {
            if (typeof  s == 'undefined') {
                return '';
            }
            if ((s + "").indexOf(".") < 0) {
                return s + ".00";
            }

            if (typeof s == 'undefined' || s == '') {
                return 0;
            }
            n = n > 0 && n <= 20 ? n : 2;
            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
            var l = s.split(".")[0].split("").reverse(),
                    r = s.split(".")[1];
            t = "";
            for (i = 0; i < l.length; i++) {
                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
            }
            return t.split("").reverse().join("") + "." + r;
        }

        /*
         * 查询
         */
        function queryFun() {
            grid.datagrid('load', $('#queryForm').serializeObject());
            return false;
        }
        function exportExcel() {
            DialogUtils.confirm("确认提醒", "确认导出?",
                    function (data) {
                        if (data) {
                            DialogUtils.progress({
                                text: '请求提交中，请等待....'
                            });
                            $.post(
                                    "${request.contextPath}/statisticalReport/toExportReportFile/${reportCode}.action",
                                    $("#queryForm").serializeObject(),
                                    function (data) {
                                        DialogUtils.progress('close');
                                        $("#queryForm").attr("action", "${request.contextPath}/statisticalReport/downloadReportFile.action?fileName=" + data.fileName);
                                        $("#queryForm").attr("method", "post");
                                        $("#queryForm").submit();
                                    }
                            );
                        }
                    }
            );
        }

        ${script}
    </script>
</head>
<body>

<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 150px; overflow: hidden; width: 100%">
        <form id="queryForm" class="form">
            <table class="table table-hover table-condensed table-condition">
            <#assign conditionItemPage_index = 0/>
            <#list  conditionItemPage as key,value >
                <#if (conditionItemPage_index % conditionMap.cols?number ==0)>
                <tr style="width:${100/conditionMap.cols?number}%"></#if>
                <th>${key}</th>
                <td>${value}</td>
                <#if (conditionItemPage_index % conditionMap.cols?number == (conditionMap.cols?number-1))></tr></#if>
                <#assign conditionItemPage_index = conditionItemPage_index+1/>
            </#list>
                <tr>

                    <td colspan="${conditionMap.cols?number * 2}" class="button operRow">
                        <a id="queryBtn" onclick="queryFun();return false;" href="javascript:void(0);"
                           class="easyui-linkbutton" data-options="iconCls:'search'">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false">
        <table id="grid"></table>
    </div>

    <div id="toolbar" style="display: none;">
        <a onclick="exportExcel();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'database'">导出为Excel</a>
        <a onclick="grid.datagrid('reload');return false;" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'transmit'">刷新</a>
    </div>
</div>
</body>
</html>