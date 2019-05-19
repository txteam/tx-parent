package com.tx.component.statistical.component.conditionCompent.impl;

import com.tx.component.statistical.mapping.ConditionItem;
import com.tx.component.statistical.mapping.BaseAttr;
import com.tx.component.statistical.type.ConditionTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by SEELE on 2016/9/22.
 */
@Component("selectConditionComponment")
public class SelectConditionComponment extends AbstractConditionComponent {
    
    @Override
    public ConditionTypeEnum supportType() {
        return ConditionTypeEnum.select;
    }
    
    @Override
    public String tagName() {
        return "select";
    }

    /**
     * 下拉列表
     * @param conditionItem
     * @return
     */
    @Override
    protected String generatorInnerHTML(ConditionItem conditionItem) {
        List<BaseAttr> childBaseAttr = buildAttrNodeFromData(conditionItem);
        ;
        StringBuffer html = new StringBuffer();
        for (BaseAttr temp : childBaseAttr) {
            html.append("<option value=\"")
                    .append(temp.getKey())
                    .append("\" >")
                    .append(temp.getValue())
                    .append("</option>");
        }
        
        return html.toString();
    }
}
