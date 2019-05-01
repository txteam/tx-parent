/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年4月27日
 * <修改描述:>
 */
package com.tx.component.basicdata.starter.persister;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tx.core.starter.properties.PersisterJPAProperties;
import com.tx.core.starter.properties.PersisterMybatisProperties;

/**
 * 基础数据容器默认配置<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年4月27日]
 * @see  [相关类/方法]0
 * @since  [产品/模块版本]
 */
@ConfigurationProperties(prefix = "tx.basicdata.persist")
public class BasicDataPersisterProperties {
    
    /** 持久化类型 */
    private String type = "mybatis";
    
    /** mybatis配置文件:service需要该逻辑 */
    private PersisterMybatisProperties mybatis;
    
    /** jpa必要的配置参数 */
    private PersisterJPAProperties jpa;
    
    /**
     * @return 返回 type
     */
    public String getType() {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 返回 mybatis
     */
    public PersisterMybatisProperties getMybatis() {
        return mybatis;
    }

    /**
     * @param 对mybatis进行赋值
     */
    public void setMybatis(PersisterMybatisProperties mybatis) {
        this.mybatis = mybatis;
    }

    /**
     * @return 返回 jpa
     */
    public PersisterJPAProperties getJpa() {
        return jpa;
    }

    /**
     * @param 对jpa进行赋值
     */
    public void setJpa(PersisterJPAProperties jpa) {
        this.jpa = jpa;
    }
}
