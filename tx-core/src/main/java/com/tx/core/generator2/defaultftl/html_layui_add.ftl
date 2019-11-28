<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html >
<html xmlns:form="http://www.w3.org/1999/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加${view.entityComment}</title>
<%@include file="../includes/commonHead.jsp" %>

<style>
	table th {width: 25%}
	table td{width: 75%}
</style>
<script type="text/javascript" >
$(document).ready(function(){
	parent.DialogUtils.progress('close');
	
	//验证器
	$('#entityForm').validator({
	    valid: function(){
	        //表单验证通过，提交表单到服务器
	        DialogUtils.progress({
	        	text : '数据提交中，请等待....'
	        });
			$('#entityForm').ajaxSubmit({
			    url:"${r"${contextPath }"}/${view.lowerCaseEntitySimpleName}/add.action",
			    success: function(data) {
			    	DialogUtils.progress('close');
					if(data){
						parent.DialogUtils.tip("新增${view.entityComment}成功.");
						parent.DialogUtils.closeDialogById("add${view.entitySimpleName}");
					}else{
						DialogUtils.alert("错误提示","新增${view.entityComment}失败.","error");
					}
			    } 
			});
			return false;
	    }
	});
});
function submitFun(){
	$('#entityForm').submit();
}
function cancelFun(){
	parent.DialogUtils.closeDialogById("add${view.entitySimpleName}");
}
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form:form id="entityForm" method="post" cssClass="form"
			modelAttribute="${view.lowerCaseEntitySimpleName}">
			<form:hidden path="${view.idPropertyName}"/>
			<table class="common_table">
<#list fieldViewMapping?values as fieldView>
	<#if !fieldView.id>
		<#if fieldView.simpleType>
			<#if validPropertyName != fieldView.fieldName>
				<tr>
					<!--//TODO:修改字段是否必填,修改其中文名-->
					<th class="narrow" ><#if fieldView.fieldComment?? >${fieldView.fieldComment}<#else >${fieldView.fieldName}</#if>:<#if fieldView.isRequired()><span class="tRed">*</span></#if></th>
					<td >
						<#if fieldView.javaType.simpleName =="boolean" || fieldView.javaType.simpleName == 'Boolean'>
							<form:select path="${fieldView.fieldName}" cssClass="text" <#if fieldView.validateExpression?exists>
                                         data-rule="${fieldView.fieldName}:${fieldView.validateExpression}"</#if>>
								<#if fieldView.javaType.simpleName == 'Boolean'>
                                    <form:option value="">全部</form:option>
								</#if>
								<form:option value="1">是</form:option>
								<form:option value="0">否</form:option>
							</form:select>
						<#elseif  fieldView.javaType.enum >
                            <form:select path="${fieldView.fieldName}" cssClass="text" <#if fieldView.validateExpression?exists>
                                         data-rule="${fieldView.fieldName}:${fieldView.validateExpression}"</#if>>
                                <form:option value="">全部</form:option>
                                <form:options items="${r"${"}${fieldView.fieldName}List}"/>
                            </form:select>
						<#elseif  fieldView.date >
                            <form:input path="${fieldView.fieldName}" cssClass="text" <#if fieldView.validateExpression?exists>
                                        data-rule="${fieldView.fieldName}:${fieldView.validateExpression}"
                                        readonly="readonly" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',startDate:'%y-{%M-1}-%d'})"
							</#if>/>
						<#else >
                            <form:input path="${fieldView.fieldName}" cssClass="text" <#if fieldView.validateExpression?exists>
                                        data-rule="${fieldView.fieldName}:${fieldView.validateExpression}"
							</#if>/>
						</#if>

					</td>
				</tr>
			</#if>
		<#else>
				<tr>
					<!--//TODO:修改字段是否必填,修改其中文名-->
					<th class="narrow" width="20%">${fieldView.fieldName}.${fieldView.foreignKeyFieldName}</th>
					<td width="80%">
						//TODO:修改其显示逻辑
						<form:input path="${fieldView.fieldName}.${fieldView.foreignKeyFieldName}" cssClass="text"/>
					</td>
				</tr>
		</#if>
	</#if>
</#list>
				<tr>
					<td class="rightOperRow" colspan="4" style="padding-right: 50px">
						<a id="submitBtn" onclick="submitFun();return false;" href="#" class="easyui-linkbutton">提交</a>  
						<a id="cancelBtn" onclick="cancelFun();return false;" href="#" class="easyui-linkbutton">取消</a>	
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
</body>