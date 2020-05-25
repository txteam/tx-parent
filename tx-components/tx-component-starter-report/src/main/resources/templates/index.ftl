<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>报表系统</title>
    <link rel="stylesheet" href="${request.contextPath}/js/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css">
    <script src="${request.contextPath}/js/jquery-1.11.3/jquery.min.js"></script>
    <script src="${request.contextPath}/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>
  </head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="tabbable" id="tabs-539703">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="#panel-275827" data-toggle="tab">第一部分</a>
                    </li>
                    <li>
                        <a href="#panel-164832" data-toggle="tab">第二部分</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="panel-275827">
                        <#list  reports as report>
                            <div class="row">
                                <div class="col-md-6">${report.name}</div>
                                <div class="col-md-6">${report.code}</div>
                            </div>
                        </#list>
                    </div>
                    <div class="tab-pane" id="panel-164832">
                        <p>
                            第二部分内容.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<#--<div class="container-fluid">-->
<#--    <div class="row">-->
<#--        <div class="col-md-6">12</div>-->
<#--        <div class="col-md-6">$12</div>-->
<#--    </div>-->
<#--</div>-->



</body>
</html>