--****************************************************************************
-- 权限项表t_auth_authitem
--****************************************************************************
create table t_auth_authitem
(
  id varchar(64) not null,			--权限项唯一键key 
  parentId varchar(64),				--父级权限id
  name varchar(256),				--权限项名 
  description varchar(1024),			--权限项目描述
  authType varchar(64) not null, 	--权限类型
  isViewAble integer default 1,		--是否可见
  isEditAble integer default 1,		--是否可编辑
  isValid integer default 1,		--isValid
  primary key(id)
);
