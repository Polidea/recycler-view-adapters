package com.polidea.adapterssample.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.polidea.adapterssample.BaseFragment;
import com.polidea.adapterssample.R;

public class GridFragment extends BaseFragment<GridAdapter> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    protected GridAdapter createAdapter() {
        return new GridAdapter();
    }

    @Override
    protected void onConfigureRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    protected void onConfigureAdapter(GridAdapter adapter) {

    }

    @Override
    protected void addDataToAdapter(GridAdapter adapter) {

    }
}
