--****************************************************************************
-- 权限项表t_auth_authitem
--****************************************************************************
create table auth_authitem${tableSuffix}
(
  id varchar2(64) not null,				--权限项唯一键key 
  systemid varchar2(64) not null,       --系统唯一键id
  parentId varchar2(64),				--父级权限id
  name varchar2(256),					--权限项名 
  description varchar2(1024),			--权限项目描述
  authType varchar2(64) not null, 		--权限类型
  viewAble number(1) default 1,		--是否可见
  editAble number(1) default 1,		--是否可编辑
  valid number(1) default 1,
  primary key(id,systemid)
);
create index idx_auth_authitem_01 on auth_authitem${tableSuffix}(parentId);
comment on table auth_authitem${tableSuffix} is '权限项表信息表';
comment on column auth_authitem${tableSuffix}.systemid is '权限项所属系统唯一键';
comment on column auth_authitem${tableSuffix}.id is '权限项唯一键key ';
comment on column auth_authitem${tableSuffix}.parentId is '父级权限id';
comment on column auth_authitem${tableSuffix}.name is '权限项名 ';
comment on column auth_authitem${tableSuffix}.description is '权限项目描述';
comment on column auth_authitem${tableSuffix}.authType is '权限类型';
comment on column auth_authitem${tableSuffix}.viewAble is '是否可见 0:不可见 1：可见';
comment on column auth_authitem${tableSuffix}.editAble is '是否可编辑 0:不可编辑 1：可编辑';
comment on column auth_authitem${tableSuffix}.valid is '是否有效  0:无效 1：有效';

--****************************************************************************
-- 权限引用表t_auth_authref
--****************************************************************************
create table auth_authref${tableSuffix}(
  id varchar2(64) not null,
  refid  varchar2(64) not null,
  authreftype varchar2(64) not null,
  authid varchar2(128) not null,
  systemid varchar2(64) not null,
  authType varchar2(64) not null,
  createdate date default sysdate not null,
  enddate date,
  createoperid varchar2(64) not null,
  validDependEndDate number(1) default 0,
  primary key(id)
);
create unique index idx_auth_authref_00 on auth_authref${tableSuffix}(refid,authreftype,authid,systemid,authType);
create index idx_auth_authref_01 on auth_authref${tableSuffix}(refid,authreftype);
create index idx_auth_authref_02 on auth_authref${tableSuffix}(systemid,refid,authreftype);
create index idx_auth_authref_03 on auth_authref${tableSuffix}(systemid,authid);
create index idx_auth_authref_04 on auth_authref${tableSuffix}(systemid,authreftype);
create index idx_auth_authref_05 on auth_authref${tableSuffix}(authreftype);
create index idx_auth_authref_06 on auth_authref${tableSuffix}(enddate);
comment on table auth_authref${tableSuffix} is '权限关联项信息表';
comment on column auth_authref${tableSuffix}.authid is '权限id';
comment on column auth_authref${tableSuffix}.authType is '权限关联项的权限项类型';
comment on column auth_authref${tableSuffix}.refId is '关联项id可以为角色id,用户id等';
comment on column auth_authref${tableSuffix}.authreftype is '权限关联项类型 AUTHREFTYPE_OPERATOR AUTHREFTYPE_OPERATOR_TEMP AUTHREFTYPE_ROLE';
comment on column auth_authref${tableSuffix}.createdate is '权限关联项创建时间';
comment on column auth_authref${tableSuffix}.enddate is '权限关联项目失效时间';
comment on column auth_authref${tableSuffix}.createoperid is '权限授予人';
comment on column auth_authref${tableSuffix}.validDependEndDate is '有效性是否依赖结束时间';
