<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<title>新增${view.entityComment}[add${view.entityTypeSimpleName}]</title>
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
				url:/*[[@{/${view.entityTypeSimpleName?uncap_first}/add}]]*/'',
			    success: function(data) {
			    	DialogUtils.progress('close');
					if(data){
						parent.DialogUtils.tip("新增${view.entityComment}成功.");
						parent.DialogUtils.closeDialogById("add${view.entityTypeSimpleName}");
					}else{
						DialogUtils.alert("错误提示","新增${view.entityComment}失败.","error");
					}
			    } 
			});
			return false;
	    }
	});
	
	//退出
	$("#cancelBtn").click(function(){
		parent.DialogUtils.closeDialogById("add${view.entityTypeSimpleName}");
	});
	//提交
	$("#submitBtn").click(function(){
		$('#entityForm').submit();
	});
});
/*]]>*/
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow-x: hidden;overflow-y: auto;">
		<form id="entityForm" method="post" class="form" th:object="${r"${"}${view.entityTypeSimpleName?uncap_first}}">
			<input id="id" name="id" type="hidden" value="" th:value="*{id}"/>
			<table class="table">
<#list view.propertyList as property>
	<#if (!property.isPrimaryKey()
		&& property.propertyName != "id"
		&& property.propertyName != "vcid"
		&& property.propertyName != "createDate" 
		&& property.propertyName != "createOperatorId" 
		&& property.propertyName != "lastUpdateDate" 
		&& property.propertyName != "lastUpdateOperatorId"
		&& property.propertyName != "valid")>
		<#if property.isSimpleValueType()>
				<tr>
			<#if property.propertyComment??>
					<th class="narrow" width="25%">${property.propertyComment}:<#if !property.isNullable()><span class="tRed">*</span></#if></th>
			<#else >
					<!--//TODO:修改字段是否必填,修改其中文名-->
					<th class="narrow" width="25%">${property.propertyName}:<#if !property.isNullable()><span class="tRed">*</span></#if></th>
			</#if>
					<td>
			<#if property.propertyType.getSimpleName() == 'boolean' || property.propertyType.getSimpleName() == 'Boolean'>
						<select id="${property.propertyName}" name="${property.propertyName}" <#if property.validateExpression??>${property.validateExpression}</#if>>
				<#if property.propertyType.getSimpleName() == 'Boolean'>
							<option value="">--- 请选择 ---</option>
				</#if>
							<option value="true" th:selected="${r"${"}${view.entityTypeSimpleName?uncap_first}.${property.propertyName} eq true}">是</form:option>
							<option value="false" th:selected="${r"${"}${view.entityTypeSimpleName?uncap_first}.${property.propertyName} eq false}">否</form:option>
						</select>
			<#elseif property.propertyType.isEnum()>
						<select id="${property.propertyName}" name="${property.propertyName}" <#if property.validateExpression??>${property.validateExpression}</#if>>
							<option value="">--- 请选择 ---</option>
							<option th:each="${property.propertyName}Temp:${r"${"}${property.propertyName}s}" th:value="${r"${"}${property.propertyName}Temp.code}" th:text="${r"${"}${property.propertyName}Temp.name}" th:selected="${r"${"}${view.entityTypeSimpleName?uncap_first}.${property.propertyName} eq ${property.propertyName}Temp}"></option>
						</select>
			<#elseif  property.propertyType.getSimpleName() == 'Date'>
						<input id="${property.propertyName}" name="${property.propertyName}" type="text" readonly="readonly"
							onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',startDate:'%y-{%M-1}-%d'})" <#if property.validateExpression??>${property.validateExpression}</#if>
							th:value="${r"${"}#dates.format(${view.entityTypeSimpleName?uncap_first}.${property.propertyName},'yyyy-MM-dd HH:mm:ss')}"/>
			<#else>
						<input id="${property.propertyName}" name="${property.propertyName}" type="text" <#if property.validateExpression??>${property.validateExpression}</#if>
							th:value="*{${property.propertyName}}"/>
			</#if>
					</td>
				</tr>			
		<#else>
				<tr>
			<#if property.propertyComment??>
					<th class="narrow" width="25%">${property.propertyComment}:<#if !property.isNullable()><span class="tRed">*</span></#if></th>
			<#else>
					<!--//TODO:修改字段是否必填,修改其中文名-->
					<th class="narrow" width="25%">${property.getColumnPropertyName()}:<#if !property.isNullable()><span class="tRed">*</span></#if></th>
			</#if>
					<td>
						<!-- //TODO:修改其显示逻辑 -->
						<input id="${property.getColumnPropertyName()}" name="${property.getColumnPropertyName()}" th:value="*{<#if property.nestedPropertyDescriptor??>${property.propertyDescriptor.getName()}?.${property.nestedPropertyDescriptor.getName()}<#else>${property.propertyDescriptor.getName()}</#if>}"/>
					</td>
				</tr>
		</#if>
	</#if>
</#list>			
			</table>
		</form>
	</div>
	
	<div data-options="region:'south',border:false" title="" style="height: 40px; overflow: hidden;padding-right: 50px;" class="dialog-button">
		<a id="submitBtn" href="javascript:void(0);" style="width: 65px;" class="easyui-linkbutton">提交</a>
	</div>
</body>