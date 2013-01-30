--****************************************************************************
-- 人员信息表
--****************************************************************************
create table wd_operator
(
  id varchar2(64),
  code varchar2(64),
  name varchar2(64),
  password varchar2(64),
  createdate date,
  updatedate date,
  description varchar2(2000),
  primary key(id)
);
