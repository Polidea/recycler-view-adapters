package com.polidea.adapterssample.grid;

import android.support.annotation.LayoutRes;
import android.view.View;
import com.polidea.adapters.BaseRecyclerViewAdapter;
import com.polidea.adapterssample.R;
import com.polidea.adapterssample.RowViewHolder;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseRecyclerViewAdapter<RowViewHolder>{

    List<String> stringList = new ArrayList<String>() {{
        for (int i = 0; i < 100; i++) {
            add("Row " + i);
        }
    }};

    @Override
    protected int getDataCount() {
        return stringList.size();
    }

    @Override
    protected RowViewHolder createHolderForLayoutResId(@LayoutRes int layoutResId, View itemView) {
        return new RowViewHolder(itemView);
    }

    @Override
    protected void onBindDataViewHolder(RowViewHolder holder, int dataPosition) {
        holder.textView.setText(stringList.get(dataPosition));
    }

    @Override
    protected int getDataViewLayoutResId(int dataPosition) {
        return R.layout.item_row;
    }
}
