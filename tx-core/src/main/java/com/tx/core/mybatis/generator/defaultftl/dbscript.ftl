--****************************************************************************
-- 表：${dbScriptMapper.tableName}
--****************************************************************************
create table ${dbScriptMapper.tableName}(
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
	${entryKey}		${dbScriptMapper.columnName2TypeNameMapping[entryKey]},
</#list>
	primary key(${dbScriptMapper.pkColumnName});
);
--create index idx_xxxx_xxxx on ${tableName}(xxxx);
--create unique index idx_xxxx_xxxx on ${tableName}(xxxx);

--comment on table ${tableName} is 'demo信息表';
<#list dbScriptMapper.columnName2TypeNameMapping?keys as entryKey>
--comment on column ${tableName}.${entry.key()} is 'xxxx';
</#list>
<!--
dbscript生成描述:
${parseMessage}
-->
