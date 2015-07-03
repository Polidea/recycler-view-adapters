package com.polidea.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

public abstract class BaseStickySectionRecyclerViewAdapter extends BaseSectionRecyclerViewAdapter
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    @LayoutRes
    protected abstract int getSectionHeaderLayoutResId();

    public void configureStickyHeaderDecoration(RecyclerView recyclerView, final StickyRecyclerHeadersDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                itemDecoration.invalidateHeaders();
            }
        });

    }

    @Override
    protected long getSectionItemViewId(int section) {
        throw new IllegalStateException("You must override getSectionItemViewId to use Sticky Headers.");
    }

    @Override
    protected final boolean hasSectionHeader(int section) {
        return false;
    }

    @Override
    public long getHeaderId(int position) {
        int dataPosition = getDataPosition(position);
        if (!isCorrectDataPosition(dataPosition)) {
            return -1;
        }
        IndexPath indexPath = getIndexPathForDataPosition(dataPosition);
        if(indexPath.equals(IndexPath.INVALID_PATH)) {
            return - 1;
        }
        return getSectionItemViewId(indexPath.section);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        int layoutResId = getSectionHeaderLayoutResId();
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return createHolderForLayoutResId(layoutResId, view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int dataPosition = getDataPosition(position);
        if (!isCorrectDataPosition(dataPosition)) {
            return;
        }
        IndexPath indexPath = getIndexPathForDataPosition(dataPosition);
        if(indexPath.equals(IndexPath.INVALID_PATH)) {
           return;
        }
        onBindSectionHeaderViewHolder(viewHolder, indexPath.section);
    }

    @LayoutRes
    @Override
    protected final int getSectionHeaderLayoutResId(int section) {
        return getSectionHeaderLayoutResId();
    }
}
