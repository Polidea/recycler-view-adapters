package com.polidea.adapterssample.staggered;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.polidea.adapterssample.BaseFragment;
import com.polidea.adapterssample.R;

public class StaggeredFragment extends BaseFragment<StaggeredAdapter> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staggered, container, false);
    }

    @Override
    protected StaggeredAdapter createAdapter() {
        return new StaggeredAdapter();
    }

    @Override
    protected void onConfigureRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    protected void onConfigureAdapter(StaggeredAdapter adapter) {
        adapter.setBottomContentInset(50);
        adapter.setTopContentInset(50);
    }

    @Override
    protected void addDataToAdapter(StaggeredAdapter adapter) {
        adapter.addDataPortion();
        adapter.setInfiniteScrollingEnabled(true);
    }
}
