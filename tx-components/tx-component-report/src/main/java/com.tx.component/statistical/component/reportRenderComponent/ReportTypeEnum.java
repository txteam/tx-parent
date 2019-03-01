package com.tx.component.statistical.component.reportRenderComponent;

/**
 * Created by SEELE on 2016/9/20.
 */
public enum ReportTypeEnum {
    PAGED_LIST_GRID("PAGED_LIST_GRID","pagedListGrid","分页表格报表"),
    LIST_GRID("LIST_GRID","listGrid","表格报表"),
    PIE("PIE","pie","分页表格报表"),
    ECHART("ECHART","echart","图形报表")
    ;

    ReportTypeEnum(String key, String code, String name) {
        this.key = key;
        this.code = code;
        this.name = name;
    }

    final String key;
    final  String code;
    final  String name;

    public String getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
