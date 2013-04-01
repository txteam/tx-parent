--****************************************************************************
-- 角色信息表
--****************************************************************************
create table t_auth_role
(
  id varchar(64) not null,
  name varchar(64) not null,
  creatOperId varchar(64) not null,
  createdate date default sysdate not null,
  updatedate date,
  updateOperId varchar(64),
  description varchar(200),
  isDefault varchar(2) default 0,
  primary key (id)
);
create unique index idx_auth_role_01 on t_auth_role(name);
create index idx_auth_role_02 on t_auth_role (creatOperId);

--comment on table t_auth_role is '角色信息表';
--comment on column t_auth_role.id is '角色id';
--comment on column t_auth_role.name is '角色名';
--comment on column t_auth_role.createOperId is '角色创建者';
--comment on column t_auth_role.updatedate is '角色创建时间';
--comment on column t_auth_role.updateOperId is '角色最后修改者';
--comment on column t_auth_role.updatedate is '角色最后修改时间';
--comment on column t_auth_role.description is '角色描述';

