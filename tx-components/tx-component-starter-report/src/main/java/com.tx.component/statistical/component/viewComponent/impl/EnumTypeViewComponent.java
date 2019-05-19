package com.tx.component.statistical.component.viewComponent.impl;

import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.mapping.ViewItemAttr;
import com.tx.component.statistical.utils.EnumUtil;
import com.tx.core.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/23.
 */
@Component
public  class EnumTypeViewComponent extends  AbstractViewComponent{
    @Override
    public String supportViewType() {
        return "enum";
    }

    @Override
    protected Map<String, ViewItemAttr> addDefaultAttrs(ViewItem viewItem) {
        Map<String, ViewItemAttr> map =  super.addDefaultAttrs(viewItem);

        String enumClass = viewItem.getEnumClass();
        String refValue = viewItem.getRefValue();

        if(StringUtils.isNotEmpty(enumClass) && StringUtils.isNotEmpty(refValue)) {
            try {
                Enum[] enums = EnumUtil.parseEnum2Arr(enumClass);
                StringBuffer value = new StringBuffer();
                value.append("function(cellvalue, options, rowObject){");
                value.append(" var enumObj = {} ; ");
                for (Enum tempEnum : enums) {
                    String enumNameValue = EnumUtil.getFieldValue(tempEnum, refValue);
                    value.append("enumObj.").append(tempEnum.name()).append(" = ")
                    .append("\"").append(enumNameValue).append("\";");
                }
                value.append("  var val = enumObj[cellvalue] ;");
                value.append(" if(typeof val=='undefined'){ val = cellvalue }");
                value.append("return val ;}");

                map.put("formatter", new ViewItemAttr("formatter", value.toString(), true));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        return map;
    }
}

