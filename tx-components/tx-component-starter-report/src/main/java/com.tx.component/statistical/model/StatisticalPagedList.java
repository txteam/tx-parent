package com.tx.component.statistical.model;

import com.github.pagehelper.PageInfo;
import com.tx.core.paged.model.PagedList;

/**
 * Created by XRX on 2017/04/06.
 */
public class StatisticalPagedList<T> extends PagedList<T> {
    //当前页面统计记录
    private T currentPageStatisticalRecord;

    //全部统计记录
    private T totalStatisticalRecord;

    public StatisticalPagedList(PageInfo<T> pagedList) {
        setCount(pagedList.getTotal());
        setList(pagedList.getList());
        setPageIndex(pagedList.getPageNum());
        setPageSize(pagedList.getPageSize());
        setQueryPageSize(pagedList.getPageSize());
    }


    public T getCurrentPageStatisticalRecord() {
        return currentPageStatisticalRecord;
    }

    public void setCurrentPageStatisticalRecord(T currentPageStatisticalRecord) {
        this.currentPageStatisticalRecord = currentPageStatisticalRecord;
    }

    public T getTotalStatisticalRecord() {
        return totalStatisticalRecord;
    }

    public void setTotalStatisticalRecord(T totalStatisticalRecord) {
        this.totalStatisticalRecord = totalStatisticalRecord;
    }
}
