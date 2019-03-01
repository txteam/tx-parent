package com.tx.component.statistical.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEELE on 2016/9/20.
 */
public class ViewMap extends BaseMap<ViewItem>  {
    private String sqlMapperId;



    @Override
    protected boolean removeObjectFieldAttr() {
        return true;
    }

    public String getSqlMapperId() {
        return sqlMapperId;
    }

    public void setSqlMapperId(String sqlMapperId) {
        this.sqlMapperId = sqlMapperId;
    }

}
