/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2014年7月2日
 * <修改描述:>
 */
package com.tx.core.util;

import java.util.Date;

import org.joda.time.DateTime;

import com.tx.core.exceptions.util.AssertUtils;

/**
 * 时间工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2014年7月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DateUtils {
    
    /** 每天秒数 */
    private static long DAY_SECOND_COUNT = 1000 * 60 * 60 * 24;
    
    private static long BASE_DATE_TIME = (new DateTime(1970,1,1,0,0,0).toDate()).getTime();
    
    /**
      * 获取对应时间对应标准时间间隔天数
      * <功能详细描述>
      * @param date
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int getDays(Date date) {
        AssertUtils.notNull(date, "date is null");
        
        long res = (date.getTime() - BASE_DATE_TIME) / DAY_SECOND_COUNT;
        return (int) res;
    }
    
    /**
      * 获取两个时间之间最大的时间<br/>
      *<功能详细描述>
      * @param afterDate
      * @param preDate
      * @return [参数说明]
      * 
      * @return Date [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static Date max(Date afterDate, Date preDate) {
        if (afterDate == null) {
            return preDate;
        }
        if (preDate == null) {
            return afterDate;
        }
        if (afterDate.compareTo(preDate) > 0) {
            return afterDate;
        } else {
            return preDate;
        }
    }
    
    /**
      * 比较两个日期
      *     如果两者为同天则返回0
      *     如果前大于后，则 > 0
      *     反之 < 0
      *<功能详细描述>
      * @param preDate
      * @param afterDate
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int compareByDay(Date afterDate, Date preDate) {
        return calculateNumberOfDaysBetween(afterDate, preDate);
    }
    
    /**
      * 计算两个日期之间间隔天数<br/>
      *     如果为同一天，则返回0，如果>0则后日期大于前日期，<0则后日期小于前日期<br/>
      *<功能详细描述>
      * @param preDate
      * @param afterDate
      * @return [参数说明]
      * 
      * @return int [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static int calculateNumberOfDaysBetween(Date afterDate, Date preDate) {
        if (org.apache.commons.lang3.time.DateUtils.isSameDay(afterDate,
                preDate)) {
            return 0;
        }
        int afterDays = getDays(afterDate);
        int preDays = getDays(preDate);
        return afterDays - preDays;
    }
    
    public static void main(String[] args) {
        DateTime now = DateTime.now();
        System.out.println(calculateNumberOfDaysBetween(now.toDate(), new DateTime("2014-12-13").toDate()));
    }
    
    public static void main1(String[] args) {
        DateTime now = DateTime.now();
        System.out.println(getDays(now.toDate()));
        
        System.out.println((new DateTime(1970,1,1,0,0,0).toDate()).getTime());
        
        System.out.println(getDays(new DateTime(1970,1,1,0,0,0).toDate()));
        
        System.out.println(getDays(new DateTime(now.getYear(),now.getMonthOfYear(),now.getDayOfMonth(),0,0,0).toDate()));
        
        System.out.println(getDays(new DateTime(now.getYear(),now.getMonthOfYear(),now.getDayOfMonth(),0,0,0).toDate()));
        
        System.out.println(getDays(new DateTime(now.getYear(),now.getMonthOfYear(),now.getDayOfMonth(),7,59,59).toDate()));
        
        DateTime be = now.plusDays(-1);
        System.out.println(getDays(new DateTime(be.getYear(),be.getMonthOfYear(),be.getDayOfMonth(),0,0,0).toDate()));
        
        System.out.println(getDays(new DateTime(be.getYear(),be.getMonthOfYear(),be.getDayOfMonth(),0,0,0).toDate()));
        
        System.out.println(getDays(new DateTime(be.getYear(),be.getMonthOfYear(),be.getDayOfMonth(),7,59,59).toDate()));
    }
}
