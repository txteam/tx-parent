<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<title>查询${view.entityComment}列表[query${view.entityTypeSimpleName}List]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>

<block th:replace="fragments/header::common_replace">
<!-- css -->
<link rel="stylesheet" type="text/css" href="../../webjars/js/jquery-ui/css/no-theme/jquery-ui.custom.min.css" th:href="@{/webjars/js/jquery-ui/css/no-theme/jquery-ui.custom.min.css}"/>
<link rel="stylesheet" type="text/css" href="../../webjars/js/jquery-easyui/themes/bootstrap/easyui.css" th:href="@{/webjars/js/jquery-easyui/themes/bootstrap/easyui.css}"/>
<link rel="stylesheet" type="text/css" href="../../webjars/js/nice-validator/jquery.validator.css" th:href="@{/webjars/js/nice-validator/jquery.validator.css}"/>
<link rel="stylesheet" type="text/css" href="../../webjars/js/viewer/css/viewer.min.css" th:href="@{/webjars/js/viewer/css/viewer.min.css}"/>
<!-- customize -->
<link rel="stylesheet" type="text/css" href="../../webjars/css/icons.css" th:href="@{/webjars/css/icons.css}"/>
<link rel="stylesheet" type="text/css" href="../../webjars/css/commons.css" th:href="@{/webjars/css/commons.css}"/>
<!-- jquery -->
<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery.min.js" th:src="@{/webjars/js/jquery.min.js}"></script>
<!-- jqueryui -->
<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery-ui/js/jquery-ui.custom.js" th:src="@{/webjars/js/jquery-ui/js/jquery-ui.custom.js}"></script>
<!-- easyui -->
<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery-easyui/jquery.easyui.min.js" th:src="@{/webjars/js/jquery-easyui/jquery.easyui.min.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery-easyui/locale/easyui-lang-zh_CN.js" th:src="@{/webjars/js/jquery-easyui/locale/easyui-lang-zh_CN.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/ext.easyui.js" th:src="@{/webjars/js/ext.easyui.js}"></script>
<!-- other -->
<script type="text/javascript" charset="utf-8" src="../../webjars/js/datepicker/WdatePicker.js" th:src="@{/webjars/js/datepicker/WdatePicker.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/jquery.form.js" th:src="@{/webjars/js/jquery.form.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/nice-validator/jquery.validator.js" th:src="@{/webjars/js/nice-validator/jquery.validator.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/nice-validator/local/zh-CN.js" th:src="@{/webjars/js/nice-validator/local/zh-CN.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/viewer/js/viewer-jquery.min.js" th:src="@{/webjars/js/viewer/js/viewer-jquery.min.js}"></script><!-- viewer -->
<!-- customize -->
<script type="text/javascript" charset="utf-8" src="../../webjars/js/commons.js" th:src="@{/webjars/js/commons.js}"></script>
<script type="text/javascript" charset="utf-8" src="../../webjars/js/components.js" th:src="@{/webjars/js/components.js}"></script>

<script type="text/javascript" th:inline="javascript">
/*<![CDATA[*/
var _contextPath = ${r"/*[[@{/}]]*/''"};
/*]]>*/
</script>
</block>

<script type="text/javascript" >
//权限判定
$.canAdd = false;
$.canUpdate = false;
$.canDelete = false;
<#if validProperty??>
$.canDisable = false;
$.canEnable = false;
</#if>

var grid = null;
var idFieldName = '${view.pkProperty.name}';
var nameFieldName = '${view.pkProperty.name}'; 
var entityName = '${view.entityComment}';

$(document).ready(function(){
	var  $editALink = $("#editALink");
	var  $viewALink = $("#viewALink");
	var  $deleteALink = $("#deleteALink");
<#if validProperty??>
	var  $enableALink = $("#enableALink");
	var  $disableALink = $("#disableALink");
</#if>

	grid = $('#grid').datagrid({
		url : _contextPath + '${sqlmap.entityTypeSimpleName?uncap_first}/queryList',
		fit : true,
<#if (fieldViewMapping?size gt 15) >
	    fitColumns : false,
<#else >
        fitColumns : true,
</#if>
		border : false,
		idField : 'id',
		checkOnSelect : false,
		selectOnCheck : false,
		nowrap : true,
		striped : true,
		singleSelect : true,
<#if isPagedList>
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 20, 30, 40, 50 ],
		loadFilter: function(data){
			var res = {total:0,rows:[]};
			if(!$.ObjectUtils.isEmpty(data)
					&& !$.ObjectUtils.isEmpty(data.list)){
				res['total'] = data.count;
				res['rows'] = data.list;
			}
			return res;
		}, 
</#if>
		frozenColumns: [[ {
			field : 'row.${view.idPropertyName}',
			title : 'pk',
			width : 150,
			hidden : true
		}]],
		columns: [[
<#list fieldViewMapping?values as fieldView>
	<#if !fieldView.id>
		<#if fieldView.simpleType>
		{
			field : '${fieldView.fieldName}',
			<%!//FIXME: 修改属性中文名 --%>
			title : <#if fieldView.fieldComment?? >'${fieldView.fieldComment}'<#else >'${fieldView.fieldName}'</#if>,
			width : 200
			<#if fieldView.date>
			,formatter: function(value, row, index){
	   			var text = '';
	   			if($.ObjectUtils.isEmpty(value)){
	   				text = '';
	   			}else{
	   				var date = new Date();
	   				date.setTime(value);
	   				text = date.format('yyyy-MM-dd hh:mm:ss');
	   			}
	   			return text;
			}
			<#elseif fieldView.javaType.enum>
		 //  ,formatter: baseJsonformatter
			</#if>
		}<#if fieldView_has_next>,</#if>
		<#else>
		{
			<%!//FIXME: 修改属性需要显示的属性字段 --%>
			field : '${fieldView.fieldName}.${fieldView.foreignKeyFieldName}',
			<%!//FIXME: 修改属性中文名 --%>
			title : '${fieldView.fieldName}.${fieldView.foreignKeyFieldName}',
			width : 100
		}<#if fieldView_has_next>,</#if>
		</#if>
	</#if>
</#list>
		<c:if test="${r"${show_grid_action == true}"}">
		,{
			field : 'action',
			title : '操作',
			width : 100,
			formatter : function(value, row, index) {
				var str = '&nbsp;';
<#if !StringUtils.isEmpty(validPropertyName)>
				if(!row.${validPropertyName} && $.canEnable){
					str += $.formatString('<img onclick="enableFun(\'{0}\',\'{1}\');" src="{2}" title="启用"/>', row[idFieldName], row[nameFieldName], '${r"${contextPath}"}/style/images/extjs_icons/control/control_play_blue.png');
					str += '&nbsp;&nbsp;';
				}
</#if>
                str += $.formatString('<img onclick="viewFun(\'{0}\',\'{1}\');" src="{2}" title="详情"/>', row[idFieldName], row[nameFieldName], '${r"${contextPath}"}/style/images/extjs_icons/pencil_go.png');
                str += '&nbsp;&nbsp;';

				if($.canUpdate){
					str += $.formatString('<img onclick="editFun(\'{0}\',\'{1}\');" src="{2}" title="编辑"/>', row[idFieldName], row[nameFieldName], '${r"${contextPath}"}/style/images/extjs_icons/pencil.png');
					str += '&nbsp;&nbsp;';
				}
				
				if($.canDelete){
					str += $.formatString('<img onclick="deleteFun(\'{0}\',\'{1}\');" src="{2}" title="删除"/>', row[idFieldName], row[nameFieldName], '${r"${contextPath}"}/style/images/extjs_icons/pencil_delete.png');
					str += '&nbsp;&nbsp;';
				}


<#if !StringUtils.isEmpty(validPropertyName)>
				if(row.${validPropertyName} && $.canDisable){
					str += $.formatString('<img onclick="disableFun(\'{0}\',\'{1}\');" src="{2}" title="禁用"/>', row[idFieldName], row[nameFieldName], '${r"${contextPath}"}/style/images/extjs_icons/control/control_stop_blue.png');
					str += '&nbsp;&nbsp;';
				}
</#if>
				return str;
			}
		}
		</c:if>	
		]],
		toolbar : '#toolbar',
		onDblClickRow : function(index, row){
			if($.canUpdate){
				editFun(row[idFieldName], row[nameFieldName]);
			}
		},
		onClickRow : function(index, row){
			$editALink.linkbutton('enable');
            $viewALink.linkbutton('enable');
			$deleteALink.linkbutton('enable');
<#if !StringUtils.isEmpty(validPropertyName)>
			
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
            $viewALink.linkbutton('disable');
			$deleteALink.linkbutton('disable');
<#if !StringUtils.isEmpty(validPropertyName)>
			$enableALink.show();
   			$viewALink.show();
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
function addFun() {
	DialogUtils.progress({
        text : '加载中，请等待....'
	});
	<%!//FIXME: 修改新增modalDialog的width,height --%>
	DialogUtils.openModalDialog(
		"add${view.entitySimpleName}",
		"新增" + entityName,
		$.formatString("${r"${contextPath}"}/${view.lowerCaseEntitySimpleName}/toAdd${view.entitySimpleName}.action"),
		500,${(fieldViewMapping?size*28+40)?string("#")},function(){
			queryFun();
	});
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
	<%!//FIXME: 修改编辑modalDialog的width,height --%>
	DialogUtils.openModalDialog(
		"update${view.entitySimpleName}",
		"编辑" + entityName + ":" + name,
		$.formatString("${r"${contextPath}"}/${view.lowerCaseEntitySimpleName}/toUpdate${view.entitySimpleName}.action?${view.lowerCaseEntitySimpleName}Id={0}",id),
            500,${(fieldViewMapping?size*28+40)?string("#")},function(){
			//queryFun();
	});
	return false;
}

/**
 * 详情
 */
function viewFun(id,name) {
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
	DialogUtils.openModalDialog(
		"view${view.entitySimpleName}",
		"" + entityName + "详情:" + name,
		$.formatString("${r"${contextPath}"}/${view.lowerCaseEntitySimpleName}/toView${view.entitySimpleName}.action?${view.lowerCaseEntitySimpleName}Id={0}",id),
            900,${(fieldViewMapping?size/2*28+50)?string("#")},function(){
			//queryFun();
		});
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
			    		'${r"${contextPath}"}/${view.lowerCaseEntitySimpleName}/deleteBy${view.upCaseIdPropertyName}.action',
			    		{${view.lowerCaseEntitySimpleName}Id:id},
			    		function(data){
			    			DialogUtils.progress('close');
			    			if(data){
			    				DialogUtils.tip("删除" + entityName + "成功");
			    			}else{
			    				var errorMessage = $.formatString("删除{0}失败.可能已被处理.如有疑问,请联系管理员.",entityName);
			    				DialogUtils.alert("错误提示",errorMessage,"error");
			    			}
			    			queryFun();
			    });
	    	}
    });
    return false;
}
<#if !StringUtils.isEmpty(validPropertyName)>
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
		    		'${r"${contextPath}"}/${view.lowerCaseEntitySimpleName}/disableBy${view.upCaseIdPropertyName}.action',
		    		{${view.lowerCaseEntitySimpleName}Id:id},
		    		function(data){
			    			DialogUtils.progress('close');
			    			if(data){
			    				DialogUtils.tip("禁用" + entityName + "成功");
			    			}else{
			    				var errorMessage = $.formatString("禁用{0}失败.",entityName);
			    				DialogUtils.alert("错误提示",errorMessage,"error");
			    			}
			    			queryFun();
		    });
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
		    		'${r"${contextPath}"}/${view.lowerCaseEntitySimpleName}/enableBy${view.upCaseIdPropertyName}.action',
		    		{${view.lowerCaseEntitySimpleName}Id:id},
		    		function(data){
			    			DialogUtils.progress('close');
			    			if(data){
			    				DialogUtils.tip("启用" + entityName + "成功");
			    			}else{
			    				var errorMessage = $.formatString("启用{0}失败.",entityName);
			    				DialogUtils.alert("错误提示",errorMessage,"error");
			    			}
			    			queryFun();
		    });
    	}
    });
    return false;
}
</#if>
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
	<!--//FIXME: 修改查询条件框体高度 -->
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 140px; overflow: hidden;">
		<form id="queryForm" class="form">
			<table class="table table-hover table-condensed">
