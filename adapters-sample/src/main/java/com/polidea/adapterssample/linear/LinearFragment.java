package com.polidea.adapterssample.linear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.polidea.adapterssample.BaseFragment;
import com.polidea.adapterssample.R;

public class LinearFragment extends BaseFragment<LinearAdapter> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linear, container, false);
    }

    @Override
    protected LinearAdapter createAdapter() {
        return new LinearAdapter();
    }

    @Override
    protected void onConfigureRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void onConfigureAdapter(LinearAdapter adapter) {
        adapter.setTopContentInset(100);
        adapter.setBottomContentInset(100);
    }

    @Override
    protected void addDataToAdapter(LinearAdapter adapter) {
        adapter.addNewSection();
        adapter.setInfiniteScrollingEnabled(true);
    }
}
