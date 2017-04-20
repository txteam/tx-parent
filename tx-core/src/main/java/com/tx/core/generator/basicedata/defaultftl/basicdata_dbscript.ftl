/*****************************************************************************
			表：${dbScriptMapper.tableName}
*****************************************************************************/
drop table if exists ${dbScriptMapper.tableName};
create table ${dbScriptMapper.tableName}(
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
	${entryKey} ${dbScriptMapper.columnName2TypeNameMapping[entryKey]} <#if (dbScriptMapper.columnName2CommentMapping[entryKey])?? &&  (dbScriptMapper.columnName2CommentMapping[entryKey])?length gt 0> comment '${dbScriptMapper.columnName2CommentMapping[entryKey]}'</#if> ,
</#list>
	primary key(${dbScriptMapper.pkColumnName})
);
