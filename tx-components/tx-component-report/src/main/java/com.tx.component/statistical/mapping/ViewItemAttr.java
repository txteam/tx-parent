package com.tx.component.statistical.mapping;

/**
 * Created by SEELE on 2016/9/20.
 */
public class ViewItemAttr extends BaseAttr {
    private boolean obj;

    public ViewItemAttr(String key, String value, boolean isFun) {
        super(key, value);
        this.obj = isFun;
    }

    public ViewItemAttr(String key, String value) {
        super(key, value);
        this.obj = false;
    }

    public ViewItemAttr(BaseAttr baseAttr) {
        super(baseAttr.getKey(), baseAttr.getValue());
    }

    public boolean isObj() {
        return obj;
    }

    public void setObj(boolean obj) {
        this.obj = obj;
    }
}
