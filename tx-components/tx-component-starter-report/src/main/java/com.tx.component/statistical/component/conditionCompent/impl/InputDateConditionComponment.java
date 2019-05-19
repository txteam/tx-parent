package com.tx.component.statistical.component.conditionCompent.impl;

import com.tx.component.statistical.mapping.BaseAttr;
import com.tx.component.statistical.type.ConditionTypeEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2016/9/22.
 */
@Component("inputDateConditionComponment")
public class InputDateConditionComponment extends AbstractConditionComponent {
    @Override
    public ConditionTypeEnum supportType() {
        return ConditionTypeEnum.input_date;
    }

    @Override
    public String tagName() {
        return "input";
    }

    protected List<BaseAttr> getDefaultAttrs() {
        List<BaseAttr> list = new ArrayList<>();
        BaseAttr baseAttr = new BaseAttr("onclick",
                "WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd' })");
        list.add(baseAttr);
        baseAttr = new BaseAttr("readOnly", "true");
        list.add(baseAttr);
        return list;
    }
}
