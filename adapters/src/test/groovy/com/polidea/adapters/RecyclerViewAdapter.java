package com.polidea.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class RecyclerViewAdapter extends BaseRecyclerViewAdapter {

    public int dataCount;

    boolean dataCoundCalled;

    RecyclerView.ViewHolder createDataViewHolder;

    boolean onCreateDataViewHolderCalled;

    boolean onBindDataViewHolderCalled;

    public int dataViewLayoutResId;

    boolean getDataViewLayoutResIdCalled;

    public int infiniteScrollingLayoutResId;

    public int topContentInsetViewLayoutResId;

    public int bottomContentInsetViewLayoutResId;

    @Override
    public void setInfiniteScrollingEnabled(boolean enabled) {
        infiniteScrollingEnabled = enabled;
    }

    @Override
    protected int getDataCount() {
        dataCoundCalled = true;
        return dataCount;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        onCreateDataViewHolderCalled = true;
        return createDataViewHolder;
    }

    @Override
    protected void onBindDataViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {
        onBindDataViewHolderCalled = true;
    }

    @Override
    protected int getDataViewLayoutResId(int dataPosition) {
        getDataViewLayoutResIdCalled = true;
        return dataViewLayoutResId;
    }

    @Override
    protected int getInfiniteScrollingLayoutResId() {
        return infiniteScrollingLayoutResId;
    }

    @Override
    protected int getTopContentInsetViewLayoutResId() {
        return topContentInsetViewLayoutResId;
    }

    @Override
    protected int getBottomContentInsetViewLayoutResId() {
        return bottomContentInsetViewLayoutResId;
    }
}
