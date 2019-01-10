/*****************************************************************************
			表：${dbScriptMapper.tableName}
*****************************************************************************/
begin
    execute immediate 'drop table ${dbScriptMapper.tableName}';
    exception when others then null;
end;

create table ${dbScriptMapper.tableName}(
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
	${entryKey} ${dbScriptMapper.columnName2TypeNameMapping[entryKey]} ,
</#list>
	primary key(${dbScriptMapper.pkColumnName})
);

<#if dbScriptMapper.tableComment??> comment on table ${dbScriptMapper.tableName} is '${dbScriptMapper.tableComment}';</#if>
<#list dbScriptMapper.columnName2CommentMapping?keys as entryKey>
	<#if (dbScriptMapper.columnName2CommentMapping[entryKey])?? && (dbScriptMapper.columnName2CommentMapping[entryKey])?length gt 0> comment on column ${dbScriptMapper.tableName}.${entryKey} is '${dbScriptMapper.columnName2CommentMapping[entryKey]}';</#if>
</#list>