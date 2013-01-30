--****************************************************************************
-- demo信息表
--****************************************************************************
create table wd_demo
(
	id	varchar2(64) not null,
	isValid number(1) not null,
	loginName varchar2(64) not null,
	password varchar2(64) not null,
	parentId varchar2(64),
	subDemoId varchar2(64),
	name varchar2(64) not null,
	createdate date default sysdate not null,
	lastupdatedate date default sysdate not null,
	description varchar2(2000),
	email varchar2(256),
	testBigDecimal integer,
	testNullBigDecimal integer,
	testIntegerObj integer,
	testNullIntegerObj integer,
	testBooleanObj integer,
	testNullBooleanObj integer,
	testInt integer,
	testBoolean integer,
	primary key(id)
);
create index idx_demo_01 on wd_demo(lastupdatedate);
create unique index idx_demo_02 on wd_demo(loginName);

comment on table wd_demo is 'demo信息表';
comment on column wd_demo.id is '权限id';
comment on column wd_demo.isValid is '是否有效：1有效 0无效';
