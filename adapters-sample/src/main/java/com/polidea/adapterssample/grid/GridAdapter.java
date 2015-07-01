package com.polidea.adapterssample.grid;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.polidea.adapters.BaseRecyclerViewAdapter;

public class GridAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder>{

    @Override
    protected int getDataCount() {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindDataViewHolder(RecyclerView.ViewHolder holder, int dataPosition) {

    }

    @Override
    protected int getDataViewLayoutResId(int dataPosition) {
        return 0;
    }
}
