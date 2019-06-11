/*****************************************************************************
-- TEST_TESTMODE3 : 
*****************************************************************************/
drop table if exists TEST_TESTMODE3;
create table TEST_TESTMODE3(
	id bigint not null,
	code varchar(32) not null,
	lastUpdateDate datetime(6) not null,
	lastUpdateOperatorId varchar(64) ,
	name varchar(32) not null,
	testInt integer ,
	testLong bigint ,
	testBigDecimal decimal(32,8) ,
	type varchar(64) ,
	createDate datetime(6) not null,
	createOperatorId varchar(64) ,
	expiryDate datetime(6) ,
	modifyAble bit ,
	parentId bigint ,
	remark varchar(512) ,
	nested1Id varchar(64) ,
	nested2Code varchar(64) ,
	success bit ,
	effictiveDate datetime(6) ,
	attributes varchar(255) ,
	description varchar(512) ,
	primary key(id)
);
