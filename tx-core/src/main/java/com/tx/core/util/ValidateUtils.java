/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年12月17日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类<br/>
 * <功能详细描述>
 * 
 * @author  tim.peng
 * @version  [版本号, 2015年12月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidateUtils {
    
    /** 移动电话号码正则表达式 */
    private static Pattern mobilePhoneNumberPattern = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0,5-9])|(14[0-9])|(18[0-9]))\\d{8}$");
    
    /** 移动电话号码正则表达式 */
    private static Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    
    /**
      * 校验身份证号码<br/>
      * <功能详细描述>
      * @param idCardNumber
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean checkIdCardNumber(String idCardNumber) {
        //校验是否为空
        if (org.apache.commons.lang3.StringUtils.isBlank(idCardNumber)) {
            return false;
        }
        //校验长度是否为15或18位
        if (!(idCardNumber.length() == 15) && !(idCardNumber.length() == 18)) {
            return false;
        }
        String pre = org.apache.commons.lang3.StringUtils.substring(idCardNumber,
                0,
                idCardNumber.length() - 1);
        String lastChar = org.apache.commons.lang3.StringUtils.substring(idCardNumber,
                idCardNumber.length() - 1,
                idCardNumber.length());
        if (org.apache.commons.lang3.StringUtils.isNumeric(pre)
                && (org.apache.commons.lang3.StringUtils.isNumeric(lastChar))
                || org.apache.commons.lang3.StringUtils.equalsIgnoreCase("x",
                        lastChar)) {
            return true;
        }
        return false;
    }
    
    /**
      * 手机号码验证
      * <功能详细描述>
      * @param mobilePhoneNumber
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean checkMobilePhoneNumber(String mobilePhoneNumber) {
        if (org.apache.commons.lang3.StringUtils.isBlank(mobilePhoneNumber)
                || mobilePhoneNumber.length() != 11) {
            return false;
        }
        if (!org.apache.commons.lang3.StringUtils.isNumeric(mobilePhoneNumber)) {
            return false;
        }
        Matcher m = mobilePhoneNumberPattern.matcher(mobilePhoneNumber);
        if (m.matches()) {
            return true;
        }
        return false;
    }
    
    /**
      * 校验Email格式是否合法
      * <功能详细描述>
      * @param email
      * @return [参数说明]
      * 
      * @return boolean [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static boolean checkEmail(String email) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(email)
                || !org.apache.commons.lang3.StringUtils.contains(email, "@")) {
            return false;
        }
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }
}
