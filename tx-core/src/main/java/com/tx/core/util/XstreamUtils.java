package com.tx.core.util;

import java.io.Writer;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
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
    private static Logger logger = LoggerFactory.getLogger(XstreamUtils.class);
    
    private static Map<Class<?>, XStream> xstreamMap = new WeakHashMap<Class<?>, XStream>();
    
    /**
     * 转换过程中特殊字符转码
     */
    private static NameCoder nameCoder = new NameCoder() {
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
      * 在xml中多余的节点生成bean时会抛出异常
      * 通过该mapperWrapper跳过不存在的属性
      * @param mapper
      * @return [参数说明]
      * 
      * @return MapperWrapper [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    private static MapperWrapper createSkipOverElementMapperWrapper(
            Mapper mapper) {
        MapperWrapper resMapper = new MapperWrapper(mapper) {
            /**
             * @param elementName
             * @return
             */
            @SuppressWarnings("rawtypes")
            @Override
            public Class realClass(String elementName) {
                Class res = null;
                ;
                try {
                    res = super.realClass(elementName);
                }
                catch (CannotResolveClassException e) {
                    logger.warn("xstream change xml to object. filed (0) not exsit. ",
                            elementName);
                }
                return res;
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
      *<获取xstream转换对象>
      *<功能详细描述>
      * @param classType
      * @param isSkipOverElement
      * @param isNewLine
      * @return [参数说明]
      * 
      * @return XStream [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static XStream getXstream(Class<?> classType,
            boolean isSkipOverElement, boolean isNewLine) {
        if (xstreamMap.containsKey(classType)) {
            return xstreamMap.get(classType);
        }
        
        /**
         * 生成domDriver 重写createWriter方法，使生成的domDriver在新的节点不会信生成一行
         */
        HierarchicalStreamDriver domDriver = null;
        if (isNewLine) {
            domDriver = new Xpp3DomDriver(nameCoder);
        }
        else {
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
        }
        else {
            res = new XStream(domDriver);
        }
        
        logger.info("create xstream by {0} , parameter {1}", new Object[] {
                classType.getName(), isSkipOverElement });
        
        res.processAnnotations(classType);
        
        xstreamMap.put(classType, res);
        
        return res;
    }
    
}
