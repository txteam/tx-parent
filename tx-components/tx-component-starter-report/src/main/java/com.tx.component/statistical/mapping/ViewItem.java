package com.tx.component.statistical.mapping;

/**
 * Created by SEELE on 2016/9/20.
 */
public class ViewItem extends BaseItem {
    /**
     * 字段名
     */
    private String column;
    
    /**
     * 字段名
     */
    private String name;
    
    /**
     * 是否统计
     */
    private String  statisticalDataSource;
    
    private String enumClass;

    /**
     * 展示的类型
     */
    private String type;
    
    private String refValue;

    private Boolean show = true;
    private String frozen = "false";

    /**
     * 统计的方式 count,sum,min  max  等聚合函数
     */
    private String statisticalType;
    
    @Override
    protected boolean removeObjectFieldAttr() {
        return true;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getRefValue() {
        return refValue;
    }
    
    public void setRefValue(String refValue) {
        this.refValue = refValue;
    }
    
    public String getEnumClass() {
        return enumClass;
    }
    
    public void setEnumClass(String enumClass) {
        this.enumClass = enumClass;
    }
    
    public String getColumn() {
        return column;
    }
    
    public void setColumn(String column) {
        this.column = column;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getStatisticalDataSource() {
        return statisticalDataSource;
    }

    public void setStatisticalDataSource(String statisticalDataSource) {
        this.statisticalDataSource = statisticalDataSource;
    }

    public Boolean getShow() {
        if(show==null){show = true;}
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getStatisticalType() {
        return statisticalType;
    }

    public void setStatisticalType(String statisticalType) {
        this.statisticalType = statisticalType;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }
}
