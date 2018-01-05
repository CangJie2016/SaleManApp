package com.li.estatesalemanapp.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.li.data.http.bean.RecordList;

public interface RecordListView extends BaseView {
    void loadSuccess(RecordList recordList);
}
