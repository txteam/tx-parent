<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${reportName}</title>

    <link rel="stylesheet" href="${request.contextPath}/js/bootstrap-3.3.7/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${request.contextPath}/css/datatables.min.css" type="text/css">
    <script type="text/javascript" src="${request.contextPath}/js/jquery-1.11.3.js" type="text/javascript"
            charset="utf-8"></script>

    <script type="text/javascript" src="${request.contextPath}/js/bootstrap-3.3.7/js/bootstrap.js"
            type="text/javascript"
            charset="utf-8"></script>

    <script type="text/javascript" src="${request.contextPath}/js/datatables.min.js" type="text/javascript"
            charset="utf-8"></script>


    <#--    <link rel="stylesheet" href="${request.contextPath}/js/jquery-ui/css/no-theme/jquery-ui-1.10.4.custom.css"-->
    <#--          type="text/css">-->
    <#--    <link rel="stylesheet" id="easyuiTheme"-->
    <#--          href="${request.contextPath}/js/jquery-easyui-1.3.5/themes/bootstrap/easyui.css" type="text/css">-->
    <#--    <link rel="stylesheet" href="${request.contextPath}/js/jquery-easyui-extension/icons.css" type="text/css">-->
    <#--    <link rel="stylesheet" href="${request.contextPath}/style/extEasyUIIcon.css" type="text/css">-->
    <#--&lt;#&ndash;<link rel="stylesheet" href="${request.contextPath}/js/validator-0.8.1/jquery.validator.css" type="text/css">&ndash;&gt;-->
    <#--&lt;#&ndash;<link rel="stylesheet" href="${request.contextPath}/js/components/fileupload/jquery.fileupload-ui.css">&ndash;&gt;-->
    <#--    <link rel="stylesheet" href="${request.contextPath}/css/commons.css">-->


    <#--    <!-- jquery &ndash;&gt;-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/jquery.min.js" type="text/javascript"-->
    <#--            charset="utf-8"></script>-->
    <#--    <!-- jqueryui &ndash;&gt;-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/jquery-ui/js/jquery-ui-1.10.4.custom.js"-->
    <#--            charset="utf-8"></script>-->
    <#--    <!-- easyui &ndash;&gt;-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"-->
    <#--            charset="utf-8"></script>-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"-->
    <#--            charset="utf-8"></script>-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-1.3.5/plugins/jquery.layout.js"-->
    <#--            charset="utf-8"></script>-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/jquery-easyui-extension/jquery.jdirk.min.js"></script>-->
    <#--    <script type="text/javascript"-->
    <#--            src="${request.contextPath}/js/jquery-easyui-extension/jeasyui.extensions.min.js"></script>-->
    <#--    <script type="text/javascript"-->
    <#--            src="${request.contextPath}/js/jquery-easyui-extension/jeasyui.extensions.menu.min.js"></script>-->
    <#--    <script type="text/javascript"-->
    <#--            src="${request.contextPath}/js/jquery-easyui-extension/jeasyui.extensions.datagrid.min.js"></script>-->
    <#--    <script type="text/javascript" src="${request.contextPath}/js/extEasyUI.js" charset="utf-8"></script>-->

    <!-- other -->
<#--    <script type="text/javascript" src="${request.contextPath}/js/datePicker/WdatePicker.js" type="text/javascript"-->
<#--            charset="utf-8"></script>-->

