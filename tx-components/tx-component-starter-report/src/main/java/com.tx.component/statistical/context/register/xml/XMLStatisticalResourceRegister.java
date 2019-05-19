/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2015年11月6日
 * <修改描述:>
 */
package com.tx.component.statistical.context.register.xml;

import com.tx.component.statistical.context.register.AbstractStatisticalResourceRegister;
import com.tx.component.statistical.mapping.*;
import com.tx.core.util.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * XML类型的统计资源注册器<br/>
 * <功能详细描述>
 *
 * @author Administrator
 * @version [版本号, 2015年11月6日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Component("xMLStatisticalResourceRegister")
public class XMLStatisticalResourceRegister extends
        AbstractStatisticalResourceRegister implements InitializingBean {
    //    private static XPath xpath;
    protected Logger logger = LoggerFactory
            .getLogger(XMLStatisticalResourceRegister.class);
    private DocumentBuilder documentBuilder;


    public XMLStatisticalResourceRegister() {
    }


    @PostConstruct
    public void init() throws Exception {

    }

    //TODO 重构
    @Override
    public ReportStatement parseResource(Resource resource) {
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
                }
            });
            Document dom4jDoc = saxReader.read(resource.getFile());
            Map<String, String> map = new HashMap<>();
            map.put("ns", dom4jDoc.getRootElement().getNamespaceURI());
            saxReader.getDocumentFactory().setXPathNamespaceURIs(map);


            ReportStatement reportStatement = parseNodeAttrs(dom4jDoc, "/ns:report", ReportStatement.class);
            reportStatement.setConditionMap(parseBaseMapObj(dom4jDoc, "/ns:report/ns:conditions",
                    "item", ConditionMap.class));
            reportStatement.setViewMap(parseBaseMapObj(dom4jDoc, "/ns:report/ns:views",
                    "item", ViewMap.class));

            String script = parseTextContainTag(dom4jDoc, "/ns:report/ns:script");
            script = htmlDiscode(script);
            reportStatement.setScript(script);
            reportStatement.setSqlMapperItems(buildSqlMapperItems(dom4jDoc, reportStatement.getCode()));

            return reportStatement;
        } catch (Exception e) {
            logger.error("解析加载报表错误", e);
            e.printStackTrace();
        }

        return null;
    }

    public String htmlDiscode(String theString)
    {
        theString = theString.replace("&gt;", ">");
        theString = theString.replace("&lt;", "<");
        theString = theString.replace("&nbsp;", " ");
        theString = theString.replace("&quot;", "\"");
        theString = theString.replace("&amp;", "&");
//        theString = theString.replace("'", "\'");
//        theString = theString.replace("<br/> ", "\n");
        return theString;
    }




