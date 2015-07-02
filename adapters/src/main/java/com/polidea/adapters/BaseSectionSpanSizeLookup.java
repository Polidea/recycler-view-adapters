package com.polidea.adapters;

public abstract class BaseSectionSpanSizeLookup extends BaseSpanSizeLookup {

    protected abstract int getRowSpanSize(IndexPath indexPath);

    public BaseSectionSpanSizeLookup(BaseSectionRecyclerViewAdapter adapter, int fullSpanSize) {
        super(adapter, fullSpanSize);
    }

    @Override
    protected int getDataSpanSize(int dataPosition) {
        IndexPath indexPath = getSectionAdapter().getIndexPathForDataPosition(dataPosition);
        return indexPath.isSection() ? getFullSpanSize() : getRowSpanSize(indexPath);
    }

    public BaseSectionRecyclerViewAdapter getSectionAdapter() {
        return (BaseSectionRecyclerViewAdapter) getAdapter();
    }
}
