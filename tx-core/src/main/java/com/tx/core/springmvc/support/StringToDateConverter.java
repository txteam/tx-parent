/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2012-12-17
 * <修改描述:>
 */
package com.tx.core.springmvc.support;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 字符串到时间类型的转换器
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2012-12-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringToDateConverter implements Converter<String, Date> {
    
    private static final String[] commonDatePattern = new String[] {
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH",
            "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyyMMddHHmmss", "yyyyMMdd",
            "yyyyMMddHH", "yyyyMMddHHmm", "yyyyMM" };
    
    /**
     * @param source
     * @return
     */
    @Override
    public Date convert(String source) {
        Date changeDate = null;
        try {
            changeDate = DateUtils.parseDate(source, commonDatePattern);
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Cannot convert String [" + source + "] to target class [" + Date.class.getName() + "]");
        }
        return changeDate;
    }
}
