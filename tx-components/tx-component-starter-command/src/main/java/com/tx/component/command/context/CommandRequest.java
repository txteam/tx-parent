package com.tx.component.command.context;

import java.util.Date;

/**
  * 交易请求<br/>
  * <功能详细描述>
  * 
  * @author  bobby
  * @version  [版本号, 2015年1月5日]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public interface CommandRequest {
    
    /** 获取操作流水 */
    public String getId();
    
    /** 操作员id */
    public String getOperatorId();
    
    /** 请求发起人所在的虚中心/分公司 */
    public String getVcid();
    
    /** 请求发起人所在的组织id */
    public String getOrganizationId();
    
    /** 获取操作创建时间 */
    public Date getCreateDate();
    
    /** 请求交易中备注 */
    public String getRemark();
}
