/*****************************************************************************
-- ${dbScript.tableName} : ${dbScript.comment!""}
*****************************************************************************/
drop table if exists ${dbScript.tableName};
create table ${dbScript.tableName}(
<#list dbScript.columnDefs as column>
	${column.columnName} ${dbScript.columnTypeMap[column.columnName]} ${(column.required)?string('not null','')},
</#list>
	primary key(${dbScript.primaryKey})
);
