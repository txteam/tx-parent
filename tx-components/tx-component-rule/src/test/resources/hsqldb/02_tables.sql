-- "webdemo模块:创建表逻辑  start..."  
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
--****************************************************************************
-- ru_rule_collection规则集合表
--****************************************************************************
create table ru_rule_collection
(
	ruleId varchar(64) not null,		   --规则集合id
	ruleItemId varchar(64) not null,	   --关联规则项id
	ruleOrder integer not null,			   --规则集合顺序
	primary key(ruleId)
);
--****************************************************************************
-- ru_rule_def规则实例信息表
--****************************************************************************
--****************************************************************************
-- ru_rule_def规则实例信息表
--****************************************************************************
create table ru_rule_def
(
	id	varchar(64) not null,              --规则id
	rule varchar(64) not null,             --规则唯一键名
	ruleType varchar(32),				   --规则类型：collection,drools...
	serviceType varchar(64),      		   --规则对应的业务类型
	state varchar(32),					   --规则状态TEST,OPERATION,ERROR,STOP
	name varchar(64),					   --规则名
	remark varchar(256),				   --规则备注
	primary key(id)
);
create unique index idx_rule_def_01 on ru_rule_def(rule,ruleType,serviceType);
-- "webdemo模块:创建表逻辑  end..."
