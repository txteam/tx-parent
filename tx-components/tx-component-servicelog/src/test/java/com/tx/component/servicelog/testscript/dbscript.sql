--****************************************************************************
-- 表：MAINFRAME_LOGIN_LOG
--****************************************************************************
create table MAINFRAME_LOGIN_LOG(
	id varchar2(64 char),
	message varchar2(255 char),
	operatorId varchar2(64 char),
	vcid varchar2(64 char),
	organizationId varchar2(64 char),
	type varchar2(255 char),
	createDate timestamp,
	clientIpAddress varchar2(255 char),
	primary key(ID)
);
