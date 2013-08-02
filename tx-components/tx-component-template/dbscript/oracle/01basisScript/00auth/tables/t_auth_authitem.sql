--****************************************************************************
-- 权限项表t_auth_authitem
--****************************************************************************
create table t_auth_authitem
(
  id varchar2(64) not null,				--权限项唯一键key 
  parentId varchar2(64),				--父级权限id
  name varchar2(256),					--权限项名 
  description varchar2(1024),			--权限项目描述
  authType varchar2(64) not null, 		--权限类型
  isViewAble number(1) default 1,		--是否可见
  isEditAble number(1) default 1,		--是否可编辑
  isValid number(1) default 1,			--isValid
  primary key(id)
);

comment on table t_auth_authitem is '权限项表信息表';
comment on column t_auth_authitem.id is '权限项唯一键key ';
comment on column t_auth_authitem.parentId is '父级权限id';
comment on column t_auth_authitem.name is '权限项名 ';
comment on column t_auth_authitem.description is '权限项目描述';
comment on column t_auth_authitem.authType is '权限类型';
comment on column t_auth_authitem.isViewAble is '是否可见 0:不可见 1：可见';
comment on column t_auth_authitem.isEditAble is '是否可编辑 0:不可编辑 1：可编辑';
comment on column t_auth_authitem.isValid is '是否有效  0:无效 1：有效';
