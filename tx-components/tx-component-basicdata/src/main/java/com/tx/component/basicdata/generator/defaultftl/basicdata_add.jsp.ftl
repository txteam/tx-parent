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
			$('#operatorForm').ajaxSubmit({
			    url:"${r"${contextPath }"}/${view.lowerCaseEntitySimpleName}/add${view.entitySimpleName}.action",
			    success: function(data) {
			    	DialogUtils.progress('close');
					if(data){
						parent.DialogUtils.tip("新增${view.lowerCaseEntitySimpleName}成功");
						parent.DialogUtils.closeDialogById("add${view.entitySimpleName}");
					}
			    } 
			});
			return false;
	    }
	});
	
    //提交
	$("#addBtn").click(function(){
		$('#${view.lowerCaseEntitySimpleName}Form').submit();
	});
});
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form:form id="${view.lowerCaseEntitySimpleName}Form" method="post" cssClass="form"
			modelAttribute="${view.lowerCaseEntitySimpleName}">
			<table class="common_table">

				<tr>
					<th class="narrow" width="20%">登录名:<span class="tRed">*</span></th>
					<td width="30%">
						<form:input path="loginName"
							data-rule="登录名:required;remote[get:${r"${contextPath }"}/${view.lowerCaseEntitySimpleName}/loginNameIsExist.action, loginName]"
							data-tip="必填"/>
					</td>
				</tr>
				
				<tr>
					<td class="rightOperRow" colspan="4" style="padding-right: 50px">
						<a id="addBtn" href="#" class="easyui-linkbutton">提交</a>  	
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
</body>