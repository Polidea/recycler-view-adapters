package com.polidea.adapters;

import android.support.v7.widget.RecyclerView;

class InfiniteScrollingDataObserver extends RecyclerView.AdapterDataObserver {

    public interface Delegate {

        void refreshInfiniteScrollingItem();
    }

    public interface Provider {

        int getInfiniteScrollingPosition();
    }

    private final Delegate delegate;

    private final Provider provider;

    InfiniteScrollingDataObserver(Delegate delegate, Provider provider) {
        this.delegate = delegate;
        this.provider = provider;
    }

    //invalidate infinite scrolling holder, to force invoking onBindViewHolder when infinite view will be displayed again

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        delegate.refreshInfiniteScrollingItem();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        delegate.refreshInfiniteScrollingItem();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        delegate.refreshInfiniteScrollingItem();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        int lastPosition = provider.getInfiniteScrollingPosition();
        if (lastPosition >= positionStart && lastPosition < positionStart + itemCount) {
            return;
        }
        delegate.refreshInfiniteScrollingItem();
    }
}
