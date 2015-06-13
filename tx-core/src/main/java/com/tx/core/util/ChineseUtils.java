/*
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2015年6月12日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 中文工具
 * 
 * @author  rain
 * @version  [版本号, 2015年6月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ChineseUtils {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] strArr = new String[] { "www.baidu.com",
                "!@#$%^&amp;*()_+{}[]|\"'?/:;&lt;<,.", "！￥……（）——：；“”‘’《》，。？、",
                "不要啊", "やめて", "한가인" };
        for (String str : strArr) {
            System.out.println("===========< 测试字符串：" + str);
            System.out.println("正则判断：" + isChineseByREG(str) + " -- "
                    + isChineseByName(str));
            System.out.println("Unicode判断结果 ：" + isChinese(str));
            System.out.println("详细判断列表：");
            char[] ch = str.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                System.out.println(c + " --< " + (isChinese(c) ? "是" : "否"));
            }
        }
    }
    
    /**
     * 
     * 统计字符串中所含有的汉字字数
     * 
     * @param str 需要统计的字符串
     * 
     * @return int 汉字个数
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static int countChineseWords(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        int count = 0;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                count++;
            }
        }
        
        return count;
    }
    
    /**
     * 
     *根据Unicode编码完美的判断中文汉字和符号
     * @param c 字符
     * 
     * @return boolean true：汉字|false：非汉字
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static boolean isChinese(char c) {
        // CJK的意思是“Chinese，Japanese，Korea”的简写 ，实际上就是指中日韩三国的象形文字的Unicode编码
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS //  4E00-9FBF：CJK 统一表意符号 
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS // F900-FAFF：CJK 兼容象形文字 
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A // 3400-4DBF：CJK 统一表意符号扩展 A 
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION // 3000-303F：CJK 符号和标点 
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS // FF00-FFEF：半角及全角形式
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION //2000-206F：常用标点 
        ) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 完整的判断中文汉字和符号
     * 
     * @param strName
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * 只能判断部分CJK字符（CJK统一汉字）
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
    
    /**
     * 
     * 只能判断部分CJK字符（CJK统一汉字）
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含 
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }
}
