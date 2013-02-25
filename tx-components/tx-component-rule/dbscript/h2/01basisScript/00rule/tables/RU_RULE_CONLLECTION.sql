--****************************************************************************
-- ru_rule_collection规则集合表
--****************************************************************************
create table ru_rule_collection
(
	ruleId varchar(64) not null,		   --规则集合id
	ruleItemId varchar(64) not null,	   --关联规则项id
	ruleOrder integer not null,			   --规则集合顺序
	primary key(id)
);
