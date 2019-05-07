package com.tx.core.util;

import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ClassUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
  * xstream工具封装
  * 用以处理xml与bean的转换
  * 
  * @author  PengQingyang
  * @version  [版本号, 2012-10-5]
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
 */
public class XstreamUtils {
    
    /** xstream日志记录器 */
    private static Logger logger = LoggerFactory.getLogger(XstreamUtils.class);
    
    /** Xstream弱引用缓存 */
    private static Map<Class<?>, XStream> xstreamMap = new WeakHashMap<Class<?>, XStream>();
    
    /**
     * 转换过程中特殊字符转码
     */
    private static NameCoder defaultNameCoder = new NameCoder() {
        public String encodeNode(String arg0) {
            return arg0;
        }
        
        public String encodeAttribute(String arg0) {
            return arg0;
        }
        
        public String decodeNode(String arg0) {
            return arg0;
        }
        
        public String decodeAttribute(String arg0) {
            return arg0;
        }
    };
    
    /**
      * 增加字符集设置功能
      * <功能详细描述>
      * @param charset
      * @return [参数说明]
      * 
      * @return NameCoder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static NameCoder getCharsetNameCoder(String charset) {
        charset = StringUtils.isEmpty(charset) ? "UTF-8" : charset;
        NameCoder nc = getNameCoder("UTF-8", charset, charset, "UTF-8");
        return nc;
    }
    
    /**
      * 
      * <功能详细描述>
      * @param encodeSourceCharset
      * @param encodeTargetCharset
      * @param decodeSourceCharset
      * @param decodeTargetCharset
      * @return [参数说明]
      * 
      * @return NameCoder [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static NameCoder getNameCoder(String encodeSourceCharset, String encodeTargetCharset,
            String decodeSourceCharset, String decodeTargetCharset) {
        final String finalEncodeSourceCharset = StringUtils.isEmpty(encodeSourceCharset) ? "UTF-8"
                : encodeSourceCharset;
        final String finalEncodeTargetCharset = StringUtils.isEmpty(encodeTargetCharset) ? "UTF-8"
                : encodeTargetCharset;
        final String finalDecodeSourceCharset = StringUtils.isEmpty(decodeSourceCharset) ? "UTF-8"
                : decodeSourceCharset;
        final String finalDecodeTargetCharset = StringUtils.isEmpty(decodeTargetCharset) ? "UTF-8"
                : decodeTargetCharset;
        NameCoder nameCoder = new NameCoder() {
            public String encodeNode(String content) {
                content = transfer(content, finalEncodeSourceCharset, finalEncodeTargetCharset);
                return content;
            }
            
            public String encodeAttribute(String content) {
                content = transfer(content, finalEncodeSourceCharset, finalEncodeTargetCharset);
                return content;
            }
            
            public String decodeNode(String content) {
                content = transfer(content, finalDecodeSourceCharset, finalDecodeTargetCharset);
                return content;
            }
            
            public String decodeAttribute(String content) {
                content = transfer(content, finalDecodeSourceCharset, finalDecodeTargetCharset);
                return content;
            }
            
            private String transfer(String content, String sourceCharset, String targetCharset) {
                if (sourceCharset.equals(targetCharset)) {
                    return content;
                } else {
                    try {
                        content = new String(content.getBytes(sourceCharset), targetCharset);
                    } catch (UnsupportedEncodingException e) {
                        throw new com.tx.core.exceptions.context.UnsupportedEncodingException(
                                MessageUtils.format("不支持的字符集.source:{} target:{}",
                                        new Object[] { finalEncodeSourceCharset, finalEncodeTargetCharset }));
                    }
                }
                return content;
            }
        };
        return nameCoder;
    }
    
    /**
      * 在xml中多余的节点生成bean时会抛出异常
      * 通过该mapperWrapper跳过不存在的属性
      * @param mapper
      * @return [参数说明]
      * 
      * @return MapperWrapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static MapperWrapper createSkipOverElementMapperWrapper(Mapper mapper) {
        MapperWrapper resMapper = new MapperWrapper(mapper) {
            
            /**
             * @param definedIn
             * @param fieldName
             * @return
             */
            @SuppressWarnings("rawtypes")
            @Override
            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                if(!super.shouldSerializeMember(definedIn, fieldName)){
                    return false;
                }else{
                    if (FieldUtils.getDeclaredField(definedIn, fieldName, true) == null) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        };
        return resMapper;
    }
    
    /**
     * 获取xstream转换对象
     * @param classType
     * @return [参数说明]
     * 
     * @return XStream [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static XStream getXstream(Class<?> classType) {
        return getXstream(classType, true, true);
    }
    
    /**
      * 获取Xstream对象<br/>
      * <功能详细描述>
      * @param classType
      * @param isSkipOverElement
      * @param isNewLine
      * @return [参数说明]
      * 
      * @return XStream [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static XStream getXstream(Class<?> classType, boolean isSkipOverElement, boolean isNewLine) {
        return getXstream(classType, defaultNameCoder, isSkipOverElement, isNewLine);
    }
    
    /**
      * 获取对应字符集的xstream对象
      * <功能详细描述>
      * @param classType
      * @param charset
      * @param isSkipOverElement
      * @param isNewLine
      * @return [参数说明]
      * 
      * @return XStream [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static XStream getXstream(Class<?> classType, String charset, boolean isSkipOverElement, boolean isNewLine) {
        return getXstream(classType, getCharsetNameCoder(charset), isSkipOverElement, isNewLine);
    }
    
    /**
      * <获取xstream转换对象>
      * <功能详细描述>
      * @param classType
      * @param isSkipOverElement
      * @param isNewLine
      * @return [参数说明]
      * 
      * @return XStream [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static XStream getXstream(Class<?> classType, NameCoder nameCoder, boolean isSkipOverElement,
            boolean isNewLine) {
        if (xstreamMap.containsKey(classType)) {
            return xstreamMap.get(classType);
        }
        if (nameCoder == null) {
            nameCoder = defaultNameCoder;
        }
        /**
         * 生成domDriver 重写createWriter方法，使生成的domDriver在新的节点不会信生成一行
         */
        HierarchicalStreamDriver domDriver = null;
        if (isNewLine) {
            domDriver = new Xpp3DomDriver(nameCoder);
        } else {
            domDriver = new Xpp3DomDriver(nameCoder) {
                public HierarchicalStreamWriter createWriter(Writer out) {
                    return new PrettyPrintWriter(out, getNameCoder()) {
                        //换行不加换行符
                        protected String getNewLine() {
                            return "";
                        }
                        
                        //行结束时不加多余的空格 
                        protected void endOfLine() {
                            return;
                        }
                    };
                }
            };
        }
        
        XStream res = null;
        if (isSkipOverElement) {
            res = new XStream(domDriver) {
                protected MapperWrapper wrapMapper(MapperWrapper next) {
                    return createSkipOverElementMapperWrapper(next);
                }
            };
        } else {
            res = new XStream(domDriver);
        }
        //XStream res = new XStream(domDriver);
        
        logger.info("create xstream by {0} , parameter {1}", new Object[] { classType.getName(), isSkipOverElement });
        
        res.processAnnotations(classType);
        
        xstreamMap.put(classType, res);
        
        return res;
    }
    
}
