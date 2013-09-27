--****************************************************************************
-- 表：${dbScriptMapper.tableName}
--****************************************************************************
create table ${dbScriptMapper.tableName}(
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
	${entryKey} ${dbScriptMapper.columnName2TypeNameMapping[entryKey]},
</#list>
	primary key(${dbScriptMapper.pkColumnName})
);
--create index idx_xxxx_xxxx on ${dbScriptMapper.tableName}(xxxx);
--create unique index idx_xxxx_xxxx on ${dbScriptMapper.tableName}(xxxx);

--comment on table ${dbScriptMapper.tableName} is 'demo信息表';
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
--comment on column ${dbScriptMapper.tableName}.${entryKey} is 'xxxx';
</#list>
