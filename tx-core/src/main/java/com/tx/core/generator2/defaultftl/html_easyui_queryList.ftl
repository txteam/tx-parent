<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<title>查询${view.entityComment}列表[query${view.entityTypeSimpleName}List]</title>
	<meta charset="utf-8"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
<#noparse>
<block th:replace="fragments/easyui_header::common_replace">
	<!-- css -->
	<link rel="stylesheet" type="text/css" href="../../webjars/js/jquery-ui/css/no-theme/jquery-ui.custom.min.css" th:href="@{/webjars/js/jquery-ui/css/no-theme/jquery-ui.custom.min.css}"/>
	<link rel="stylesheet" type="text/css" href="../../webjars/js/nice-validator/jquery.validator.css" th:href="@{/webjars/js/nice-validator/jquery.validator.css}"/>
	<link rel="stylesheet" type="text/css" href="../../webjars/js/viewer/css/viewer.min.css" th:href="@{/webjars/js/viewer/css/viewer.min.css}"/>
	<link rel="stylesheet" type="text/css" href="../../webjars/css/icons.css" th:href="@{/webjars/css/icons.css}"/>
	<!-- customize -->
	<link rel="stylesheet" type="text/css" href="../../webjars/themes/easyui/js/jquery-easyui/themes/bootstrap/easyui.css" th:href="@{/webjars/themes/easyui/css/easyui.css}"/>
	<link rel="stylesheet" type="text/css" href="../../webjars/themes/easyui/css/commons.css" th:href="@{/webjars/themes/easyui/css/commons.css}"/>
	
	<!-- jquery -->
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery.min.js" th:src="@{/webjars/js/jquery.min.js}"></script>
	<!-- jqueryui -->
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery-ui/js/jquery-ui.custom.js" th:src="@{/webjars/js/jquery-ui/js/jquery-ui.custom.js}"></script>
	<!-- easyui -->
	<script type="text/javascript" charset="utf-8" src="../../webjars/themes/easyui/js/jquery-easyui/jquery.easyui.min.js" th:src="@{/webjars/themes/easyui/js/jquery-easyui/jquery.easyui.min.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/themes/easyui/js/jquery-easyui/locale/easyui-lang-zh_CN.js" th:src="@{/webjars/themes/easyui/js/jquery-easyui/locale/easyui-lang-zh_CN.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/themes/easyui/js/ext.easyui.js" th:src="@{/webjars/themes/easyui/js/ext.easyui.js}"></script>
	<!-- other -->
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/datepicker/WdatePicker.js" th:src="@{/webjars/js/datepicker/WdatePicker.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery.form.js" th:src="@{/webjars/js/jquery.form.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/nice-validator/jquery.validator.js" th:src="@{/webjars/js/nice-validator/jquery.validator.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/nice-validator/local/zh-CN.js" th:src="@{/webjars/js/nice-validator/local/zh-CN.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/js/viewer/js/viewer-jquery.min.js" th:src="@{/webjars/js/viewer/js/viewer-jquery.min.js}"></script><!-- viewer -->
	<!-- customize -->
	<script type="text/javascript" charset="utf-8" src="../../webjars/themes/easyui/js/commons.js" th:src="@{/webjars/themes/easyui/js/commons.js}"></script>
	<script type="text/javascript" charset="utf-8" src="../../webjars/themes/easyui/js/components.js" th:src="@{/webjars/themes/easyui/js/components.js}"></script>
	<script type="text/javascript" th:inline="javascript">
	/*<![CDATA[*/
	var _contextPath = /*[[@{/}]]*/'/';
	/*]]>*/
	</script>
</block>
</#noparse>

<script type="text/javascript" th:inline="javascript">
/*<![CDATA[*/
var grid = null;
var idFieldName = '${view.pkProperty.propertyName}';
var nameFieldName = '${view.nameProperty.propertyName}'; 
var entityName = '${view.entityComment}';
$(document).ready(function(){
	var  $editALink = $("#editALink");
	var  $deleteALink = $("#deleteALink");
<#if view.validProperty??>
	var  $enableALink = $("#enableALink");
	var  $disableALink = $("#disableALink");
</#if>

	grid = $('#grid').datagrid({
		url :/*[[@{/${view.entityTypeSimpleName?uncap_first}/queryList}]]*/'',
		fit : true,
<#if (view.propertyList?size gt 15) >
	    fitColumns : false,
<#else >
        fitColumns : true,
</#if>
		border : false,
		idField : '${view.pkProperty.propertyName}',
		checkOnSelect : false,
		selectOnCheck : false,
		nowrap : true,
		striped : true,
		singleSelect : true,
		frozenColumns: [ [ 
			{title : '${view.pkProperty.propertyName}',width : 100,hidden:true,field:'${view.pkProperty.propertyName}'}
		] ],
		columns: [ [
<#list view.propertyList as property>
	<#if property.isPrimaryKey()>
	<#elseif (
		property.propertyName == "vcid"
		|| property.propertyName == "createOperatorId" 
		|| property.propertyName == "lastUpdateOperatorId"
		)>
	<#elseif (
		property.propertyName == "parentId"
		)>
			{title:'<#if property.propertyComment??>${property.propertyComment}<#else>${property.propertyName}</#if>',width:100,hidden:true,field:'${property.propertyName}'},
	<#elseif (
		property.propertyName == "lastUpdateDate" 
		)>
			{title:'<#if property.propertyComment??>${property.propertyComment}<#else>${property.propertyName}</#if>',width:100,hidden:true,field:'${property.propertyName}',formatter:$.Formatters.dateFun},
	<#else>
		<#if property.propertyType.getSimpleName() == 'boolean' || property.propertyType.getSimpleName() == 'Boolean'>
			{title:'<#if property.propertyComment??>${property.propertyComment}<#else>${property.propertyName}</#if>',width:80,field:'${property.propertyName}',formatter:$.Formatters.booleanFun},
		<#elseif property.propertyType.getSimpleName() == 'Date'>
			{title:'<#if property.propertyComment??>${property.propertyComment}<#else>${property.propertyName}</#if>',width:100,field:'${property.propertyName}',formatter:$.Formatters.dateFun},
		<#else>
			{title:'<#if property.propertyComment??>${property.propertyComment}<#else>${property.propertyName}</#if>',width:100,field:'${property.propertyName}'},
		</#if>
	</#if>
</#list>
			{title:'操作',width:100,field:'_action',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
<#if view.validProperty??>
					if(!row.valid){
						str += $.formatString('<img onclick="enableFun(\'{0}\',\'{1}\');" src="{2}" title="启用"/>', row.id, row.name, _contextPath + 'webjars/css/images/extjs_icons/control/control_play_blue.png');
						str += '&nbsp;';
					}
</#if>
					str += $.formatString('<img onclick="editFun(\'{0}\',\'{1}\');" src="{2}" title="编辑"/>', row.id, row.name, _contextPath + 'webjars/css/images/extjs_icons/pencil.png');
					str += '&nbsp;';
					str += $.formatString('<img onclick="deleteFun(\'{0}\',\'{1}\');" src="{2}" title="删除"/>', row.id, row.name, _contextPath + 'webjars/css/images/extjs_icons/pencil_delete.png');
					str += '&nbsp;';
<#if view.validProperty??>
					if(row.valid){
						str += $.formatString('<img onclick="disableFun(\'{0}\',\'{1}\');" src="{2}" title="禁用"/>', row.id, row.name, _contextPath + 'webjars/css/images/extjs_icons/control/control_stop_blue.png');
						str += '&nbsp;';
					}
</#if>
					return str;
				}
			}
		] ],
		toolbar : '#toolbar',
		onDblClickRow : function(index, row){
			editFun(row[idFieldName], row[nameFieldName]);
		},
		onClickRow : function(index, row){
			$editALink.linkbutton('enable');
			$deleteALink.linkbutton('enable');
<#if view.validProperty??>
			if(row.valid){
				$enableALink.linkbutton('disable');
				$enableALink.hide();
				$disableALink.show();
				$disableALink.linkbutton('enable');
			}else{
				$disableALink.linkbutton('disable');
				$disableALink.hide();
				$enableALink.show();
				$enableALink.linkbutton('enable');
			}
</#if>
		},
		onLoadSuccess : function() {
			$(this).datagrid('unselectAll');
			$(this).datagrid('tooltip');
			
			$editALink.linkbutton('disable');
			$deleteALink.linkbutton('disable');
<#if view.validProperty??>
			$enableALink.show();
			$disableALink.show();
			$enableALink.linkbutton('disable');
			$disableALink.linkbutton('disable');
</#if>
		}
	});
});
/*
 * 查询
 */
