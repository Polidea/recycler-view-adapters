package com.polidea.adapters;

import android.support.v7.widget.GridLayoutManager;

public abstract class BaseSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    protected abstract int getDataSpanSize(int dataPosition);

    private final BaseRecyclerViewAdapter adapter;

    private final int fullSpanSize;

    public BaseSpanSizeLookup(BaseRecyclerViewAdapter adapter, int fullSpanSize) {
        this.adapter = adapter;
        this.fullSpanSize = fullSpanSize;
    }

    @Override
    public final int getSpanSize(int position) {
        return adapter.isFullSpanItemPosition(position) ? fullSpanSize : getDataSpanSize(adapter.getDataPosition(position));
    }

    public BaseRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public int getFullSpanSize() {
        return fullSpanSize;
    }
}
