package com.tx.component.statistical.model;

/**
 * Created by SEELE on 2016/7/13.
 */
public class ChartInfo {
    private String name;
    private String value;
    private String group;

    private String desc;


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
