package com.li.estatesalemanapp.presenter;

import android.content.Context;

import com.cangjie.basetool.mvp.BasePresenter;
import com.li.data.http.HttpMethods;
import com.li.data.http.bean.RecordList;
import com.li.estatesalemanapp.presenter.view.RecordListView;

import rx.Subscriber;

import static com.li.estatesalemanapp.ui.RecordListActivity.LOAD_PAGE_SIZE;

public class RecordListPresenter extends BasePresenter<RecordListView> {

    public RecordListPresenter(RecordListView mvpView, Context context) {
        super(mvpView, context);
    }


    public void loadRecordList(Integer mCurrentPage) {
        HttpMethods.getInstance().recordList(new Subscriber<RecordList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.getMessage();
            }

            @Override
            public void onNext(RecordList recordList) {
                mvpView.loadSuccess(recordList);
            }
        }, mCurrentPage, LOAD_PAGE_SIZE);
    }


}
