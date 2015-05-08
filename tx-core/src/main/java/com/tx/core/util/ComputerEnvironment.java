/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-5-21
 * <修改描述:>
 */
package com.tx.core.util;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计算机运行环境辅助类<br/>
 * 
 * @author brady
 * @version [版本号, 2013-5-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class ComputerEnvironment {
    
    //日志记录器<br/>
    private static Logger logger = LoggerFactory.getLogger(ComputerEnvironment.class);
    
    private static Set<String> hostAddressSet = new HashSet<String>();
    
    private static Set<String> macAddressSet = new HashSet<String>();
    
    private static List<IpConfigInfo> ipConfigInfoList = new ArrayList<ComputerEnvironment.IpConfigInfo>();
    
    static {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface networkInterface = nis.nextElement();
                
                // 网卡显示名称
                String displayName = networkInterface.getDisplayName();
                
                //获取当前机器的mac地址
                String macAddress = converMac(networkInterface.getHardwareAddress());
                if (StringUtils.isBlank(macAddress)) {
                    continue;
                }
                
                //获取当前机器的ip相关信息
                List<InterfaceAddress> iaList = networkInterface.getInterfaceAddresses();
                List<String> hostNameList = new ArrayList<String>();
                List<String> hostAddressList = new ArrayList<String>();
                for (InterfaceAddress iat : iaList) {
                    InetAddress address = iat.getAddress();
                    hostNameList.add(address.getHostName().trim());
                    hostAddressList.add(address.getHostAddress().trim());
                }
                
                ipConfigInfoList.add(new IpConfigInfo(displayName, macAddress,
                        hostNameList, hostAddressList));
            }
        } catch (SocketException e) {
            logger.error(e.toString(), e);
        }
        
        for (IpConfigInfo ipConfigInfoTemp : ipConfigInfoList) {
            macAddressSet.add(ipConfigInfoTemp.getMacAddress());
            hostAddressSet.addAll(ipConfigInfoTemp.getHostAddressList());
        }
    }
    
    /** <默认构造函数> */
    private ComputerEnvironment() {
    }
    
    /**
     * 
     * 把mac byte字符转换成mac字符串
     * 
     * @param macByte
     * @return
     * 
     * @return String [返回类型说明]
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static String converMac(byte[] macByte) {
        if (ArrayUtils.isNotEmpty(macByte)) {
            StringBuilder sb = new StringBuilder(32); // 完整mac地址一般位23位
            int m = 0;
            for (int i = 0; i < macByte.length; i++) {
                //byte表示范围-128~127，//因此>127的数被表示成负数形式，这里+256转换成正数  
                m = macByte[i] < 0 ? (macByte[i] + 256) : macByte[i];
                sb.append(Integer.toHexString(m).toUpperCase());
            }
            return sb.toString().trim();
        }
        return null;
    }
    
    /**
     * ipConfig相关信息 <功能详细描述>
     * 
     * @author brady
     * @version [版本号, 2013-5-21]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public static class IpConfigInfo {
        // 网卡名称
        private String displayName;
        
        // 网卡物理地址
        private String macAddress;
        
        // 网卡ip地址名称
        private List<String> hostNameList;
        
        // 网卡ip地址
        private List<String> hostAddressList;
        
        /** <默认构造函数> */
        private IpConfigInfo(String displayName, String macAddress,
                List<String> hostNameList, List<String> hostAddressList) {
            super();
            this.displayName = displayName;
            this.macAddress = macAddress;
            this.hostNameList = hostNameList;
            this.hostAddressList = hostAddressList;
        }
        
        /**
         * @return 返回 displayName
         */
        public String getDisplayName() {
            return displayName;
        }
        
        /**
         * @param 对displayName进行赋值
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
        
        /**
         * @return 返回 macAddress
         */
        public String getMacAddress() {
            return macAddress;
        }
        
        /**
         * @param 对macAddress进行赋值
         */
        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }
        
        /**
         * @return 返回 hostNameList
         */
        public List<String> getHostNameList() {
            return hostNameList;
        }
        
        /**
         * @param 对hostNameList进行赋值
         */
        public void setHostNameList(List<String> hostNameList) {
            this.hostNameList = hostNameList;
        }
        
        /**
         * @return 返回 hostAddressList
         */
        public List<String> getHostAddressList() {
            return hostAddressList;
        }
        
        /**
         * @param 对hostAddressList进行赋值
         */
        public void setHostAddressList(List<String> hostAddressList) {
            this.hostAddressList = hostAddressList;
        }
        
    }
    
    /** 获取本机所有的IP地址 */
    public static Set<String> getHostAddressSet() {
        return hostAddressSet;
    }
    
    /** 获取本机所有的物理地址 */
    public static Set<String> getMacAddressSet() {
        return macAddressSet;
    }
    
    /** 获取本机所有的地址信息 */
    public static List<IpConfigInfo> getIpConfigInfoList() {
        return ipConfigInfoList;
    }
    
    public static void main(String[] args) throws Exception {
        Set<String> hostAddressSet2 = ComputerEnvironment.getHostAddressSet();
        Set<String> macAddressSet2 = ComputerEnvironment.getMacAddressSet();
        
        for (String string : hostAddressSet2) {
            System.out.println(string);
        }
        
        for (String string : macAddressSet2) {
            System.out.println(string);
        }
        
        /*
         * 
         * Enumeration<NetworkInterface> nis =
         * NetworkInterface.getNetworkInterfaces();
         * 
         * while (nis.hasMoreElements()) { NetworkInterface networkInterface =
         * nis.nextElement(); System.out.println("---------------disname:" +
         * networkInterface.getDisplayName());
         * System.out.println("---------------name:" +
         * networkInterface.getName());
         * System.out.println("---------------Virtual:" +
         * networkInterface.isVirtual());
         * 
         * 
         * System.out.println("mac:"); if (networkInterface.getHardwareAddress()
         * != null) { //String macAddress = new
         * String(networkInterface.getHardwareAddress());
         * System.out.println(networkInterface.getHardwareAddress()); byte[] mac
         * = networkInterface.getHardwareAddress(); int m = 0;
         * System.out.println(mac.length); for (int i = 0; i < mac.length; i++)
         * { //byte表示范围-128~127，因此>127的数被表示成负数形式，这里+256转换成正数 m = mac[i] < 0 ?
         * (mac[i] + 256) : mac[i];
         * System.out.print(Integer.toHexString(m).toUpperCase() + "\t");
         * 
         * } System.out.println(""); }
         * 
         * Enumeration<InetAddress> niTemp =
         * networkInterface.getInetAddresses(); while (niTemp.hasMoreElements())
         * { InetAddress ia = niTemp.nextElement();
         * 
         * System.out.println(ia.getHostName());
         * System.out.println(ia.getHostAddress()); }
         * 
         * List<InterfaceAddress> iaList =
         * networkInterface.getInterfaceAddresses(); for (InterfaceAddress iat :
         * iaList) { System.out.println(iat.getAddress().getHostName());
         * System.out.println(iat.getAddress().getHostAddress()); }
         * 
         * }
         */
    }
}
