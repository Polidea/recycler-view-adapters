package com.polidea.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected abstract int getDataCount();

    protected abstract VH onCreateDataViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindDataViewHolder(VH holder, int dataPosition);

    @LayoutRes
    protected abstract int getDataViewLayoutResId(int dataPosition);

    boolean infiniteScrollingEnabled;

    int topContentInset = C.NO_VALUE;

    int bottomContentInset = C.NO_VALUE;

    InfiniteScrollingListener<VH> infiniteScrollingListener = InfiniteScrollingListener.NULL;

    InfiniteDataObserver infiniteDataObserver;

    public int getTopContentInset() {
        return topContentInset;
    }

    public void setTopContentInset(int topContentInset) {
        this.topContentInset = topContentInset;
    }

    public boolean isTopContentInsetSet() {
        return topContentInset != C.NO_VALUE;
    }

    public int getBottomContentInset() {
        return bottomContentInset;
    }

    public void setBottomContentInset(int bottomContentInset) {
        this.bottomContentInset = bottomContentInset;
    }

    public boolean isBottomContentInsetSet() {
        return bottomContentInset != C.NO_VALUE;
    }

    public void setInfiniteScrollingEnabled(boolean enabled) {
        if (enabled == this.infiniteScrollingEnabled) {
            return;
        }
        int lastPosition = getLastPosition();
        this.infiniteScrollingEnabled = enabled;
        if (enabled) {
            infiniteDataObserver = new InfiniteDataObserver(this);
            registerAdapterDataObserver(infiniteDataObserver);
            notifyItemInserted(lastPosition);
        } else {
            notifyItemRemoved(lastPosition);
            unregisterAdapterDataObserver(infiniteDataObserver);
            infiniteDataObserver = null;
        }
    }

    public boolean isInfiniteScrollingEnabled() {
        return infiniteScrollingEnabled;
    }

    public void setInfiniteScrollingListener(@NonNull InfiniteScrollingListener<VH> infiniteScrollingListener) {
        this.infiniteScrollingListener = infiniteScrollingListener;
    }

    public int getDataPosition(int adapterPosition) {
        return isTopContentInsetSet() ? adapterPosition - 1 : adapterPosition;
    }

    public int getAdapterPosition(int dataPosition) {
        return isTopContentInsetSet() ? dataPosition + 1 : dataPosition;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (isInfiniteScrollingEnabled() && viewType == getInfiniteScrollingLayoutResId()) {
            View itemView = inflater.inflate(viewType, viewGroup, false);
            return createInfiniteScrollingHolder(itemView);
        } else if (isTopContentInsetSet() && viewType == getTopContentInsetViewLayoutResId()) {
            return createContentInsetViewHolderForInset(viewGroup, viewType, inflater, topContentInset);
        } else if (isBottomContentInsetSet() && viewType == getBottomContentInsetViewLayoutResId()) {
            return createContentInsetViewHolderForInset(viewGroup, viewType, inflater, bottomContentInset);
        }

        return onCreateDataViewHolder(viewGroup, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int dataPosition = getDataPosition(position);

        if (isFullSpanItemPosition(position)) {
            internalConfigureFullSpan(holder);
        }

        if (isInfiniteScrollingPosition(position)) {
            infiniteScrollingListener.onInfiniteScrollingLoadMore(this);
            bindInfiniteScrollingHolder(holder);
        } else if (!isTopContentInsetViewPosition(position) && !isBottomContentInsetViewPosition(position)) {
            VH viewHolder = (VH) holder;
            configureFullSpanIfNeeded(viewHolder, dataPosition);

            onBindDataViewHolder(viewHolder, dataPosition);
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (isInfiniteScrollingEnabled() && isInfiniteScrollingPosition(position)) {
            return getInfiniteScrollingLayoutResId();
        } else if (isTopContentInsetSet() && isTopContentInsetViewPosition(position)) {
            return getTopContentInsetViewLayoutResId();
        } else if (isBottomContentInsetSet() && isBottomContentInsetViewPosition(position)) {
            return getBottomContentInsetViewLayoutResId();
        } else {
            return getDataViewLayoutResId(getDataPosition(position));
        }
    }

    @Override
    public final long getItemId(int position) {
        int itemViewType = getItemViewType(position);
        if (isTopContentInsetSet() && itemViewType == getTopContentInsetViewLayoutResId()) {
            return C.TOP_CONTENT_INSET_ROW_ID;
        } else if (isInfiniteScrollingEnabled() && itemViewType == getInfiniteScrollingLayoutResId()) {
            return C.INFINITE_SCROLLING_ROW_ID;
        } else if (isBottomContentInsetSet() && itemViewType == getBottomContentInsetViewLayoutResId()) {
            return C.BOTTOM_CONTENT_INSET_ROW_ID;
        }
        return getDataItemViewId(getDataPosition(position));
    }

    @Override
    public final int getItemCount() {
        return getDataCount() + (infiniteScrollingEnabled ? 1 : 0) + (isTopContentInsetSet() ? 1 : 0) + (isBottomContentInsetSet() ? 1 : 0);
    }

    @LayoutRes
    protected int getInfiniteScrollingLayoutResId() {
        throw new IllegalStateException("You must override getInfiniteScrollingLayoutResId method when you are using infinite scrolling.");
    }

    @LayoutRes
    protected int getTopContentInsetViewLayoutResId() {
        return R.layout.rva_item_top_content_inset;
    }

    @LayoutRes
    protected int getBottomContentInsetViewLayoutResId() {
        return R.layout.rva_item_bottom_content_inset;
    }

    public final void notifyDataChanged(int dataPosition) {
        notifyItemChanged(getAdapterPosition(dataPosition));
    }

    public final void notifyDataRangeChanged(int dataPositionStart, int itemCount) {
        notifyDataRangeChanged(getAdapterPosition(dataPositionStart), itemCount);
    }

    public final void notifyDataInserted(int dataPosition) {
        notifyItemInserted(getAdapterPosition(dataPosition));
    }

    public final void notifyDataMoved(int fromDataPosition, int toDataPosition) {
        notifyItemMoved(getAdapterPosition(fromDataPosition), getAdapterPosition(toDataPosition));
    }

    public final void notifyDataRangeInserted(int dataPositionStart, int itemCount) {
        notifyItemRangeInserted(getAdapterPosition(dataPositionStart), itemCount);
    }

    public final void notifyDataRemoved(int position) {
        notifyItemRemoved(getAdapterPosition(position));
    }

    public final void notifyDataRangeRemoved(int positionStart, int itemCount) {
        notifyItemRangeRemoved(getAdapterPosition(positionStart), itemCount);
    }

    protected RecyclerView.ViewHolder createInfiniteScrollingHolder(View itemView) {
        return new EmptyViewHolder(itemView);
    }

    protected void bindInfiniteScrollingHolder(RecyclerView.ViewHolder holder) {

    }

    protected void configureFullSpanIfNeeded(VH holder, int position) {

    }

    protected long getDataItemViewId(int dataPosition) {
        return C.NO_ID;
    }

    boolean isInfiniteScrollingPosition(int position) {
        boolean bottomContentInsetSet = isBottomContentInsetSet();
        int lastPosition = getLastPosition();
        if (bottomContentInsetSet) {
            lastPosition--;
        }
        return infiniteScrollingEnabled && position == lastPosition;
    }

    boolean isTopContentInsetViewPosition(int position) {
        return isTopContentInsetSet() && position == 0;
    }

    boolean isBottomContentInsetViewPosition(int position) {
        return isBottomContentInsetSet() && position == getLastPosition();
    }

    boolean isFullSpanItemPosition(int position) {
        int itemViewType = getItemViewType(position);
        return (isTopContentInsetSet() && itemViewType == getTopContentInsetViewLayoutResId())
                || (isInfiniteScrollingEnabled() && itemViewType == getInfiniteScrollingLayoutResId())
                || (isBottomContentInsetSet() && itemViewType == getBottomContentInsetViewLayoutResId());
    }

    void internalConfigureFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams staggeredLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            staggeredLayoutParams.setFullSpan(true);
        }
    }

    RecyclerView.ViewHolder createContentInsetViewHolderForInset(ViewGroup viewGroup, int viewType, LayoutInflater inflater, int contentInset) {
        ViewGroup itemView = (ViewGroup) inflater.inflate(viewType, viewGroup, false);
        View contentInsetView = itemView.findViewById(R.id.v_item_content_inset);
        ViewGroup.LayoutParams layoutParams = contentInsetView.getLayoutParams();
        layoutParams.height = contentInset;
        contentInsetView.setLayoutParams(layoutParams);
        return new EmptyViewHolder(itemView);
    }

    int getLastPosition() {
        return getItemCount() - 1;
    }
}
