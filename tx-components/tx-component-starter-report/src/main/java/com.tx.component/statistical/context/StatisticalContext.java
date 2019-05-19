package com.tx.component.statistical.context;

import com.tx.component.statistical.component.reportRenderComponent.impl.AbstractReportRenderHandler;
import com.tx.core.exceptions.util.AssertUtils;
import org.springframework.ui.ModelMap;

/**
 * Created by SEELE on 2016/6/24.
 */
public class StatisticalContext extends StatisticalContextBuilder {
    private static StatisticalContext context;

    public static StatisticalContext getContext() {
        AssertUtils.notNull(context, "context is null.not init.");
        StatisticalContext res = context;
        return res;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        StatisticalContext.context = this;
    }


    public AbstractReportRenderHandler getReportRenderHandler(String type) {
        return  resolveReportRenderHandler(type);
    }



}