<#--    <script type="text/javascript" src="${request.contextPath}/js/commons.js" charset="utf-8"></script>-->


    <script>
        $(document).ready(function () {
            var _contextPath = "${request.contextPath}";


            $('.dataTables-example').DataTable({
                "ajax": {
                    "url": "${request.contextPath}/statisticalReport/queryData/${reportCode}",
                    "dataSrc": "list"
                },

                "columns": [
                    <#list viewItemsUnFrozen as item >
                    {   <#list item as attr >
                        <#if !attr.obj>
                    "${attr.key}":   "${attr.value}" ,
                        </#if>
                    <#--{ "data": "${item.field}" },-->
                    </#list> },
                    </#list>

                ],


                pageLength: 10,
                responsive: true,
                dom: '<"html5buttons"B>lTfgitp',
                buttons: [
                    // {extend: 'copy'},
                    {extend: 'csv'},
                    {extend: 'excel', title: 'ExampleFile'},
                    {extend: 'pdf', title: 'ExampleFile'},

                    {
                        extend: 'print',
                        customize: function (win) {
                            $(win.document.body).addClass('white-bg');
                            $(win.document.body).css('font-size', '10px');

                            $(win.document.body).find('table')
                                .addClass('compact')
                                .css('font-size', 'inherit');
                        }
                    }
                ],
                "language": {
                    "sProcessing": "处理中...",
                    "sLengthMenu": "显示 _MENU_ 项结果",
                    "sZeroRecords": "没有匹配结果",
                    "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                    "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                    "sInfoPostFix": "",
                    "sSearch": "搜索:",
                    "sUrl": "",
                    "sEmptyTable": "表中数据为空",
                    "sLoadingRecords": "载入中...",
                    "sInfoThousands": ",",
                    "oPaginate": {
                        "sFirst": "首页",
                        "sPrevious": "上页",
                        "sNext": "下页",
                        "sLast": "末页"
                    },
                    "oAria": {
                        "sSortAscending": ": 以升序排列此列",
                        "sSortDescending": ": 以降序排列此列"
                    }
                }


            });


        });
    </script>
<#--    <script type="text/javascript">-->
<#--        var grid = null;-->
<#--        var _contextPath = "${request.contextPath}";-->

<#--        //页脚扩展-支持背景色修改-->
<#--        var myview = $.extend({}, $.fn.datagrid.defaults.view, {-->
<#--            renderFooter: function (target, container, frozen) {-->
<#--                var opts = $.data(target, 'datagrid').options;-->
<#--                var rows = $.data(target, 'datagrid').footer || [];-->
<#--                var fields = $(target).datagrid('getColumnFields', frozen);-->
<#--                var table = ['<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>'];-->

<#--                for (var i = 0; i < rows.length; i++) {-->
<#--                    var styleValue = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';-->
<#--                    var style = styleValue ? 'style="' + styleValue + '"' : '';-->
<#--                    table.push('<tr class="datagrid-row" datagrid-row-index="' + i + '"' + style + '>');-->
<#--                    table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));-->
<#--                    table.push('</tr>');-->
<#--                }-->
<#--                table.push('</tbody></table>');-->
<#--                $(container).html(table.join(''));-->
<#--            }-->
<#--        });-->

<#--        $(document).ready(function () {-->
<#--            grid = $('#grid').datagrid({-->
<#--                url: '${request.contextPath}/statisticalReport/queryData/${reportCode}',-->
<#--                view: myview,-->
<#--                fit: true,-->
<#--                fitColumns: true,-->
<#--                border: false,-->
<#--                idField: 'id',-->
<#--                checkOnSelect: false,-->
<#--                selectOnCheck: false,-->
<#--                nowrap: false,-->
<#--                striped: true,-->
<#--                singleSelect: true,-->
<#--                pagination: true,-->
<#--                showFooter: true,-->
<#--                pageSize: 15,-->
<#--                pageList: [15, 50, 100, 500, 1000, 2000],-->
<#--                rownumbers: true,-->
<#--                loadFilter: function (data) {-->
<#--                    var res = {total: 0, rows: [], footer: []};-->
<#--                    if (data != null && data.list != null) {-->
<#--                        res['total'] = data.count;-->
<#--                        res['rows'] = data.list;-->
<#--                        if (data.currentPageStatisticalRecord != null) {-->
<#--                            data["${firstColumn}"] = "<br/><b>总&nbsp;&nbsp;&nbsp;&nbsp;计</b>"-->
<#--                            var currentPageStatisticalRecord = data.currentPageStatisticalRecord;-->
<#--                            var totalStatisticalRecord = data.totalStatisticalRecord;-->
<#--                            currentPageStatisticalRecord["${firstColumn}"] = "<br/><b>本页统计</b>";-->
<#--                            totalStatisticalRecord["${firstColumn}"] = "<br/><b>总&nbsp;&nbsp;&nbsp;&nbsp;计</b>";-->

<#--                            currentPageStatisticalRecord.grid_footer = true;-->
<#--                            totalStatisticalRecord.grid_footer = true;-->

<#--                            res['footer'] = [currentPageStatisticalRecord, totalStatisticalRecord];-->
<#--                        }-->

<#--                    }-->
<#--                    return res;-->
<#--                },-->
<#--                rowStyler: function (index, row) {-->
<#--                    if (typeof row.grid_footer != 'undefined') {-->
<#--                        return 'background-color:#EFEFEF;color:black;font-weight:bolder;';-->
<#--                    }-->

<#--                },-->
<#--                frozenColumns: [[-->
<#--                    <#list viewItemsFrozen as item >-->
<#--                    {-->
<#--            <#list item as attr >-->
<#--            ${attr.key}:<#if attr.obj>${attr.value}-->
<#--            <#else >"${attr.value}" </#if>,-->
<#--            &lt;#&ndash;${attr.key}:<#if boolean_format(attr.obj)>${attr.value}<#else >"${attr.value}"</#if>&ndash;&gt;-->
<#--            </#list>-->
<#--        }-->
<#--            <#if item_has_next>,</#if>-->
<#--            </#list>-->
<#--        ]],-->
<#--            columns: [[-->
<#--                <#list viewItemsUnFrozen as item >-->
<#--                {-->
<#--            <#list item as attr >-->
<#--            ${attr.key}:<#if attr.obj>${attr.value}-->
<#--            <#else >"${attr.value}" </#if>,-->
<#--            &lt;#&ndash;${attr.key}:<#if boolean_format(attr.obj)>${attr.value}<#else >"${attr.value}"</#if>&ndash;&gt;-->
<#--            </#list>-->
<#--        }-->
<#--            <#if item_has_next>,</#if>-->
<#--            </#list>-->
<#--        ]],-->
<#--            toolbar: '#toolbar',-->
<#--                onLoadSuccess-->
<#--        :-->

<#--            function () {-->
<#--                $(this).datagrid('unselectAll');-->
<#--                $(this).datagrid('tooltip');-->

<#--            }-->
<#--        })-->
<#--            ;-->

<#--        });-->

<#--        function fmoney(s, n) {-->
<#--            if (typeof s == 'undefined') {-->
<#--                return '';-->
<#--            }-->
<#--            if ((s + "").indexOf(".") < 0) {-->
<#--                return s + ".00";-->
<#--            }-->

<#--            if (typeof s == 'undefined' || s == '') {-->
<#--                return 0;-->
<#--            }-->
<#--            n = n > 0 && n <= 20 ? n : 2;-->
<#--            s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";-->
<#--            var l = s.split(".")[0].split("").reverse(),-->
<#--                r = s.split(".")[1];-->
<#--            t = "";-->
<#--            for (i = 0; i < l.length; i++) {-->
<#--                t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");-->
<#--            }-->
<#--            return t.split("").reverse().join("") + "." + r;-->
<#--        }-->

<#--        /*-->
<#--         * 查询-->
<#--         */-->
<#--        function queryFun() {-->
<#--            grid.datagrid('load', $('#queryForm').serializeObject());-->
<#--            return false;-->
<#--        }-->

<#--        function exportExcel() {-->
<#--            DialogUtils.confirm("确认提醒", "确认导出?",-->
<#--                function (data) {-->
<#--                    if (data) {-->
<#--                        DialogUtils.progress({-->
<#--                            text: '请求提交中，请等待....'-->
<#--                        });-->
<#--                        $.post(-->
<#--                            "${request.contextPath}/statisticalReport/toExportReportFile/${reportCode}.action",-->
<#--                            $("#queryForm").serializeObject(),-->
<#--                            function (data) {-->
<#--                                DialogUtils.progress('close');-->
<#--                                $("#queryForm").attr("action", "${request.contextPath}/statisticalReport/downloadReportFile.action?fileName=" + data.fileName);-->
<#--                                $("#queryForm").attr("method", "post");-->
<#--                                $("#queryForm").submit();-->
<#--                            }-->
<#--                        );-->
<#--                    }-->
<#--                }-->
<#--            );-->
<#--        }-->

<#--        ${script}-->
<#--    </script>-->
</head>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>基本数据表示例与响应插件</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="#" class="dropdown-item">选项 1</a>
                            </li>
                            <li><a href="#" class="dropdown-item">选项 2</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">

                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover dataTables-example">
<#--                            <thead>-->
<#--                            <tr>-->
<#--                                <th>渲染引擎</th>-->
<#--                                <th>浏览器</th>-->
<#--                                <th>平台(s)</th>-->
<#--                                <th>引擎版本</th>-->
<#--                                <th>CSS等级</th>-->
<#--                            </tr>-->
<#--                            </thead>-->
<#--                            <tbody>-->
<#--                            <tr class="gradeX">-->
<#--                                <td>Trident</td>-->
<#--                                <td>Internet-->
<#--                                    Explorer 4.0-->
<#--                                </td>-->
<#--                                <td>Win 95+</td>-->
<#--                                <td class="center">4</td>-->
<#--                                <td class="center">X</td>-->
<#--                            </tr>-->

<#--                            </tbody>-->
<#--                            <tfoot>-->
<#--                            <tr>-->
<#--                                <th>渲染引擎</th>-->
<#--                                <th>浏览器</th>-->
<#--                                <th>平台(s)</th>-->
<#--                                <th>引擎版本</th>-->
<#--                                <th>CSS等级</th>-->
<#--                            </tr>-->
<#--                            </tfoot>-->
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>