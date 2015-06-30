package com.polidea.adapterssample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }
}
