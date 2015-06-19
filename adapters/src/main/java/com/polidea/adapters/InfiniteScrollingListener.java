package com.polidea.adapters;

import android.support.v7.widget.RecyclerView;

public interface InfiniteScrollingListener<VH extends RecyclerView.ViewHolder> {

    void onLoadMore(BaseRecyclerViewAdapter<VH> adapter);

    InfiniteScrollingListener NULL = new InfiniteScrollingListener() {
        @Override
        public void onLoadMore(BaseRecyclerViewAdapter adapter) {

        }
    };
}
