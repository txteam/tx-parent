<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper 
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${sqlmap.entityTypeSimpleName?uncap_first}">

	<!-- auto generate default resultMap -->
	<resultMap id="${sqlmap.entityTypeSimpleName?uncap_first}Map" 
		type="${sqlmap.entityTypeName}">
<#list sqlmap.columnList as column>
	<#if !column.isSimpleValueType()>
		<result column="${column.columnName}" property="${column.columnPropertyName}"/>
	</#if>
</#list>
	</resultMap>
	
	<!-- auto generate default find -->
	<select id="find" 
		parameterType="${sqlmap.entityTypeName}"
		resultMap="${sqlmap.entityTypeSimpleName?uncap_first}Map">
		SELECT 
<#list sqlmap.columnList as column>
				${sqlmap.simpleTableName}.${column.columnName}<#if column_has_next>,</#if>
</#list>
		  FROM ${sqlmap.tableName} ${sqlmap.simpleTableName}
		 WHERE
		<trim prefixOverrides="AND | OR">
<#list sqlmap.pkColumnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"}"}
	        </if>
</#list>
<#if sqlmap.codeColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.codeColumn.propertyName})">  
	            AND ${sqlmap.simpleTableName}.${sqlmap.codeColumn.columnName} = ${r"#{"}${sqlmap.codeColumn.columnPropertyName}${r"}"}
	        </if>
</#if>
		</trim>
	</select>
	
	<!-- auto generate default query -->
	<select id="query" 
		parameterType="java.util.Map"
		resultMap="${sqlmap.entityTypeSimpleName?uncap_first}Map">
		SELECT 
<#list sqlmap.columnList as column>
				${sqlmap.simpleTableName}.${column.columnName}<#if column_has_next>,</#if>
</#list>
		  FROM ${sqlmap.tableName} ${sqlmap.simpleTableName}
		<trim prefix="WHERE" prefixOverrides="AND | OR">
<#list sqlmap.columnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"} ]]>"}
	        </if>
</#list>
<#list sqlmap.pkColumnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(exclude${column.propertyName?cap_first})">
				${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} ${r"<>"} ${r"#{exclude"}${column.propertyName?cap_first}${r"} ]]>"}
			</if>
</#list>
		</trim>
		ORDER BY <#list sqlmap.pkColumnList as column>${column.columnName}<#if column_has_next>,</#if></#list>
	</select>

	<!-- auto generate default count -->
	<select id="queryCount" 
		parameterType="java.util.Map"
		resultType="java.lang.Integer">
		SELECT COUNT(1)
		  FROM ${sqlmap.tableName} ${sqlmap.simpleTableName}
		<trim prefix="WHERE" prefixOverrides="AND | OR">
<#list sqlmap.columnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"} ]]>"}
	        </if>
</#list>
<#list sqlmap.pkColumnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(exclude${column.propertyName?cap_first})">
				${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} ${r"<>"} ${r"#{exclude"}${column.propertyName?cap_first}${r"} ]]>"}
			</if>
</#list>
		</trim>
	</select>
	
	<!-- auto generate default insert -->
    <insert id="insert" 
		parameterType="${sqlmap.entityTypeName}">
		INSERT INTO ${sqlmap.tableName}
		(
<#list sqlmap.columnList as column>
	<#if column.isInsertable()>
			${column.columnName}<#if column_has_next>,</#if>
	</#if>
</#list>
		)
		VALUES
		(
<#list sqlmap.columnList as column>
	<#if column.isInsertable()>
			${r"#{"}${column.columnPropertyName}${r"}"}<#if column_has_next>,</#if>
	</#if>
</#list>
		)
	</insert>
	
	<!-- auto generate default delete -->
	<delete id="delete" 
		parameterType="${sqlmap.entityTypeName}">
		DELETE FROM ${sqlmap.tableName}  WHERE
		<trim prefixOverrides="AND | OR">
<#list sqlmap.pkColumnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            AND ${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"}"}
	        </if>
</#list>
		</trim>
	</delete>
	
	<!-- auto generate default update -->
	<update id="update"
	    parameterType="java.util.Map">  
	    UPDATE ${sqlmap.tableName}
	    <trim prefix="SET" suffixOverrides=",">
<#list sqlmap.columnList as column>
	<#if (!column.isPrimaryKey() && column.isUpdatable())>
			<if test="_parameter.containsKey('${column.propertyName}')">
	    		${column.columnName} = ${r"#{"}${column.columnPropertyName},javaType=${column.columnPropertyType.getName()}${r"}"},
	    	</if>
	</#if>
</#list>
	    </trim>
	    WHERE
		<trim prefixOverrides="AND | OR">
<#list sqlmap.pkColumnList as column>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            AND ${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"}"}
	        </if>
</#list>
		</trim>
	</update> 

</mapper>