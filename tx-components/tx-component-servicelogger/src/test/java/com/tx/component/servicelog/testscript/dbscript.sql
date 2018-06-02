--****************************************************************************
-- 表：MAINFRAME_LOGIN_LOG
--****************************************************************************
create table MAINFRAME_LOGIN_LOG(
	CLIENTIPADDRESS varchar2(255 char),
	ORGANIZATIONID varchar2(64 char),
	MESSAGE varchar2(255 char),
	CREATEDATE timestamp,
	VCID varchar2(64 char),
	ID varchar2(64 char),
	OPERATORID varchar2(64 char),
	OPERATORNAME varchar2(64 char),
	OPERATORLOGINNAME varchar2(64 char),
	TYPE varchar2(255 char),
	primary key(ID)
);