function queryFun() {
	grid.datagrid('load',$('#queryForm').serializeObject());
	return false;
}
/*
 * 新增
 */
<#assign dialog_width=view.propertyList?size*28+40>
function addFun() {
	DialogUtils.progress({
        text : '加载中，请等待....'
	});
	
	var addUrl = /*[[@{/${view.entityTypeSimpleName?uncap_first}/toAdd}]]*/'';
	//FIXME: 修改新增modalDialog的width,height
	DialogUtils.openModalDialog(
		"add${view.entityTypeSimpleName}",
		"新增" + entityName,
		$.formatString(addUrl),
		500,${(dialog_width>650)?string('650','' + dialog_width)},
		function(){
			queryFun();
		},{maximizable : true}
	);
	return false;
}
/**
 * 编辑
 */
function editFun(id,name) {
	if (id == undefined) {
		var rows = grid.datagrid('getSelections');
		id = rows[0][idFieldName];
		name = rows[0][nameFieldName];
	}
	if($.ObjectUtils.isEmpty(id)){
		DialogUtils.alert("没有选中的" + entityName);
		return ;
	}
	DialogUtils.progress({
        text : '加载中，请等待....'
	});
	
	var updateUrl = /*[[@{/${view.entityTypeSimpleName?uncap_first}/toUpdate?${view.pkProperty.propertyName}={0}}]]*/'';
	//FIXME: 修改编辑modalDialog的width,height
	DialogUtils.openModalDialog(
		"update${view.entityTypeSimpleName}",
		"编辑" + entityName + ":" + name,
		$.formatString(updateUrl,id),
        500,${(dialog_width>650)?string('650','' + dialog_width)},
        function(){
			queryFun();
		},{maximizable : true}
	);
	return false;
}
/*
 * 删除
 */
