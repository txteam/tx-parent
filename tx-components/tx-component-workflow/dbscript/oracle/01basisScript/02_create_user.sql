prompt EXECUTE 02_create_user.sql...

-- 创建用户
CREATE USER wdadmin IDENTIFIED BY wdadmin
  DEFAULT TABLESPACE WD_DAT
  TEMPORARY TABLESPACE WD_TEMP;
GRANT DBA TO wdadmin;

-- 授权
GRANT ALL PRIVILEGE TO wdadmin;
ALTER USER wdadmin DEFAULT ROLE ALL;

prompt EXECUTE 02_create_user.sql...DONE.

EXIT;

