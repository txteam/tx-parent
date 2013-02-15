--****************************************************************************
-- workflow流程实例信息表
--****************************************************************************
create table wf_process_def
(
	id	varchar(64) not null,              --流程定义id:由系统自生成
	key varchar(64) not null,
	name varchar(64) not null,
	
	isValid integer not null,
	loginName varchar(64) not null,
	password varchar(64) not null,
	primary key(id)
);
    
    /** 流程定义key:对应activiti中的key */
    private String key;
    
    /** 流程名 */
    private String name;
    
    /** 流程类别 */
    private String category;
    
    /** 流程版本号 */
    private String version;
    
    /** 流程定义对应的业务类型 */
    private String serviceType;
    
    /** 流程状态:用以支持，测试态，运营态等流程状态 */
    private String state = WorkFlowConstants.PROCESS_DEFINITION_STATE_TEST;
create index idx_demo_01 on wd_demo(lastupdatedate);
create unique index idx_demo_02 on wd_demo(loginName);

