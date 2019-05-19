package com.tx.component.statistical.component.viewComponent.impl;

import com.alibaba.fastjson.JSONObject;
import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.mapping.ViewItemAttr;
import com.tx.component.statistical.utils.EnumUtil;
import com.tx.core.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2018/04/13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class JSONTypeViewComponent extends  AbstractViewComponent {
    @Override
    public String supportViewType() {
        return "json";
    }

    @Override
    protected Map<String, ViewItemAttr> addDefaultAttrs(ViewItem viewItem) {
        Map<String, ViewItemAttr> map =  super.addDefaultAttrs(viewItem);

        String enumClass = viewItem.getEnumClass();
        String refValue = viewItem.getRefValue();

        if(StringUtils.isNotEmpty(refValue)) {
            try {
                StringBuffer value = new StringBuffer();
                value.append("function(cellvalue, options, rowObject){");
                value.append("  var jsonVal =  " +refValue +";");
                value.append("  var val = jsonVal[cellvalue] ;");
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
