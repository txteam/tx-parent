package com.tx.component.statistical.component.viewComponent.impl;

import com.tx.core.util.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by SEELE on 2016/9/23.
 */
@Component
public class BaseViewComponent extends AbstractViewComponent {
    @Override
    public String supportViewType() {
        return null;
    }

    @Override
    public boolean isSupport(String typeStr) {
        return StringUtils.isEmpty(typeStr);
    }
}
