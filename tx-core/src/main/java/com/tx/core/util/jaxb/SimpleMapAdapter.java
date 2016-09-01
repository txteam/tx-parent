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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

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
        public List<JAXBElement<String>> entryElement;
        
        public MapElement() {
        }
        
        public MapElement(List<JAXBElement<String>> entryElement) {
            this.entryElement = entryElement;
        }
    }
    
    @Override
    public MapElement marshal(Map<String, String> params) throws Exception {
        //        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //        DocumentBuilder db = dbf.newDocumentBuilder();
        //        Document document = db.newDocument();
        //        Element rootElement = document.createElement("map");
        //        document.appendChild(rootElement);
        //
        //        for(Entry<String,String> entry : map.entrySet()) {
        //            Element mapElement = document.createElement(entry.getKey());
        //            mapElement.setTextContent(entry.getValue());
        //            rootElement.appendChild(mapElement);
        //        }
        //
        //        AdaptedMap adaptedMap = new AdaptedMap();
        //        adaptedMap.setValue(document);
        MapElement mapElements = new MapElement();
        mapElements.entryElement = new ArrayList<>();
        for(Entry<String, String> entryTemp :params.entrySet()){
            String elementName = entryTemp.getKey();
            String elementValue = entryTemp.getValue();
            mapElements.entryElement.add(buildJAXBElement(elementName, elementValue));
        }
        return mapElements;
    }
    
    @Override
    public Map<String, String> unmarshal(MapElement paramsElement)
            throws Exception {
        //        Map<String, String> resMap = new HashMap<String, String>();
        //        NodeList nodeList = paramsElement.paramsElement.getChildNodes();
        //        for (int i = 0; i < nodeList.getLength(); i++) {
        //            Node node = nodeList.item(i);
        //            resMap.put(node.getNodeName(), node.getNodeValue());
        //        }
        Map<String, String> resMap = new HashMap<String, String>();
        List<JAXBElement<String>> entryElement = paramsElement.entryElement;
        for (int i = 0; i < entryElement.size(); i++) {
            JAXBElement<String> entryTemp = entryElement.get(i);
            resMap.put(entryTemp.getName().getLocalPart(), entryTemp.getValue());
        }
        return resMap;
    }
    
    @SuppressWarnings("unused")
    private <T> JAXBElement<T> buildJAXBElement(String elementName,
            T elementValue) {
        @SuppressWarnings("unchecked")
        JAXBElement<T> res = buildJAXBElement(elementName,
                elementValue,
                (Class<T>) elementValue.getClass());
        return res;
    }
    
    private <T> JAXBElement<T> buildJAXBElement(String elementName,
            T elementValue, Class<T> type) {
        QName qName = new QName(elementName);
        JAXBElement<T> el = new JAXBElement<T>(qName, type, elementValue);
        return el;
    }
}
