/*
 * 描          述:  <描述>
 * 修  改   人:  Rain.he
 * 修改时间:  2015年5月4日
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.argument.IllegalArgException;

/**
 * 随机工具
 * 
 * @author Rain.he
 * @version [版本号, 2015年5月4日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RandomUtils extends org.apache.commons.lang.math.RandomUtils {
    
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtils.randomRangeInt(4));
        }
    }
    
    /** 用于产生随机字符串,去掉不容易识别的字符 */
    private static final String RANDOM_STRING = "3456789abcdefghjkmpqrstwxyABCDEFGHJKMPQRSTWXY";
    
    /** 用于产生随机数字字符串 */
    private static final String RANDOM_INTEGER = "0123456789";
    
    /**
     * 产生[0,maxNumber)之间的随机整数<br />
     * 
     * @param maxNumber 任意大于0的数字,否则返回0到Integer的最大值之间的任意数字, 如果传入0,则返回0
     * @return 返回[0,maxNumber)之间的随机数字
     * 
     * @return [0,maxNumber)之间的随机整数
     */
    public static int randomInt(int maxNumber) {
        if (maxNumber == 0) {
            return 0;
        }
        if (0 < maxNumber) {
            return JVM_RANDOM.nextInt(maxNumber);
        }
        return JVM_RANDOM.nextInt();
    }
    
    /**
     * 产生[minNumber,maxNumber)之间的随机正整数<br />
     * 
     * @param minNumber 任意大于等于0的数字
     * @param maxNumber 任意大于等于0的数字
     * @return int [minNumber,maxNumber)之间的随机整数
     */
    public static int randomInt(int minNumber, int maxNumber) {
        if (minNumber < 0 || maxNumber < 0) {
            throw new IllegalArgumentException("minNumber 和 maxNumber 必须是非负数");
        }
        
        if (minNumber == maxNumber) { // 如果最大值等于最小值,则返回0
            return 0;
        }
        if (minNumber > maxNumber) { // 如果minNumber大于maxNumber,则交换两个数字
            minNumber ^= maxNumber;
            maxNumber ^= minNumber;
            minNumber ^= maxNumber;
        }
        
        return JVM_RANDOM.nextInt(maxNumber - minNumber) + minNumber;
    }
    
    /**
     * 
     * 通过提供默认字符串库,从重随机选择length个来组成随机字符串返回
     * 
     * @param str 字符串库
     * @param length 生成范围长度
     * 
     * @return String 生成的字符串
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String randomRangeString(String str, int length) {
        if (StringUtils.isBlank(str) || length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int size = str.length();
        for (int index = 0; index < length; index++) {
            sb.append(str.charAt(randomInt(size)));
        }
        return sb.toString();
    }
    
    /**
     * 
     * 通过默认字符串库,随机选择length个来组成随机字符串返回
     * 
     * @param length 生成范围长度
     * 
     * @return String 生成的字符串
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String randomRangeString(int length) {
        return randomRangeString(RANDOM_STRING, length);
    }
    
    /**
     * 
     * 通过默认数字字符串库,随机选择length个来组成随机字符串返回
     * 
     * @param length 生成范围长度
     * 
     * @return String 数字字符串
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String randomRangeInt(int length) {
        return randomRangeString(RANDOM_INTEGER, length);
    }
    
    /**
     * 
     * 通过默认数字字符串库,随机选择length个来组成随机字符串返回<br/>
     * 左起第一位不会是0
     * 
     * @param length 生成范围长度
     * 
     * @return String 数字字符串
     * @exception [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String randomRangeIntNotLeftZero(int length) {
        int randomInt = randomInt(1, 9);
        return randomInt + randomRangeString(RANDOM_INTEGER, length - 1);
    }
    
    /**
     * 计算命中率<br />
     * 以100为基数根据rate参数计算命中率<br />
     * 
     * @param rate 命中概率,如果小于等于0,则直接不命中
     * @return 如果命中,则返回true,否则返回false
     */
    public static boolean hitRate100(int rate) {
        return hitRate(rate, 100);
    }
    
    /**
     * 计算命中率<br />
     * 以maxRate为基数根据rate参数计算命中率<br />
     * 
     * @param rate 命中概率,如果小于等于0,则直接不命中
     * @param maxRate 命中基数,如果小于等于0,则直接不命中
     * @return 如果命中,则返回true,否则返回false
     */
    public static boolean hitRate(int rate, int maxRate) {
        if (rate <= 0 || maxRate <= 0) {
            return false;
        }
        return rate > randomInt(0, maxRate) ? true : false;
    }
    
    /**
     * 
     * 随机生成的汉字 <br />
     * 尽可能的去掉生僻字和繁体字(但是不能避免)<br/>
     * 原理是从汉字区位码找到汉字。 在汉字区位码中分高位与底位，且其中有简体又有繁体。 位数越前生成的汉字繁体的机率越大。<br />
     * 所以在本方法中高位从176取，底位从161取，去掉大部分的繁体和生僻字。 但仍然会有.<br />
     * 
     * @return String 随机汉字
     * @exception throws UnsupportedEncodingException 不支持GBK编码
     */
    public static String randomChineseCharacter() {
        // 此返回.会返回全部汉子,包括繁体字和非常用字
        //        return String.valueOf((char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1))));
        String str = null;
        
        // 定义高低位
        int hightPos = 176 + randomInt(39);
        int lowPos = 161 + randomInt(93);
        
        byte[] b = new byte[2];
        
        b[0] = Integer.valueOf(hightPos).byteValue();
        b[1] = Integer.valueOf(lowPos).byteValue();
        
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            str = new String(b);
            System.err.println("不支持gbk编码");
        }
        
        return str;
    }
    
    /**
     * 根据需要生成汉子的数量生成汉字<br/>
     * 尽可能的去掉生僻字和繁体字(但是不能避免)<br/>
     * 原理是从汉字区位码找到汉字。 在汉字区位码中分高位与底位，且其中有简体又有繁体。 位数越前生成的汉字繁体的机率越大。<br />
     * 所以在本方法中高位从176取，底位从161取，去掉大部分的繁体和生僻字。 但仍然会有.<br />
     * 
     * @param number 生成汉字数量(大于0)
     * 
     * @return String 生成的汉字
     * @exception [异常类型] [异常说明]
     * @see RandomUtils#randomChineseCharacter()
     */
    public static String randomChineseCharacter(int number) {
        if (number <= 0) {
            throw new IllegalArgException("只能传入大于0的整数.");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(randomChineseCharacter());
        }
        return sb.toString();
    }
}
