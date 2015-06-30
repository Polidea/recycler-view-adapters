package com.polidea.adapterssample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RowViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public RowViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }
}
