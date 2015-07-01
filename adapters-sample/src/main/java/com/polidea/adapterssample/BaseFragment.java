package com.polidea.adapterssample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.polidea.adapters.BaseRecyclerViewAdapter;
import com.polidea.adapters.InfiniteScrollingListener;

public abstract class BaseFragment<ADAPTER extends BaseRecyclerViewAdapter> extends Fragment implements InfiniteScrollingListener<RecyclerView.ViewHolder> {

    protected abstract ADAPTER createAdapter();

    protected abstract void onConfigureRecyclerView(RecyclerView recyclerView);

    protected abstract void onConfigureAdapter(ADAPTER adapter);

    protected abstract void addDataToAdapter(ADAPTER adapter);

    private RecyclerView recyclerView;

    private Handler handler = new Handler();

    private ADAPTER adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = createAdapter();
        adapter.setInfiniteScrollingListener(this);
        onConfigureAdapter(adapter);

        addDataToAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        onConfigureRecyclerView(recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        recyclerView.setAdapter(null);
    }

    @Override
    public void onInfiniteScrollingLoadMore(BaseRecyclerViewAdapter<RecyclerView.ViewHolder> adapter) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addDataToAdapter(BaseFragment.this.adapter);
            }
        }, 1500);
    }
}
