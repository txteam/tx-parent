package com.tx.component.statistical.component.conditionCompent;

import com.tx.component.statistical.mapping.ConditionItem;
import com.tx.component.statistical.type.ConditionTypeEnum;

/**
 * Created by SEELE on 2016/9/22.
 */
public interface ConditionComponent {
    /**
     * 支持的类型
     * @return
     */
    ConditionTypeEnum supportType();
    /**
     * 生成相应的页面
     * @param conditionItem
     * @return
     */
    String generatorHtml(ConditionItem conditionItem);

    String tagName();

    boolean isSupport(String typeStr);
}
