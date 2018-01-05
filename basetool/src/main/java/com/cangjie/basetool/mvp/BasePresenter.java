package com.cangjie.basetool.mvp;


import android.content.Context;

import com.cangjie.basetool.mvp.BaseView;

/**
 * author：CangJie on 2016/8/18 14:38
 * email：cangjie2016@gmail.com
 */
public class BasePresenter<V extends BaseView> {
    public V mvpView;
    protected Context mContext;

    public BasePresenter(V mvpView, Context context) {
        this.mvpView = mvpView;
        this.mContext = context;
    }
}
