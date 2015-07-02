package com.polidea.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

public abstract class BaseSectionRecyclerViewAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    protected abstract boolean hasSectionHeader(int section);

    protected abstract int getSectionCount();

    protected abstract int getRowCount(int section);

    @LayoutRes
    protected abstract int getSectionHeaderLayoutResId(int section);

    @LayoutRes
    protected abstract int getRowLayoutResId(IndexPath indexPath);

    protected abstract void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section);

    protected abstract void onBindRowViewHolder(RecyclerView.ViewHolder holder, IndexPath indexPath);

    public IndexPath getIndexPathForDataPosition(int dataPosition) {
        int rows = 0;
        int section;
        for (section = 0; section < getSectionCount(); ++section) {
            int rowsInSection = getRowCount(section) + (hasSectionHeader(section) ? 1 : 0);
            if (rows + rowsInSection > dataPosition) {
                break;
            }
            rows += rowsInSection;
        }
        if (section == getSectionCount()) {
            return IndexPath.INVALID_PATH;
        }
        if (rows == dataPosition) {
            return new IndexPath(section, hasSectionHeader(section) ? IndexPath.SECTION_HEADER : 0);
        }
        rows += hasSectionHeader(section) ? 1 : 0;
        return new IndexPath(section, dataPosition - rows);
    }

    public int getDataPositionForIndexPath(IndexPath indexPath) {
        if (indexPath.section >= getSectionCount() || indexPath.row >= getRowCount(indexPath.section)) {
            return RecyclerView.NO_POSITION;
        }
        int position = 0;
        for (int section = 0; section < indexPath.section; ++section) {
            if (hasSectionHeader(section)) {
                ++position;
            }
            position += getRowCount(section);
        }
        if (indexPath.row == IndexPath.SECTION_HEADER) {
            return position;
        } else {
            return position + (hasSectionHeader(indexPath.section) ? 1 : 0) + indexPath.row;
        }
    }

    @Override
    protected final int getDataCount() {
        int itemCount = 0;
        int numberOfSections = getSectionCount();
        for (int section = 0; section < numberOfSections; section++) {
            if (hasSectionHeader(section)) {
                itemCount++;
            }
            itemCount += getRowCount(section);
        }
        return itemCount;
    }

    @Override
    protected final void onBindDataViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {
        IndexPath indexPath = getIndexPathForDataPosition(dataPosition);
        if (indexPath.row == IndexPath.SECTION_HEADER) {
            onBindSectionHeaderViewHolder(holder, indexPath.section);
        } else {
            onBindRowViewHolder(holder, indexPath);
        }
    }

    @Override
    protected final int getDataViewLayoutResId(int dataPosition) {
        IndexPath indexPath = getIndexPathForDataPosition(dataPosition);
        if (indexPath.isSection()) {
            return getSectionHeaderLayoutResId(indexPath.section);
        } else {
            return getRowLayoutResId(indexPath);
        }
    }

    @Override
    protected void configureFullSpanIfNeeded(RecyclerView.ViewHolder holder, int dataPosition) {
        super.configureFullSpanIfNeeded(holder, dataPosition);

        IndexPath indexPath = getIndexPathForDataPosition(dataPosition);
        if(indexPath.isSection()) {
            internalConfigureFullSpan(holder);
        }
    }

    @Override
    protected final long getDataItemViewId(int dataPosition) {
        IndexPath indexPath = getIndexPathForDataPosition(dataPosition);
        if (indexPath.isSection()) {
            return getSectionItemViewId(indexPath.section);
        } else {
            return getRowItemViewId(indexPath);
        }
    }

    protected long getSectionItemViewId(int section) {
        return C.NO_ID;
    }

    protected long getRowItemViewId(IndexPath indexPath) {
        return C.NO_ID;
    }

    public final void notifyRowChanged(IndexPath indexPath) {
        notifyDataChanged(getDataPositionForIndexPath(indexPath));
    }

    public final void notifyRowInserted(IndexPath indexPath) {
        notifyDataInserted(getDataPositionForIndexPath(indexPath));
    }

    public final void notifyRowMoved(IndexPath fromIndexPath, IndexPath toIndexPath) {
        notifyDataMoved(getDataPositionForIndexPath(fromIndexPath), getDataPositionForIndexPath(toIndexPath));
    }

    public final void notifyRowRemoved(IndexPath indexPath) {
        notifyDataRemoved(getDataPositionForIndexPath(indexPath));
    }
}
