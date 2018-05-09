package com.tx.component.statistical.component.viewComponent.impl;

import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.mapping.ViewItemAttr;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/23.
 */
@Component
public  class NumberTypeViewComponent extends  AbstractViewComponent{
    @Override
    public String supportViewType() {
        return "number";
    }

    @Override
    protected Map<String, ViewItemAttr> addDefaultAttrs(ViewItem viewItem) {
        Map<String, ViewItemAttr> map =  super.addDefaultAttrs(viewItem);
        map.put("align",new ViewItemAttr("align","right"));

        StringBuffer value = new StringBuffer();

        //TODO  此处直接引用了页面上定义的fmoney方法，后续需要做相应的改造
        value.append("function(value,row,index){")
                .append("return \"<h3 style='color:orange'>\"+ fmoney(value)+ \"</h3>\"")
                .append("}");


        map.put("formatter", new ViewItemAttr("formatter", value.toString(), true));

        return map;
    }

}

