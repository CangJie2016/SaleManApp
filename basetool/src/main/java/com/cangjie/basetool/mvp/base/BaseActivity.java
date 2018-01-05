package com.cangjie.basetool.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.cangjie.basetool.utils.ToastHelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    protected abstract void showLoading();
    protected abstract void hideLoading();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void disPlay(String toast){
        ToastHelper.showToast(toast,this);
    }

}
