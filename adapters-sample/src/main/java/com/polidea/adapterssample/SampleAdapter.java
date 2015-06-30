package com.polidea.adapterssample;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import com.polidea.adapters.BaseSectionRecyclerViewAdapter;
import com.polidea.adapters.IndexPath;
import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends BaseSectionRecyclerViewAdapter {

    private final SparseArray<List<String>> sectionData = new SparseArray<>();

    private final SparseArray<Boolean> sectionHeaderData = new SparseArray<>();

    public void addNewSection() {
        int section = sectionData.size();

        List<String> rowList = sectionData.get(section);
        if(rowList == null) {
            rowList = new ArrayList<>();
            sectionData.append(section, rowList);
        }
        for (int row = 0; row < 20; row++) {
            rowList.add("Section: " + section + ", Row: " + row);
        }
        sectionHeaderData.put(section, section % 2 == 0);

        notifyDataSetChanged();
    }

    @Override
    protected long getSectionItemViewId(int section) {
        return section;
    }

    @Override
    protected long getRowItemViewId(IndexPath indexPath) {
        return (indexPath.section + " " + indexPath.row).hashCode();
    }

    @Override
    protected boolean hasSectionHeader(int section) {
        return sectionHeaderData.get(section);
    }

    @Override
    protected int getSectionCount() {
        return sectionData.size();
    }

    @Override
    protected int getRowCount(int section) {
        return sectionData.get(section).size();
    }

    @Override
    protected int getSectionHeaderLayoutResId(int section) {
        return R.layout.item_header;
    }

    @Override
    protected int getRowLayoutResId(IndexPath indexPath) {
        return R.layout.item_row;
    }

    @Override
    protected int getInfiniteScrollingLayoutResId() {
        return R.layout.item_infinite_scrolling;
    }

    @Override
    protected RecyclerView.ViewHolder createHolderForLayoutResId(@LayoutRes int layoutResId, View itemView) {
        switch (layoutResId) {
            case R.layout.item_header:
                return new HeaderViewHolder(itemView);
            case R.layout.item_row:
                return new RowViewHolder(itemView);
        }
        throw new IllegalStateException("Cannot create view holder for layoutResId = " + layoutResId);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.textView.setText("Section " + section);
    }

    @Override
    protected void onBindRowViewHolder(RecyclerView.ViewHolder holder, IndexPath indexPath) {
        String data = sectionData.get(indexPath.section).get(indexPath.row);

        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        rowViewHolder.textView.setText(data);
    }
}
