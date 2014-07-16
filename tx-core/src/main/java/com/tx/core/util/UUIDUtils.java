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
 * @author  brady
 * @version  [版本号, 2013-8-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UUIDUtils {
    
    private static final IdentifierGenerator generator = new UUIDHexGenerator();
    
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
      * 根据一个int值获取一个映射字符
      *<功能详细描述>
      * @param value
      * @return [参数说明]
      * 
      * @return char [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private char getCharByInt(int value) {
        if (value < 0) {
            value = value * -1;
        }
        char result = '0';
        if (value < 68) {
            result = integer2characterMap.get(value);
        } else {
            value = value % 68;
            result = integer2characterMap.get(value);
        }
        return result;
    }
    
    /**
     * 利用hibernaeUUID生成器，生成唯一键
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String generateUUID() {
        return generator.generate(null, null).toString();
    }
    
    public static void main(String[] args) {
        for (int i = 0; i <= 255; i++) {
            System.out.println((char) i + " : " + (i));
        }
        
        System.out.println("-----------------");
        
        for (Entry<Integer, Character> entry : integer2characterMap.entrySet()) {
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
}
