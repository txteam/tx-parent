del "00webdemo" /f /s /q
del "01auth" /f /s /q

del scriptInput /f /s /q
xcopy ..\01basisScript\. /s /y
mkdir scriptInput\functions
mkdir scriptInput\initdata
mkdir scriptInput\jobs
mkdir scriptInput\packages
mkdir scriptInput\procedures
mkdir scriptInput\sequences
mkdir scriptInput\tables
mkdir scriptInput\triggers
mkdir scriptInput\views

cd "00rule"
  call standard-combine.bat
cd ..

del oracleStandard /f /s /q
mkdir oracleStandard
type scriptInput\tables\*.sql     >oracleStandard\02_tables.sql
type scriptInput\sequences\*.sql  >oracleStandard\03_sequences.sql
type scriptInput\packages\*.sql   >oracleStandard\04_packages.sql
type scriptInput\functions\*.sql  >oracleStandard\05_functions.sql
type scriptInput\procedures\*.sql >oracleStandard\06_procedures.sql
type scriptInput\triggers\*sql    >oracleStandard\07_triggers.sql
type scriptInput\views\*.sql      >oracleStandard\08_views.sql
type scriptInput\initdata\*.sql   >oracleStandard\09_initdata.sql
type scriptInput\jobs\*.sql       >oracleStandard\10_jobs.sql
del oracleStandard.sql /f /s /q
type oracleStandard\*.sql >oracleStandard.sql

copy 01_create_database.sql   oracleStandard\00_tablespace.sql
copy 02_create_user.sql   oracleStandard\01_createuser.sql
pause