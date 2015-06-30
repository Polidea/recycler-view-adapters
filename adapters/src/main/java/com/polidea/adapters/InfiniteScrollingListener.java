package com.polidea.adapters;

import android.support.v7.widget.RecyclerView;

public interface InfiniteScrollingListener<VH extends RecyclerView.ViewHolder> {

    void onInfiniteScrollingLoadMore(BaseRecyclerViewAdapter<VH> adapter);

    InfiniteScrollingListener NULL = new InfiniteScrollingListener() {
        @Override
        public void onInfiniteScrollingLoadMore(BaseRecyclerViewAdapter adapter) {

        }
    };
}
