--****************************************************************************
-- 权限引用表t_auth_authref
--****************************************************************************
create table t_auth_authref
(
  authid varchar(128) not null,
  refid  varchar(64) not null,
  authType varchar(64) not null,
  authreftype varchar(64) not null,
  createdate date default sysdate not null,
  enddate date,
  createoperid varchar(64) not null,
  isValidDependEndDate integer default 0,
  primary key(authid,refid,authreftype)
);
create index idx_auth_authref_00 on t_auth_authref(authid);
create index idx_auth_authref_01 on t_auth_authref(authreftype);
create index idx_auth_authref_02 on t_auth_authref(refid,authreftype);
create index idx_auth_authref_03 on t_auth_authref(enddate);

