/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月31日
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.tx.core.exceptions.SILException;

/**
 * 加密工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EncryptUtils {
    
    /**
      * 加密类型<br/>
      * <功能详细描述>
      * 
      * @author  Administrator
      * @version  [版本号, 2016年8月31日]
      * @see  [相关类/方法]
      * @since  [产品/模块版本]
     */
    public static enum EncryptTypeEnum {
        
        /* 不可逆加密 */
        MD5("MD5", "MD5"),
        
        SHA_1("MD5", "SHA-1"),
        
        SHA_256("SHA_256", "SHA-256"),
        
        /* 可逆加密：对称加密 */
        DES("SHA_256", "SHA-256"),
        
        IDEA("SHA_256", "SHA-256"),
        
        RC2("SHA_256", "SHA-256"),
        
        RC4("SHA_256", "SHA-256"),
        
        SKIPJACK("SHA_256", "SHA-256"),
        
        RC5("SHA_256", "SHA-256"),
        
        AES("SHA_256", "SHA-256");
        
        private String key;
        
        private String algorithm;
        
        /** <默认构造函数> */
        private EncryptTypeEnum(String key, String algorithm) {
            this.key = key;
            this.algorithm = algorithm;
        }
        
        /**
         * @return 返回 key
         */
        public String getKey() {
            return key;
        }
        
        /**
         * @param 对key进行赋值
         */
        public void setKey(String key) {
            this.key = key;
        }
        
        /**
         * @return 返回 algorithm
         */
        public String getAlgorithm() {
            return algorithm;
        }
        
        /**
         * @param 对algorithm进行赋值
         */
        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
        
    }
    
    /**
     * 对字符串加密<br/>
     *     加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     * 
     * @param strSrc [要加密的字符串]
     * @param encryptType [加密类型]
     *  
     * @return
     */
    public static String encrypt(String text, String character,
            EncryptTypeEnum encryptType) {
        MessageDigest md = null;
        String strDes = null;
        try {
            if (character == null) {
                character = "UTF-8";
            }
            byte[] bt = text.getBytes(character);
            if (encryptType == null) {
                encryptType = EncryptTypeEnum.MD5;
            }
            String algorithm = encryptType.getAlgorithm();
            md = MessageDigest.getInstance(algorithm);
            md.update(bt);
            
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e1) {
            throw new SILException("NoSuchAlgorithmException.", e1);
        } catch (UnsupportedEncodingException e2) {
            throw new SILException("NoSuchAlgorithmException.", e2);
        }
        return strDes;
    }
    
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
