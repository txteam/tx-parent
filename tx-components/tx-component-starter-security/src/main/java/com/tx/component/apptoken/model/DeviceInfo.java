/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年12月27日
 * <修改描述:>
 */
package com.tx.component.apptoken.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 设备绑定信息<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年12月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Entity
@Table(name = "sec_device_info", indexes = {
        @Index(name = "idx_device_info_00", unique = true, columnList = "deviceId"),
        @Index(name = "idx_device_info_01", unique = true, columnList = "signKey"),
        @Index(name = "idx_device_info_02", columnList = "clientIpAddress"),
        @Index(name = "idx_device_info_03", columnList = "createDate") })
public class DeviceInfo implements Serializable {
    
    /** 注释内容 */
    private static final long serialVersionUID = -3702182890422456820L;
    
    /** 设备绑定信息唯一键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    //: 设备的唯一标识
    @Column(nullable = false, length = 64)
    private String deviceId;
    
    //:客户端ip地址
    @Column(nullable = false, length = 64)
    private String clientIpAddress;
    
    //:客户端真实ip地址
    @Column(nullable = true, length = 64)
    private String realIpAddress;
    
    //中转ip地址(写入以前应该对长度进行特殊处理)
    @Column(nullable = true, length = 512)
    private String forwardedIpAddress;
    
    //远端调用ip地址
    @Column(nullable = true, length = 64)
    private String remoteIpAddress;
    
    //： 设备签名
    @Column(nullable = false, length = 64)
    private String signKey;
    
    //设备的国际移动设备身份码
    @Column(nullable = true, length = 64)
    private String imei;
    
    //: 设备的国际移动用户识别码
    @Column(nullable = true, length = 64)
    private String imsi;
    
    //: 设备的型号
    @Column(nullable = true, length = 64)
    private String model;
    
    //: 设备的生产厂商
    @Column(nullable = true, length = 64)
    private String vendor;
    
    //操作系统类型
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 64)
    private AgentTypeEnum osAgentType;
    
    //操作系统版本
    @Column(nullable = false, length = 64)
    private String osAgentVersion;
    
    //: 该设备第几次写入签名
    @Column(nullable = false)
    private int bindingCount;
    
    //最后更新时间
    @Column(nullable = false)
    private Date lastUpdateDate;
    
    //创建时间
    @Column(nullable = false)
    private Date createDate;
    
    /**
     * @return 返回 id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param 对id进行赋值
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return 返回 deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }
    
    /**
     * @param 对deviceId进行赋值
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    /**
     * @return 返回 signKey
     */
    public String getSignKey() {
        return signKey;
    }
    
    /**
     * @param 对signKey进行赋值
     */
    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
    
    /**
     * @return 返回 imei
     */
    public String getImei() {
        return imei;
    }
    
    /**
     * @param 对imei进行赋值
     */
    public void setImei(String imei) {
        this.imei = imei;
    }
    
    /**
     * @return 返回 imsi
     */
    public String getImsi() {
        return imsi;
    }
    
    /**
     * @param 对imsi进行赋值
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
    
    /**
     * @return 返回 model
     */
    public String getModel() {
        return model;
    }
    
    /**
     * @param 对model进行赋值
     */
    public void setModel(String model) {
        this.model = model;
    }
    
    /**
     * @return 返回 vendor
     */
    public String getVendor() {
        return vendor;
    }
    
    /**
     * @param 对vendor进行赋值
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
    /**
     * @return 返回 bindingCount
     */
    public int getBindingCount() {
        return bindingCount;
    }
    
    /**
     * @param 对bindingCount进行赋值
     */
    public void setBindingCount(int bindingCount) {
        this.bindingCount = bindingCount;
    }
    
    /**
     * @return 返回 lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    /**
     * @param 对lastUpdateDate进行赋值
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    /**
     * @return 返回 createDate
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @param 对createDate进行赋值
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @return 返回 clientIpAddress
     */
    public String getClientIpAddress() {
        return clientIpAddress;
    }
    
    /**
     * @param 对clientIpAddress进行赋值
     */
    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }
    
    /**
     * @return 返回 realIpAddress
     */
    public String getRealIpAddress() {
        return realIpAddress;
    }
    
    /**
     * @param 对realIpAddress进行赋值
     */
    public void setRealIpAddress(String realIpAddress) {
        this.realIpAddress = realIpAddress;
    }
    
    /**
     * @return 返回 forwardedIpAddress
     */
    public String getForwardedIpAddress() {
        return forwardedIpAddress;
    }
    
    /**
     * @param 对forwardedIpAddress进行赋值
     */
    public void setForwardedIpAddress(String forwardedIpAddress) {
        this.forwardedIpAddress = forwardedIpAddress;
    }
    
    /**
     * @return 返回 remoteIpAddress
     */
    public String getRemoteIpAddress() {
        return remoteIpAddress;
    }
    
    /**
     * @param 对remoteIpAddress进行赋值
     */
    public void setRemoteIpAddress(String remoteIpAddress) {
        this.remoteIpAddress = remoteIpAddress;
    }
    
    /**
     * @return 返回 osAgentType
     */
    public AgentTypeEnum getOsAgentType() {
        return osAgentType;
    }
    
    /**
     * @param 对osAgentType进行赋值
     */
    public void setOsAgentType(AgentTypeEnum osAgentType) {
        this.osAgentType = osAgentType;
    }
    
    /**
     * @return 返回 osAgentVersion
     */
    public String getOsAgentVersion() {
        return osAgentVersion;
    }
    
    /**
     * @param 对osAgentVersion进行赋值
     */
    public void setOsAgentVersion(String osAgentVersion) {
        this.osAgentVersion = osAgentVersion;
    }
}
