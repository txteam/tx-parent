/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-8-27
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;

/**
 * 生成UUID唯一键工具类<br/>
 * <功能详细描述>
 * 
 * @author brady
 * @version [版本号, 2013-8-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UUIDUtils {
    
    private static final IdentifierGenerator generator = new UUIDHexGenerator();
    
    //private static final IdentifierGenerator generator16 = new UUID16HexGenerator();
    
    //0-9共含有10个字符
    private static final Map<Integer, Character> integer2characterMap = new HashMap<Integer, Character>();
    
    static {
        //48为'0',57为'9'  0-9 10个
        for (int i = 0; i < 10; i++) {
            integer2characterMap.put(i, (char) ((int) '0' + i));
        }
        //97为'A-Z' 122为'z' 共26个字母  共36个
        for (int i = 0; i < 26; i++) {
            integer2characterMap.put(i + 10, (char) ((int) 'A' + i));
        }
        //
        for (int i = 0; i < 32; i++) {
            integer2characterMap.put(i + 36, (char) ((int) 'À' + i));
        }
    }
    
    /**
     * 利用hibernaeUUID生成器，生成唯一键 <功能详细描述>
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String generateUUID() {
        return generator.generate(null, null).toString();
    }
    
    /*    public static String generateUUID16() {
        return generator16.generate(null, null).toString();
    }*/
    
    public static void main(String[] args) {
        for (int i = 0; i <= 255; i++) {
            System.out.println((char) i + " : " + (i));
        }
        
        System.out.println("-----------------");
        
        for (Entry<Integer, Character> entry : integer2characterMap
                .entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    
    /**
     * @return
     */
    @Override
    public String toString() {
        return "UUIDUtils []";
    }
    
    //    public static class UUID16HexGenerator extends AbstractUUIDGenerator {
    //        
    //        private static int count = 0;
    //        
    //        private static int maxCount = Integer.parseInt("zzz", 36);
    //        
    //        private synchronized int count() {
    //            if (count > maxCount) {
    //                count = 0;
    //            }
    //            return count++;
    //        }
    //        
    //        public UUID16HexGenerator() {
    //            super();
    //        }
    //        
    //        /**
    //         * @param session
    //         * @param object
    //         * @return
    //         * @throws HibernateException
    //         */
    //        @Override
    //        public Serializable generate(SharedSessionContractImplementor session, Object object)
    //                throws HibernateException {
    //            // IP地址,36进制,7位
    //            // 时间戳(单位毫秒),36进制,6位
    //            // 同时间调用的自增长数字 36进制(zzz,46655) - 3位
    //            
    //            long currentTimeMillis = System.currentTimeMillis();
    //            String str1 = Long.toString(getHostAddressBy36(), 36); // IP地址,32进制,7位
    //            String str2 = Long.toString(currentTimeMillis / 1000, 36); // 时间戳(单位毫秒),32进制,6位
    //            String str3 = StringUtils.leftPad(Long.toString(count(), 36), 3, '0'); // 同时间调用的自增长数字 32进制(zzz,46655) - 3位
    //            
    //            StringBuilder sb = new StringBuilder();
    //            sb.append(str1).append(str2).append(str3);
    //            return StringUtils.right(sb.toString(), 16);
    //        }
    //        
    //        private long getHostAddressBy36() {
    //            String addr = "127.0.0.1";
    //            try {
    //                addr = ComputerEnvironment.getLocalHostAddress();
    //            } catch (UnknownHostException e) {
    //                throw new SILException("本机网络地址获取错误 : " + e.getMessage(), e);
    //            }
    //            StringBuilder sb = new StringBuilder();
    //            String[] split = addr.split("\\.");
    //            for (String string : split) {
    //                sb.append(Integer.toString(Integer.parseInt(string), 16));
    //            }
    //            
    //            return Long.parseLong(sb.toString(), 16);
    //        }
    //    }
}
