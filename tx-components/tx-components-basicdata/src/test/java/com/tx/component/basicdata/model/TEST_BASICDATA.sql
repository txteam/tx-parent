--****************************************************************************
-- 表：TEST_BASICDATA
--****************************************************************************
create table TEST_BASICDATA(
	NAME varchar2(64 char),
	CREATEDATE timestamp,
	ID varchar2(64 char),
	REMARK varchar2(2000 char),
	LASTUPDATEDATE timestamp,
	TYPE_ varchar2(255 char),
	CODE varchar2(64 char),
	primary key(ID)
);