<#list view.queryConditionName2ConditionInfoMapping?values as conditionInfo>
	<#if conditionInfo_index%3 = 0>
				<tr>
	</#if>
					<!--//FIXME: 修改查询条件中文名 -->
					<th>${conditionInfo.queryConditionKey}</th>
	<#if conditionInfo.queryConditionJavaType.simpleName == "Date">
					<td><input id="${conditionInfo.queryConditionKey}" name="${conditionInfo.queryConditionKey}"
							readonly="readonly"
							onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</td>
	<#elseif conditionInfo.queryConditionJavaType.simpleName?uncap_first == "boolean">
					<td><select id="${conditionInfo.queryConditionKey}" name="${conditionInfo.queryConditionKey}"/>
							<option value="">全部</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
	<#else >
					<td><input id="${conditionInfo.queryConditionKey}" name="${conditionInfo.queryConditionKey}"/></td>
	</#if>
	<#if (conditionInfo_has_next && (conditionInfo_index+1)%3 == 0)>
				</tr>
	</#if>
	<#if (!conditionInfo_has_next && conditionInfo_index%3 != 0)>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
	</#if>
</#list>
				<tr>
					<td colspan="6" class="button operRow">
						<a id="queryBtn" onclick="queryFun();return false;" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'search'">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
    </div> 
    
	<div id="toolbar" style="display: none;">		
		<c:if test='${r"${authContext.hasAuth("}"add_${view.lowerCaseEntitySimpleName}") }'>
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">新增</a>
		</c:if>
		<c:if test='${r"${authContext.hasAuth("}"update_${view.lowerCaseEntitySimpleName}") }'>
			<a id="editALink" onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil'">编辑</a>
		</c:if>
		<c:if test='${r"${authContext.hasAuth("}"view_${view.lowerCaseEntitySimpleName}") }'>
			<a id="viewALink" onclick="viewFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_go'">详情</a>
		</c:if>

		<c:if test='${r"${authContext.hasAuth("}"delete_${view.lowerCaseEntitySimpleName}") }'>
			<a id="deleteALink" onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_delete'">删除</a>
		</c:if>
<#if !StringUtils.isEmpty(validPropertyName)>
		<a id="enableALink" onclick="enableFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'control_play_blue'">启用</a>
		<a id="disableALink" onclick="disableFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'control_stop_blue'">禁用</a>
</#if>
		<a onclick="grid.datagrid('reload');return false;" href="javascript:void(0);" 
			class="easyui-linkbutton" data-options="plain:true,iconCls:'transmit'">刷新</a>
	</div>
</div>
</body>