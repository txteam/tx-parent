/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2016年8月31日
 * <修改描述:>
 */
package com.tx.core.util.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <key>value</key> -> Map
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2016年8月31日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SimpleMapAdapter extends
        XmlAdapter<SimpleMapAdapter.MapElement, Map<String, String>> {
    
    public static class MapElement {
        
        @XmlAnyElement
        public List<Element> entryElements;
        
        public MapElement() {
        }
        
        public MapElement(List<Element> entryElements) {
            this.entryElements = entryElements;
        }
    }
    
    @Override
    public MapElement marshal(Map<String, String> params) throws Exception {
        MapElement mapElements = new MapElement();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        mapElements.entryElements = new ArrayList<Element>();
        for (Entry<String, String> entryTemp : params.entrySet()) {
            String elementName = entryTemp.getKey();
            String elementValue = entryTemp.getValue();
            mapElements.entryElements.add(buildElement(elementName,
                    elementValue,
                    document));
        }
        return mapElements;
    }
    
    @Override
    public Map<String, String> unmarshal(MapElement paramsElement)
            throws Exception {
        Map<String, String> resMap = new HashMap<String, String>();
        for (Element node : paramsElement.entryElements) {
            resMap.put(node.getNodeName(), node.getTextContent());
        }
        return resMap;
    }
    
    private Element buildElement(String elementName, String elementValue,
            Document document) {
        Element res = document.createElement(elementName);
        res.setTextContent(elementValue);
        return res;
    }
}
