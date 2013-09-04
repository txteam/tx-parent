--****************************************************************************
-- 权限项表t_auth_authitem
--****************************************************************************
create table auth_authitem${tableSuffix}(
  id varchar(64) not null,			--权限项唯一键key
  parentId varchar(64),				--父级权限id
  systemId varchar(64) not null,    --系统id
  name varchar(256),				--权限项名 
  description varchar(1024),			--权限项目描述
  authType varchar(64) not null, 	--权限类型
  viewAble bit default 1,		--是否可见
  editAble bit default 1,		--是否可编辑
  valid bit default 1,		--isValid
  primary key(id,systemId)
);
create index idx_auth_authitem_01 on auth_authitem${tableSuffix}(parentId);
--****************************************************************************
-- 权限引用表t_auth_authref
--****************************************************************************
create table auth_authref${tableSuffix}(
  id varchar(64) not null,
  refid  varchar(64) not null,
  authreftype varchar(64) not null,
  authid varchar(128) not null,
  systemid varchar(64) not null,
  authType varchar(64) not null,
  createdate timestamp default now() not null,
  enddate timestamp,
  createoperid varchar(64) not null,
  validDependEndDate bit default 0,
  primary key(id)
);
create unique index idx_auth_authref_00 on auth_authref${tableSuffix}(refid,authreftype,authid,systemid,authType);
create index idx_auth_authref_01 on auth_authref${tableSuffix}(refid,authreftype);
create index idx_auth_authref_02 on auth_authref${tableSuffix}(systemid,refid,authreftype);
create index idx_auth_authref_03 on auth_authref${tableSuffix}(systemid,authid);
create index idx_auth_authref_04 on auth_authref${tableSuffix}(systemid,authreftype);
create index idx_auth_authref_05 on auth_authref${tableSuffix}(authreftype);
create index idx_auth_authref_06 on auth_authref${tableSuffix}(enddate);
