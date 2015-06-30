package com.polidea.adapterssample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.polidea.adapters.BaseRecyclerViewAdapter;
import com.polidea.adapters.InfiniteScrollingListener;

public class SampleActivity extends AppCompatActivity implements InfiniteScrollingListener<RecyclerView.ViewHolder> {

    private RecyclerView recyclerView;

    private SampleAdapter adapter;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        adapter = new SampleAdapter();
        adapter.setInfiniteScrollingListener(this);
        adapter.setTopContentInset(100);
        adapter.setBottomContentInset(100);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addDataToAdapter();
    }

    private void addDataToAdapter() {
        adapter.addNewSection();
        adapter.setInfiniteScrollingEnabled(true);
    }

    @Override
    public void onInfiniteScrollingLoadMore(BaseRecyclerViewAdapter<RecyclerView.ViewHolder> adapter) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addDataToAdapter();
            }
        }, 1500);
    }
}
