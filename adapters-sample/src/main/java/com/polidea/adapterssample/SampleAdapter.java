package com.polidea.adapterssample;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.polidea.adapters.BaseSectionRecyclerViewAdapter;
import com.polidea.adapters.IndexPath;

public class SampleAdapter extends BaseSectionRecyclerViewAdapter{

    @Override
    protected RecyclerView.ViewHolder createHolderForLayout(@LayoutRes int viewLayout, View itemView) {
        return null;
    }

    @Override
    protected boolean hasSectionHeader(int section) {
        return false;
    }

    @Override
    protected int getSectionCount() {
        return 0;
    }

    @Override
    protected int getRowCount(int section) {
        return 0;
    }

    @Override
    protected int getSectionHeaderLayoutResId(int section) {
        return 0;
    }

    @Override
    protected int getRowLayoutResId(IndexPath indexPath) {
        return 0;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindRowViewHolder(RecyclerView.ViewHolder holder, IndexPath indexPath) {

    }
}
