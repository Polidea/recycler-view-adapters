package com.polidea.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View

public class SectionRecyclerViewAdapter extends BaseSectionRecyclerViewAdapter {

    Map<Integer, Boolean> hasSectionHeaderMap = new HashMap<>();

    Map<Integer, Integer> sectionHeaderLayoutResIdMap = new HashMap<>();

    Map<IndexPath, Integer> rowLayoutResIdMap = new HashMap<>();

    Map<Integer, RecyclerView.ViewHolder> holderForLayoutResIdMap = new HashMap<>();

    int sectionCount;

    Map<Integer, Integer> rowCountMap = new HashMap<>();

    List<Integer> onBindSectionHeaderViewHolderSectionList = new ArrayList<>();

    List<IndexPath> onBindRowViewHolderIndexPathList = new ArrayList<>();

    Map<Integer, Integer> sectionItemViewIdMap = new HashMap<>();

    Map<IndexPath, Integer> rowItemViewIdMap = new HashMap<>()

    @Override
    protected boolean hasSectionHeader(int section) {
        return hasSectionHeaderMap.get(section);
    }

    @Override
    protected int getSectionCount() {
        return sectionCount;
    }

    @Override
    protected int getRowCount(int section) {
        return rowCountMap.get(section);
    }

    @Override
    protected int getSectionHeaderLayoutResId(int section) {
        return sectionHeaderLayoutResIdMap.get(section);
    }

    @Override
    protected int getRowLayoutResId(IndexPath indexPath) {
        return rowLayoutResIdMap.get(indexPath);
    }

    @Override
    protected RecyclerView.ViewHolder createHolderForLayoutResId(@LayoutRes int layoutResId, View itemView) {
        return holderForLayoutResIdMap.get(layoutResId);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        onBindSectionHeaderViewHolderSectionList.add(section);
    }

    @Override
    protected void onBindRowViewHolder(RecyclerView.ViewHolder holder, IndexPath indexPath) {
        onBindRowViewHolderIndexPathList.add(indexPath);
    }

    @Override
    protected long getSectionItemViewId(int section) {
        return sectionItemViewIdMap.get(section)
    }

    @Override
    protected long getRowItemViewId(IndexPath indexPath) {
        return rowItemViewIdMap.get(indexPath)
    }
}
