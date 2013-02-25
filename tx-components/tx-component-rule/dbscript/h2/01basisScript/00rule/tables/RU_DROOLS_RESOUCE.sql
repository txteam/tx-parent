--****************************************************************************
-- ru_drools_resource drools规则资源存储
--****************************************************************************
create table ru_drools_resource
(
	ruleId varchar(64),					   --规则id
	resoucePath varchar(128),			   --规则路径
	package varchar(256),				   --规则所在包名
	bytes BLOB,						   	   --规则文件流
    primary key (ruleId)
);
