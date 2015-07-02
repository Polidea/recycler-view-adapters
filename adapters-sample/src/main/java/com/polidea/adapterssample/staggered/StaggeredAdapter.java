package com.polidea.adapterssample.staggered;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.polidea.adapters.BaseSectionRecyclerViewAdapter;
import com.polidea.adapters.IndexPath;
import com.polidea.adapterssample.HeaderViewHolder;
import com.polidea.adapterssample.R;
import com.polidea.adapterssample.RowViewHolder;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class StaggeredAdapter extends BaseSectionRecyclerViewAdapter {

    private List<String> stringList = new ArrayList<>();

    public void addDataPortion() {
        for (int i = 0; i < 20; i++) {
            int result = i % 3;
            if(result == 0) {
                stringList.add(Type.SMALL);
            } else if(result == 1) {
                stringList.add(Type.MEDIUM);
            } else {
                stringList.add(Type.LARGE);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    protected long getSectionItemViewId(int section) {
        return section;
    }

    @Override
    protected long getRowItemViewId(IndexPath indexPath) {
        return (indexPath.getRow() + " " + indexPath.getSection()).hashCode();
    }

    @Override
    protected boolean hasSectionHeader(int section) {
        return true;
    }

    @Override
    protected int getSectionCount() {
        return 1;
    }

    @Override
    protected int getRowCount(int section) {
        return stringList.size();
    }

    @Override
    protected int getSectionHeaderLayoutResId(int section) {
        return R.layout.item_header;
    }

    @Override
    protected int getRowLayoutResId(IndexPath indexPath) {
        String string = stringList.get(indexPath.getRow());
        switch (string) {
            case Type.SMALL:
                return R.layout.item_row_small;
            case Type.MEDIUM:
                return R.layout.item_row;
            case Type.LARGE:
                return R.layout.item_row_large;
        }
        throw new IllegalStateException("Layout for string '" + string + "' does not exist");
    }

    @Override
    protected void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.textView.setText("Section " + section);
    }

    @Override
    protected void onBindRowViewHolder(RecyclerView.ViewHolder holder, IndexPath indexPath) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        rowViewHolder.textView.setText(stringList.get(indexPath.getRow()));
    }

    @Override
    protected RecyclerView.ViewHolder createHolderForLayoutResId(@LayoutRes int layoutResId, View itemView) {
        switch (layoutResId) {
            case R.layout.item_header:
                return new HeaderViewHolder(itemView);
            case R.layout.item_row_small:
            case R.layout.item_row:
            case R.layout.item_row_large:
                return new RowViewHolder(itemView);
        }
        throw new IllegalStateException("Cannot create view holder for layoutResId = " + layoutResId);
    }

    @Override
    protected int getInfiniteScrollingLayoutResId() {
        return R.layout.item_infinite_scrolling;
    }

    @StringDef({
            Type.SMALL,
            Type.MEDIUM,
            Type.LARGE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        String SMALL = "SMALL";

        String MEDIUM = "MEDIUM";

        String LARGE = "LARGE";
    }
}
