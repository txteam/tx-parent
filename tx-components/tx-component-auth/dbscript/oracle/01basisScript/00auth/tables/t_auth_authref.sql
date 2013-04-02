--****************************************************************************
-- 权限关联表t_auth_authref
--****************************************************************************
--drop table t_auth_authref;
create table t_auth_authref
(
  authid varchar2(128) not null,
  refid  varchar2(64) not null,
  authreftype varchar2(64) not null,
  createdate date default sysdate not null,
  enddate date,
  createoperid varchar2(64) not null,
  isValidDependEndDate number(1) default 0,
  primary key(authid,refid,authreftype)
);
create index idx_auth_authref_02 on t_auth_authref(refid,authreftype);
create index idx_auth_authref_03 on t_auth_authref(enddate);

comment on table t_auth_authref is '权限关联项信息表';
comment on column t_auth_authref.authid is '权限id';
comment on column t_auth_authref.refId is '关联项id可以为角色id,用户id等';
comment on column t_auth_authref.authreftype is '权限关联项类型 AUTHREFTYPE_OPERATOR AUTHREFTYPE_OPERATOR_TEMP AUTHREFTYPE_ROLE';
comment on column t_auth_authref.createdate is '权限关联项创建时间';
comment on column t_auth_authref.enddate is '权限关联项目失效时间';
comment on column t_auth_authref.createoperid is '权限授予人';
comment on column t_auth_authref.isValidDependEndDate is '有效性是否依赖结束时间';
