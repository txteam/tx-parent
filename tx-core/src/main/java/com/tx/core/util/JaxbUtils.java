/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月24日
 * <修改描述:>
 */
package com.tx.core.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.tx.core.exceptions.SILException;
import com.tx.core.support.jaxb.ListWrapper;

/**
 * Jaxb工具类<br/>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JaxbUtils {
    
    private static Map<Class<?>, JAXBContext> jaxbContextMap = new WeakHashMap<>();
    
    /**
      * JavaBean转换成xml 
      * <功能详细描述>
      * @param obj
      * @param types
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String toXML(Object obj, Class<?>... types) {
        return toXML(obj, "UTF-8", types);
    }
    
    /** 
     * JavaBean转换成xml 
     * 默认编码UTF-8 
     * @param obj 
     * @param writer 
     * @return  
     */
    public static String toXML(Object obj, String encoding, Class<?>... types) {
        String result = null;
        StringWriter writer = null;
        try {
            Class<?> type = obj.getClass();
            JAXBContext context = getJAXBContext(type, types);
            
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            
            writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            throw new SILException("fromXML exception.", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return result;
    }
    
    /** 
     * JavaBean转换成xml 
     * 默认编码UTF-8 
     * @param obj 
     * @param writer 
     * @return  
     */
    public static String toXML(Object obj) {
        return toXML(obj, "UTF-8");
    }
    
    /** 
     * JavaBean转换成xml 
     * @param obj 
     * @param encoding  
     * @return  
     */
    public static String toXML(Object obj, String encoding) {
        String result = null;
        StringWriter writer = null;
        try {
            Class<?> type = obj.getClass();
            //获取类对应的JAXBContext
            JAXBContext context = getJAXBContext(type);
            
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            
            writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            throw new SILException("toXML exception.", e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return result;
    }
    
    /**
      * 根据Xml转换为Bean
      * <功能详细描述>
      * @param xml
      * @param type
      * @param types
      * @return [参数说明]
      * 
      * @return T [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> T fromXML(String xml, Class<T> type, Class<?>... types) {
        StringReader reader = null;
        try {
            JAXBContext context = getJAXBContext(type, types);
            
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            reader = new StringReader(xml);
            @SuppressWarnings("unchecked")
            T result = (T) unmarshaller.unmarshal(new StreamSource(reader));
            return result;
        } catch (Exception e) {
            throw new SILException("fromXML exception.", e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    /**
      * 根据Xml转换为BeanList
      * <功能详细描述>
      * @param xml
      * @param type
      * @param types
      * @return [参数说明]
      * 
      * @return List<T> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static <T> List<T> listFromXML(String xml, Class<T> type,
            Class<?>... types) {
        StringReader reader = null;
        try {
            JAXBContext context = getJAXBContext(ListWrapper.class,
                    ArrayUtils.add(types, type));
            
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            reader = new StringReader(xml);
            @SuppressWarnings("unchecked")
            ListWrapper<T> wrapper = (ListWrapper<T>) unmarshaller.unmarshal(new StreamSource(
                    reader),
                    ListWrapper.class)
                    .getValue();
            
            List<T> result = wrapper.getItems();
            return result;
        } catch (Exception e) {
            throw new SILException("listFromXML exception.", e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    /** 
     * 获取JAXBContext实例
     * <功能详细描述>
     * @param obj
     * @return
     * @throws JAXBException [参数说明]
     * 
     * @return JAXBContext [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static <T> JAXBContext getJAXBContext(Class<T> type,
            Class<?>... types) throws JAXBException {
        JAXBContext context = null;
        if (types == null || types.length == 0) {
            if (jaxbContextMap.containsKey(type)) {
                context = jaxbContextMap.get(type);
            } else {
                context = JAXBContext.newInstance(type);
                jaxbContextMap.put(type, context);
            }
        } else {
            if (type == null) {
                context = JAXBContext.newInstance(types);
            } else {
                context = JAXBContext.newInstance(ArrayUtils.add(types, type));
            }
        }
        return context;
    }
}
