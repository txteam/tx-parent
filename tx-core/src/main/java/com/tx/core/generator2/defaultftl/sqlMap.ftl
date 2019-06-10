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
	<#elseif column.columnName != column.columnPropertyName>
		<result column="${column.columnName}" property="${column.columnPropertyName}"/>
	<#else>
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
<#if sqlmap.pkColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.pkColumn.propertyName})">  
	            AND ${sqlmap.simpleTableName}.${sqlmap.pkColumn.columnName} = ${r"#{"}${sqlmap.pkColumn.columnPropertyName}${r"}"}
	        </if>
</#if>
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
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(conditions)">
			<foreach collection="conditions" item="conditionTemp">
			<choose>  
		        <when test="conditionTemp.withoutValue">
		        	${r"AND ${conditionTemp.column} ${conditionTemp.operator}"}
		        </when>
		        <when test="conditionTemp.foreach">  
		            ${r"AND ${conditionTemp.column} ${conditionTemp.operator}"} <foreach collection="conditionTemp.value" item="valueTemp" open="(" close=")" separator=",">${r"#{valueTemp}"}</foreach>
		        </when>
		        <otherwise>  
		            ${r"AND ${conditionTemp.column} ${conditionTemp.operator}"} ${r"#{conditionTemp.value}"}
		        </otherwise>
			</choose>
			</foreach>
	        </if>
<#if sqlmap.parentIdColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.parentIdColumn.propertyName}s)">  
	            AND ${sqlmap.simpleTableName}.${sqlmap.parentIdColumn.columnName} IN <foreach collection="${sqlmap.parentIdColumn.propertyName}s" open="(" close=")" separator="," item="parentIdTemp">${r"#{parentIdTemp}"}</foreach>
	        </if>
</#if>
<#list sqlmap.columnList as column>
	<#if column.propertyName == "createDate">
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(minCreateDate)">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} >= ${r"#{"}minCreateDate${r"} ]]>"}
	        </if>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(maxCreateDate)">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} < ${r"#{"}maxCreateDate${r"} ]]>"}
	        </if>
	<#elseif column.propertyType.getName() == "java.util.Date" || column.propertyType.getName() == "java.sql.Date">
	<#elseif column.propertyType.getName() == "float" || column.propertyType.getName() == "java.lang.Float">
	<#elseif column.propertyType.getName() == "double" || column.propertyType.getName() == "java.lang.Double">
	<#elseif !column.isSimpleValueType()>
			<if test="${column.propertyName} != null">
				<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.columnPropertyName})">  
		            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"} ]]>"}
		        </if>
	        </if>			
	<#else>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"} ]]>"}
	        </if>			
	</#if>	
