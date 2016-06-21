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
		SELECT 
<#list select.sqlMapColumnList as column>
<#if column.isSimpleType()>
				${select.simpleTableName}.${column.columnName}<#if !column.isSameName()> AS ${column.propertyName}</#if><#if column_has_next>,</#if>
<#else>
				${select.simpleTableName}.${column.columnName} AS ${column.propertyName}_${column.joinPropertyName}<#if column_has_next>,</#if>
</#if>
</#list>
		  FROM ${select.tableName} ${select.simpleTableName}
		 WHERE
		<trim prefixOverrides="AND | OR">
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${select.idPropertyName})">  
	            AND ${select.simpleTableName}.${select.idColumnName} = ${r"#{"}${select.idPropertyName}${r"}"}
	        </if>
<#list select.otherCondition as condition>
			AND ${select.simpleTableName}.${condition}
</#list>
		</trim>
	</select>
	
	<!-- auto generate default query -->
	<select id="${select.queryId}" 
		parameterType="java.util.Map"
		resultMap="${select.resultMapId}">
		SELECT 
<#list select.sqlMapColumnList as column>
<#if column.isSimpleType()>
				${select.simpleTableName}.${column.columnName}<#if !column.isSameName()> AS ${column.propertyName}</#if><#if column_has_next>,</#if>
<#else>
				${select.simpleTableName}.${column.columnName} AS ${column.propertyName}_${column.joinPropertyName}<#if column_has_next>,</#if>
</#if>
</#list>
		  FROM ${select.tableName} ${select.simpleTableName}
		<trim prefix="WHERE" prefixOverrides="AND | OR">
<#list select.queryConditionMap?keys as key>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${key})">  
	            ${r"<![CDATA[ "}AND ${select.simpleTableName}.${select.queryConditionMap[key]}${r" ]]>"}
	        </if>
</#list>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(exclude${select.idPropertyName?cap_first})">
				${r"<![CDATA[ "}AND ${select.simpleTableName}.${select.idColumnName} ${r"<>"} ${r"#{exclude"}${select.idPropertyName?cap_first}${r",javaType=java.lang.String} ]]>"}
			</if>
<#list select.otherCondition as condition>
			AND ${select.simpleTableName}.${condition}
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
<#list select.queryConditionMap?keys as key>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${key})">  
	            ${r"<![CDATA[ "}AND ${select.simpleTableName}.${select.queryConditionMap[key]}${r" ]]>"}
	        </if>
</#list>
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(exclude${select.idPropertyName?cap_first})">
				${r"<![CDATA[ "}AND ${select.simpleTableName}.${select.idColumnName} ${r"<>"} ${r"#{exclude"}${select.idPropertyName?cap_first}${r",javaType=java.lang.String} ]]>"}
			</if>
<#list select.otherCondition as condition>
			AND ${select.simpleTableName}.${condition}
</#list>
		</trim>
	</select>
    
    <!-- auto generate default insert -->
    <insert id="${insert.id}" 
		parameterType="${insert.parameterType}">
<#if insert.isUseSelectKey()>
		<selectKey keyProperty="${insert.selectKey.keyProperty}" resultType="${insert.selectKey.resultType}">
			SELECT ${insert.selectKey.sequence}.nextVal FROM DUAL
		</selectKey>	
</#if>
		INSERT INTO ${insert.tableName}
		(
<#list insert.sqlMapColumnList as column>
			${column.columnName}<#if column_has_next>,</#if>
</#list>
		)
		VALUES
		(
<#list insert.sqlMapColumnList as column>
<#if column.isSimpleType()>
			${r"#{"}${column.propertyName}${r"}"}<#if column_has_next>,</#if>
<#else>
			<if test="${column.propertyName} != null">
				${r"#{"}${column.propertyName}.${column.joinPropertyName}${r"}"}<#if column_has_next>,</#if>
	        </if>
	        <if test="${column.propertyName} == null">
				${r"#{"}${column.propertyName},javaType=${column.javaType.name}${r"}"}<#if column_has_next>,</#if>
	        </if>
</#if>
</#list>
		)
	</insert>
	
	<!-- auto generate default delete -->
	<delete id="${delete.id}" 
		parameterType="${delete.parameterType}">
		DELETE FROM ${delete.tableName}  WHERE
		<trim prefixOverrides="AND | OR">
			<if test="@com.tx.core.util.OgnlUtils@isNotEmpty(${delete.idPropertyName})">  
	            AND ${delete.idColumnName} = ${r"#{"}${delete.idPropertyName}${r"}"}
	        </if>
		</trim>
	</delete>

	<!-- auto generate default update -->
	<update id="${update.id}"
	    parameterType="java.util.Map">  
	    UPDATE ${update.tableName} 
	    <trim prefix="SET" suffixOverrides=",">
<#list update.sqlMapColumnList as column>
<#if !column.isId()>
<#if column.isSimpleType()>
			<if test="_parameter.containsKey('${column.propertyName}')">
	    		${column.columnName} = ${r"#{"}${column.propertyName},javaType=${column.javaType.name}${r"}"},
	    	</if>	
<#else>
			<if test="_parameter.containsKey('${column.propertyName}')">
				<if test="${column.propertyName} != null">
					${column.columnName} = ${r"#{"}${column.propertyName}.${column.joinPropertyName},javaType=${column.javaType.name}${r"}"},
		        </if>
		        <if test="${column.propertyName} == null">
					${column.columnName} = ${r"#{"}${column.propertyName},javaType=${column.javaType.name}${r"}"},
		        </if>
	    	</if>
</#if>
</#if>
</#list>
	    </trim>
	    WHERE ${update.idColumnName} = ${r"#{"}${update.idPropertyName}${r"}"} 
	</update>  

</mapper>
<!--
sqlMap生成描述:
${parseMessage}
-->