//    public static void main(String[] args) {
//        String xml ="<script xmlns=\"http://wtms.com/xml/statistical-1.0.xsd\">\n" +
//                "        function projectNameFmt(cellValue,row,index){\n" +
//                "             return \"<a>\"+cellValue+\"</a>\" +cellValue\n" +
//                "        }\n" +
//                "\n" +
//                "    </script>";
//
//        String newxml = xml.replaceAll("^<.*>|</.*>$","");
//        System.out.println(newxml);
//    }

    private String parseText(Document dom4jDoc, String expression) {
        Element elm = (Element) dom4jDoc.selectSingleNode(expression);
        String xml = elm.asXML();
        return elm.getText();
    }

    private String parseTextContainTag(Document dom4jDoc, String expression) {
        Element elm = (Element) dom4jDoc.selectSingleNode(expression);
        String xml = elm.asXML();
        String newxml = xml.replaceAll("^<.*>|</.*>$", "");
        return newxml;
    }


    /**
     * 资源是否被能被解析
     *
     * @param tempResource
     * @return
     */
    @Override
    public boolean supportRegister(Resource tempResource) {
        return tempResource.getFilename().toLowerCase().endsWith("xml");
    }


    private <T extends BaseMap> T parseBaseMapObj(Document dom4jDoc, String expression, String chileNodeName,
                                                  Class<T> clsMap)
            throws XPathExpressionException, InstantiationException,
            IllegalAccessException {
        ParameterizedType parameterizedType = (ParameterizedType) clsMap.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        Class<? extends BaseItem> clsItem = null;
        if (types.length > 0) {
            clsItem = (Class<? extends BaseItem>) types[0];
        }
        Element elm = (Element) dom4jDoc.selectSingleNode(expression);
        T viewMap = buildBaseMapAttr(clsMap, elm);

        List<Element> elementChilds = elm.elements();
        List<BaseAttrEntry> conditionItems = new ArrayList<>();
        for (int i = 0; i < elementChilds.size(); i++) {
            Element el = elementChilds.get(i);
            String name = el.getName();
            if (name.equalsIgnoreCase(chileNodeName)) {
                BaseAttrEntry conditionItem = buildBaseMapAttr(clsItem, el);
                conditionItems.add(conditionItem);
            }
        }
        //设置属性
        viewMap.setItems(conditionItems);
        return viewMap;
    }

    private <T extends BaseAttrEntry> T parseNodeAttrs(Document dom4jDoc, String expression,
                                                       Class<T> clsMap)
            throws XPathExpressionException, InstantiationException,
            IllegalAccessException {
        Element elm = (Element) dom4jDoc.selectSingleNode(expression);
        return buildBaseMapAttr(clsMap, elm);
    }


    /**
     * 构建查询脚本mapper -- mybatis
     *
     * @param dom4jDoc
     * @param namespace
     * @return
     * @throws XPathExpressionException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private List<SqlMapperItem> buildSqlMapperItems(Document dom4jDoc,
                                                    String namespace) throws XPathExpressionException,
            InstantiationException, IllegalAccessException {
        List<SqlMapperItem> srSqlItems = new ArrayList<>();

        List<Element> dsElList = dom4jDoc.selectNodes("//ns:sqlMappers/ns:sqlMapper");
        for (Iterator<Element> it = dsElList.iterator(); it.hasNext(); ) {
            Element elm = it.next();
            SqlMapperItem sqlMapperItem = buildBaseMapAttr(SqlMapperItem.class, elm);
            String nodeName = elm.getName();
            sqlMapperItem.setNamespace(namespace);


            String returnType = elm.attributeValue("returnType");
            String parameterType = elm.attributeValue("parameterType");
            String datasourceId = elm.attributeValue("datasourceId");

            Class returnCls = forNameDefaultMap(returnType);
            Class parameterTypeCls = forNameDefaultMap(parameterType);

            sqlMapperItem.setReturnType(returnCls);
            sqlMapperItem.setParameterType(parameterTypeCls);
            sqlMapperItem.setDatasourceId(datasourceId);


            String asXML = elm.asXML();
            asXML = asXML.replaceAll("^<.*>|</.*>$", "");

//            asXML = asXML.substring(asXML.indexOf(">", 5) + 1);
//            asXML = asXML.replace("</" + nodeName + ">", "");
            sqlMapperItem.setSqlScript(asXML);

            srSqlItems.add(sqlMapperItem);

        }
        return srSqlItems;
    }

//
//    //设置属性[目前 - 只对类型为String的有效]
//    //注入属性值
//    private BaseAttrEntry buildBaseMapAttr(Class<? extends BaseAttrEntry> baseMap,
//                                           Node node) throws IllegalAccessException, InstantiationException {
//        BaseAttrEntry baseMapObj = baseMap.newInstance();
//        Map<String, NodeAttr> nodeAttrs = buildAttrs(node);
//        baseMapObj.setNodeAttrs(nodeAttrs);
//        return baseMapObj;
//    }

    private <T extends BaseAttrEntry> T buildBaseMapAttr(Class<T> baseMap,
                                                         Element element) throws IllegalAccessException, InstantiationException {
        T baseMapObj = baseMap.newInstance();
        Map<String, BaseAttr> nodeAttrs = buildAttrs(element);
        baseMapObj.setNodeAttrs(nodeAttrs);
        return baseMapObj;
    }

    private Map<String, BaseAttr> buildAttrs(Element element) {
        Map<String, BaseAttr> nodeAttrs = new HashMap<>();
        List<Attribute> attributes = element.attributes();
        for (Iterator<Attribute> it = attributes.iterator(); it.hasNext(); ) {
            Attribute attribute = it.next();

            BaseAttr baseAttr = new BaseAttr();
            baseAttr.setKey(attribute.getName().toLowerCase());
            baseAttr.setValue(attribute.getValue());
            nodeAttrs.put(baseAttr.getKey(), baseAttr);
        }

        return nodeAttrs;
    }

//    private Map<String, NodeAttr> buildAttrs(Node node) {
//        Map<String, NodeAttr> nodeAttrs = new HashMap<>();
//        NamedNodeMap attributes = node.getAttributes();
//        for (int i = 0; i < attributes.getLength(); i++) {
//            NodeAttr nodeAttr = new NodeAttr();
//            Node tempAttrNode = attributes.item(i);
//            nodeAttr.setKey(tempAttrNode.getNodeName().toLowerCase());
//            nodeAttr.setValue(tempAttrNode.getNodeValue());
//            nodeAttrs.put(nodeAttr.getKey(), nodeAttr);
//        }
//        return nodeAttrs;
//    }

    private Class forNameDefaultMap(String name) {
        if (StringUtils.isNotEmpty(name)) {
            if (typeMap.containsKey(name)) {
                return typeMap.get(name);
            } else {
                try {
                    return Class.forName(name);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return Map.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
