/*
 * 描          述:  <描述>
 * 修  改   人:  txteam_administrator
 * 修改时间:  2016年11月3日
 * <修改描述:>
 */
package com.tx.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.tx.core.exceptions.SILException;

/**
 * 汉语拼音工具类<br/>
 * <功能详细描述>
 * 
 * @author  txteam_administrator
 * @version  [版本号, 2016年11月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class PinyinUtils {
    
    /**
     * 获取字符串转换出的拼音字符串,不会自动追加空格,解析过程将会忽略非汉语字符<br/>
     * <功能详细描述>
     * @param inputString
     * @param splitByWhitespace 每个拼音后是否添加空格
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String parseToPinyin(String inputString) {
        String pinyin = parseToPinyin(inputString, false, true);
        return pinyin;
    }
    
    /**
      * 获取字符串转换出的拼音字符串<br/>
      * <功能详细描述>
      * @param inputString
      * @param splitByWhitespace 每个拼音后是否添加空格
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String parseToPinyin(String inputString,
            boolean isSplitPinyinByWhitespace,
            boolean isIgnorNotChineseCharacters) {
        if (StringUtils.isEmpty(inputString)) {
            return inputString;
        }
        
        //汉语拼音格式输出类
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //输出设置，大小写，音标方式等
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //WITH_TONE_NUMBER(以数字代替声调) :  zhong1  zhong4
        //WITHOUT_TONE (无声调) :             zhong   zhong
        //WITH_TONE_MARK (有声调) :           zhōng  zhòng
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //WITH_U_AND_COLON : lu:3
        //WITH_V :            lv3
        //WITH_U_UNICODE :    lü3
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        
        char[] input = inputString.trim().toCharArray();
        StringBuffer output = new StringBuffer("");
        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\u4E00-\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i],
                            format);
                    if (ArrayUtils.isEmpty(temp)) {
                        continue;
                    }
                    output.append(temp[0]);
                    if (isSplitPinyinByWhitespace) {
                        //每个拼音后是否添加空格
                        output.append(" ");
                    }
                } else {
                    if (!isIgnorNotChineseCharacters) {
                        output.append(Character.toString(input[i]));
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new SILException("将输入字符串转换为拼音异常.", e);
        }
        return output.toString();
    }
    
    /**
      * 解析为拼音简写，解析过程将会忽略非汉语字符<br/>
      * <功能详细描述>
      * @param inputString
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String parseToPY(String inputString) {
        String res = parseToPY(inputString, true);
        return res;
    }
    
    /**
     * 获取字符串转换出的拼音字符串<br/>
     * <功能详细描述>
     * @param inputString
     * @param splitByWhitespace 每个拼音后是否添加空格
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static String parseToPY(String inputString,
            boolean isIgnorNotChineseCharacters) {
        if (StringUtils.isEmpty(inputString)) {
            return inputString;
        }
        if (StringUtils.isBlank(inputString)) {
            return "";
        }
        inputString = inputString.trim();
        
        //汉语拼音格式输出类
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //输出设置，大小写，音标方式等
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //WITH_TONE_NUMBER(以数字代替声调) :  zhong1  zhong4
        //WITHOUT_TONE (无声调) :             zhong   zhong
        //WITH_TONE_MARK (有声调) :           zhōng  zhòng
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //WITH_U_AND_COLON : lu:3
        //WITH_V :            lv3
        //WITH_U_UNICODE :    lü3
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        
        char[] input = inputString.trim().toCharArray();
        StringBuffer output = new StringBuffer("");
        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\u4E00-\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i],
                            format);
                    if (ArrayUtils.isEmpty(temp)) {
                        continue;
                    }
                    output.append(temp[0].charAt(0));
                } else {
                    if (!isIgnorNotChineseCharacters) {
                        output.append(Character.toString(input[i]));
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new SILException("将输入字符串转换为拼音异常.", e);
        }
        return output.toString();
    }
    
    public static void main(String[] args) {
        String chs = "我是中国人! I'm Chinese!";
        System.out.println(chs);
        System.out.println(parseToPinyin(chs, false, true));
        System.out.println(parseToPY(chs, true));
        System.out.println(parseToPY(null, true));
        System.out.println(parseToPY("", true));
        System.out.println(parseToPY(" ", true));
    }
}
