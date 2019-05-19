package com.tx.component.statistical.mapping;

/**
 * Created by SEELE on 2016/9/20.
 */
public class ConditionItem extends BaseItem {
    private String id;
    private String name;
    private String type;
    private String datasourceId;
    private String sqlMapperId;
    private String enumClass;
    private String items;
    private String labelName;
    private String labelValue;

    private String cssClass;
    private String cssStyle;

    @Override
    protected boolean removeObjectFieldAttr() {
        return true;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getSqlMapperId() {
        return sqlMapperId;
    }

    public void setSqlMapperId(String sqlMapperId) {
        this.sqlMapperId = sqlMapperId;
    }

    public String getEnumClass() {
        return enumClass;
    }

    public void setEnumClass(String enumClass) {
        this.enumClass = enumClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }
}
