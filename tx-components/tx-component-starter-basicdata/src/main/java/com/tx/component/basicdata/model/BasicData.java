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
     * 获取唯一键<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getId();
    
    /**
     * 获取名称<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getName();
    
    /**
     * 获取备注<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public String getRemark();
    
    /**
     * 获取最后更新时间<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public Date getLastUpdateDate();
    
    /**
     * 获取创建时间<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return Date [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
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
    
    /**
     * 获取视图类型<br/>
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return BasicDataViewTypeEnum [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public BasicDataViewTypeEnum getViewType();
    
    /**
     * 设置视图类型<br/>
     * <功能详细描述>
     * @param viewType [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public void setViewType(BasicDataViewTypeEnum viewType);
}
