--****************************************************************************
-- 角色信息表t_auth_role
--****************************************************************************
create table t_auth_role
(
  id varchar(64) not null,
  name varchar(64) not null,
  createOperId varchar(64) not null,
  createdate date default sysdate not null,
  updatedate date,
  updateOperId varchar(64),
  description varchar(200),
  isDefault varchar(2) default 0,
  primary key (id)
);
create unique index idx_auth_role_01 on t_auth_role(name);
create index idx_auth_role_02 on t_auth_role (createOperId);
