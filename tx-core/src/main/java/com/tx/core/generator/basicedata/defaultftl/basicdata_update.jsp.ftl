<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add${view.entitySimpleName}</title>
<%@include file="../includes/commonHead.jsp" %>

<script type="text/javascript" >
$(document).ready(function(){
	parent.DialogUtils.progress('close');
	
	//验证器
	$('#${view.lowerCaseEntitySimpleName}Form').validator({
	    valid: function(){
	        //表单验证通过，提交表单到服务器
	        DialogUtils.progress({
	            text : '数据提交中，请等待....'
	    	});
			$('#${view.lowerCaseEntitySimpleName}Form').ajaxSubmit({
			    url:"${r"${contextPath }"}/${view.lowerCaseEntitySimpleName}/update${view.entitySimpleName}.action",
			    success: function(data) {
			    	DialogUtils.progress('close');
					if(data){
						parent.DialogUtils.tip("修改${view.lowerCaseEntitySimpleName}成功");
						parent.DialogUtils.closeDialogById("update${view.entitySimpleName}");
					}else{
						parent.DialogUtils.tip("修改${view.lowerCaseEntitySimpleName}失败");
					}
			    } 
			});
			return false;
	    }
	});
});
function submitFun(){
	$('#${view.lowerCaseEntitySimpleName}Form').submit();
}
function cancelFun(){
	parent.DialogUtils.closeDialogById("update${view.entitySimpleName}");
}
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form:form id="${view.lowerCaseEntitySimpleName}Form" method="post" cssClass="form"
			modelAttribute="${view.lowerCaseEntitySimpleName}">
			<form:hidden path="id"></form:hidden>
			<table class="common_table">
<#list fieldViewMapping?values as fieldView>
<#if !fieldView.id>
<#if fieldView.updateAble>
<#if fieldView.simpleType>
				<tr>
					<!--//TODO:修改字段是否必填,修改其中文名-->
					<th class="narrow" width="20%">${fieldView.fieldName}:<#if fieldView.isRequired()><span class="tRed">*</span></#if></th>
					<td width="80%">
						<form:input path="${fieldView.fieldName}" cssClass="text" <#if fieldView.validateExpression?exists>
							data-rule="${fieldView.fieldName}:${fieldView.validateExpression}" 
						</#if>/>
					</td>
				</tr>
<#else>
				<tr>
					<!--//TODO:修改字段是否必填,修改其中文名-->
					<th class="narrow" width="20%">${fieldView.fieldName}.${fieldView.foreignKeyFieldName}</th>
					<td width="80%">
						//TODO:修改其显示逻辑
						<form:input path="${fieldView.fieldName}" cssClass="text"/>
					</td>
				</tr>
</#if>
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