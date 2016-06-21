<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper 
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapper.namespace}">

	<!-- auto generate default resultMap -->
	<resultMap id="${select.resultMapId}" 
		type="${select.parameterType}">
<#list insert.sqlMapColumnList as column>
<#if !column.isSimpleType()>
		<result column="${column.propertyName}_${column.joinPropertyName}" property="${column.propertyName}.${column.joinPropertyName}"/>
</#if>
</#list>
	</resultMap>
	
	<!-- auto generate default find -->
	<select id="${select.findId}" 
		parameterType="${select.parameterType}"
		resultMap="${select.resultMapId}">
		SELECT *
		  FROM ${select.tableName} ${select.simpleTableName}
		 WHERE
		<trim prefixOverrides="AND | OR">
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${select.idPropertyName})">  
	            AND ${select.simpleTableName}.${select.idColumnName} = ${r"#{"}${select.idPropertyName}${r"}"}
	        </if>
		</trim>
	</select>
	
	<!-- auto generate default query -->
	<select id="${select.queryId}" 
		parameterType="java.util.Map"
		resultMap="${select.resultMapId}">
		SELECT *
		  FROM ${select.tableName} ${select.simpleTableName}
		<trim prefix="WHERE" prefixOverrides="AND | OR">
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            AND ${select.simpleTableName}.${column.columnName} = ${r"#{"}${column.propertyName},javaType=${column.javaType.name}${r"}"}
	        </if>
<#else>
			<if test="${column.propertyName} != null">
				<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName}.${column.joinPropertyName})">  
		            AND ${select.simpleTableName}.${column.columnName} = ${r"#{"}${column.propertyName}.${column.joinPropertyName},javaType=${column.javaType.name}${r"}"}
		        </if>
	        </if>
</#if>
</#list>
		</trim>
		<choose>  
	        <when test="@com.tx.core.util.OgnlUtils@isNotEmpty(orderSql)">  
	            ORDER BY ${r"#{"}orderSql${r"}"}
	        </when>
	        <otherwise>  
	            ORDER BY ${select.idColumnName}
	        </otherwise>  
	    </choose>
	</select>
	
		<!-- auto generate default count -->
	<select id="${select.queryId}Count" 
		parameterType="java.util.Map"
		resultType="java.lang.Integer">
		SELECT COUNT(1)
		  FROM ${select.tableName} ${select.simpleTableName}
		<trim prefix="WHERE" prefixOverrides="AND | OR">
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            AND ${select.simpleTableName}.${column.columnName} = ${r"#{"}${column.propertyName},javaType=${column.javaType.name}${r"}"}
	        </if>
<#else>
			<if test="${column.propertyName} != null">
				<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName}.${column.joinPropertyName})">  
		            AND ${select.simpleTableName}.${column.columnName} = ${r"#{"}${column.propertyName}.${column.joinPropertyName},javaType=${column.javaType.name}${r"}"}
		        </if>
	        </if>
</#if>
</#list>
		</trim>
	</select>

</mapper>
<!--
sqlMap生成描述:
${parseMessage}
-->