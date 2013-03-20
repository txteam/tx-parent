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
--****************************************************************************
-- 规则二进制存储
--****************************************************************************
create table ru_rule_pro_byte
(
	ruleId varchar(64) not null,		   		  --对应规则id
	paramKey varchar(64) not null,				  --对应属性key
	simpleRulePropertyParam varchar(64) not null, --对应的属性
	paramValueOrdered integer default 0 not null, --对应属性排序值
	paramValue blob,							  --对应属性值
	primary key(ruleId)
);
--对于同一规则，相同属性，相同排序的值仅只能有一个
create unique index idx_rule_pro_byte_01 on ru_rule_pro_byte(ruleId,paramKey,paramValueOrdered);
create unique index idx_rule_pro_byte_02 on ru_rule_pro_byte(ruleId,simpleRulePropertyParam,paramValueOrdered);
--****************************************************************************
-- 规则二进制存储
--****************************************************************************
create table ru_rule_pro_value
(
	ruleId varchar(64) not null,		   		  --对应规则id
	paramKey varchar(64) not null,				  --对应属性key
	simpleRulePropertyParam varchar(64) not null,	  --对应属性
	paramValueOrdered integer default 0 not null, --对应属性排序值
	paramValue varchar(2000),					  --对应属性值
	primary key(ruleId)
);
--对于同一规则，相同属性，相同排序的值仅只能有一个
create unique index idx_rule_pro_value_01 on ru_rule_pro_value(ruleId,paramKey,paramValueOrdered);
create unique index idx_rule_pro_value_02 on ru_rule_pro_value(ruleId,simpleRulePropertyParam,paramValueOrdered,paramValueOrdered);