function deleteFun(id,name) {
	if (id == undefined) {
		var rows = grid.datagrid('getSelections');
		id = rows[0][idFieldName];
		name = rows[0][nameFieldName];
	}
	if($.ObjectUtils.isEmpty(id)){
		DialogUtils.alert("没有选中的" + entityName);
		return false;
	}
	//判断是否确认删除指定的${view.entityComment}
	DialogUtils.confirm("确认提醒" , 
    	$.formatString("是否确认删除{0}:[{1}]?",entityName,name) , 
    	function(data){
	    	if(data){
	    		DialogUtils.progress({
	    	        text : '数据提交中，请等待....'
	    		});
	    		//如果确认删除指定的${view.entityComment}
	    		$.post(
	    			/*[[@{/${view.entityTypeSimpleName?uncap_first}/deleteBy${view.pkProperty.propertyName?cap_first}}]]*/'',
		    		{${view.pkProperty.propertyName}:id},
		    		function(data){
		    			DialogUtils.progress('close');
		    			if(data){
		    				DialogUtils.tip("删除" + entityName + "成功");
		    			}else{
		    				var errorMessage = $.formatString("删除{0}失败.可能已被处理.如有疑问,请联系管理员.",entityName);
		    				DialogUtils.alert("错误提示",errorMessage,"error");
		    			}
		    			queryFun();
			    	}
			    );
	    	}
    });
    return false;
}
<#if view.validProperty??>
/*
 * 禁用
 */
