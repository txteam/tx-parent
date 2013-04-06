--****************************************************************************
-- ru_rule_def规则实例信息表
--****************************************************************************
create table ru_rule_def
(
	id	varchar2(64) not null,              --规则id
	rule varchar2(64) not null,             --规则唯一键名
	ruleType varchar2(32),				   --规则类型：collection,drools...
	serviceType varchar2(64),      		   --规则对应的业务类型
	state varchar2(32),					   --规则状态TEST,OPERATION,ERROR,STOP
	name varchar2(64),					   --规则名
	remark varchar2(4000),				   --规则备注
	primary key(id)
);
create unique index idx_rule_def_01 on ru_rule_def(rule,ruleType,serviceType);
