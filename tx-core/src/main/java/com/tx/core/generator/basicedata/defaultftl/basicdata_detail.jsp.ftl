<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html >
<html xmlns:form="http://www.w3.org/1999/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${view.entityComment}详情</title>
<%@include file="../includes/commonHead.jsp" %>

<style>
	table th {width: 20%}
    table td{width: 30%}
</style>


<script type="text/javascript" >
$(document).ready(function(){
	parent.DialogUtils.progress('close');
	
	//验证器

});
function cancelFun(){
    parent.DialogUtils.closeDialogById("view${view.entitySimpleName}");
}

</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
        <div class="form">
			<table >
<#list fieldViewMapping?values as fieldView>
				<#if fieldView_index%2 ==0  >
                <tr>
				</#if>
					<th class="narrow" ><#if fieldView.fieldComment?? >${fieldView.fieldComment}<#else >${fieldView.fieldName}</#if>:<#if fieldView.isRequired()><span class="tRed">*</span></#if></th>
					<td >
						<#if fieldView.javaType.simpleName =="boolean" || fieldView.javaType.simpleName == 'Boolean'>
							<c:if test="${r"${"}${view.lowerCaseEntitySimpleName}.${fieldView.fieldName}}">
								是
							</c:if>
                            <c:if test="not ${r"${"}${view.lowerCaseEntitySimpleName}.${fieldView.fieldName}}">
                                否
                            </c:if>
						<#else >
							${r"${"}${view.lowerCaseEntitySimpleName}.${fieldView.fieldName}}
						</#if>
					</td>
			<#if (fieldView_index+1)%2 ==0  >
				</tr>
			</#if>

</#list>
				<tr>
					<td class="rightOperRow" colspan="4" style="padding-right: 50px">
						<a id="cancelBtn" onclick="cancelFun();return false;" href="#" class="easyui-linkbutton">关闭</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>