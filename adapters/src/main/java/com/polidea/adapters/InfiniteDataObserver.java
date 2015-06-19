package com.polidea.adapters;

import android.support.v7.widget.RecyclerView;

class InfiniteDataObserver extends RecyclerView.AdapterDataObserver {

    private final BaseRecyclerViewAdapter adapter;

    InfiniteDataObserver(BaseRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        //invalidate infinite scrolling holder, to force invoking onBindViewHolder when infinite view will be displayed again
        adapter.notifyItemChanged(adapter.getLastPosition());
    }
}
