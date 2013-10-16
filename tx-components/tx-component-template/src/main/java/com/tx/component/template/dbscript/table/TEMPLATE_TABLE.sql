--****************************************************************************
-- 表：TEMPLATE_TABLE
--****************************************************************************
create table TEMPLATE_TABLE(
	TABLESTATUS varchar2(255 char),
	TABLETYPE varchar2(64 char),
	SYSTEMID varchar2(64 char),
	NAME varchar2(64 char),
	CREATEDATE timestamp,
	CREATEOPERATORID varchar2(64 char),
	ID varchar2(64 char),
	REMARK varchar2(2000 char),
	LASTUPDATEDATE timestamp,
	TABLENAME varchar2(64 char),
	primary key(ID)
);
--create index idx_xxxx_xxxx on TEMPLATE_TABLE(xxxx);
--create unique index idx_xxxx_xxxx on TEMPLATE_TABLE(xxxx);

--comment on table TEMPLATE_TABLE is 'TEMPLATE_TABLE信息表';
--comment on column TEMPLATE_TABLE.TABLESTATUS is 'xxxx';
--comment on column TEMPLATE_TABLE.TABLETYPE is 'xxxx';
--comment on column TEMPLATE_TABLE.SYSTEMID is 'xxxx';
--comment on column TEMPLATE_TABLE.NAME is 'xxxx';
--comment on column TEMPLATE_TABLE.CREATEDATE is 'xxxx';
--comment on column TEMPLATE_TABLE.CREATEOPERATORID is 'xxxx';
--comment on column TEMPLATE_TABLE.ID is 'xxxx';
--comment on column TEMPLATE_TABLE.REMARK is 'xxxx';
--comment on column TEMPLATE_TABLE.LASTUPDATEDATE is 'xxxx';
--comment on column TEMPLATE_TABLE.TABLENAME is 'xxxx';