function disableFun(id,name){
	if (id == undefined) {
		var rows = grid.datagrid('getSelections');
		id = rows[0][idFieldName];
		name = rows[0][nameFieldName];
	}
	if($.ObjectUtils.isEmpty(id)){
		DialogUtils.alert("没有选中的" + entityName);
		return false;
	}
	//判断是否确认禁用指定${view.entityComment}
	DialogUtils.confirm(
    		"确认提醒" , 
    		$.formatString("是否确认禁用{0}:[{1}]?",entityName,name), 
    function(data){
    	if(data){
    		DialogUtils.progress({
    	        text : '数据提交中，请等待....'
    		});
    		//如果确认禁用指定${view.entityComment}
    		$.post(
				/*[[@{/${view.entityTypeSimpleName?uncap_first}/disableBy${view.pkProperty.propertyName?cap_first}}]]*/'',
	    		{${view.pkProperty.propertyName}:id},
	    		function(data){
	    			DialogUtils.progress('close');
	    			if(data){
	    				DialogUtils.tip("禁用" + entityName + "成功.");
	    			}else{
	    				var errorMessage = $.formatString("禁用{0}失败.",entityName);
	    				DialogUtils.alert("错误提示",errorMessage,"error");
	    			}
	    			queryFun();
	    		}
		    );
    	}
    });
    return false;
}
/*
 * 启用
 */
function enableFun(id,name){
	if (id == undefined) {
		var rows = grid.datagrid('getSelections');
		id = rows[0][idFieldName];
		name = rows[0][nameFieldName];
	}
	if($.ObjectUtils.isEmpty(id)){
		DialogUtils.alert("没有选中的" + entityName);
		return false;
	}
	//判断是否确认禁用指定${view.entityComment}
	DialogUtils.confirm(
    		"确认提醒" , 
    		$.formatString("是否确认启用{0}:[{1}]?",entityName,name), 
    function(data){
    	if(data){
    		DialogUtils.progress({
    	        text : '数据提交中，请等待....'
    		});
    		//如果确认启用指定${view.entityComment}
    		$.post(
				/*[[@{/${view.entityTypeSimpleName?uncap_first}/enableBy${view.pkProperty.propertyName?cap_first}}]]*/'',
	    		{${view.pkProperty.propertyName}:id},
	    		function(data){
	    			DialogUtils.progress('close');
	    			if(data){
	    				DialogUtils.tip("启用" + entityName + "成功.");
	    			}else{
	    				var errorMessage = $.formatString("启用{0}失败.",entityName);
	    				DialogUtils.alert("错误提示",errorMessage,"error");
	    			}
	    			queryFun();
		    	}
		    );
    	}
    });
    return false;
}
</#if>
/*]]>*/
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<!--//FIXME: 修改查询条件框体高度 -->
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 105px; overflow: hidden;">
		<form id="queryForm" class="form">
			<table class="table table-hover table-condensed">
				<tr>
<#if view.codeProperty??>
					<th width="20%">编码:</th>
					<td width="30%"><input id="code" name="code"/></td>
<#elseif view.nameProperty??>
					<th width="20%">名称:</th>
					<td width="30%"><input id="name" name="name"/></td>
<#else>
					<th width="20%">&nbsp;</th>
					<td width="30%">&nbsp;</td>
</#if>
<#if view.validProperty??>
					<th width="20%">是否有效</th>
					<td width="30%">
						<select id="valid" name="valid">
							<option value="">--- 不限 ---</option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</td>
<#else>
					<th width="20%">&nbsp;</th>
					<td width="30%">&nbsp;</td>
</#if>
				</tr>
				<tr>
					<td colspan="4" class="button operRow">
						<a id="queryBtn" onclick="queryFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'search'">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
    </div> 
    
	<div id="toolbar" style="display: none;">		
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">新增</a>
		<a id="editALink" onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil'">编辑</a>
		<a id="deleteALink" onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_delete'">删除</a>
<#if view.validProperty??>
		<a id="enableALink" onclick="enableFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'control_play_blue'">启用</a>
		<a id="disableALink" onclick="disableFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'control_stop_blue'">禁用</a>
</#if>
		<a onclick="grid.datagrid('reload');return false;" href="javascript:void(0);" 
			class="easyui-linkbutton" data-options="plain:true,iconCls:'transmit'">刷新</a>
	</div>
</body>