/*****************************************************************************
			表：${dbScriptMapper.tableName}
*****************************************************************************/
drop table if exists ${dbScriptMapper.tableName};
create table ${dbScriptMapper.tableName}(
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
	${entryKey} ${dbScriptMapper.columnName2TypeNameMapping[entryKey]},
</#list>
	primary key(${dbScriptMapper.pkColumnName})
);
