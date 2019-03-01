package com.tx.component.statistical.component.conditionCompent.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tx.component.statistical.mapping.BaseAttr;
import com.tx.component.statistical.type.ConditionTypeEnum;

/**
 * Created by SEELE on 2016/9/22.
 */
@Component("inputDateTimeConditionComponent")
public class InputDateTimeConditionComponent extends AbstractConditionComponent {
    @Override
    public ConditionTypeEnum supportType() {
        return ConditionTypeEnum.input_date_time;
    }

    @Override
    public String tagName() {
        return "input";
    }

    protected List<BaseAttr> getDefaultAttrs() {
        List<BaseAttr> list = new ArrayList<>();
        BaseAttr baseAttr = new BaseAttr("onclick",
                "WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss' })");
        list.add(baseAttr);
        baseAttr = new BaseAttr("readOnly", "true");
        list.add(baseAttr);
        return list;
    }
}
