package com.tx.component.statistical.mapping;

/**
 * Created by SEELE on 2016/9/20.
 */
public class BaseAttr {
    private String key;
    private String value;

    public BaseAttr(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public BaseAttr() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