</#list>
<#if sqlmap.pkColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(exclude${sqlmap.pkColumn.propertyName?cap_first})">
				${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${sqlmap.pkColumn.columnName} ${r"<>"} ${r"#{exclude"}${sqlmap.pkColumn.propertyName?cap_first}${r"} ]]>"}
			</if>
</#if>
		</trim>
		<choose>  
	        <when test="@com.tx.core.util.OgnlUtils@isNotEmpty(orders)">  
	            ORDER BY <foreach collection="orders" item="orderTemp" separator=",">${r"${orderTemp.column} ${orderTemp.direction}"}</foreach>
	        </when>
	        <otherwise>  
	            ORDER BY ${sqlmap.defaultOrderBy}
	        </otherwise>  
	    </choose>
	</select>

	<!-- auto generate default count -->
	<select id="queryCount" 
		parameterType="java.util.Map"
		resultType="java.lang.Integer">
		SELECT COUNT(1)
		  FROM ${sqlmap.tableName} ${sqlmap.simpleTableName}
		<trim prefix="WHERE" prefixOverrides="AND | OR">
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(conditions)">
			<foreach collection="conditions" item="conditionTemp">
			<choose>  
		        <when test="conditionTemp.withoutValue">
		        	${r"AND ${conditionTemp.column} ${conditionTemp.operator}"}
		        </when>
		        <when test="conditionTemp.foreach">  
		            ${r"AND ${conditionTemp.column} ${conditionTemp.operator}"} <foreach collection="conditionTemp.value" item="valueTemp" open="(" close=")" separator=",">${r"#{valueTemp}"}</foreach>
		        </when>
		        <otherwise>  
		            ${r"AND ${conditionTemp.column} ${conditionTemp.operator}"} ${r"#{conditionTemp.value}"}
		        </otherwise>
			</choose>
			</foreach>
	        </if>
<#if sqlmap.parentIdColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.parentIdColumn.propertyName}s)">  
	            AND ${sqlmap.simpleTableName}.${sqlmap.parentIdColumn.columnName} IN <foreach collection="${sqlmap.parentIdColumn.propertyName}s" open="(" close=")" separator="," item="parentIdTemp">${r"#{parentIdTemp}"}</foreach>
	        </if>
</#if>
<#list sqlmap.columnList as column>
	<#if column.propertyName == "createDate">
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(minCreateDate)">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} >= ${r"#{"}minCreateDate${r"} ]]>"}
	        </if>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(maxCreateDate)">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} < ${r"#{"}maxCreateDate${r"} ]]>"}
	        </if>
	<#elseif column.propertyType.getName() == "java.util.Date" || column.propertyType.getName() == "java.sql.Date">
	<#elseif column.propertyType.getName() == "float" || column.propertyType.getName() == "java.lang.Float">
	<#elseif column.propertyType.getName() == "double" || column.propertyType.getName() == "java.lang.Double">
	<#elseif !column.isSimpleValueType()>
			<if test="${column.propertyName} != null">
				<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.columnPropertyName})">  
		            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"} ]]>"}
		        </if>
	        </if>			
	<#else>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${column.propertyName})">  
	            ${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${column.columnName} = ${r"#{"}${column.columnPropertyName}${r"} ]]>"}
	        </if>			
	</#if>	
</#list>
<#if sqlmap.pkColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(exclude${sqlmap.pkColumn.propertyName?cap_first})">
				${r"<![CDATA[ "}AND ${sqlmap.simpleTableName}.${sqlmap.pkColumn.columnName} ${r"<>"} ${r"#{exclude"}${sqlmap.pkColumn.propertyName?cap_first}${r"} ]]>"}
			</if>
</#if>
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
<#if sqlmap.pkColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.pkColumn.propertyName})">  
	            AND ${sqlmap.pkColumn.columnName} = ${r"#{"}${sqlmap.pkColumn.columnPropertyName}${r"}"}
	        </if>
</#if>
<#if sqlmap.codeColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.codeColumn.propertyName})">  
	            AND ${sqlmap.codeColumn.columnName} = ${r"#{"}${sqlmap.codeColumn.columnPropertyName}${r"}"}
	        </if>
</#if>
		</trim>
	</delete>
	
	<!-- auto generate default update -->
	<update id="update"
	    parameterType="java.util.Map">  
	    UPDATE ${sqlmap.tableName}
	    <trim prefix="SET" suffixOverrides=",">
<#list sqlmap.columnList as column>
	<#if (!column.isPrimaryKey() && column.isUpdatable() && column.propertyName != "createDate" && column.propertyName != "createOperatorId" && column.propertyName != "code")>
			<if test="_parameter.containsKey('${column.propertyName}')">
	    		${column.columnName} = ${r"#{"}${column.columnPropertyName},javaType=${column.columnPropertyType.getName()}${r"}"},
	    	</if>
	</#if>
</#list>
	    </trim>
	    WHERE
		<trim prefixOverrides="AND | OR">
<#if sqlmap.pkColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.pkColumn.propertyName})">  
	            AND ${sqlmap.pkColumn.columnName} = ${r"#{"}${sqlmap.pkColumn.columnPropertyName}${r"}"}
	        </if>
</#if>
<#if sqlmap.codeColumn??>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${sqlmap.codeColumn.propertyName})">  
	            AND ${sqlmap.codeColumn.columnName} = ${r"#{"}${sqlmap.codeColumn.columnPropertyName}${r"}"}
	        </if>
</#if>
		</trim>
	</update> 

</mapper>