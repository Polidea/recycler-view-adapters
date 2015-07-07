package com.polidea.adapterssample.linear;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import com.polidea.adapters.BaseStickySectionRecyclerViewAdapter;
import com.polidea.adapters.IndexPath;
import com.polidea.adapterssample.HeaderViewHolder;
import com.polidea.adapterssample.R;
import com.polidea.adapterssample.RowViewHolder;
import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends BaseStickySectionRecyclerViewAdapter {

    private final SparseArray<List<String>> sectionData = new SparseArray<>();

    public void addNewSection() {
        int section = sectionData.size();

        List<String> rowList = sectionData.get(section);
        if (rowList == null) {
            rowList = new ArrayList<>();
            sectionData.append(section, rowList);
        }
        for (int row = 0; row < 20; row++) {
            rowList.add("Section: " + section + ", Row: " + row);
        }

        notifyDataSetChanged();
    }

    @Override
    protected int getSectionHeaderLayoutResId() {
        return R.layout.item_header;
    }

    @Override
    protected long getSectionItemViewId(int section) {
        return section;
    }

    @Override
    protected long getRowItemViewId(IndexPath indexPath) {
        return (indexPath.getSection() + " " + indexPath.getRow()).hashCode();
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
        headerViewHolder.titleTextView.setText("Section " + section);
    }

    @Override
    protected void onBindRowViewHolder(RecyclerView.ViewHolder holder, IndexPath indexPath) {
        String data = sectionData.get(indexPath.getSection()).get(indexPath.getRow());

        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        rowViewHolder.titleTextView.setText(data);
    }
}
