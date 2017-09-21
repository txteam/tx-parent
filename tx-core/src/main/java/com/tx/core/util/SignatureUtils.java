/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2017年3月15日
 * <修改描述:>
 */
package com.tx.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.TxConstants;
import com.tx.core.util.ComputerEnvironment.IpConfigInfo;

/**
 * 签名工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2017年3月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SignatureUtils {
    
    public static void main(String[] args) {
        //172.17.200.43|00-90-F5-F4-1A-EC|85A625276D8D50587030DACCEFCFA37E
        //172.17.200.43|00-90-F5-F4-1A-EC|85A625276D8D50587030DACCEFCFA37E
        //172.17.200.43|00-90-F5-F4-1A-EC|85A625276D8D50587030DACCEFCFA37E
        System.out.println(generateSignature());
    }
    
    /**
      * 生成签名信息<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String generateSignature() {
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        String ipInfo = null;
        try {
            ipInfo = getIpAddressInfo();
            if (ipInfo.startsWith("127.0.0.1") || ipInfo.startsWith("localhost")) {
                ipInfo = getIpInfoHashCodeMD5();
            }
        } catch (UnknownHostException | SocketException e1) {
            ipInfo = getIpInfoHashCodeMD5();
        }
        sb.append(ipInfo).append("|");
        //拼接classPath对应的hashCode值
        sb.append(getClassPathHashCodeMD5());
        
        String signature = sb.toString();
        //String.valueOf(Math.abs(sb.toString().hashCode()));
        return signature;
    }
    
    /**
      * 获取类路径HashCode值<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static String getClassPathHashCodeMD5() {
        String classpath = SignatureUtils.class.getResource("/").getPath();
        String classpathHashCode = String.valueOf(Math.abs(classpath.hashCode()));
        return MD5Utils.encode(classpathHashCode);
    }
    
    /**
     * 获取本机的Mac地址<br/>
     * <功能详细描述>
     * @param ia
     * @return
     * @throws SocketException [参数说明]
     * 
     * @return String [返回类型说明]
    * @throws UnknownHostException 
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    private static String getIpAddressInfo() throws SocketException, UnknownHostException {
        StringBuilder ipAddressInfoSB = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        InetAddress ia = InetAddress.getLocalHost();
        String address = ia.getHostAddress();
        ipAddressInfoSB.append(address).append("|");
        //获取网卡，获取地址
        byte[] macBytes = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < macBytes.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = macBytes[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        String mac = sb.toString().toUpperCase();
        ipAddressInfoSB.append(mac);
        return ipAddressInfoSB.toString();
    }
    
    /**
      * 获取签名信息<br/>
      * <功能详细描述>
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static String getIpInfoHashCodeMD5() {
        // 根据当前机器的mac地址以及应用所在目录生成签名
        List<IpConfigInfo> ipConfigInfos = ComputerEnvironment.getIpConfigInfoList();
        Set<String> macSet = new TreeSet<String>();
        Set<String> ipAddressSet = new TreeSet<String>();
        
        for (IpConfigInfo ipConfigInfo : ipConfigInfos) {
            String macAddressTemp = ipConfigInfo.getMacAddress();
            if (StringUtils.isNotBlank(macAddressTemp)) {
                macSet.add(macAddressTemp);
            }
            for (String hostAddress : ipConfigInfo.getHostAddressList()) {
                if (StringUtils.isNotBlank(hostAddress)) {
                    ipAddressSet.add(hostAddress.trim());
                }
            }
        }
        
        // 拼接字符串
        StringBuilder sb = new StringBuilder(TxConstants.INITIAL_STR_LENGTH);
        for (String macTemp : macSet) {
            sb.append(macTemp).append(";");
        }
        ipAddressSet.add("|");
        for (String addressTemp : ipAddressSet) {
            sb.append(addressTemp).append(";");
        }
        String ipInfo = String.valueOf(Math.abs(sb.toString().hashCode()));
        return MD5Utils.encode(ipInfo);
    }
}
