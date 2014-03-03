<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>addPost</title>
<%@include file="../includes/commonHead.jsp" %>

<script type="text/javascript" >
$(document).ready(function(){
	parent.DialogUtils.progress('close');
	
	$("#organizationName").chooseOrganization({
		eventName : "chooseOrganizationForAddPost",
		contextPath : _contextPath,
		title : "请选择所属组织",
		width : 260,
		height : 400,
		handler : function(organization){
			$("#organizationName").val(organization.name);
			$("#organizationId").val(organization.id);
		},
		clearHandler: function(){
			$("#organizationName").val('');
			$("#organizationId").val('');
		}
	});
	
	//验证器
	$('#operatorForm').validator({
	    valid: function(){
	        //表单验证通过，提交表单到服务器
	        DialogUtils.progress({
	        	text : '数据提交中，请等待....'
	        });
			$('#operatorForm').ajaxSubmit({
			    url:"${contextPath}/operator/addOperator.action",
			    success: function(data) {
			    	DialogUtils.progress('close');
					if(data){
						parent.DialogUtils.tip("新增操作员成功");
						parent.DialogUtils.closeDialogById("addOperator");
					}
			    } 
			});
			return false;
	    }
	});
	
    //提交
	$("#addBtn").click(function(){
		$('#operatorForm').submit();
	});
});
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form:form id="operatorForm" method="post" cssClass="form"
			modelAttribute="operator">
			<table class="common_table">
				<tr>
					<th class="narrow" width="20%">登录名:<span class="tRed">*</span></th>
					<td width="30%">
						<form:input path="loginName"
							data-rule="登录名:required;remote[get:${contextPath }/operator/loginNameIsExist.action, loginName]"
							data-tip="必填"/>
					</td>
					<th class="narrow" width="20%">姓名:<span class="tRed">*</span></th>
					<td width="30%">
						<form:input path="userName" cssClass="text"
							data-rule="姓名:required;"/>
					</td>
				</tr>
				<tr>
					<th class="narrow">所属组织<span class="tRed">*</span></th>
					<td>
						<input id="organizationId" name="organization.id" type="hidden"
							value="${organization.id }"
							readonly="readonly"/>
						<input id="organizationName" name="organization.name"
							value="${organization.name }" 
							data-rule="所属组织:required;"
							class="selectInput" readonly="readonly"/>
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