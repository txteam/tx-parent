/*****************************************************************************
-- TEST_TESTMODE2 : 
*****************************************************************************/
drop table if exists TEST_TESTMODE2;
create table TEST_TESTMODE2(
	code varchar(64) not null,
	lastUpdateDate datetime(6) ,
	lastUpdateOperatorId varchar(64) ,
	name varchar(64) ,
	testInt integer ,
	testLong bigint ,
	type varchar(64) ,
	createDate datetime(6) ,
	createOperatorId varchar(64) ,
	remark varchar(512) ,
	nested1Id varchar(64) ,
	nested2Code varchar(64) ,
	attributes varchar(255) ,
	description varchar(512) ,
	primary key(code)
);
