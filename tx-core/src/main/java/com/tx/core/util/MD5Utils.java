/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年6月21日
 * <修改描述:>
 */
package com.tx.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.argument.ArgIllegalException;

/**
 * MD5加密工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年6月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MD5Utils {
    
    /** MD5签名 */
    private static final String algorithm = "MD5";
    
    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
    /**
     * encrypt the source
     * 
     * @param source
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String encode(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new ArgIllegalException(MessageUtils.format("不支持的消息加密类型:{}",
                    new Object[] { algorithm }));
        }
        byte buffer[] = source.getBytes();
        md5.update(buffer);
        byte bDigest[] = md5.digest();
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = bDigest[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
