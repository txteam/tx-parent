--****************************************************************************
-- 规则二进制存储
--****************************************************************************
create table ru_rule_pro_value
(
	ruleId varchar(64) not null,		   		  --对应规则id
	paramKey varchar(64) not null,				  --对应属性key
	simpleRulePropertyParam varchar(64) not null, --对应属性
	paramValueOrdered integer default 0 not null, --对应属性排序值
	paramValue varchar(2000),					  --对应属性值
	primary key(ruleId)
);
--对于同一规则，相同属性，相同排序的值仅只能有一个
create unique index idx_rule_pro_value_01 on ru_rule_pro_value(ruleId,paramKey,paramValueOrdered);
create unique index idx_rule_pro_value_02 on ru_rule_pro_value(ruleId,simpleRulePropertyParam,paramValueOrdered,paramValueOrdered);
