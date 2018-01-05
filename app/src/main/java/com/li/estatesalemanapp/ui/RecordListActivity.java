package com.li.estatesalemanapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.view.recycle_view.CreateRecyclerView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.li.data.http.bean.Record;
import com.li.data.http.bean.RecordList;
import com.li.estatesalemanapp.R;
import com.li.estatesalemanapp.adapter.RecordViewHolder;
import com.li.estatesalemanapp.presenter.RecordListPresenter;
import com.li.estatesalemanapp.presenter.view.RecordListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordListActivity extends PresenterActivity<RecordListPresenter> implements RecordListView, RecyclerArrayAdapter.OnLoadMoreListener {
    @Bind(R.id.rv)
    EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter arrayAdapter;

    private int mCurrentPage = 1, mPageSize;
    public final static Integer LOAD_PAGE_SIZE = 5;

    @Override
    protected RecordListPresenter createPresenter() {
        return new RecordListPresenter(this, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        ButterKnife.bind(this);

        arrayAdapter = new RecyclerArrayAdapter(mContext){
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecordViewHolder(parent, new RecordViewHolder.OnClickItemListener() {
                    @Override
                    public void onClickItem(Record record) {
                        Intent intent = new Intent(mContext, InputRecordActivity.class);
                        intent.putExtra("id", record.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        new CreateRecyclerView().CreateRecyclerView(mContext, recyclerView, arrayAdapter, this);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        refresh();
    }

    @Override
    public void onLoadMore() {
        if (mCurrentPage * LOAD_PAGE_SIZE >= mPageSize) {
            arrayAdapter.stopMore();
        } else {
            mCurrentPage++;
            mPresenter.loadRecordList(mCurrentPage);
        }
    }

    public void reload(){
        mPresenter.loadRecordList(mCurrentPage);
    }

    public void refresh(){
        mCurrentPage = 1;
        reload();
    }

    @Override
    public void loadSuccess(RecordList recordList) {
        mPageSize = recordList.getSize();
        if (mCurrentPage == 1) arrayAdapter.clear();
        arrayAdapter.addAll(recordList.getRecordList());
    }
}
