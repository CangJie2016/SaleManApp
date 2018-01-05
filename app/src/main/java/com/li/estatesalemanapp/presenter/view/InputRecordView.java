package com.li.estatesalemanapp.presenter.view;


import com.cangjie.basetool.mvp.BaseView;
import com.li.data.http.bean.CheckFace;
import com.li.data.http.bean.Record;

public interface InputRecordView extends BaseView {
    void faceTokenFailed(String message);

    void faceTokenSuccess(CheckFace faceToken);

    void submitSuccess(String returnInfo);

    void submitFailed(String returnInfo);

    void showData(Record record);
}
