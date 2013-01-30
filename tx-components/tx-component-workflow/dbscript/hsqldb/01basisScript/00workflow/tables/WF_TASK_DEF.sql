--****************************************************************************
-- 人员信息表
--****************************************************************************
create table wd_operator
(
  id varchar(64),
  code varchar(64),
  name varchar(64),
  password varchar(64),
  createdate date,
  updatedate date,
  description varchar(2000),
  primary key(id)
);
