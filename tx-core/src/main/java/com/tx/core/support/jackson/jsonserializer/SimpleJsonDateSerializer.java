/*
 * 描          述:  <描述>
 * 修  改   人:  PengQingyang
 * 修改时间:  2013-10-8
 * <修改描述:>
 */
package com.tx.core.support.jackson.jsonserializer;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Json日期序列化类<br/>
 * <功能详细描述>
 * 
 * @author  PengQingyang
 * @version  [版本号, 2013-10-8]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleJsonDateSerializer extends JsonSerializer<Date> {
    
    private static String dateFormatter = "yyyy-MM-dd";
    
    /**
     * @param value
     * @param jgen
     * @param provider
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public void serialize(Date value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        String formattedDate = DateFormatUtils.format(value, dateFormatter);
        jgen.writeString(formattedDate);
    }
    
    /**
     * @param 对dateFormatter进行赋值
     */
    public static void setDateFormatter(String dateFormatter) {
        SimpleJsonDateSerializer.dateFormatter = dateFormatter;
    }
}
