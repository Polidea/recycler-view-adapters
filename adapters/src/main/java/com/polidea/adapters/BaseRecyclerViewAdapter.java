package com.polidea.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Extended RecyclerViewAdapter that contains few new features:
 * <ul>
 * <li>Infinite scrolling - it adds new item at the end of recycler view items.
 * To use this set listener via (@see setInfiniteScrollingListener(InfiniteScrollingListener)) and enable/disable infinite scrolling by calling (@see setInfiniteScrollingEnabled(enabled)
 * When user scrolls to end of the list adapter call (@see InfiniteScrollingListener.onInfiniteScrollingLoadMore(BaseRecyclerViewAdapter))</li>
 * <li>Top content inset - it adds new item at begin of recycle view items with specified height (in pixels)</li>
 * <li>Bottom content inset - it adds new item at end of recycle view items (after infinite scrolling item) with specified height (in pixels)</li>
 * </ul>
 * <p/>
 * Those features adds new items (it is especially important when we add top content inset), so to make position again 0-indexed it provide new dataPosition. Please use it for binding your data to adapter
 * <p/>
 * It adds also new notify methods for using with dataPosition.
 * <p/>
 * It uses resource id's for item view type
 *
 * @param <VH> Default ViewHolder class
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Returns total number of your data items
     *
     * @return Total number of your data items
     */
    protected abstract int getDataCount();

    /**
     * Creating view holder for specified layoutResId (view type)
     *
     * @param layoutResId Layout resource is for which to create view holder
     * @param itemView    View that is inflated from layoutResId
     * @return View holder for specified layoutResId
     */
    protected abstract VH createHolderForLayoutResId(@LayoutRes int layoutResId, View itemView);

    /**
     * Here you should bind your data to holder
     *
     * @param holder       Holder that was previously created in (@see createHolderForLayoutResId(int, View))
     * @param dataPosition Data position
     */
    protected abstract void onBindDataViewHolder(VH holder, int dataPosition);

    /**
     * Get layout resource id that should be used for specified dataPosition
     *
     * @param dataPosition Data position
     * @return Layout resource id
     */
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

    /**
     * Adds new item at begin of recycle view items.
     * Please call it before first data refresh on adapter happen. If you want to call it after this, remember to call (@see notifyDataSetChanged())
     *
     * @param topContentInset Top content inset in px
     */
    public void setTopContentInset(int topContentInset) {
        this.topContentInset = topContentInset;
    }

    public boolean isTopContentInsetSet() {
        return topContentInset != C.NO_VALUE;
    }

    public int getBottomContentInset() {
        return bottomContentInset;
    }

    /**
     * Adds new item at end of recycle view items.
     * Please call it before first data refresh on adapter happen. If you want to call it after this, remember to call (@see notifyDataSetChanged())
     *
     * @param bottomContentInset Bottom content inset in px
     */
    public void setBottomContentInset(int bottomContentInset) {
        this.bottomContentInset = bottomContentInset;
    }

    public boolean isBottomContentInsetSet() {
        return bottomContentInset != C.NO_VALUE;
    }

    /**
     * Adds/Removes item at end of recycle view items. Enable this when you have still some data to fetch and disable when there is no data to fetch.
     *
     * @param enabled
     */
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

    /**
     * Sets inifite scrolling listener that informs about moving to end of the list. In that case you should fetch next portion of data
     *
     * @param infiniteScrollingListener
     */
    public void setInfiniteScrollingListener(@NonNull InfiniteScrollingListener<VH> infiniteScrollingListener) {
        this.infiniteScrollingListener = infiniteScrollingListener;
    }

    /**
     * Get 0-indexed data position that should be used in your adapter
     *
     * @param adapterPosition Real position of item in adapter
     * @return 0-indexed data position
     */
    public int getDataPosition(int adapterPosition) {
        return isTopContentInsetSet() ? adapterPosition - 1 : adapterPosition;
    }

    /**
     * Get real position of item in adapter
     *
     * @param dataPosition Data position
     * @return Real position in adapter
     */
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

        View itemView = inflater.inflate(viewType, viewGroup, false);
        return createHolderForLayoutResId(viewType, itemView);
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
            configureSpanIfNeeded(viewHolder, dataPosition);

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

    /**
     * This method must be overriden when using infinite scrolling
     *
     * @return Layout res id for infinite scrolling item
     */
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

    /**
     * Configure span for specified dataPosition.
     * It should be only used when using StaggeredGridLayoutManager.
     * GridLayoutManager contains own methods for setting span size for specified position.
     */
    protected void configureSpanIfNeeded(VH holder, int dataPosition) {

    }

    /**
     * Get item view id for specified dataPosition
     *
     * @param dataPosition Data position
     * @return Id of item view
     */
    protected long getDataItemViewId(int dataPosition) {
        return C.NO_ID;
    }

    protected boolean isCorrectDataPosition(int dataPosition) {
        return dataPosition >= 0 && dataPosition < getDataCount();
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
