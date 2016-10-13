/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年10月3日
 * <修改描述:>
 */
package com.tx.component.basicdata.model;

import java.io.Serializable;
import java.util.Date;

import com.tx.core.support.initable.model.ConfigInitAble;

/**
 * 基础数据接口实现<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年10月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface BasicData extends ConfigInitAble, Serializable {
    
    /**
     * @return 返回 id
     */
    public String getId();
    
    /**
     * @return 返回 name
     */
    public String getName();
    
    /**
     * @return 返回 remark
     */
    public String getRemark();
    
    /**
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate();
    
    /**
     * @return 返回 createDate
     */
    public Date getCreateDate();
    
    /**
     * 设置基础数据唯一键<br/>
     * <功能详细描述>
     * @param name [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setId(String id);
    
    /**
     * 设置基础数据名称<br/>
     * <功能详细描述>
     * @param name [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setName(String name);
    
    /**
     * 设置备注信息<br/>
     * <功能详细描述>
     * @param remark [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setRemark(String remark);
}
