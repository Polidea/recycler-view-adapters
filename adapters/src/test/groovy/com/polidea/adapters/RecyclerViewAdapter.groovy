package com.polidea.adapters

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView
import android.view.View

public class RecyclerViewAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    public int dataCount;

    boolean dataCoundCalled;

    RecyclerView.ViewHolder createHolder;

    boolean onCreateHolderCalled;

    boolean onBindDataViewHolderCalled;

    public int dataViewLayoutResId;

    boolean getDataViewLayoutResIdCalled;

    public int infiniteScrollingLayoutResId;

    public int topContentInsetViewLayoutResId;

    public int bottomContentInsetViewLayoutResId;

    Map<Integer, Long> dataItemViewIdMap = new HashMap<>();

    @Override
    public void setInfiniteScrollingEnabled(boolean enabled) {
        def field = BaseRecyclerViewAdapter.class.getDeclaredField("infiniteScrollingEnabled")
        field.setAccessible(true)
        field.setBoolean(this, enabled)
    }

    @Override
    protected int getDataCount() {
        dataCoundCalled = true;
        return dataCount;
    }

    @Override
    protected RecyclerView.ViewHolder createHolderForLayoutResId(@LayoutRes int layoutResId, View itemView) {
        onCreateHolderCalled = true;
        return createHolder;
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

    @Override
    protected long getDataItemViewId(int dataPosition) {
        return dataItemViewIdMap.get(dataPosition);
    }
}
