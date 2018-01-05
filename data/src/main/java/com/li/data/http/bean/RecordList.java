package com.li.data.http.bean;

import java.util.List;

/**
 * Created by 李振强 on 2017/11/30.
 */

public class RecordList {
    private Integer size;
    private List<Record> recordList;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }
}
