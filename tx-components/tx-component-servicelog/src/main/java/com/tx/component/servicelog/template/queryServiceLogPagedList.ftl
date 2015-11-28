<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>query${jpaMetaClass.entitySimpleName}</title>
<%@include file="../includes/commonHead.jsp" %>
<script type="text/javascript" >
var serviceLogTable = null;
$(document).ready(function(){
	serviceLogTable = $('#serviceLogTable').datagrid({
		url : '${r"${contextPath}"}/servicelog/${jpaMetaClass.modulePackageSimpleName}/${StringUtils.uncapitalize('${jpaMetaClass.entitySimpleName}')}/query${jpaMetaClass.entitySimpleName}PagedList.action',
		fit : true,
		fitColumns : true,
		border : false,
		pagination : true,
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		sortName : 'createDate',
		sortOrder : 'desc',
		checkOnSelect : false,
		selectOnCheck : false,
		nowrap : false,
		striped : true,
		rownumbers : true,
		singleSelect : true,
		loadFilter: function(data){
			var res = {total:0,rows:[]};
			if(!$.ObjectUtils.isEmpty(data)
					&& !$.ObjectUtils.isEmpty(data.list)){
				res['total'] = data.count;
				res['rows'] = data.list;
			}
			return res;
		}, 
		frozenColumns : [ [ {
			field : 'id',
			title : '编号',
			width : 150,
			hidden : true
		}, {
			field : 'createDate',
			title : '记录日志时间',
			width : 180,
			sortable : true,
			formatter: function(cellvalue, options, rowObject){
	   			var createDate = new Date();
	   			createDate.setTime(cellvalue);
	   			return createDate.format('yyyy-MM-dd hh:mm:ss');;
			}
		}, {
			field : 'vcid',
			title : '虚中心id',
			width : 100,
			hidden : true
		} , {
			field : 'operatorId',
			title : '操作员id',
			width : 100,
			hidden : true
		}, {
			field : 'operatorLoginName',
			title : '操作员登录名',
			width : 120
		}, {
			field : 'operatorName',
			title : '操作员名',
			width : 120
		},{
			field : 'organizationId',
			title : '组织',
			width : 100,
			hidden : true
		}] ],
		columns : [[ {
			field : 'message',
			title : '日志信息',
			width : 200
		}
<#list classTopGetters as getterTemp>
		,{
			field : '${getterTemp}',
			title : '${getterTemp}',
			width : 150
		}
</#list>		
		]],
		toolbar : '#toolbar',
		onLoadSuccess : function() {
			//$('#queryForm table').show();
			parent.$.messager.progress('close');
			$(this).datagrid('tooltip');
		},
		onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			//$(this).datagrid('unselectAll');
			//$(this).datagrid('selectRow', rowIndex);
			/*
			$('#menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
			*/
		}
	});
	
	$("#queryBtn").click(function(){
		serviceLogTable.datagrid('load',{
			minCreateDate: $(":input[name=minCreateDate]",$("#queryForm")).val(),
			maxCreateDate: $(":input[name=maxCreateDate]",$("#queryForm")).val()
		});
	});
});
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 85px; overflow: hidden;">
		<form id="queryForm" class="form">
			<table class="table table-hover table-condensed">
				<tr>
						<th>开始时间</th>
						<td><input id="minCreateDate" name="minCreateDate"
							readonly="readonly" placeholder="点击选择时间"
							onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'maxCreateDate\')}' })" />
						</td>
						<th>结束时间</th>
						<td><input id="maxCreateDate" name="maxCreateDate"
							readonly="readonly" placeholder="点击选择时间"
							onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'minCreateDate\')}'})" />
						</td>
					</tr>
				<tr>
					<td colspan="4" class="button operRow">
						<a id="queryBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'search'">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="serviceLogTable"></table>
    </div> 
</div> 

<div id="toolbar" style="display: none;"> 
	<a onclick="serviceLogTable.datagrid('reload');return false;" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'transmit'">刷新</a>
</div>
</body>