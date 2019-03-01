package com.tx.component.statistical.mapping;

import java.util.List;

/**
 * Created by SEELE on 2016/9/20.
 */
public class BaseMap<T extends BaseItem> extends BaseAttrEntry {
    protected List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
