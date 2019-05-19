package com.tx.component.statistical.component.viewComponent.impl;

import com.tx.component.statistical.component.viewComponent.ViewComponent;
import com.tx.component.statistical.mapping.BaseAttr;
import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.mapping.ViewItemAttr;
import com.tx.core.util.StringUtils;

import java.util.*;

/**
 * Created by SEELE on 2016/9/23.
 */
public abstract class AbstractViewComponent implements ViewComponent{
    @Override
    public boolean isSupport(String typeStr) {
        return supportViewType().equalsIgnoreCase(typeStr);
    }

    @Override
    public Map<String, BaseAttr> rebuildNodeAttr(ViewItem viewItem) {
        List<ViewItemAttr> list = new ArrayList<>();
        Map<String, BaseAttr> nodeAttrsMap = viewItem.getNodeAttrs();
        //移除对应的属性
        Set<String> needRemoveAttrs = needRemoveAttr();
        if (needRemoveAttrs != null) {
            for (String str : needRemoveAttrs) {
                nodeAttrsMap.remove(str.toLowerCase());
            }
        }
        
        //转换对象
        Map<String, BaseAttr> viewItemAttrMap = new HashMap<>();
        for (BaseAttr baseAttr : nodeAttrsMap.values()) {
            viewItemAttrMap.put(baseAttr.getKey(), new ViewItemAttr(baseAttr));
        }
        
        Map<String, ViewItemAttr> defaultAttrs = addDefaultAttrs(viewItem);
        if (defaultAttrs != null) {
            for (ViewItemAttr nodeAttr : defaultAttrs.values()) {
                BaseAttr currentBaseAttr = viewItemAttrMap
                        .get(nodeAttr.getKey());
                if (currentBaseAttr == null
                        || StringUtils.isEmpty(currentBaseAttr.getValue())) {
                    viewItemAttrMap.put(nodeAttr.getKey(), nodeAttr);
                }
            }
        }
        return viewItemAttrMap;
    }



   /* void buildViewAttrs(ReportStatement reportStatement){
        List<ViewItem> viewItems = reportStatement.getViewMap().getItems();

        for(ViewItem viewItem:viewItems){
            List<ViewItemAttr> list = new ArrayList<>();
            Map<String, NodeAttr> nodeAttrsMap =    viewItem.getNodeAttrs();
            //移除对应的属性
            Set<String> needRemoveAttrs = needRemoveAttr();
            if(needRemoveAttrs!=null) {
                for (String str : needRemoveAttrs) {
                    nodeAttrsMap.remove(str.toLowerCase());
                }
            }

            //转换对象
            Map<String,NodeAttr> viewItemAttrMap = new HashMap<>();
            for(NodeAttr nodeAttr:nodeAttrsMap.values()){
                viewItemAttrMap.put(nodeAttr.getKey(),new ViewItemAttr(nodeAttr));
            }

            Map<String,ViewItemAttr> defaultAttrs = addDefaultAttrs(viewItem);
            if(defaultAttrs!=null) {
                for (ViewItemAttr nodeAttr : defaultAttrs.values()) {
                    NodeAttr currentNodeAttr = viewItemAttrMap.get(nodeAttr.getKey());
                    if(currentNodeAttr==null || StringUtils.isEmpty(currentNodeAttr.getValue())){
                        viewItemAttrMap.put(nodeAttr.getKey(),nodeAttr);
                    }
                }
            }
            viewItem.setNodeAttrs(viewItemAttrMap);
        }
    }*/

    protected   Map<String,ViewItemAttr> addDefaultAttrs(ViewItem viewItem){
        Map<String,ViewItemAttr> viewItemAttrs = new HashMap<>();
        viewItemAttrs.put("width",new ViewItemAttr("width","150",true));
        viewItemAttrs.put("field",new ViewItemAttr("field",viewItem.getColumn()));
        viewItemAttrs.put("title",new ViewItemAttr("title",viewItem.getName()));
        viewItemAttrs.put("sortable",new ViewItemAttr("sortable","true",true));

        if(viewItem.getNodeAttrs().containsKey("formatterfun")){
            viewItemAttrs.put("formatter",new ViewItemAttr("formatter",viewItem.getNodeAttrs().get("formatterfun").getValue(),true));
        }

        return viewItemAttrs;
    }


    protected Set<String> needRemoveAttr(){
        Set<String> set = new HashSet<>();
        return set;

    }

}
