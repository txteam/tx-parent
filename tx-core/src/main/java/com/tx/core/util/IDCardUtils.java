package com.tx.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 身份证实用工具
 * @author Rain
 *
 */
public abstract class IDCardUtils {
    private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2 }; //
    
    private static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 }; //
    
    private static int[] ai = new int[18];
    
    /** 根据身份证前17位计算出第18位 */
    private static String getVerify(String eightcardid) {
        int remaining = 0;
        if (eightcardid.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eightcardid.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }
            
            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
            return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
            
        }
        throw new RuntimeException("出错了!");
    }
    
    /** 随机省 第一二位 前两位 01- 64 */
    private static String getProvince() {
        return getRandomNumber(11, 64, 2);
    }
    
    /** 随机市 第三四位 两位 01-02 */
    private static String getCity() {
        return getRandomNumber(1, 2, 2);
    }
    
    /** 随机县 第五六位 两位 01-05 */
    private static String getCounty() {
        return getRandomNumber(1, 5, 2);
    }
    
    /** 年 第 7--10位 年份：1970-1990 */
    private static String getYear() {
        return getRandomNumber(1970, 1990, 4);
    }
    
    /** 第11 12 位 月份：01-12 */
    private static String getMonth() {
        return getRandomNumber(1, 12, 2);
    }
    
    /** 第13 14 位 日期：01-28 */
    private static String getDay() {
        return getRandomNumber(1, 28, 2);
    }
    
    /** 随机数 第15 16 位 两位：01-99 */
    private static String getSequence() {
        return getRandomNumber(1, 99, 2);
    }
    
    /** 第17 位 一位性别：0-9 */
    private static String getSex() {
        return getRandomNumber(0, 9, 1);
    }
    
    /**
     * 获得随机数字
     * 
     * @param start
     *            随机数字区间开始
     * @param end
     *            随机数字区间结束
     * @param length
     *            返回数字的长度,如果不到此长度,则前导加"0"
     * @return
     */
    private static String getRandomNumber(int start, int end, int length) {
        String number = String.valueOf((int) (Math.random() * (end - start) + start));
        for (int i = length - number.length(); i > 0; i--) {
            number = "0" + number;
        }
        return number;
    }
    
    /** 合成数据 */
    private static String get17ID() {
        StringBuilder sb = new StringBuilder();
        sb.append(getProvince()).append(getCity()).append(getCounty());
        sb.append(getYear()).append(getMonth()).append(getDay());
        sb.append(getSequence()).append(getSex());
        return sb.toString();
    }
    
    /** 获得随机身份证号码 */
    public static String getRandomIDCard() {
        StringBuilder str = new StringBuilder();
        str.append(get17ID());
        str.append(getVerify(str.toString()));
        return str.toString();
    }
    
    /**
     * 获取身份证信息
     * 
     * @param idCard
     * @return
     */
    public static IDCard getIDCard(String idCard) {
        return IDCard.newIDCard(idCard);
    }
    
    public static class IDCard {
        private String idCard = null; // 传入的真实身份证
        
        private String idCard15 = null; // 根据传入的身份证计算得出的15位身份证
        
        private String idCard18 = null;// 根据传入的身份证计算得出的18位身份证
        
        private boolean validity = false; // 15位身份证没有办法校验是否正确,就直接设定为true.true:正确|false:错误
        
        private int age = -1; // 年龄
        
        private Date birthday = null; // 生日
        
        private boolean sex = true; // 性别:true:男性|false:女性
        
        private IDCard() {
        }
        
        /**
         * 创建身份证
         * 
         * @param idcard
         * @return
         */
        public static IDCard newIDCard(String idcard) {
            IDCard idCard = new IDCard();
            idCard.setIdCard(idcard);
            return idCard;
        }
        
        /**
         * 获得传入的身份证号码
         * 
         * @return
         */
        public String getIdCard() {
            return idCard;
        }
        
        /**
         * 设置身份证号码<br>
         * 如果最后一位为'x'则,自动改为'X'进行校验
         * 
         * @param idCard
         */
        public void setIdCard(String idCard) {
            idCard = idCard.trim().toUpperCase();
            if (StringUtils.isEmpty(idCard)) {
                throw new NullPointerException("不能传入空的身份证号码");
            }
            if (idCard.length() != 15 && idCard.length() != 18) {
                throw new RuntimeException("身份证号码长度必须等于15位或者18位");
            }
            
            if (idCard.length() == 15) {
                this.idCard15 = idCard;
                this.idCard = idCard;
                this.validity = true;
                idCard15ToIdCard18();
            } else {
                this.idCard18 = idCard;
                this.idCard = idCard;
                this.validity = checkIdCard18();
                idCard18ToIdCard15();
            }
            extractInfo();
        }
        
        /**
         * 获得年龄
         * 
         * @return
         */
        public int getAge() {
            return this.age;
        }
        
        /**
         * 获得生日
         * 
         * @return
         */
        public Date getBirthday() {
            return this.birthday;
        }
        
        /**
         * 是否是男性
         * 
         * @return true:男性|false:女性
         */
        public boolean isMale() {
            return this.sex;
        }
        
        /**
         * 是否是女性
         * 
         * @return true:女性|false:男性
         */
        public boolean isFemale() {
            return !this.sex;
        }
        
        /**
         * 获得18位身份证
         * 
         * @return
         */
        public String getIdCard18() {
            return this.idCard18;
        }
        
        /**
         * 获得15位身份证
         * 
         * @return
         */
        public String getIdCard15() {
            return this.idCard15;
        }
        
        /**
         * 校验身份证是否正确<br>
         * 如果身份证长度为15位,则始终返回true,因为15位没有办法校验<br>
         * 
         * @return
         */
        public boolean isValidity() {
            return this.validity;
        }
        
        /**
         * 身份证信息<br>
         * 生日,年龄,性别
         */
        private void extractInfo() {
            // 解析生日
            try {
                this.birthday = new SimpleDateFormat("yyyyMMdd").parse(this.idCard18.substring(6,
                        14));
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("从身份证解析生日错误");
            }
            // 解析年龄
            this.age = calcAge(this.birthday);
            // 解析性别
            if (Integer.valueOf(this.idCard18.substring(16, 17)).intValue() % 2 == 0) {
                this.sex = false;
            } else {
                this.sex = true;
            }
        }
        
        /**
         * 15位身份证转换成18位
         * 
         * @return
         */
        private void idCard15ToIdCard18() {
            String id17 = this.idCard.substring(0, 6) + "19"
                    + this.idCard15.substring(6, 15); // 15为身份证补\'19\'
            char[] code = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
                    '2' }; // 11个
            int[] factor = { 0, 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10,
                    9, 7 }; // 18个;
            int[] idcd = new int[18];
            for (int i = 1; i < 18; i++) {
                idcd[i] = Integer.valueOf(id17.substring(17 - i, 17 - i + 1));
            }
            int sum = 0;
            for (int i = 1; i < 18; i++) {
                sum = sum + idcd[i] * factor[i];
            }
            this.idCard18 = id17 + code[sum % 11];
        }
        
        /**
         * 18位身份证转换成15位
         * 
         * @return
         */
        private void idCard18ToIdCard15() {
            StringBuilder idcard = new StringBuilder(this.idCard18);
            idcard.delete(17, 18);
            idcard.delete(6, 8);
            this.idCard15 = idcard.toString();
        }
        
        /**
         * 18位身份证校验<br>
         * 校验1:18位长度<br>
         * 校验2:月份只能在01~12之间 校验3:前17位全数字<br>
         * 校验4:第18位的校验码<br>
         * 
         * @return true:是正确身份证|false:不是正确身份证
         */
        private boolean checkIdCard18() {
            int len = this.idCard18.length();
            int[] iconst = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
                    2 };
            int[] m = { 0, 10, 9, 8, 7, 6, 5, 4, 3, 1, 2 };
            String f = "1234567890X";
            if (this.idCard18 == null || this.idCard18.trim().isEmpty()
                    || len != 18) {
                return false;
            } else {
                String headStr = this.idCard18.substring(0, len - 1);
                String footStr = this.idCard18.substring(len - 1, len);
                int e = m[f.indexOf(footStr)];
                if (!Pattern.compile("\\d*").matcher(headStr).matches()) {
                    return false;
                }
                if (!checkBirthday(this.idCard18.substring(6, 14))) {
                    return false;
                }
                char[] nums = headStr.toCharArray();
                int rs = 0;
                for (int i = 0; i < len - 1; i++) {
                    int num = Integer.valueOf(String.valueOf(nums[i]))
                            .intValue();
                    rs += (num * iconst[i]);
                }
                if ((rs % 11) != e) {
                    return false;
                }
                return true;
            }
        }
        
        /**
         * 校验生日的有效性<br>
         * 月份 小于1月,大于12月则无效<br>
         * 日期必须大于1 小于等于当月最大日期
         * 
         * @param birthday
         *            格式:yyyyMMdd
         * @return true:有效|false:无效
         */
        private boolean checkBirthday(String birthday) {
            int yyyy = Integer.valueOf(birthday.substring(0, 4)); // 年份
            int MM = Integer.valueOf(birthday.substring(4, 6)).intValue(); // 月份
            int dd = Integer.valueOf(birthday.substring(6, 8)).intValue(); // 日期
            
            if (MM < 1 || 12 < MM || dd < 1) {
                return false;
            }
            
            if (MM == 1 || MM == 3 || MM == 5 || MM == 7 || MM == 8 || MM == 10
                    || MM == 12) { // 大月
                if (31 < dd) {
                    return false;
                }
            } else if (MM == 4 || MM == 6 || MM == 9 || MM == 11) { // 小月
                if (30 < dd) {
                    return false;
                }
            } else { // 2月
                StringBuilder date = new StringBuilder();
                date.append(yyyy)
                        .append('-')
                        .append("03")
                        .append('-')
                        .append("00");
                try {
                    Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
                    int DD = Integer.valueOf(new SimpleDateFormat("dd").format(parse));
                    if (DD < dd) {
                        return false;
                    }
                } catch (ParseException e) {
                    return false;
                }
            }
            return true;
        }
        
        /** 计算年龄 */
        private int calcAge(Date birthday) {
            if (birthday != null) {
                int iage = 0;
                Calendar birthDate = Calendar.getInstance();
                birthDate.setTime(birthday);
                Calendar today = Calendar.getInstance();
                
                if (today.get(Calendar.YEAR) > birthDate.get(Calendar.YEAR)) {
                    iage = today.get(Calendar.YEAR)
                            - birthDate.get(Calendar.YEAR) - 1;
                    if (today.get(Calendar.MONTH) + 1 > birthDate.get(Calendar.MONTH)) {
                        iage++;
                    } else if (today.get(Calendar.MONTH) + 1 == birthDate.get(Calendar.MONTH)) {
                        if (today.get(Calendar.DAY_OF_MONTH) > birthDate.get(Calendar.DAY_OF_MONTH)) {
                            iage++;
                        }
                    }
                }
                return iage;
            }
            return 0;
        }

		@Override
		public String toString() {
			return "IDCard []";
		}
    }
    
    public static void main(String[] args) {
        for (int index = 0;index < 100 ; index++) {
            System.out.println(getRandomIDCard());
        }
       
    }
}
