package com.li.estatesalemanapp.adapter;

import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.li.data.http.bean.Record;
import com.li.estatesalemanapp.R;
import com.li.estatesalemanapp.util.ImageLoaderHelper;

/**
 * Created by 李振强 on 2017/11/30.
 */

public class RecordViewHolder extends BaseViewHolder<Record> {

    private final ImageView iv_photo;
    private final TextView title, content;
    private final ConstraintLayout container;
    private final OnClickItemListener listener;

    public RecordViewHolder(ViewGroup parent, OnClickItemListener listener) {
        super(parent, R.layout.item_record);
        container = (ConstraintLayout)$(R.id.container);
        iv_photo = (ImageView) $(R.id.imageView);
        title = (TextView) $(R.id.tv_title);
        content = (TextView) $(R.id.tv_content);
        this.listener = listener;
    }

    @Override
    public void setData(final Record data) {
        title.setText(data.getTitle());
        content.setText(data.getContent());
        ImageLoaderHelper.displayImage(data.getPhotoUrl(),iv_photo);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(data);
            }
        });
    }

    public interface OnClickItemListener{
        void onClickItem(Record record);
    }
}
