package com.li.estatesalemanapp.presenter;

import android.content.Context;

import com.cangjie.basetool.mvp.BasePresenter;
import com.li.data.http.HttpMethods;
import com.li.data.http.bean.BaseBean;
import com.li.data.http.bean.CheckFace;
import com.li.data.http.bean.Record;
import com.li.estatesalemanapp.presenter.view.InputRecordView;

import java.io.File;

import rx.Subscriber;

public class InputRecordPresenter extends BasePresenter<InputRecordView> {
    public InputRecordPresenter(InputRecordView mvpView, Context context) {
        super(mvpView, context);
    }

    public void checkPhoto(File file) {
        HttpMethods.getInstance().upload(new Subscriber<CheckFace>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mvpView.faceTokenFailed(e.getMessage());
            }

            @Override
            public void onNext(CheckFace checkFace) {
                mvpView.faceTokenSuccess(checkFace);
            }
        },file);
    }

    public void submit(String title, String content, String faceToken) {
        HttpMethods.getInstance().submit(new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mvpView.submitFailed(e.getMessage());
            }

            @Override
            public void onNext(BaseBean baseBean) {
                mvpView.submitSuccess(baseBean.msg);
            }
        }, title, content, faceToken);
    }

    public void loadRecord(int recordId) {
        mvpView.showLoading();
        HttpMethods.getInstance().record(new Subscriber<Record>() {
            @Override
            public void onCompleted() {
                mvpView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Record record) {
                mvpView.showData(record);
            }
        }, recordId);
    }
}
