package com.tx.component.statistical.component.viewComponent.impl;

import com.tx.component.statistical.mapping.ViewItem;
import com.tx.component.statistical.mapping.ViewItemAttr;
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
public class ALinkViewComponent extends  AbstractViewComponent {
    @Override
    public String supportViewType() {
        return "alink";
    }
    @Override
    protected Map<String, ViewItemAttr> addDefaultAttrs(ViewItem viewItem) {
        Map<String, ViewItemAttr> map =  super.addDefaultAttrs(viewItem);

        String enumClass = viewItem.getEnumClass();
        String refValue = viewItem.getRefValue();

        if(StringUtils.isNotEmpty(refValue)) {
            refValue = refValue.trim();
            try {
                StringBuffer value = new StringBuffer();
               String fmt =  " function formatterFn(value,row,index){\n" +
                        "            var text=\"\";\n" +
                        "            if(!$.ObjectUtils.isEmpty(value)){\n" +
                        "                    text= $.formatString('<a href=\"javascript:"+refValue+"(\\'{0}\\',\\'{1}\\')\"  style=\"text-decoration:underline;\">{2}</a>',\n" +
                        "                    value,index ,value );\n" +
                        "            }\n" +
                        "            return text;\n" +
                        "        }";

                map.put("formatter", new ViewItemAttr("formatter", fmt, true));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return map;
    }
}
