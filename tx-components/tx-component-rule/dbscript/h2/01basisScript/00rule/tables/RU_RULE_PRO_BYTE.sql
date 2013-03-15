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
